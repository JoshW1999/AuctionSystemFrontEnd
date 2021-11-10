import org.junit.Test;
import static org.junit.Assert.assertEquals;


public class ItemClassTest {
    Item item = new Item("itemname", "seller", "buyer", 100, (float)82.3);

    @Test
    public void writeItemShouldEqual(){
        assertEquals("itemname            seller          buyer           100 082.30", item.writeItem());
    }

    @Test
    public void itemConstructorItemNameCheck(){
        assertEquals("itemname", item.itemName);
    }

    @Test
    public void itemConstructorSellerCheck(){
        assertEquals("seller", item.seller);
    }

    @Test
    public void itemConstructorBuyerCheck(){
        assertEquals("buyer", item.buyer);
    }

    @Test
    public void itemConstructorDaysCheck(){
        assertEquals(100, item.days);
    }

    @Test
    public void itemConstructorPriceCheck(){
        assertEquals(82.3, item.price, 0.01);
    }
}