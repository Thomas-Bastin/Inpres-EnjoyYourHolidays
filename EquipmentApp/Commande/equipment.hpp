#ifndef EQUIPMENT_H
#define EQUIPMENT_H

#include <iostream>
#include <stdlib.h>
#include <string.h>
#include <fstream>
#include <string>
#include "../UtilityLib/date.hpp"
#include "../UtilityLib/utilitylib.hpp"

using namespace std;

class Equipment{
    protected:
        string libelle;
        string marque;
        float prix;
        vector<string> equipments;

    public:

    //Constructeur par Default
    Equipment();

    //Constructeur d'init
    Equipment(vector<string> sEquipment);
    
    Equipment(string sline);

    //Constructeur de copievoid setIdGroupes(const Liste<int> &idG);
    Equipment(const Equipment &e);

    //Deconstructeur
    ~Equipment();

    string getLibelle() const;

    string getMarque() const;

    float getPrix() const;
    
    vector<string> getEquipments() const;

    //OPPERATOR SURCHARGE
    //=
    Equipment& operator=(const Equipment& t2);

    friend bool operator==(const Equipment& t1, const Equipment& t2);

    //<<
    friend std::ostream& operator<<(std::ostream& s, const Equipment& t1);
};
#endif