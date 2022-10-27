.SILENT:

*.o = Commande.o Equipment.o
bin = ./.obj/

ALL: $(*.o)

Commande.o:	commande.cpp commande.hpp
			echo Commande.o
			g++	commande.cpp -c -o Commande.o

Equipment.o:	equipment.cpp equipment.hpp
				echo Equipment.o
				g++	equipment.cpp -c -o Equipment.o

#Clean/Clear des .o et de l'application
clean:	
		rm $(*.o)
		clear

clear:	
		rm $(*.o)
		clear