.SILENT:

*.o = AccessMaterial.o

ALL: AccessMaterial.o

AccessMaterial.o:	accessmaterial.cpp accessmaterial.hpp
						echo AccessMaterial.o
						g++	accessmaterial.cpp -c -o AccessMaterial.o


#Clean/Clear des .o et de l'application
clean:	
		rm $(*.o)
		clear

clear:	
		rm $(*.o)
		clear