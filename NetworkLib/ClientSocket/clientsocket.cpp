#include "clientsocket.hpp"

        ClientSocket::ClientSocket()
        {
            setSocket(-1);
        }

        ClientSocket::ClientSocket(string socket)
        {
            vector<string> Tokens = getTokens(socket, L":");
            unsigned int tailleSock;
            int port;

            try{
                port = stoi(Tokens[1]);
            }
            catch(...){
                throw "ClientSocket.Constructor Error: Port is not an int";
            }
            memset(&Adresse, 0, sizeof(Adresse));
            Adresse.sin_family = AF_INET;
            Adresse.sin_addr.s_addr = htonl(INADDR_ANY);
            inet_aton(Tokens[0].c_str(), &Adresse.sin_addr);
            Adresse.sin_port = htons(port);
            
            
            InitSocket();
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


        
        void ClientSocket::close()
        {
            if(::close(getSocket()) == -1)
            {
                string msg = "ListeSocket.Close Error: ";
                string tmp;
                tmp = to_string(errno);
                msg += tmp;
                throw msg;
            }
        }


        void ClientSocket::InitSocket()
        {
            //1. Creation de la Socket
            setSocket(socket(AF_INET, SOCK_STREAM, 0));
            cerr << "InitSocket.CreationSocket Success" << endl;

            //2. Connexion
            if( connect(getSocket(), (struct sockaddr *) &Adresse, sizeof(Adresse)) == -1 ){
                string msg = "ClientSocket.Connect Error: ";
                string tmp;
                tmp = to_string(errno);
                msg += tmp;
                throw msg;
            }
            else cerr << "ClientSocket.Connect Sucess" << endl;
        }
        
void ClientSocket::SendString(string s)
{
    cerr << "ClientSocket.Send: " << s << endl;
    s+="~";
    send(getSocket(), s.c_str(), strlen(s.c_str()), 1);
}

string ClientSocket::ReceiveString()
{
    char last;
    std::string data;
    bool AllGet = false;
    int i = 0;

    while(AllGet != true && i < 5) { // what you wish to receive
        ssize_t rcvd; 
        rcvd = ::recv(getSocket(), &last, sizeof(char), 0);

        if( rcvd < 0 ) {
            string msg = "ListeSocket.Receive Error: ";
            string tmp;
            tmp = to_string(errno);
            msg += tmp;
            throw msg;
        }
        else if( rcvd == 0 ) {
            i++;
            cerr << "ListeSocket.Receive: Tentative n°" << i << " de réception de la fin du message." << endl; 
            break; // No data to receive, remote end closed connection, so quit.
        }
        else if(last == '~'){
            AllGet = true;
            break;
        }
        else {
            data += last; // Received into buffer, attach to data buffer.
        }
    }


    return data;
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