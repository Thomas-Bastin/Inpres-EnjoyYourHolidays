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


void ServiceSocket::SendString(string s)
{
    send(getSocket(), s.c_str(), s.length()-1, 1);
}


void ServiceSocket::Send(void * message, size_t len)
{
    send(getSocket(), message, len, 1);
}


string ServiceSocket::ReceiveString()
{
    char buf[1024];
    std::string data;

    while( *(data.cend()-1) == '\n' == *data.cend() == '\r' ) { // what you wish to receive
        ::ssize_t rcvd = ::recv(getSocket(), buf, sizeof(buf), 0);
        if( rcvd < 0 ) {
            string msg = "ListeSocket.Receive Error: ";
            msg += (const char *)strerror(errno);
            throw msg;
            return "";
        } else if( !rcvd ) {
            break; // No data to receive, remote end closed connection, so quit.
        } else {
            data.append(buf, rcvd); // Received into buffer, attach to data buffer.
        }
    }
    
    return data;
}

void ServiceSocket::Receive(void * pointer, int SIZE)
{
    int nbrbyterecus = 0;
    int taillemsg = 0;

    do{
        if((nbrbyterecus = recv(getSocket(), (char *)pointer + taillemsg, SIZE - taillemsg, 0)) == -1){
            string msg = "ListeSocket.Receive Error: ";
            msg += (const char *)strerror(errno);
            throw msg;
        }
        else{
            taillemsg += nbrbyterecus;
        }
    }
    while(nbrbyterecus != 0 && nbrbyterecus != -1 && taillemsg < SIZE);
}
//#{}