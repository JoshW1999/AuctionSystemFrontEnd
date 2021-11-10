import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


/**
Backend class is the main driver of the backend part of our program.

It will read the merged daily transaction file, and update the available items file
and user accounts file accordingly. In the updateFiles() it will read the merged daily
transaction line by line and will create a transaction instance to verify if the
transaction is valid. If it is then the backend will process the code then either update
the available items file, user account file, or maybe both.
*/
public class Backend {
    AvailableItemFile itemFile;         //Instance of available items file
    UserAccountFile accountFile;        //Instance of user account file
    String itemFileName;                //File name for available items file
    String accountFileName;             //File name for user account file
    String dailyTransactionFileName;

    /*
        Purpose: The constructor of the backend, initialized the available items file
                 and user account file
        Input:
                String itemFileName: The file name of the available items file
                String accountFileName: The file name of the user account file
        Output: None
    */
    public Backend(String itemFileName, String accountFileName, String dailyTransactionFileName) {
        this.itemFileName = itemFileName;
        this.accountFileName = accountFileName;
        this.dailyTransactionFileName = dailyTransactionFileName;
        backendFileInit();
    }


    /*
        Purpose: To initialize the UserAccountFile and AvailableItemFile property
                 of the class
        Input: None
        Output: Boolean, to show if an error have occurred
    */
    public Boolean backendFileInit() {
        try{
            accountFile = new UserAccountFile(this.accountFileName);
            itemFile = new AvailableItemFile(this.itemFileName);
        }catch(Exception e){
            System.out.println("ERROR: An error occurred.");
            e.printStackTrace();
            return false;
        }
        return true;
    }


    /*
        Purpose: Reads the merged daily transaction line by line, verify the transaction
                 line by line and updates the available items file and user account file
        Input: None
        Output: None
    */
    boolean updateFiles() {
        try {
            File file = new File(this.dailyTransactionFileName);
            Scanner reader = new Scanner(file);

            while(reader.hasNextLine()){
                
                String[] trans = reader.nextLine().trim().split("\\s+");
                Transaction transaction = new Transaction(trans);

                if(!transaction.isValidTransaction(this.itemFile, this.accountFile)) continue;
                if(trans[0].equals("01")){
                    this.accountFile.addUser(trans[1], trans[2], Float.parseFloat(trans[3]));
//                    System.out.println("01");
                }

                /**
                * 02 - delete
                */
                else if(trans[0].equals("02")){

                    String target = trans[1];
                    this.accountFile.deleteUser(target);
                    
                    //Filtering the item file which has seller/buyer as the deleted user
                    List<Item> filteredItemList = new ArrayList<Item>();
                    for(int i = 0; i < this.itemFile.itemList.size(); i++){
                        if(this.itemFile.itemList.get(i).seller.equals(target)){
                            continue;
                        }
                        if(this.itemFile.itemList.get(i).buyer.equals(target)){
                            this.itemFile.itemList.get(i).buyer = "";
                            this.itemFile.itemList.get(i).price = (float)0;                       
                        }
                        filteredItemList.add(this.itemFile.itemList.get(i));
                        this.itemFile.itemList = filteredItemList;
                    }
//                    System.out.println("02");
                }

                /**
                * 05 - refund
                */
                else if(trans[0].equals("05")){
                    float toRefund = Float.parseFloat(trans[3]);
                    for(int i=0;i<this.accountFile.accountList.size();i++){
                        //Deduct credit from seller
                        if(this.accountFile.accountList.get(i).username.equals(trans[2])) this.accountFile.accountList.get(i).credit -= toRefund;
                        
                        //Add credit to buyer
                        if(this.accountFile.accountList.get(i).username.equals(trans[1])) this.accountFile.accountList.get(i).credit += toRefund;
                    }
//                    System.out.println("05");
                }

                /**
                * 06 - addcredit
                */
                else if(trans[0].equals("06")){
                    this.accountFile.addCredit(trans[1],Float.parseFloat(trans[3]));
//                    System.out.println("06");
                }

                /**
                    * 04 - bid
                    * TO DO
                    */
                else if(trans[0].equals("04")){
                    // System.out.println("04");
                    this.itemFile.bid(trans);
//                    System.out.println("04");
                }

                /**
                    * 03 - advertise
                    * TO DO
                */
                //Update item files
                else if(trans[0].equals("03")){
                    this.itemFile.addItem(trans[1], trans[2], "", Integer.parseInt(trans[3]), Float.parseFloat(trans[4]));
//                    System.out.println("03");
                }

                //00 - logout
                else if(trans[0].equals("00")){
//                    System.out.println("00");
                    continue;
                }
            }
            reader.close();

        } catch (Exception e) {
            System.out.println("An error occured.");
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /*
        Purpose: Resolves the bidding if day is equals to 0, will run once the items are
                 read as well as days deducted by 1
        Input: None
        Output: None
    */
    boolean resolveBidding(){
        try{
            for(int i = 0; i < this.itemFile.itemList.size(); i++){
                Item item = this.itemFile.itemList.get(i);
                //If days left is 0, needs to resolve item
                if(item.days==0){
                    //Checks there is a buyer
                    if(!item.buyer.equals("")){
                        int sellerIndex = -1, buyerIndex = -1;
                        for(int j = 0; j < this.accountFile.accountList.size(); j++){
                            User user = this.accountFile.accountList.get(j);
                            if(user.username.equals(item.buyer)){
                                buyerIndex = j;
                            //    System.out.println("Buyer found");
                            }

                            if(user.username.equals(item.seller)) {
                                sellerIndex = j;
                            //    System.out.println("Seller found");
                            }
                        }
                        // System.out.println("Seller index: " +sellerIndex);
                        if(sellerIndex >= 0 && buyerIndex >= 0) {
                            // System.out.println("Resolving bid");
                            this.accountFile.accountList.get(sellerIndex).credit += item.price;
                            this.accountFile.accountList.get(buyerIndex).credit -= item.price;
                        }
                        if(sellerIndex < 0) {
                            // System.out.println("Item:" + item.itemName);
                            System.out.println("ERROR: Seller not found");
                        }

                        if(buyerIndex < 0){
                            // System.out.println("Item:"+ item.itemName);
                            System.out.println("ERROR: Buyer not found");
                        } 
                    }
                }
            }
//            System.out.println("Resolve bidding statement");
        }catch(Exception e){
            return false;
        }
        return true;
    }

    /*
        Purpose: Overwrite the existing available items file and user account file
        Input: None
        Output: None
    */
    boolean writeFiles() {
        boolean writeItemFileIsSuccess = this.itemFile.write();
        boolean writeAccountFileIsSuccess = this.accountFile.write();
//        System.out.println("Write file statements");
        return writeItemFileIsSuccess && writeAccountFileIsSuccess;
    }

    /*
        Purpose: Main function of the back end driver
        Input:
                String[] args: Command-line argument
        Output: None
    */
    public static void main(String[] args) {
        Backend backend = new Backend("resources/ai.txt", "resources/ua.txt", "resources/merged_dt.txt");
        boolean resolveBiddingIsSuccess = backend.resolveBidding();
        // System.out.println("Before update: ");
        // System.out.println("Item file size: " + backend.itemFile.itemList.size());
        // System.out.println("Account file size: " + backend.accountFile.accountList.size());
        boolean updateFilesIsSuccess = backend.updateFiles();
        // System.out.println("After update: ");
        // System.out.println("Item file size: " + backend.itemFile.itemList.size());
        // System.out.println("Account file size: " + backend.accountFile.accountList.size());
//        boolean writeFilesIsSuccess = backend.writeFiles();
        backend.writeFiles();
    }

}
