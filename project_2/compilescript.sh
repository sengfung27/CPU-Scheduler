#! /bin/bash

# make sure you have Mono install in you machine, else you can do:
# sudo apt install mono-devel
# chmod u+x compilescript.sh

mono-csc locksystem.cs &&
mono locksystem.exe