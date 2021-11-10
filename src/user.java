/**
    User class is used to store username, credit, and type of the user
    Will be used to update the information of a particular user
*/
class User {
    String username;        //Username of the user
    float credit;           //Current credit of the user
    String type;            //Type of the user's account


    /*
        Purpose: Constructor of the user class
        Input: 
                String username: The username of the user
                String type: The type of the user's account
                float credit: The current credit of the user
        Output: None
    */
    public User(String username, String type, float credit) {
        this.username = username;
        this.type = type;
        this.credit = credit;
    }


    /*
        Purpose: Encode the information of the current user to the specified output
        Input: None
        Output: String that represents the information of the current user to be
                written to the new user account file
    */
    String writeUser(){
        String transaction = this.username;
        
        for(int i = 0;i<16-this.username.length();i++) transaction += " ";
        transaction += this.type + " ";

        String fund = String.format("%.2f", this.credit);//Float.toString(this.credit);
        int originalLength = fund.length();
        for(int i = 0;i<9-originalLength;i++) fund = "0"+fund;

        transaction += fund;

        return transaction;

    }
}