#include <unistd.h>
#include <stdlib.h>
#include <iostream>
#include <fstream>
#include <string>

#include "./ClientSocket/clientsocket.hpp"

using namespace std;

int main(){
    string ssocket;
    cout << "Entrez le socket " << endl;
    cin >> ssocket; cin.get();

    try{
        ClientSocket Csock = ClientSocket(ssocket);
        Csock.SendString("Hello");
        cerr << "Send Hello"<<endl;

        
        cerr << Csock.ReceiveString() << endl; 
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
}

//#{}