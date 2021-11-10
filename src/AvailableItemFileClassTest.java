import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class AvailableItemFileClassTest {
    AvailableItemFile itemFile = new AvailableItemFile("resources/availableItemsTest.txt");

    @Test
    public void availableItemFileClassConstructorCheck() {
        assertEquals("resources/availableItemsTest.txt", itemFile.fileName);
    }

    @Test
    public void readFileTest() {
        assertEquals(true, itemFile.readFile());
    }


    @Test
    public void addItemToItemListTest() {
        assertEquals(true, itemFile.addItem("test_item", "seller", "buyer", 80, (float) 533.2));
    }


    @Test
    public void bidTest() {
        String[] trans = {"04", "item_name", "admin", "admin", "00011"};
        assertEquals(true, itemFile.bid(trans));
    }


    @Test
    //Loop coverage - loop 0 times
    public void itemExistsLoopCoverageZeroTimes() {
        itemFile.itemList.clear();
        System.out.println(itemFile.itemList.size());
        assertEquals(false, itemFile.itemExists("test_item"));
    }


    @Test
    //Loop coverage - loop 1 time
    public void itemExistsLoopCoverageOneTime() {
        System.out.println(itemFile.itemList.size());
        assertEquals(true, itemFile.itemExists("item_name"));
    }

    @Test
    //Loop coverage - loop 2 times
    public void itemExistsLoopCoverageTwoTimes() {
        itemFile.addItem("test_item", "seller", "buyer", 1, (float) 1);
        System.out.println(itemFile.itemList.size());
        assertEquals(true, itemFile.itemExists("test_item"));
    }

    @Test
    //Loop coverage - loop many times
    public void itemExistsLoopCoverageManyTimes() {
        itemFile.addItem("test_item", "seller", "buyer1", 1, (float) 1);
        itemFile.addItem("test_item1", "seller1", "buyer2", 1, (float) 1);
        itemFile.addItem("test_item2", "seller2", "buyer3", 1, (float) 1);
        itemFile.addItem("test_item3", "seller3", "buyer4", 1, (float) 1);
        itemFile.addItem("test_item4", "seller4", "buyer5", 1, (float) 1);
        System.out.println(itemFile.itemList.size());
        assertEquals(false, itemFile.itemExists("test_item9"));
    }


    @Test
    public void writeToAvailableItemFileTest() {
        assertEquals(true, itemFile.write());
    }
}