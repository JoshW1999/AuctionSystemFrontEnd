import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.util.Scanner;
import java.io.FileWriter;


/**
    AvailableItemFile class is used to store information regarding the items that
    are in the available item file. This class will also be used to overwrite the
    existing available item file with the updated information from the merged daily
    transaction.
*/
class AvailableItemFile {
    List<Item> itemList = new ArrayList<Item>();        //ArrayList of available items
    String fileName;                                    //File name of the available items file


    /*
        Purpose: Constructor of the available item file. Reads the actual available
                 items file to populate the itemList.
        Input:
                String filename: The file name of the available items file
        Output: None
    */
    public AvailableItemFile(String filename) {
        this.fileName = filename;
        readFile();
    }

    /*
        Purpose: Read in the file an populate the itemList with instances of
                 items
        Input: None
        Output: Boolean, to check whether an error have occurred on the process
    */
    Boolean readFile() {
        try {
            File file = new File(this.fileName);
            Scanner reader = new Scanner(file);
            while (reader.hasNextLine()) {
                String line = reader.nextLine().trim();
                if (line.equals("END")) {
                    reader.close();
                    break;
                }
                String[] itemInfo = line.split("\\s+");


                int days;
                float price;

                if(itemInfo.length==4){ //If no buyer
                    days = Integer.parseInt(itemInfo[2]);
                    price = Float.parseFloat(itemInfo[3]);
                }
                else{
                    days = Integer.parseInt(itemInfo[3]);
                    price = Float.parseFloat(itemInfo[4]);
                }

                //If days is less than 0, ignore the item aka remove it from the list
                if(days<0) {
                    continue;
                }

                //If no buyer
                if(itemInfo.length==4){
                    this.addItem(itemInfo[0], itemInfo[1], "", days-1, price);
                    continue;
                }

                this.addItem(itemInfo[0], itemInfo[1], itemInfo[2], days-1, price);

            }
            // for(Item i: itemList){
            // System.out.println(i.itemName + " " + i.seller + " " + i.buyer + " " + i.days
            // + " " + i.price);
            // }
        } catch (Exception e) {
            System.out.println("ERROR: An error occurred.");
            e.printStackTrace();
            return false;
        }
        return true;
    }


    /*
        Purpose: Adds an item to the item list
        Input:
                String itemName: The name of the item
                String seller: The username of the seller
                String buyer: The username of the buyer
                int days: The number of days left up for bidding
                float price: The price of the current item
        Output: None
    */
    boolean addItem(String itemName, String seller, String buyer, int days, float price) {
        Item item = new Item(itemName, seller, buyer, days, price);
        this.itemList.add(item);
        return this.itemList.get(this.itemList.size()-1).itemName.equals(itemName) && this.itemList.get(this.itemList.size()-1).seller.equals(seller);
    }

    /*
        Purpose: Place a new bidding on an item in the item list
        Input:
                String[] trans: An array of the transaction code from the daily transaction file
        Output: None
    */
    boolean bid(String[] trans){
        try{
            for(int i=0;i< this.itemList.size();i++){
                if(this.itemList.get(i).itemName.equals(trans[1]) && this.itemList.get(i).seller.equals(trans[2])){
                    this.itemList.get(i).buyer = trans[3];
                    this.itemList.get(i).price = Float.parseFloat(trans[4]);
                    // System.out.println(this.itemList.get(i).buyer.equals(trans[3]));
                }
            }
        }catch(Exception e){
            return false;
        }
        return true;
    }

	/*
	 * Purpose: To verify whether an Item exists in the itemList 
	 * Input: 
	 * 		String itemName: The name of the item that needs to be verified 
	 * Output:
	 * 		Boolean, verification of whether the item exists
	 */
	boolean itemExists(String itemName) {
		for (int i = 0; i < this.itemList.size(); i++) {
			if (this.itemList.get(i).itemName.equals(itemName))
				return true;
		}
		return false;
	}

    /*
        Purpose: Overwrite the available items file
        Input: None
        Output: None
    */
    boolean write() {
        // TODO
        
        try{
            FileWriter writer = new FileWriter(this.fileName);
            for(int i=0;i<this.itemList.size();i++){
                writer.write(this.itemList.get(i).writeItem());
            }
            writer.write("END");
            writer.close();
        }catch(Exception e){
            System.out.println("An error occured.");
            e.printStackTrace();
            return false;
        }
        return true;
        
    }
}