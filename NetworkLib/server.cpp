#include <unistd.h>
#include <stdlib.h>
#include <iostream>
#include <fstream>

#include <signal.h>

#include "ListenSocket/listensocket.hpp"

using namespace std;
void catch_int(int sig_num);

ListenSocket lis;

int main(){
    /*SIGINT*/
    sigset_t mask;
    sigfillset(&mask);
    sigdelset(&mask, SIGINT);
    sigprocmask(SIG_SETMASK, &mask, NULL);

    struct sigaction signal_action;                /* define table */
    signal_action.sa_handler = catch_int;   /* insert handler function */
    signal_action.sa_flags = 0;                    /* init the flags field */
    sigemptyset( &signal_action.sa_mask );     /* are no masked interrupts */
    sigaction( SIGINT, &signal_action, NULL ); /* install the signal_action */

    
    try{
        lis = ListenSocket(50001);
    }
    catch(const char * msg){
        cerr << msg << endl;
        exit(1);
    }
    catch(string t){
        cerr << t << endl;
        exit(1);
    }

    try{
        lis.Accept();
    }
    catch(const char * msg){
        cerr << msg << endl;
        exit(2);
    }
    catch(string t){
        cerr << t << endl;
        exit(2);
    }

    try{
        string tmp;
        tmp = ListenSocket::services[0].ReceiveString();
        cout <<"Serveur.Receive: " << tmp <<endl;

        ListenSocket::services[0].SendString("ACK");
    }
    catch(const char * msg){
        cerr << msg << endl;
        exit(3);
    }
    catch(string t){
        cerr << t << endl;
        exit(3);
    }
    
    cerr << "Serveur: Fermeture"<< endl;

    return 0;
}



void catch_int(int sig_num){
    lis.close();
    cerr<<"\nSIGINT Received"<<endl;
    exit(0);
}

//#{}