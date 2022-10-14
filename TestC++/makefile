.SILENT:

*.o = $(bin)ServiceSocket.o $(bin)ListenSocket.o
bin = ./.obj/

NetworkLib.objs = ../NetworkLib/.obj/*
NetworkLib.Client = ../NetworkLib/ClientSocket/clientsocket.hpp
NetworkLib.Listen = ../NetworkLib/ListenSocket/listensocket.hpp
NetworkLib.Service = ../NetworkLib/ServiceSocket/servicesocket.hpp

ThreadLib.objs = ../MyLibThread_POSIX/mylibthread_POSIX.o

ALL: Client.app Server.app

Client.app:		client.cpp $(NetworkLib.Client)
				echo Client.app
				g++	client.cpp $(NetworkLib.objs) -o Client.app -I -lnsl -I -lsocket -pthread -m64 -D SUN;

Server.app:		server.cpp $(NetworkLib.objs) $(NetworkLib.Listen) $(NetworkLib.Service) $(ThreadLib.objs)
				echo Server.app
				g++	server.cpp $(NetworkLib.objs) $(ThreadLib.objs) -o Server.app -I -lnsl -I -lsocket -pthread -m64 -D SUN;


#Clean/Clear des .o et de l'application
clean:	
		rm Client.app
		rm Server.app
		clear

clear:	
		rm Client.app
		rm Server.app
		clear