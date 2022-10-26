.SILENT:

*.o = $(bin)ServiceSocket.o $(bin)ListenSocket.o
bin = ./.obj/

NetworkLib.objs = ../NetworkLib/.obj/*
NetworkLib.Client = ../NetworkLib/ClientSocket/clientsocket.hpp
NetworkLib.Listen = ../NetworkLib/ListenSocket/listensocket.hpp
NetworkLib.Service = ../NetworkLib/ServiceSocket/servicesocket.hpp

ThreadLib.objs = ../MyLibThread_POSIX/mylibthread_POSIX.o

UtilityLib.objs = ../UtilityLib/UtilityLib.o
UtilityLib.hpp = ../UtilityLib/utilitylib.hpp

Date.objs = ../UtilityLib/Date.o
Date.hpp = ../UtilityLib/date.hpp


ALL: Client/Client.app Server/Server.app

Client/Client.app:		client.cpp $(NetworkLib.Client) $(NetworkLib.hpp) $(Date.hpp)
				echo Client.app
				g++	client.cpp $(NetworkLib.objs) $(UtilityLib.objs) $(Date.objs) -o Client/Client.app -I -lnsl -I -lsocket -pthread -m64 -D SUN;

Server/Server.app:		server.cpp $(NetworkLib.objs) $(NetworkLib.Listen) $(NetworkLib.Service) $(ThreadLib.objs) $(NetworkLib.hpp) $(Date.hpp)
				echo Server.app
				g++	server.cpp $(NetworkLib.objs) $(ThreadLib.objs) $(UtilityLib.objs) $(Date.objs) -o Server/Server.app -I -lnsl -I -lsocket -pthread -m64 -D SUN;


#Clean/Clear des .o et de l'application
clean:	
		rm Client/Client.app
		rm Server/Server.app
		clear

clear:	
		rm Client/Client.app
		rm Server/Server.app
		clear