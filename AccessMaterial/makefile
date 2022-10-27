.SILENT:

*.o = AccessMaterial.o
Util = ../UtilityLib/utilitylib.hpp
Commande = ../Commande/commande.hpp
Equipment = ../Commande/equipment.hpp

Util.o = ../UtilityLib/UtilityLib.o
Commande.o = ../Commande/Commande.o
Equipment.o = ../Commande/Equipment.o

ALL: AccessMaterial.o



AccessMaterial.o:	accessmaterial.cpp accessmaterial.hpp $(Util) $(Commande) $(Equipment)
						echo AccessMaterial.o
						g++	accessmaterial.cpp -c -o AccessMaterial.o


#Clean/Clear des .o et de l'application
clean:	
		rm $(*.o)
		clear

clear:	
		rm $(*.o)
		clear