import java.util.ArrayList;
import java.util.List;
import java.io.File;  
import java.util.Scanner; 
import java.io.FileWriter;


/**
    UserAccountFile class is used to store information regarding the users that
    are in the user accounts file. This class will also be used to overwrite the
    existing user accounts file with the updated information from the merged daily
    transaction.
*/
class UserAccountFile{
    List<User> accountList = new ArrayList<User>();         //ArrayList of user accounts
    String fileName;                                        //File name of the available items file


    /* 
        Purpose: Constructor of the user account file. Reads the actual user accounts
                 file to populate the accountList.
        Input: 
                String filename: The file name of the user account file.
        Output: None
    */
    public UserAccountFile(String fileName){ 
        this.fileName = fileName;
        readFile();
    }

    public boolean readFile() {
        try{
            File file = new File(this.fileName);
            Scanner reader = new Scanner(file);
            while (reader.hasNextLine()) {
                String line = reader.nextLine().trim();

                if(line.equals("END")){
                    reader.close();
                    break;
                }

                String[] userInfo = line.split("\\s+");
                this.addUser(userInfo[0],userInfo[1],Float.parseFloat(userInfo[2]));
            }

            // for(User i: accountList){
            //     System.out.println(i.username + " " + i.type + " " + i.credit);
            // }
        }catch(Exception e){
            System.out.println("ERROR: An error occurred.");
            e.printStackTrace();
            return false;
        }
        return true;
    }


    /*
        Purpose: Adds a user to the account list
        Input:
                String username: The username of the user
                String type: The account type of the user
                float credit: The current credit of the user
        Output: None
    */
    boolean addUser(String username, String type, float credit){
        User user = new User(username, type, credit);
        this.accountList.add(user);

        return this.accountList.get(this.accountList.size()-1).username.equals(username)
                && this.accountList.get(this.accountList.size()-1).type.equals(type)
                && this.accountList.get(this.accountList.size()-1).credit == credit;

    }


    /*
        Purpose: To remove an existing user from the account list
        Input: 
                String username: The username of the user to be removed
        Output: None
    */
    boolean deleteUser(String username){
        for(int i=0;i<this.accountList.size();i++){
            if(this.accountList.get(i).username==username){
                this.accountList.remove(i);
                break;
            }
        }

        return !this.userExists(username);

    }

    /*
        Purpose: To add some credit to a user, could be an admin add credit or
                 regular add credit
        Input: 
                String[] trans: An array of strings of the transaction code to check
                                whether it is an admin add credit or regular add credit.
        Output: None
    */
    boolean addCredit(String username, float credit){
        int userLoc = 0;
        float prevCredit = 0;
        for(int i=0;i<this.accountList.size();i++){
            if(this.accountList.get(i).username==username){
                prevCredit = this.accountList.get(i).credit;
                userLoc = i;
                this.accountList.get(i).credit += credit;
                break;
            }
        }

        if(this.accountList.get(userLoc).credit == prevCredit + credit) return true;

        return false;

    }

    /*
        Purpose: To verify whether a user exist in the accountList
        Input:
                String username: The username of the user that needs to be verified
        Output: Boolean, verification of whether the user exists
    */
    boolean userExists(String username){
        for(int i=0;i<this.accountList.size();i++){
            if(this.accountList.get(i).username.equals(username)) return true;
        }
        return false;
    }

    /*
        Purpose: To retrieve an information of a particular user
        Input:
                String username: The username of the user that will be retrieved
        Output: The user instance
    */
    User getUser(String username){
        if(userExists(username)){
            for(int i=0;i<this.accountList.size();i++){ 
                if(this.accountList.get(i).username.equals(username)) return accountList.get(i);
            }
        }

        return null;

    }

    /*
        Purpose: Overwrite the user accounts file
        Input: None
        Output: None
    */
    boolean write(){
        try{
            FileWriter writer = new FileWriter(this.fileName);
            for(int i=0;i<this.accountList.size();i++){
                writer.write(this.accountList.get(i).writeUser()+"\n");
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