#ifndef ACCESSMATERIAL_HPP
#define ACCESSMATERIAL_HPP


#include <stdio.h>
#include <stdlib.h>
#include <string>
#include <string.h>
#include <iostream>
#include <fstream>

#include <sstream>
#include <wchar.h>
#include <codecvt>
#include <locale>
#include <vector>
#include <ctime>

#include "../UtilityLib/utilitylib.hpp"
#include "../Commande/commande.hpp"

using namespace std;

class AccessMaterial{
    public:
        static string ActionFilePath;
        static string MaterialDirPath;

        static int setActionFilePath(string path);
        static int setMaterialDirPath(string path);


        static int addMaterial(string key, string line);

        static int modifyMaterial(string key, int id, string line);

        static int removeMaterial(string key, int id);

        static Equipment getMaterial(string key, int id);



        static int addAction(Commande cmd);
        
        static int modifyAction(int id, Commande cmd);

        static int removeAction(int id);

        static Commande getAction(int id);

        static vector<Commande> getAllActions();
};
#endif // !UTILITYLIB_HPP