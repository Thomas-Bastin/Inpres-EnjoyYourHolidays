#include "listensocket.hpp"

        ListenSocket::ListenSocket()
        {
            setSocket(-1);
        }

        ListenSocket::ListenSocket(int port)
        {
            InitSocket(getHost(port)); 
        }

        ListenSocket::ListenSocket(string socket)
        {
            InitSocket(getHost(DEFAULT_PORT));
        }

        ListenSocket::ListenSocket(const ListenSocket &e)
        {
            setSocket(e.getSocket());
        }

        ListenSocket::~ListenSocket()
        {
        }

        //Setters and Getters
        void ListenSocket::setSocket(int c)
        {
            hsocket = c;
        }

        int ListenSocket::getSocket() const
        {
            return hsocket;
        }

        //OPPERATOR SURCHARGE
        //=
        ListenSocket& ListenSocket::operator=(const ListenSocket& e)
        {
            setSocket(e.getSocket());
            return *this;
        }

        //<<
        std::ostream& operator<<(std::ostream& s, const ListenSocket& t1)
        {
            s << t1.getSocket();
            return s;
        }

        //Opérateur de Casting, retourne par valeur la valeur contenue dans pcur

        ListenSocket::operator int() const{
            return getSocket();
        } 

        int& ListenSocket::operator&(void){
            return hsocket;
        }


        
        void ListenSocket::close()
        {
            if(::close(getSocket()) == -1)
            {
                string msg = "ListeSocket.Close Error: ";
                msg += (const char *)strerror(errno);
                throw msg;
            }
        }


        void ListenSocket::InitSocket(struct sockaddr_in adresseSocket)
        {
            //1. Creation de la Socket
            hsocket = socket(AF_INET, SOCK_STREAM, 0);
            cerr << "InitSocket.CreationSocket Success" << endl;

            
            //2. Bind de la socket
            if (bind(hsocket, (struct sockaddr *) &adresseSocket, sizeof(struct sockaddr_in)) == -1)
            {
                string msg = "InitSocket.BindSocket Error: ";
                msg += (const char *)strerror(errno);
                close();
                throw msg;
            }
            cerr << "InitSocket.BindSocket Success" << endl;
        }

        struct sockaddr_in ListenSocket::getHost(int port)
        {
            in_addr adresseIP;
            sockaddr_in adresseSocket;
            int tailleSockaddr_in;
            hostent* infosHost; //Info Host
            char chartmp[10000];
            string hostName;

            //Recupération HostName
            gethostname(chartmp, 10000);

            //Récupération info Host
            if((infosHost = gethostbyname( chartmp ) ) == 0)
            {
                string msg = "ListeSocket.getHost Error: ";
                msg += (const char *)strerror(errno);
                throw msg;
            }
            else cerr << "ListeSocket.getHost Success" << endl;
            
            memcpy(&adresseIP, infosHost->h_addr, infosHost->h_length);            
            memset(&adresseSocket, 0, sizeof(sockaddr_in));
            adresseSocket.sin_family = AF_INET; /* Domaine */
            adresseSocket.sin_port = htons(port);
            memcpy(&adresseSocket.sin_addr, infosHost->h_addr,infosHost->h_length);

            return adresseSocket;
        }

        void ListenSocket::Accept()
        {
            if(listen(getSocket(), SOMAXCONN) == -1 ){
                string msg = "ListeSocket.Accept Error: ";
                msg += (const char *)strerror(errno);
                close();
                throw msg;
            }
            else cerr << "ListenSocket.Listen Success"<<endl;
            
            socklen_t taille = sizeof(sockaddr_in);
            if( (accept(getSocket(), (sockaddr *) &Adresse, &taille)) == -1 ){
                string msg = "ListeSocket.Accept Error: ";
                msg += (const char *)strerror(errno);
                close();
                throw msg;
            }
            else cerr << "ListenSocket.Accept Success"<<endl;

            /*SocketServiceCréer*/

        }

//#{}