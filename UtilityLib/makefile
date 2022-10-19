.SILENT:

*.o = UtilityLib.o

ALL: UtilityLib.o

UtilityLib.o:	utilitylib.cpp utilitylib.hpp
						echo UtilityLib.o
						g++	utilitylib.cpp -c -o UtilityLib.o


#Clean/Clear des .o et de l'application
clean:	
		rm $(*.o)
		clear

clear:	
		rm $(*.o)
		clear