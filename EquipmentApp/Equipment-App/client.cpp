#include <unistd.h>
#include <stdlib.h>
#include <iostream>
#include <fstream>
#include <string>
#include <signal.h>

#include "../NetworkLib/ClientSocket/clientsocket.hpp"
#include "../UtilityLib/utilitylib.hpp"
#include "../Commande/commande.hpp"
#include "../Commande/equipment.hpp"

using namespace std;


bool Login();
int checkLoginTrue(string, string);

void menu1();

void AddAction();
void ListAction();
void DeleteAction();
void AddEquipment();



void SIG_INT(int sig_num);
void initSig(void);
void initConfig();


ClientSocket Csock;

string socketName;

int main(){
    initConfig();

    cerr<<socketName<<endl;

    initSig();

    timespec t;
    t.tv_nsec = 0;
    t.tv_sec = 5;
    string f;

    DateFormat format = DateFormat("dd-mm-yyyy");
    Date::setFormat(format);

    try{
        Csock = ClientSocket(socketName);
    }
    catch(const char * t){
        cerr << t << endl;
        return 127;
    }
    catch(string t){
        cerr << t << endl;
        return 126;
    }
    catch(...){
        cerr << "Unknown Exception"<<endl;
    }    
    
    try{
        system("clear");
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
    int choix;
    do{
        system("clear");
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
                ListAction();
            break;
            
            case 3:
                DeleteAction();
            break;
            
            case 4:
                AddEquipment();
            break;


            case 0:
                Csock.SendString("LOGOUT");
                cout << "Déconnection réussie :-)"<<endl;
                UtilityLib::WaitEnterIsPressed();
                system("clear");
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
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
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
    else{
        Csock.SendString("TIMEOUT");
        exit(121);
    }
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
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

void AddAction(){
    int GlobalTry = false;
    bool fail = true;

    do{
        system("clear");

        string todo;
        Actions act;
        string TypeKey;
        string Label;
        string date;
        Date wishdate;

        int CounTry = 0;
        do{
            cout << "Entrez l'action a réalisé (livraison, réparation, déclassement):\n";
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
        else if(todo.compare("réparation") == 0) act = Actions::repair;
        else if(todo.compare("déclassement") == 0) act = Actions::downgrade;


        cout << "Entrez le Type de matériel: ";
        cin>>TypeKey;
        cin.get();
        cerr << "Entrée Utilisateur: " << TypeKey << endl;

        cout << "Entrez le nom (unique) du matériel:\t";
        cin>>Label;
        cin.get();
        cerr << "Entrée Utilisateur: " << Label << endl;
        

        bool ValidationDate;
        CounTry = 0;
        do{
            cout << "Entrez la date (jj-mm-yyyy): ";
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

        system("clear");

        stringstream message;
        message << "HMAT#" << act << "#" << TypeKey << "#" << Label << "#" << wishdate;
        Csock.SendString(message.str());


        string recv = Csock.ReceiveString();
        vector<string> msgrecv = UtilityLib::getTokens(recv, L"#");
        cerr << "MsgReceive: " << recv << endl;

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
            if(msgrecv[1].compare("ok") == 0){
                cout << "Action Enregistré à l'id: " << msgrecv[2] << endl;
                cerr << "Action Success, Id: " << msgrecv[2] << endl;
                fail = false;
            }
            else if(msgrecv[1].compare("ko") == 0){
                cout << "Action non réussie car " << msgrecv[2] << endl;
                cerr << "Action Error: " << msgrecv[2] << endl;
                fail = true;
                UtilityLib::WaitEnterIsPressed();
            }
            else{
                cerr << "Unknown Parameters in HMAT from server" << endl;
                fail = true;
                UtilityLib::WaitEnterIsPressed();
            }
        }

        GlobalTry++;
    }while(GlobalTry < 3 && fail == true);
}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

void ListAction(){
    string receivedMsg;
    vector<string> cmd;
    vector<string> slist;
    vector<Commande> Commandes;

    Csock.SendString("LISTCMD");
    receivedMsg = Csock.ReceiveString();

    cmd = UtilityLib::getTokens(receivedMsg, L"#");


    if(cmd[0].compare("LISTCMD") != 0){
        cerr << "Unknown Message in LISTCMD" << endl;
        Csock.SendString("TIMEOUT");
        Csock.close();
        exit(120);
    }

    if(cmd[1].compare("nocmd") == 0){
        cout << "Il n'y a pas de commande actuellement." << endl;
        return;
    }

    slist = UtilityLib::getTokens(cmd[1], L"$");

    if(slist.size() == 0){ cout << "Erreur 404" << endl; return;}

    for(int i = 0 ; i<slist.size() ; i++){
        Commandes.push_back(Commande(slist[i]));
    }

    system("clear");
    cout << "Id:     Actions:        Type Equipement:        Label Equipement:       Date:" << endl;

    for(int i = 0 ; i<Commandes.size() ; i++){
        Commande tmp = Commandes[i];

        cout << tmp.getId() << "\t";
        if(tmp.getAction() == Actions::delivery){
            cout << "Livraison";
        }
        if(tmp.getAction() == Actions::downgrade){
            cout << "Déclassement";
        }
        if(tmp.getAction() == Actions::repair){
            cout << "Réparation";
        }

        cout << "\t" << tmp.getEquiKey() << "\t\t\t" << tmp.getEquiId();
        cout << "\t\t\t" << tmp.getDate();
        cout << endl;
    }

    return;
}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

void DeleteAction(){
    int id;
    stringstream msg;
    string recvmsg;
    vector<string> recv;
    ListAction();
    
    while(true){
        cout << "Entrez l'id a supprimé: " << endl;
        cin >> id; cin.get();

        msg << "CHMAT#" << id;
        Csock.SendString(msg.str());

        recvmsg = Csock.ReceiveString();
        recv = UtilityLib::getTokens(recvmsg, L"#");

        if(recv.size() == 0){
            cout << "Erreur: Message innatendus..." << endl;
            return;
        }

        if(recv[0].compare("CHMAT") != 0){
            cout << "Erreur: Commande inconnue..." << endl;
            return;
        }


        if(recv[1].compare("ok") == 0){
            cout << "Succès: Supression réussie" << endl;
            return;
        }
        
        if(recv[1].compare("ko") == 0 && recv[2].compare("notfound") == 0){
            cout << "Erreur: L'id entrée n'a pas été trouvé..." << endl;
            continue;
        }
        else{
            cout << "Erreur: Raison inconnues" <<endl;
            return;
        }
    }
}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

void AddEquipment(){
    int GlobalTry = false;
    bool fail = true;

    do{
        system("clear");

        string type;
        string libelle;
        string marque;
        float prix;
        int n;
        vector<string> accessoires;

        cout << "Entrez le Type de matériel:" << endl;
        cin>>type; cin.get();

        cout << "Entrez le Nom du matériel (Unique par Type):" << endl;
        cin>>libelle; cin.get();
        
        cout << "Entrez la marque du matériel:" << endl;
        std::getline(std::cin, marque);


        cout << "Entrez le prix du matériel:" << endl;
        cin>>prix; cin.get();
        
        cout << "Entrez le nombre d'accessoire:"<<endl;
        cin>>n; cin.get();
        system("clear");

        for(int i=0 ; i<n ; i++){
            string tmp;
            cout << "Accessoire n°" << i+1 << ":" << endl;
            std::getline(std::cin, tmp);

            accessoires.push_back(tmp);
            system("clear");
        }

        system("clear");

        stringstream message;
        message << "ASKMAT#"<< type << "#" << libelle << "#" << marque << "#" << prix;

        if(n != 0){
            message << "#";
            for(int i=0; i<accessoires.size() ; i++){
                if(i == accessoires.size()-1){
                    message << accessoires[i];
                }
                else{
                    message << accessoires[i] << "$";
                }
            }
        }


        Csock.SendString(message.str());


        string recv = Csock.ReceiveString();
        vector<string> msgrecv = UtilityLib::getTokens(recv, L"#");
        cerr << "MsgReceive: " << recv << endl;

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

        if(msgrecv[0].compare("ASKMAT") == 0){
            if(msgrecv[1].compare("ok") == 0){
                cout << "Matériel Enregistré: "<< endl;
                cerr << "Equipment Success"<< endl;
                fail = false;
            }
            else if(msgrecv[1].compare("ko") == 0){
                cout << "Matériel non ajouté car " << msgrecv[2] << endl;
                cerr << "Equipment Error: " << msgrecv[2] << endl;
                fail = true;
                UtilityLib::WaitEnterIsPressed();
            }
            else{
                cerr << "Unknown Parameters in ASKMAT from server" << endl;
                fail = true;
                UtilityLib::WaitEnterIsPressed();
            }
        }

        GlobalTry++;
    }while(GlobalTry < 3 && fail == true);
}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

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
    Csock.SendString("TIMEOUT");
    Csock.close();
    cerr<<"\nSIGINT Received"<<endl;
    exit(0);
}
//#{}


void initConfig(){
    ifstream read;
    ofstream write;

    read.open("./client.cfg");

    if(read.fail()){
        write.open("./client.cfg");
        write << "Socket=192.168.1.61:50001" << endl;
        write.close();
        read.open("./client.cfg");
    }

    vector<string> index;
    string line;

    while(getline(read, line)){
        index.push_back(line);
    }


    for(int i = 0 ; i<index.size() ; i++){
        vector<string> hashmap = UtilityLib::getTokens(index[i],L"=");
        
        if(hashmap[0].compare("Socket") == 0){
            socketName = hashmap[1];
            continue;
        }
    }

    read.close();
    return;
}