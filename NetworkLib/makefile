.SILENT:

*.o = $(bin)ServiceSocket.o $(bin)ListenSocket.o $(bin)ClientSocket.o
bin = ./.obj/
Libls = ./ListenSocket/
Liblss = ./ServiceSocket/
Liblc = ./ClientSocket/
LibNt = ./

ALL: $(*.o)

$(bin)ClientSocket.o:	$(Liblc)clientsocket.cpp $(Liblc)clientsocket.hpp
						echo ClientSocket.o
						g++ $(Liblc)clientsocket.cpp -c -o $(bin)ClientSocket.o

$(bin)ServiceSocket.o:	$(Liblss)servicesocket.cpp $(Liblss)servicesocket.hpp
						echo ServiceSocket.o
						g++ $(Liblss)servicesocket.cpp -c -o $(bin)ServiceSocket.o

$(bin)ListenSocket.o:	$(Libls)listensocket.cpp $(Libls)listensocket.hpp $(Liblss)servicesocket.hpp
						echo ListenSocket.o
						g++	$(Libls)listensocket.cpp -c -o $(bin)ListenSocket.o


#Clean/Clear des .o et de l'application
clean:	
		rm $(*.o)
		clear

clear:	
		rm $(*.o)
		clear