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

    try{
        listen.Accept();
    }
    catch(const char * msg){
        cerr << msg << endl;
    }

    cerr << "Serveur: Fermeture"<< endl;
}

//#{}