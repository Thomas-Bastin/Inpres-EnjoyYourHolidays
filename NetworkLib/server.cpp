#include <unistd.h>
#include <stdlib.h>
#include <iostream>
#include <fstream>

#include "ListenSocket/listensocket.hpp"

using namespace std;

int main(){
    ListenSocket listen;
    
    try{
        listen = ListenSocket(50001);
    }
    catch(const char * msg){
        cerr << msg << endl;
    }
    catch(string t){
        cerr << t << endl;
    }

    try{
        listen.Accept();
    }
    catch(const char * msg){
        cerr << msg << endl;
    }
    catch(string t){
        cerr << t << endl;
    }

    try{
        cerr <<"Serveur.Receive: " << ListenSocket::services[0].ReceiveString() << endl;
        ListenSocket::services[0].SendString("Test");
    }
    catch(const char * msg){
        cerr << msg << endl;
    }
    catch(string t){
        cerr << t << endl;
    }
    
    cerr << "Serveur: Fermeture"<< endl;
}

//#{}