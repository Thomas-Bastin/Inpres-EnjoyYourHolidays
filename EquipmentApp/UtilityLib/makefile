.SILENT:

*.o = UtilityLib.o Date.o

ALL: $(*.o)

UtilityLib.o:	utilitylib.cpp utilitylib.hpp
						echo UtilityLib.o
						g++	utilitylib.cpp -c -o UtilityLib.o

Date.o:		date.cpp date.hpp
			echo Date.o
			g++ date.cpp -c -o Date.o


#Clean/Clear des .o et de l'application
clean:	
		rm $(*.o)
		clear

clear:	
		rm $(*.o)
		clear