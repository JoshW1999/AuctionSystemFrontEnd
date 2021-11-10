import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class UserAccountFileTest {

    UserAccountFile accountFile = new UserAccountFile("resources/userAccountsTest.txt");

    @Test
    public void UserAccountFileClassConstructorCheck() {
        assertEquals("resources/userAccountsTest.txt", accountFile.fileName);
    }

    @Test
    public void readFileTest() {
        assertEquals(true, accountFile.readFile());
    }

    @Test
    public void addUserToList(){
        assertEquals(true, accountFile.addUser("admin", "AA", (float)32.45));
    }

    @Test
    public void addCreditToUser(){
        accountFile.addUser("admin", "AA", (float)0);
        assertEquals(true, accountFile.addCredit("admin", (float)500));
    }

    @Test
    public void deleteUserFromList(){
        accountFile.addUser("new_admin", "AA", (float)0);
        assertEquals(true, accountFile.userExists("new_admin"));
        assertEquals(true, accountFile.deleteUser("new_admin"));
    }

    @Test
    public void testUserExists(){
        accountFile.addUser("new_user1", "FS", (float)0);
        assertEquals(true, accountFile.userExists("new_user1"));
    }

    @Test
    public void testGetUser(){
        accountFile.addUser("new_user2", "FS", (float)0);

        assertEquals(true, accountFile.getUser("new_user2").username.equals("new_user2")
                        && accountFile.getUser("new_user2").type.equals("FS")
                        && accountFile.getUser("new_user2").credit == (float)0);
    }

    @Test
    public void testWrite(){
        accountFile.addUser("new_user3", "FS", (float)0);
        assertEquals(true, accountFile.write());
    }

    // Decision coverage decision statement true
    @Test
    public void testDecisionsGetUserTrue(){
        accountFile.addUser("new_user4", "FS", (float)0);
        assertEquals(true, accountFile.getUser("new_user4").username.equals("new_user4")
                && accountFile.getUser("new_user4").type.equals("FS")
                && accountFile.getUser("new_user4").credit == (float)0);
    }

    // Decision coverage decision statement not true
    @Test
    public void testDecisionsGetUserNotTrue() {
        assertEquals(null, accountFile.getUser("random_user"));
    }
}
