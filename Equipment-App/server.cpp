#include <unistd.h>
#include <stdlib.h>
#include <iostream>
#include <fstream>

#include <signal.h>
#include <threads.h>

#include "../NetworkLib/ListenSocket/listensocket.hpp"
#include "../MyLibThread_POSIX/mylibthread_POSIX.h"
#include "../UtilityLib/utilitylib.hpp"
#include "../UtilityLib/date.hpp"

#include "../AccessMaterial/accessmaterial.hpp"
#include "../Commande/commande.hpp"
#include "../Commande/equipment.hpp"


using namespace std;

// UtilityFunctions
void initSig(void);
void initMut(void);
void initCond(void);
void initServices(void);
void SIG_INT(int sig_num);

//Fonctionnalitée:
int Login(vector<string> message);

// Threads
void ServiceThread(void);

// Global Variables
ListenSocket lis;
ifstream Readlogin;

//MutexVarCond:
pthread_mutex_t mutexLoginFile;
pthread_mutex_t mutexService;
pthread_mutex_t mutexDB;
pthread_cond_t condService;


//ConfigVar will be read on configfile
int const port = 50001;
int const MAXCLIENT = 5;
string const LoginPath = "./login.csv";
string AccessMaterial::MaterialDirPath = "./DB/";
string AccessMaterial::ActionFilePath = "./Actions.csv";
//... /!\ CommandePath, EquipmentDirPath

enum State
{
    NotLogged=0,
    Logged=1,
};

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


int main(){
    initSig();
    initMut();
    initCond();
    initServices();

    DateFormat format = DateFormat("dd-mm-yyyy");
    Date::setFormat(format);

    try{
        lis = ListenSocket(port);
        while(lis.Accept()){
            ListenSocket::Write++;
            condSig(&condService);
        }
    }
    catch(const char * msg){
        cerr << msg << endl;
        exit(1);
    }
    catch(string t){
        cerr << t << endl;
        exit(1);
    }

    cerr << "Serveur: Unhandled Error..."<< endl;
    lis.close();

    return 127;
}


///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


// Threads Services:
// Gère un client de sa connexion a sa déconnexion.
void ServiceThread(void){
    DateFormat format = DateFormat("dd-mm-yyyy");
    Date::setFormat(format);

    int LocalIndex;
    ServiceSocket socket;
    string msg;
    vector<string> message;
    int test = 0;
    State status = NotLogged;



    while(true){

        mLock(&mutexService);
        while( ListenSocket::Read >= ListenSocket::Write ){
            pthread_cond_wait(&condService, &mutexService);
        }

        //Est Réveillé par VarCond
        //On récupère le socket grace a l'index de lecture
        //RelacheMutex

        socket = ListenSocket::services[ListenSocket::Read];        
        ListenSocket::Read++;
        mUnLock(&mutexService);


        while(true){
            msg = socket.ReceiveString();
            cerr << "ReceptionMessage: " << msg << endl;
            message = UtilityLib::getTokens(msg, L"#");


            if(msg.compare("TIMEOUT") == 0){
                cerr << "TIMEOUT, end of connexion" << endl;
                break;
            }
            if(message[0].compare("TIMEOUT") == 0){
                cerr << "TIMEOUT, end of connexion" << endl;
                status = State::NotLogged;
                break;
            }


            if(status == Logged){
                if(msg.compare("LOGOUT") == 0){
                    cerr << "LOGOUT, end of connexion" << endl;
                    status = State::NotLogged;
                    continue;
                }
                if(message[0].compare("LOGOUT") == 0){
                    cerr << "LOGOUT, end of connexion" << endl;
                    status = State::NotLogged;
                    continue;
                }


                //OtherCommands
                if(message[0].compare("HMAT") == 0){
                    //Ask Action on equipment (livraison, réparation, déclassement)
                    // HMAT#action#matériel#date souhaitée

                    mLock(&mutexDB);
                    vector<Commande> tmp = AccessMaterial::getAllActions();
                    
                    for(int i = 0; i<tmp.size() ; i++){
                        if(Commande::idCount < tmp[i].getId()){
                            Commande::idCount = tmp[i].getId();
                        }
                    }

                    int retval = AccessMaterial::addAction(Commande(message));
                    mUnLock(&mutexDB);

                    // ACK:	HMAT#ok#id
                    if(retval >= 0){
                        stringstream m;
                        m << "HMAT#ok#"<< retval;
                        socket.SendString(m.str());
                    }
                    //      HMAT#ko#reason
                    else{
                        if(retval == -1){
                            stringstream m;
                            m << "HMAT#ko#"<< "L'Action existe déjà";
                            socket.SendString(m.str());
                        }
                        if(retval == -2){
                            stringstream m;
                            m << "HMAT#ko#"<< "Action ne peut être effectué, car le produit n'est pas encore commandé";
                            socket.SendString(m.str());
                        }
                        if(retval == -3){
                            stringstream m;
                            m << "HMAT#ko#"<< "Le matériel référencier (clé,label) n'existe pas";
                            socket.SendString(m.str());
                        }
                    }
                    
                    
                }

                if(message[0].compare("LISTCMD") == 0){
                    stringstream s;

                    mLock(&mutexDB);
                    vector<Commande> tmp = AccessMaterial::getAllActions();
                    mUnLock(&mutexDB);
                    
                    if(tmp.size() == 0){
                        socket.SendString("LISTCMD#nocmd");
                        continue;
                    }

                    s << "LISTCMD#";

                    for(int i = 0; i<tmp.size() ; i++){
                        if(i == tmp.size()-1){
                            s << tmp[i];
                        }
                        else{
                            s << tmp[i] << "$";
                        }
                    }

                    socket.SendString(s.str());
                }

                if(message[0].compare("CHMAT") == 0){
                    //Delete an asked action during this session
                    int id = stoi(message[1]);

                    mLock(&mutexDB);
                    int retval = AccessMaterial::removeAction(id);
                    mUnLock(&mutexDB);

                    if(retval == 0){
                        socket.SendString("CHMAT#ok");
                    }
                    else if(retval == 1){
                        socket.SendString("CHMAT#ko#notfound");
                    }
                    else{
                        socket.SendString("CHMAT#ko#unkownerror");
                    }
                }

                if(message[0].compare("ASKMAT") == 0){
                    //Command new equipment with an already created category or not
                    mLock(&mutexDB);
                    int retval = AccessMaterial::addMaterial(message[1], Equipment(message));
                    mUnLock(&mutexDB);

                    if(retval == 0){
                        socket.SendString("ASKMAT#ok");
                    }
                    else if(retval == -1){
                        socket.SendString("ASKMAT#ko#Le Matériel existe déjà dans cette catégorie.");
                    }
                }
            }          
            

            if(status != Logged){
                if(message[0].compare("LOGIN") == 0){
                    int check = Login(message);

                    if(check == 0){
                        socket.SendString("LOGIN#true");
                        status = Logged;
                    }
                    else if(check == 1){
                        socket.SendString("LOGIN#false#password");
                    }
                    else if(check == 2){
                        socket.SendString("LOGIN#false#login");
                    }
                    else{
                        socket.SendString("LOGIN#false#unkown");
                    }
                }
            }
        }
        socket.close();
        //EndOfConnection retour dans VarCond
    }
}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


