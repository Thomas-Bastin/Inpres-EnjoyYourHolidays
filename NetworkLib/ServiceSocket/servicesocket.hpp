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
#include <vector>
#include <wchar.h>
#include <sstream>
#include <locale>
#include <codecvt>


#define DEFAULT_PORT 50001


using namespace std;


class ServiceSocket
{
    private:
        int servicesocket;
        bool used;
    
    public:
        ServiceSocket();

        ServiceSocket(int listen);

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
        void Send(void * message, size_t len);

        string ReceiveString();
        void Receive(void * pointer, int SIZE);
        

        static vector<string> getTokens(string line, const wchar_t * sep);
};
#endif

//-I -lnsl -I -lsocket -pthread -m64 -D SUN;