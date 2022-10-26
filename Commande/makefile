.SILENT:

*.o = Commande.o
bin = ./.obj/

ALL: $(*.o)

Commande.o:	commande.cpp commande.hpp
			echo Commande.o
			g++	commande.cpp -c -o Commande.o


#Clean/Clear des .o et de l'application
clean:	
		rm $(*.o)
		clear

clear:	
		rm $(*.o)
		clear