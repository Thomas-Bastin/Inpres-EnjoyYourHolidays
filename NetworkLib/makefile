.SILENT:

*.o = $(bin)ListenSocket.o $(bin)ServiceSocket.o
bin = ./.obj/
Libls = ./ListenSocket/
Liblss = ./ServiceSocket/
LibNt = ./

ALL: Client.app Server.app

Client.app:		client.cpp $(bin)
				echo Client.app
				g++	client.cpp -o Client.app -I -lnsl -I -lsocket -pthread -m64 -D SUN;

Server.app:		server.cpp $(*.o)
				echo Server.app
				g++	server.cpp $(*.o) -o Server.app -I -lnsl -I -lsocket -pthread -m64 -D SUN;


$(bin)ClientSocket.o:	$(Liblss)clientsocket.cpp $(Liblss)clientsocket.hpp
						echo ClientSocket.o
						g++ $(Liblss)clientsocket.cpp -c -o $(bin)ClientSocket.o

$(bin)ServiceSocket.o:	$(Liblss)servicesocket.cpp $(Liblss)servicesocket.hpp
						echo ServiceSocket.o
						g++ $(Liblss)servicesocket.cpp -c -o $(bin)ServiceSocket.o

$(bin)ListenSocket.o:	$(Libls)listensocket.cpp $(Libls)listensocket.hpp
						echo ListenSocket.o
						g++	$(Libls)listensocket.cpp -c -o $(bin)ListenSocket.o


#Clean/Clear des .o et de l'application
clean:	
		rm $(*.o)
		rm Client.app
		rm Server.app
		clear

clear:	
		rm $(*.o)
		rm Client.app
		rm Server.app
		clear