//Login Management:
int Login(vector<string> message){
    vector<string> TokenLine;
    int status = 2;


    mLock(&mutexLoginFile);
    Readlogin.open(LoginPath);
    
    if(Readlogin.fail()){
        cerr << "LoginFile not exist at this path" << endl;
        ofstream Writelogin;
        Writelogin.open(LoginPath);
        Writelogin << "thomas;abc" << endl;
        Writelogin << "Wagner;abc" << endl;
        Writelogin.close();

        Readlogin.open(LoginPath);
    }


    for(string line; getline(Readlogin, line);) 
    {
        TokenLine = UtilityLib::getTokens(line, L";");

        if(message[1].compare(TokenLine[0]) == 0){
            status = 1;
            cerr<< "Login found in file" << endl;

            if(message[2].compare(TokenLine[1]) == 0){
                status = 0;
                cerr<< "Good PassWord" << endl;
                break;
            }
            else cerr << "WrongPassWord" << endl;
        }
    }
    Readlogin.close();
    mUnLock(&mutexLoginFile);

    if(status == 2) cerr << "Login not found" << endl;

    return status;
}


///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

// UtilityFunctions
void initServices(void){
    pthread_t handler;

    for(int i = 0; i<MAXCLIENT ; i++){
        pthread_create(&handler, NULL, (void* (*)(void*))ServiceThread, NULL);
        pthread_detach(handler);
    }   
}


void initMut(void){
    int error;

    // Initialisation mutexService
    if((error = mInitDef(&mutexService)) != 0){
        cerr << "(SERVEUR " << getTid() << ") Erreur Initialisation mutexService: "<<error<<endl;
        exit(2);
    }

    // Initialisation mutexService
    if((error = mInitDef(&mutexLoginFile)) != 0){
        cerr << "(SERVEUR " << getTid() << ") Erreur Initialisation mutexService: "<<error<<endl;
        exit(5);
    }

    // Initialisation mutexService
    if((error = mInitDef(&mutexDB)) != 0){
        cerr << "(SERVEUR " << getTid() << ") Erreur Initialisation mutexService: "<<error<<endl;
        exit(4);
    }
}

void initCond(void){
    int error;

    // Init Var Condition
    if((error = pthread_cond_init(&condService, NULL)) != 0){
        cerr << "(SERVEUR " << getTid() << ") Erreur Initialisation condService: "<<error<<endl;
        exit(2);
    }
}


void initSig(void){
    /*SIGINT*/
    sigset_t mask;
    sigfillset(&mask);
    sigdelset(&mask, SIGINT);
    sigprocmask(SIG_SETMASK, &mask, NULL);

    struct sigaction signal_action;                /* define table */
    signal_action.sa_handler = SIG_INT;   /* insert handler function */
    signal_action.sa_flags = 0;                    /* init the flags field */
    sigemptyset( &signal_action.sa_mask );     /* are no masked interrupts */
    sigaction( SIGINT, &signal_action, NULL ); /* install the signal_action */
}

void SIG_INT(int sig_num){

    for(int i = ListenSocket::Read ; i<lis.services.size() ; i++){
        lis.services[i].close();
    }

    lis.close();
    cerr<<"\nSIGINT Received"<<endl;
    exit(0);
}