#include "accessmaterial.hpp"

string AccessMaterial::ActionFilePath = "./Actions";
string AccessMaterial::MaterialDirPath = "./Materials";

int AccessMaterial::setActionFilePath(string path){
    ActionFilePath = path;
    return 0;
}

int AccessMaterial::setMaterialDirPath(string path){
    MaterialDirPath = path;
    return 0;
}


int AccessMaterial::addMaterial(string key, string line){
    return -1;
}

int AccessMaterial::modifyMaterial(string key, int id, string line){
    return -1;
}

int AccessMaterial::removeMaterial(string key, int id){
    return -1;
}



int AccessMaterial::addAction(Commande cmd){

    //Check si Materiel existe:
    //Si non return -3;

    //Check si Actions possible
    ifstream read;
    ofstream write;

    string line;
    vector<Commande> list;
    
    int idfound = -1;

    read.open(ActionFilePath);
    
    if(read.fail()){
        if(cmd.getAction() == 1){
            //cerr << "Actions File not found so created" << endl;
            write.open(ActionFilePath);
            write << cmd << endl;
            write.close();
            read.close();
            return 0;
        }
        else{
            //cerr << "Action ne peut être effectué, car le produit n'est pas encore commandée" << endl;
            write.close();
            read.close();
            return -2;
        }
    }

    write.open(ActionFilePath);

    int i = 0;
    while(getline(read,line)){
        list.push_back(Commande(line));
        if(cmd == list[i]){
            //cerr << "action Already Exist" << endl;
            write.close();
            read.close();
            return -1;
        }

        i++;
    }

    int ind = -1;
    bool found = false;
    for(int i = 0; i<list.size() ; i++){
        if(cmd.getEquiId().compare(list[i].getEquiId()) == 0 && cmd.getEquiKey().compare(list[i].getEquiKey()) == 0 ){
            found == true;
            ind = i;
            break;
        }
    }

    if(found == false && cmd.getAction() == 1){
        list.push_back(cmd);
        for(int j ; j<list.size() ; j++){
            write << list[j] << endl;
        }
        write.close();
        read.close();
        return 0;
    }

  
    if(list[ind].getAction() != 1){
        //cerr << "Action ne peut être effectué, car le produit n'est pas encore commandée" << endl;
        write.close();
        read.close();
        return -2;
    }


    list.push_back(cmd);
    for(int j ; j<list.size() ; j++){
        write << list[j] << endl;
    }
    write.close();
    read.close();
    return 0;    
}
        
int AccessMaterial::modifyAction(int id, Commande cmd){
    return -1;
}

int AccessMaterial::removeAction(int id){
    ifstream read;
    string line;
    vector<Commande> vec;

    read.open(AccessMaterial::ActionFilePath);

    bool found = false;
    int ind;
    while(getline(read, line)){
        vec.push_back(Commande(line));
    }

    for(int i =0;i<vec.size() ; i++){
        if(vec[i].getId() == id){
            found = true;
            ind = i;
            break;
        }
    }
    if(found == true){
        Commande tmp = vec[ind];

        tmp.setId(-1);

        vec[ind] = tmp;
        return 0;
    }

    //NotFound
    return -1;
}

Commande AccessMaterial::getAction(int id){
    ifstream read;
    string line;
    vector<Commande> vec;

    read.open(AccessMaterial::ActionFilePath);

    bool found = false;
    int ind;
    while(getline(read, line)){
        vec.push_back(Commande(line));
    }

    for(int i =0;i<vec.size() ; i++){
        if(vec[i].getId() == id){
            found = true;
            ind = i;
            break;
        }
    }
    if(found == true){
        return vec[ind];
    }

    throw "Id Not Found";
}

vector<Commande> getAllActions(){
    ifstream read;
    string line;
    vector<Commande> vec;

    read.open(AccessMaterial::ActionFilePath);

    while(getline(read, line)){
        vec.push_back(Commande(line));
    }
    cerr<<"Action File lines: " << vec.size()<<endl;
    return vec;
}