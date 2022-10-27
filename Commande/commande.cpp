#include "commande.hpp"
    int Commande::idCount = 1;

    //Constructeur par Default
    Commande::Commande(){
        id = -1;
        equipmentKey = "-1";
        equipmentId = "-1";
    }

    //Constructeur d'init
    Commande::Commande(vector<string> scommande){
        Commande::idCount++;
        id = Commande::idCount;

        action = static_cast<Actions>(stoi(scommande[1]));

        equipmentKey = scommande[2];
        equipmentId = scommande[3];

        
        wishdate = Date(scommande[4].c_str());
    }

    Commande::Commande(string sline){
        vector<string> scommande = UtilityLib::getTokens(sline, L";");

        id = stoi(scommande[0]);

        action = static_cast<Actions>(stoi(scommande[1]));

        equipmentKey = scommande[2];
        equipmentId = scommande[3];

        wishdate = Date(scommande[4].c_str());
    }

    //Constructeur de copie
    Commande::Commande(const Commande &e){
        *this = e;
    }

    //Deconstructeur
    Commande::~Commande(){

    }

    int Commande::getId() const{
        return id;
    }

    void Commande::setId(int i){
        id = i;
    }

    Actions Commande::getAction() const{
        return action;
    }

    string Commande::getEquiKey() const{
        return equipmentKey;
    }
    
    string Commande::getEquiId() const{
        return equipmentId;
    }
    
    Date Commande::getDate() const{
        return wishdate;
    }
    
    //OPPERATOR SURCHARGE
    //=
    Commande& Commande::operator=(const Commande& t2){
        this->id = t2.id;
        this->action = t2.action;
        this->equipmentId = t2.equipmentId;
        this->equipmentKey = t2.equipmentKey;
        this->wishdate = t2.wishdate;
        return *this;
    }

    bool operator==(const Commande& t1, const Commande& t2){
        if(t1.id == t2.id) return true;
        else return false;
    }

    //<<
    std::ostream& operator<<(std::ostream& s, const Commande& t1){
        s << t1.id << ";" << t1.action << ";" << t1.equipmentId << ";" << t1.equipmentKey << ";" << t1.wishdate;
        return (s);
    }