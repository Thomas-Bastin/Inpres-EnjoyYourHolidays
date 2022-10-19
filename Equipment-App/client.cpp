#include <unistd.h>
#include <stdlib.h>
#include <iostream>
#include <fstream>
#include <string>

#include "../NetworkLib/ClientSocket/clientsocket.hpp"
#include "../UtilityLib/utilitylib.hpp"

using namespace std;

bool login();
void menu1();
int checkLoginTrue(string, string);

ClientSocket Csock;


int main(){
    timespec t;
    t.tv_nsec = 0;
    t.tv_sec = 5;
    string f;

    try{
        Csock = ClientSocket("192.168.1.61:50001");
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
    
    try{
        while(true){
            if(Login()){
                cerr << "Login réussi" << endl;
            }
            else{
                cerr << "Login raté après 3 Tentatives" << endl;
                Csock.close();
                exit(3);
            }

            menu1();
        }
    }
    catch(const char * t){
        cerr << t << endl;
        exit(1);
    }
    catch(string t){
        cerr << t << endl;
        exit(1);
    }
    catch(...){
        cerr << "Unknown Exception"<<endl;
        exit(1);
    }   

    exit(0);
}

void menu1(){
    system("cls");
    int choix;
    do{
        cout << "|------------------------------------------------|" << endl;
        cout << "| Gestion Matériel :                             |" << endl;
        cout << "| ------------------                             |" << endl;
        cout << "|                                                |" << endl;
        cout << "| 1. ToDo                                        |" << endl;
        cout << "| 2. ToDo                                        |" << endl;
        cout << "| 3. ToDo                                        |" << endl;
        cout << "| 4. ToDo                                        |" << endl;
        cout << "|                                                |" << endl;
        cout << "|------------------------------------------------|" << endl;
        cout << "| 0. Quitter                                     |" << endl;    
        cout << "|------------------------------------------------|" << endl;
        cin >> choix; cin.get();

        switch (choix)
        {
            case 1:
                /* code */
            break;

            case 2:
                /* code */
            break;
            
            case 3:
                /* code */
            break;
            
            case 4:
                /* code */
            break;


            case 0:
                cout << "Aurevoir :-)"<<endl;
                return;
            break;

            default:
                cout << "Choix Inconnus" << endl;
            break;
        }
        UtilityLib::WaitEnterIsPressed();
    }while(true);
}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

bool Login(){
    string login;
    string password;
    int valid;
    int tentativeCounter;


    valid = 2;
    tentativeCounter = 0;
    do{
        cout << "\n\n Entrez votre login:" << endl;
        cin >> login; cin.get();

        cout << "\n\n Entrez votre Mot de Passe:" << endl;
        cin >> password; cin.get();

        valid = checkLoginTrue(login, password);

        if(valid == 1){
            cout << "Le Mot de Passe est incorrecte" << endl;
        }
        if(valid == 2){
            cout << "Le Login est incorrecte" << endl;
        }

        tentativeCounter++;
    }while(valid != 0 && tentativeCounter < 3);

    if(valid == 0) return true;
    else return false;
}

int checkLoginTrue(string login, string password){
    vector<string> response;

    //Envoie message LOGIN
    stringstream t;
    t << "LOGIN#" << login << "#" << password;
    Csock.SendString(t.str().c_str());

    //Attente ACK
    response = UtilityLib::getTokens(Csock.ReceiveString(), L"#");

    if(response.size() != 2){
        cerr << "Un message non attendus a été récupérer" << endl;
        exit(0);
    }

    if(response[0].compare("LOGIN") != 0){
        cerr << "Un message non attendus a été récupérer" << endl;
        exit(0);
    }


    if(response[1].compare("true") == 0){
        return 0;
    }
    else if(response[1].compare("false") == 0){
        if(response[2].compare("password") == 0){
            return 1;
        }
        else{
            return 2;
        }
    }
    else{
        cerr << "Un message non attendus a été récupérer" << endl;
        exit(0);
    }
}
//#{}