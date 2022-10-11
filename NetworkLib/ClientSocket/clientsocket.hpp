#ifndef CLIENTSOCKET_H
#define CLIENTSOCKET_H

#include <iostream>
#include <stdlib.h>
#include <string.h>
#include <fstream>

#include <stdio.h>
#include <errno.h>
#include <netdb.h>
#include <netinet/in.h>
#include <netinet/tcp.h>
#include <arpa/inet.h>

#include <stdlib.h>
#include <unistd.h>

#include <vector>
#include <wchar.h>
#include <sstream>
#include <locale>
#include <codecvt>


#define DEFAULT_PORT 50001


using namespace std;

class ClientSocket
{
    private:
        int hsocket;
        struct sockaddr_in Adresse;
        //vector<ServiceSocket> services Socket;
    
    public:
        ClientSocket();

        ClientSocket(string socket);

        ClientSocket(string ip, string port);

        ClientSocket(const ClientSocket &e);

        ~ClientSocket();

        //Setters and Getters
        void setSocket(int c);

        int getSocket()const;

        void close();

        //OPPERATOR SURCHARGE
        //=
        ClientSocket& operator=(const ClientSocket& e);

        //<<
        friend std::ostream& operator<<(std::ostream& s, const ClientSocket& t1);

        //() Casting
        operator int() const;
        int& operator&(void);
	    int* getRef();

        void SendString(string s);
        void Send(void *);

        string ReceiveString();
        void * Receive();

        void InitSocket();
        void Connect();

        static vector<string> getTokens(string line, const wchar_t * sep);
};
#endif

//-I -lnsl -I -lsocket -pthread -m64 -D SUN;