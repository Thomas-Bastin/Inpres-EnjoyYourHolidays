#include <unistd.h>
#include <stdlib.h>
#include <iostream>
#include <fstream>

#include <signal.h>
#include <threads.h>

#include "../NetworkLib/ListenSocket/listensocket.hpp"
#include "../MyLibThread_POSIX/mylibthread_POSIX.h"
#include "../UtilityLib/utilitylib.hpp"

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


// Threads
void ServiceThread(void);

// Global Variables
ListenSocket lis;


//MutexVarCond:
pthread_mutex_t mutexService;
pthread_cond_t condService;


///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
int main(){
    initSig();
    initMut();
    initCond();
    initServices();

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

            if(message.size() == 0){
                cerr << "Untokenized message" << endl;
                exit(100);
            }
            
            if(message[0].compare("LOGIN") == 0){
                int check = Login(message);

                if(check == 0){
                    socket.SendString("LOGIN#true");
                }
                else if(check == 1){
                    socket.SendString("LOGIN#false#password");
                }
                else{
                    socket.SendString("LOGIN#false#login");
                }
            }

            

            if(message[0].compare("LOGOUT") == 0){
                break;
            }
            if(message[0].compare("TIMEOUT") == 0){
                break;
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
    //CheckFile

    //return Réponse
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
    lis.close();
    cerr<<"\nSIGINT Received"<<endl;
    exit(0);
}