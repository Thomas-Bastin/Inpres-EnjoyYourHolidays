#include "clientsocket.hpp"

        ClientSocket::ClientSocket()
        {
            setSocket(-1);
        }

        ClientSocket::ClientSocket(string socket)
        {
            vector<string> Tokens = getTokens(socket, L":");

            in_addr adresseip;
            unsigned int tailleSock;
            int port;

            try{
                port = stoi(Tokens[1]);
            }
            catch(...){
                throw "ClientSocket.Constructor Error: Port is not an int";
            }
            
            Adresse.sin_addr.s_addr = inet_addr(Tokens[0].c_str());
            Adresse.sin_family = AF_INET;
            Adresse.sin_port = htons(port);

            InitSocket();
            Connect();
        }

        ClientSocket::ClientSocket(const ClientSocket &e)
        {
            setSocket(e.getSocket());
        }

        ClientSocket::~ClientSocket()
        {
        }

        //Setters and Getters
        void ClientSocket::setSocket(int c)
        {
            hsocket = c;
        }

        int ClientSocket::getSocket() const
        {
            return hsocket;
        }

        //OPPERATOR SURCHARGE
        //=
        ClientSocket& ClientSocket::operator=(const ClientSocket& e)
        {
            setSocket(e.getSocket());
            return *this;
        }

        //<<
        std::ostream& operator<<(std::ostream& s, const ClientSocket& t1)
        {
            s << t1.getSocket();
            return s;
        }

        //Opérateur de Casting, retourne par valeur la valeur contenue dans pcur

        ClientSocket::operator int() const{
            return getSocket();
        } 

        int& ClientSocket::operator&(void){
            int l = getSocket();
            return l;
        }


        
        void ClientSocket::close()
        {
            if(::close(getSocket()) == -1)
            {
                string msg = "ListeSocket.Close Error: ";
                msg += (const char *)strerror(errno);
                throw msg;
            }
        }


        void ClientSocket::InitSocket()
        {
            //1. Creation de la Socket
            setSocket(socket(AF_INET, SOCK_STREAM, 0));
            cerr << "InitSocket.CreationSocket Success" << endl;
            
            
            //2. Bind de la socket
            if (bind(getSocket(), (struct sockaddr *) &Adresse, sizeof(struct sockaddr_in)) == -1)
            {
                string msg = "InitSocket.BindSocket Error: ";
                msg += (const char *)strerror(errno);
                throw msg;
            }
            else cerr << "InitSocket.BindSocket Success" << endl;
        }

        void ClientSocket::Connect(){
            //3. Connexion
            if( connect(getSocket(), (struct sockaddr *) &Adresse, sizeof(sockaddr_in)) == -1 ){
                string msg = "ClientSocket.Connect Error: ";
                msg += (const char *)strerror(errno);
                close();
                throw msg;
            }
            else cerr << "ClientSocket.Connect Sucess";
        }
        



    vector<string> ClientSocket::getTokens(string line, const wchar_t * sep)
    {
        vector<string> tokens;
        wstring temp;

        //Convert string to wstring
        wstringstream wss(wstring_convert<codecvt_utf8<wchar_t>>().from_bytes(line));

        while(getline(wss, temp, *sep)){
            //1rst convert wstring to string
            //2nd add to the tokens list
            tokens.push_back(wstring_convert<codecvt_utf8<wchar_t>>().to_bytes(temp));
        }

        return tokens;
    }

//#{}