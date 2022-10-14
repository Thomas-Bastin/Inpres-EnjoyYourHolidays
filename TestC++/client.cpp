#include <unistd.h>
#include <stdlib.h>
#include <iostream>
#include <fstream>
#include <string>

#include "../NetworkLib/ClientSocket/clientsocket.hpp"

using namespace std;

int main(){
    timespec t;
    t.tv_nsec = 0;
    t.tv_sec = 5;
    string f;

    try{
        ClientSocket Csock = ClientSocket("192.168.1.61:50001");
        Csock.SendString("Hello");
        Csock.SendString("Hello2");
        Csock.SendString("LOGOUT");
    }
    catch(const char * t){
        cerr << t << endl;
    }
    catch(string t){
        cerr << t << endl;
    }
    catch(...){
        cerr << "Unknown Exception"<<endl;
    }

    cout << "| Gestion Matériel" << endl;
    cout << "| " << endl;
    cout << "| \t1. Login" << endl;
    cout << "| \t1. Logout" << endl;
    cout << "| " << endl;
    cout << "| \t0. Quitter" << endl;
}

//#{}