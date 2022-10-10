#include "servicesocket.hpp"
ServiceSocket::ServiceSocket(int servicesocket)
{
    setSocket(servicesocket);
}

ServiceSocket::ServiceSocket(const ServiceSocket &e)
{
    setSocket(e.getSocket());
}

ServiceSocket::~ServiceSocket(){}

//Setters and Getters
void ServiceSocket::setSocket(int c)
{
    servicesocket = c;
}

int ServiceSocket::getSocket() const
{
    return servicesocket;
}

//OPPERATOR SURCHARGE
//=
ServiceSocket &ServiceSocket::operator=(const ServiceSocket &e)
{
    setSocket(e.getSocket());
    return *this;
}

//<<
std::ostream &operator<<(std::ostream &s, const ServiceSocket &t1)
{
    s << t1.getSocket();
    return s;
}

//Opérateur de Casting, retourne par valeur la valeur contenue dans pcur

ServiceSocket::operator int() const
{
    return getSocket();
}

int &ServiceSocket::operator&(void)
{
    return servicesocket;
}

void ServiceSocket::close()
{
    if (::close(getSocket()) == -1)
    {
        string msg = "ListeSocket.Close Error: ";
        msg += (const char *)strerror(errno);
        throw msg;
    }
}


//#{}
void ServiceSocket::SendString(string s)
{
    
}

void ServiceSocket::Send(void *)
{
    
}

string ServiceSocket::ReceiveString()
{
    return "s";
}

void * Receive()
{
    void * pointer = NULL;
    return pointer;
}