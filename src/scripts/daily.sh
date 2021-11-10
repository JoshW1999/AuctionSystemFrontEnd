#!/bin/sh

# For text colouring purpose
BLUE='\033[1;34m'
RED='\033[0;31m'
GREEN='\033[0;32m'
NC='\033[0m'


# Declare location of the program (compiled under src folder)
front_program="../../../front-end/auction-system"
current_user_account="../../../../resources/ua.txt"
available_items="../../../../resources/ai.txt"


#run front end over number of ticket transaction sessions
#saving the output daily transaction file for each session in a separate file
for input in *.txt; do
   echo "\n${BLUE}Running on file $input ${NC}"
   $front_program $current_user_account $available_items < $input
done

#Concatenates the separate daily transaction files into a merged daily transaction file
cat ../../../../resources/daily_transactions/*.txt > ../../../../resources/merged_dt.txt

#delete all separate daily transaction file
rm ../../../../resources/daily_transactions/*.txt

#run back end with the merged daily transaction file as input
echo "\n${BLUE}Running Back End${NC}"
cd ../../../../
make
