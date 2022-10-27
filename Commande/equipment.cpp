#include "equipment.hpp"

    //Constructeur par Default
    Equipment::Equipment(){
        libelle = "-1";
        marque = "-1";
        prix = -1.0;
        equipments.clear();
    }

    //Constructeur d'init
    Equipment::Equipment(vector<string> scommande){
        libelle = scommande[2];
        marque = scommande[3];
        prix = stof(scommande[4]);
        equipments = UtilityLib::getTokens(scommande[5], L"$");
    }

    Equipment::Equipment(string sline){
        vector<string> scommande = UtilityLib::getTokens(sline, L";");
        
        libelle = scommande[0];
        marque = scommande[1];
        prix = stof(scommande[2]);
        equipments = UtilityLib::getTokens(scommande[3], L"$");
    }

    //Constructeur de copie
    Equipment::Equipment(const Equipment &e){
        *this = e;
    }

    //Deconstructeur
    Equipment::~Equipment(){

    }

    string Equipment::getLibelle() const{
        return libelle;
    }
    string Equipment::getMarque() const{
        return marque;
    }
    float Equipment::getPrix() const{
        return prix;
    }
    vector<string> Equipment::getEquipments() const{
        return equipments;
    }


    //OPPERATOR SURCHARGE
    //=
    Equipment& Equipment::operator=(const Equipment& t2){
        this->libelle = t2.libelle;
        this->marque = t2.marque;
        this->prix = t2.prix;
        this->equipments = t2.equipments;
        return *this;
    }

    bool operator==(const Equipment& t1, const Equipment& t2){
        if(t1.libelle == t2.libelle) return true;
        else return false;
    }

    //<<
    std::ostream& operator<<(std::ostream& s, const Equipment& t1){
        s << t1.libelle << ";" << t1.marque << ";" << t1.prix << ";";

        for(int i = 0; i < t1.equipments.size() ; i++){
            if(i == (t1.equipments.size()-1)){
                s << t1.equipments[i];
            }
            else{
                s << t1.equipments[i] << "$";
            }
        }

        return (s);
    }