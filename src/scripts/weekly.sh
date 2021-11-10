#!/bin/sh

# For text colouring purpose
BLUE='\033[1;34m'
RED='\033[0;31m'
GREEN='\033[0;32m'
NC='\033[0m'


#Go to each day folder to simulate a daily run
cd input
for dir in *
do
    cd $dir
    echo "\n\n${GREEN}$dir${NC}"
    
    #Run daily script
    ../../daily.sh
    
    #Copy file printouts after each run
    cp ../../../../resources/merged_dt.txt merged_dt.dat
    cp ../../../../resources/ai.txt ai.dat
    
    cd ..
done
