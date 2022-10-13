#include "servicesocket.hpp"
ServiceSocket::ServiceSocket(int servicesocket)
{
    setSocket(servicesocket);
}

ServiceSocket::ServiceSocket(const ServiceSocket &e)
{
    setSocket(e.getSocket());
}

ServiceSocket::~ServiceSocket() {}

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
        string tmp;
        tmp = to_string(errno);
        msg += tmp;
        throw msg;
    }
}

void ServiceSocket::SendString(string s)
{
    cerr << "ListeSocket.Send: " << s << endl;
    s += "~";
    send(getSocket(), s.c_str(), strlen(s.c_str())+1, 1);
}

string ServiceSocket::ReceiveString()
{
    char last;
    std::string data;
    bool AllGet = false;
    int i = 0;

    while (AllGet != true && i < 5)
    { // what you wish to receive
        ssize_t rcvd;
        rcvd = ::recv(getSocket(), &last, 1, 0);

        if (rcvd < 0)
        {
            string msg = "ListeSocket.Receive Error: ";
            string tmp;
            tmp = to_string(errno);
            msg += tmp;
            throw msg;
        }
        else if (rcvd == 0)
        {
            i++;
            cerr << "ListeSocket.Receive: Tentative n°" << i << " de réception de la fin du message." << endl;
            break; // No data to receive, remote end closed connection, so quit.
        }
        else if (last == '~')
        {
            data[data.length()] = '\0';
            AllGet = true;
            break;
        }
        else
        {
            data += last; // Received into buffer, attach to data buffer.
        }
    }

    cerr <<"ListeSocket.Received: " << data << endl;

    return data;
}

void ServiceSocket::Receive(void *pointer, int SIZE)
{
    throw "Not Implemented Exception ServiceSocket.Receive(void)";
}

void ServiceSocket::Send(void *message, size_t len)
{
    throw "Not Implemented Exception ServiceSocket.Send(void)";
}

vector<string> ServiceSocket::getTokens(string line, const wchar_t *sep)
{
    vector<string> tokens;
    wstring temp;

    //Convert string to wstring
    wstringstream wss(wstring_convert<codecvt_utf8<wchar_t>>().from_bytes(line));

    while (getline(wss, temp, *sep))
    {
        //1rst convert wstring to string
        //2nd add to the tokens list
        tokens.push_back(wstring_convert<codecvt_utf8<wchar_t>>().to_bytes(temp));
    }

    return tokens;
}
//#{}