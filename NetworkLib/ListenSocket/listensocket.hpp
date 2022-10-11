#ifndef LISTENSOCKET_H
#define LISTENSOCKET_H

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

#include "../ServiceSocket/servicesocket.hpp"

#define DEFAULT_PORT 50001

using namespace std;

class ListenSocket
{
    private:
        int hsocket;
        struct sockaddr_in Adresse;
        vector<ServiceSocket> services;
    
    public:
        ListenSocket();

        ListenSocket(int port);

        ListenSocket(string socket);

        ListenSocket(const ListenSocket &e);

        ~ListenSocket();

        //Setters and Getters
        void setSocket(int c);

        int getSocket()const;

        void close();

        //OPPERATOR SURCHARGE
        //=
        ListenSocket& operator=(const ListenSocket& e);

        //<<
        friend std::ostream& operator<<(std::ostream& s, const ListenSocket& t1);

        //() Casting
        operator int() const;
        int& operator&(void);
	    int* getRef();

        void InitSocket();

        static struct sockaddr_in getHost(int port);

        void Accept();


        static vector<string> getTokens(string line, const wchar_t * sep);
};
#endif

//-I -lnsl -I -lsocket -pthread -m64 -D SUN;