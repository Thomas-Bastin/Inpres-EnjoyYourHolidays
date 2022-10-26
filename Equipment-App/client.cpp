#include <unistd.h>
#include <stdlib.h>
#include <iostream>
#include <fstream>
#include <string>

#include "../NetworkLib/ClientSocket/clientsocket.hpp"
#include "../UtilityLib/utilitylib.hpp"
#include "../Commande/commande.hpp"

using namespace std;

bool Login();
void menu1();
void AddAction();
int checkLoginTrue(string, string);

ClientSocket Csock;


int main(){
    timespec t;
    t.tv_nsec = 0;
    t.tv_sec = 5;
    string f;

    DateFormat format = DateFormat("dd-mm-yyyy");
    Date::setFormat(format);

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
                cout << "Login raté après 3 Tentatives" << endl;
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
    system("clear");
    int choix;
    do{
        cout << "|------------------------------------------------|" << endl;
        cout << "| Gestion Matériel :                             |" << endl;
        cout << "| ------------------                             |" << endl;
        cout << "|                                                |" << endl;
        cout << "| 1. Ajout Action Matériel                       |" << endl; //To SubMenu create new action
        cout << "| 2. Listing Actions Commandées                  |" << endl; //Render the action list
        cout << "| 3. Supression Actions Commandées               |" << endl; //Deletion action
        cout << "| 4. Commande Matériel                           |" << endl; //To SubMenu create a new equipment (new type or existing one)
        cout << "|                                                |" << endl;
        cout << "|------------------------------------------------|" << endl;
        cout << "| 0. Quitter                                     |" << endl;    
        cout << "|------------------------------------------------|" << endl;
        cin >> choix; cin.get();

        switch (choix)
        {
            case 1:
                AddAction();
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
        cout << "\nEntrez votre login:" << endl;
        cin >> login; cin.get();

        cout << "\nEntrez votre Mot de Passe:" << endl;
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





//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


void AddAction(){
    int GlobalTry = false;
    bool fail = true;

    do{
        string todo;
        Actions act;
        string TypeKey;
        string Label;
        string date;
        Date wishdate;

        int CounTry = 0;
        do{
            cout << "Entrez l'action a réalisé (livraison, réparation, déclassement):\t";
            cin >> todo;
            cin.get();

            cerr << "Entrée Utilisateur: " << todo << endl;

            CounTry ++;

            if(CounTry == 3){
                cout << "Trop de tentative ratée..." << endl;
                return;
            }

        }while(todo.compare("livraison")!=0 && todo.compare("réparation")!=0 && todo.compare("déclassement")!=0);

        if(todo.compare("livraison") == 0) act = Actions::delivery;
        if(todo.compare("réparation") == 0) act = Actions::repair;
        if(todo.compare("déclassement") == 0) act = Actions::downgrade;


        cout << "Entrez le Type de matériel:\t";
        cin>>TypeKey;
        cin.get();
        cerr << "Entrée Utilisateur: " << TypeKey << endl;

        cout << "Entrez le nom (unique) du matériel:\t";
        cin>>Label;
        cin.get();
        cerr << "Entrée Utilisateur: " << Label << endl;
        
        bool ValidationDate;
        int CounTry = 0;
        do{
            cout << "Entrez la date (jj-mm-yyyy):\t";
            cin>>date;
            cin.get();
            CounTry ++;

            cerr << "Entrée Utilisateur: " << date << endl;

            if(CounTry == 3){
                cout << "Trop de tentative ratée..." << endl;
                return;
            }

            ValidationDate = true;
            try{
                wishdate = Date(date.c_str());
            }
            catch(invalid_argument error){
                ValidationDate = false;
            }
            catch(domain_error error){
                ValidationDate = false;
            }
            catch(out_of_range error){
                ValidationDate = false;
            }

        }while(ValidationDate == false);

        stringstream message;
        message << "HMAT#" << act << "#" << TypeKey << "#" << Label << "#" << wishdate;
        Csock.SendString(message.str());


        string recv = Csock.ReceiveString();
        vector<string> msgrecv = UtilityLib::getTokens(recv, L"#");

        if(msgrecv.size() == 0){
            if(recv.compare("TIMEOUT") == 0){
                cout << "Fin de connection avec le serveur..." << endl;
                cerr << "TIMEOUT in Commande Actions on equipment" << endl;
                Csock.close();
                exit(120);
            }
            else{
                cerr << "Unknown Message in Commande Actions on equipment" << endl;
                Csock.close();
                exit(125);
            }
        }

        if(msgrecv[0].compare("HMAT") == 0){
            if(msgrecv[1].compare("ok")){
                cout << "Action Enregistré à l'id: " << msgrecv[2] << endl;
                cerr << "Action Error: " << msgrecv[2] << endl;
                fail = false;
            }
            else if(msgrecv[1].compare("")){
                cout << "Action non réussie car " << msgrecv[2] << endl;
                cerr << "Action Error: " << msgrecv[2] << endl;
                fail = true;
            }
            else{
                cerr << "Unknown Parameters in HMAT from server" << endl;
                fail = true;
            }
        }

        GlobalTry++;
    }while(GlobalTry < 3 && fail == true);
}
//#{}