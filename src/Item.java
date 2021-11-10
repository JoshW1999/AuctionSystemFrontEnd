/**
    Item class is used to store the item name, seller username, current user with the
    highest current bid, current highest bidding price, and numbers of days left up
    for bidding.

    This class will also be used to update the information of a particular item;
*/
class Item {
    String itemName;        //Name of the item
    String seller;          //Username of the seller of the item
    String buyer;           //Username of the user with the highest bid
    float price;            //Current highest bidding price
    int days;               //Number of days left up for bidding

    /*
        Purpose: Constructor of Item class
        Input:
                String itemName: The name of the item
                String seller: The username of the seller
                String buyer: The username of the buyer
                int days: The number of days left up for bidding
                float price: The price of the current item
        Output: None
    */
    public Item(String itemName, String seller, String buyer, int days, float price) {
        this.itemName = itemName;
        this.seller = seller;
        this.buyer = buyer;
        this.days = days;
        this.price = price;
    }


    /*
        Purpose: Encode the information of the current item to the specified output
        Input: None
        Output: String that represents the information of the current item to be
                written to the new user account file
    */
    String writeItem(){
        if(this.days == 0) return "";
        String transaction = this.itemName;
        
        for(int i = 0;i<20-this.itemName.length();i++) transaction += " ";
        transaction += this.seller;
        for(int i = 0;i<16-this.seller.length();i++) transaction += " ";
        
        transaction += this.buyer;
        for(int i = 0;i<16-this.buyer.length();i++) transaction += " ";
        
        String bidDay = Integer.toString(this.days);
        int originalLength = bidDay.length();
        for(int i=0;i<3-originalLength;i++){
            bidDay = "0"+bidDay;
        }
        transaction += bidDay + " ";

        String fund = String.format("%.2f", this.price);
        originalLength = fund.length();
        for(int i = 0;i<6-originalLength;i++) fund = "0"+fund;
        transaction += fund + "\n";


        return transaction;
    }
}