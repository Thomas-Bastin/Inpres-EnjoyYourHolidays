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

#define MAXCLIENT 5

using namespace std;

// UtilityFunctions
void initSig(void);
void initMut(void);
void initCond(void);
void initServices(void);
void SIG_INT(int sig_num);

//Fonctionnalitée:
int Login(vector<string> message);

int addAction(vector<string> message);

// Threads
void ServiceThread(void);

// Global Variables
ListenSocket lis;
ifstream Readlogin;

//MutexVarCond:
pthread_mutex_t mutexLoginFile;
pthread_mutex_t mutexService;
pthread_cond_t condService;



const char LoginPath[] = "./login.csv";


enum State
{
    NotLogged=0,
    Logged=1,
};

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
int main(){
    initSig();
    initMut();
    initCond();
    initServices();

    DateFormat format = DateFormat("dd-mm-yyyy");
    Date::setFormat(format);

    try{
        lis = ListenSocket(50001);
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
// Threads Services:
// Gère un client de sa connexion a sa déconnexion.
void ServiceThread(void){
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
                cerr << "TIMEOUT catch, end of connexion" << endl;
                break;
            }
            if(message[0].compare("TIMEOUT") == 0){
                cerr << "TIMEOUT catch, end of connexion" << endl;
                break;
            }


            if(status == Logged){
                if(msg.compare("LOGOUT") == 0){
                    cerr << "LOGOUT catch, end of connexion" << endl;
                    break;
                }
                if(message[0].compare("LOGOUT") == 0){
                    cerr << "LOGOUT catch, end of connexion" << endl;
                    break;
                }


                //OtherCommands
                if(message[0].compare("HMAT") == 0){
                    //Ask Action on equipment (livraison, réparation, déclassement)
                    // HMAT#action#matériel#date souhaitée
                    int retval = addAction(message);

                    // ACK:	HMAT#ok#id
                    if(retval > 0){
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
                    //List all asked action during this session

                    //if list size == 0, send #nocmd 

                    //List of actions (object)
                    //Put all actions in string
                    //Send actions

                    // ACK:  	LISTCMD#action1$action2$action3$...
                    //          LISTCMD#nocmd
                }

                if(message[0].compare("CHMAT") == 0){
                    //Delete an asked action during this session

                    // Client:  CHMAT#idaction
                    // ACK:	    CHMAT#ok
                    //          CHMAT#ko#reason
                }

                if(message[0].compare("ASKMAT") == 0){
                    //Command new equipment with an already created category or not

                    // Client:	ASKMAT#type#libellé#marque#prix#accessoire1$accessoire2$accessoire3$... 

                    // ACK:	    ASKMAT#ok#id
                    //          ASKMAT#ko#reason
                    

                    //Matériel::Type(nomfichier) ID, Libellé, état(OK,KO,DES), Marque, Prix, Acessoires
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

        
        cerr << "Write: " << ListenSocket::Write << ", Read: " << ListenSocket::Read << endl;
        cerr << "Socket: "<< endl;
        for(int i = 0 ; i<ListenSocket::services.size() ; i++){
            cerr << ListenSocket::services[i] << "\t";
        }
        cerr << endl;

        //EndOfConnection retour dans VarCond
    }
}
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
// Add Action
// Try to add an action to the list
int addAction(vector<string> message){

}
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

    for(int i = 0; i<lis.services.size() ; i++){
        lis.services[i].close();
    }
    lis.close();
    cerr<<"\nSIGINT Received"<<endl;
    exit(0);
}