import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class BackendClassTest {
    Backend backend = new Backend("resources/availableItemsTest.txt", "resources/userAccountsTest.txt","resources/dailyTransactionTest.txt");

    @Test
    public void BackendConstructorItemFileNameCheck() {
        assertEquals("resources/availableItemsTest.txt", backend.itemFileName);
    }

    @Test
    public void BackendConstructorUserFileNameCheck() {
        assertEquals(true, backend.updateFiles());
    }

    @Test
    public void BackendConstructorDailyTransFileNameCheck() {
        assertEquals(true, backend.updateFiles());
    }

    @Test
    public void backendFileInitTest() {
        assertEquals(true, backend.backendFileInit());
    }


    @Test
    public void updateFilesTest() {
        assertEquals(true, backend.updateFiles());
    }

    @Test
    public void resolveBiddingTest() {
//        String itemName, String seller, String buyer, int days, float price
        backend.itemFile.addItem("test_item", "admin", "admin", 0, (float)10);
        assertEquals(true, backend.resolveBidding());
    }

    @Test
    public void writeFilesTest() {
        assertEquals(true, backend.writeFiles());
    }

}