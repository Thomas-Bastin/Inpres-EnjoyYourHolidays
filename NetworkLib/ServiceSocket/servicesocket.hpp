#ifndef SERVICESOCKET_H
#define SERVICESOCKET_H

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

#include "../ListenSocket/listensocket.hpp"


#define DEFAULT_PORT 50001


using namespace std;


class ServiceSocket
{
    private:
        int servicesocket;
    
    public:
        ServiceSocket(int listen);

        ServiceSocket(ListenSocket listen);

        ServiceSocket(const ServiceSocket &e);

        ~ServiceSocket();

        //Setters and Getters
        void setSocket(int c);

        int getSocket()const;

        void close();

        //OPPERATOR SURCHARGE
        //=
        ServiceSocket& operator=(const ServiceSocket& e);

        //<<
        friend std::ostream& operator<<(std::ostream& s, const ServiceSocket& t1);

        //() Casting
        operator int() const;
        int& operator&(void);
	    int* getRef();

        void SendString(string s);
        void Send(void *);

        string ReceiveString();
        void * Receive();
};
#endif

//-I -lnsl -I -lsocket -pthread -m64 -D SUN;