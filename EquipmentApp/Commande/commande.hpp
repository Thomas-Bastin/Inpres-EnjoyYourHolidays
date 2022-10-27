#ifndef COMMANDES_H
#define COMMANDES_H

#include <iostream>
#include <stdlib.h>
#include <string.h>
#include <fstream>
#include <string>
#include "../UtilityLib/date.hpp"
#include "../UtilityLib/utilitylib.hpp"
#include "./equipment.hpp"

using namespace std;

enum Actions{
    delivery = 1,
    repair = 2,
    downgrade = 3,
};

class Commande{
    protected:
        int id;
        Actions action;
        string equipmentKey;
        string equipmentId;
        Date wishdate;

    public:
    static int idCount;

    //Constructeur par Default
    Commande();

    //Constructeur d'init
    Commande(vector<string> scommande);
    
    Commande(string sline);

    //Constructeur de copievoid setIdGroupes(const Liste<int> &idG);
    Commande(const Commande &e);

    //Deconstructeur
    ~Commande();

    int getId() const;
    void setId(int i);
    Actions getAction() const;
    string getEquiKey() const;
    string getEquiId() const;
    Date getDate() const;

    //OPPERATOR SURCHARGE
    //=
    Commande& operator=(const Commande& t2);

    friend bool operator==(const Commande& t1, const Commande& t2);

    //<<
    friend std::ostream& operator<<(std::ostream& s, const Commande& t1);
};
#endif