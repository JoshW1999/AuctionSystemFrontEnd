import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class UserClassTest {
    User user = new User("admin", "AA", (float)23.45);

    @Test
    public void userConstructorUsernameCheck(){
        assertEquals("admin", user.username);
    }

    @Test
    public void userConstructorTypeCheck(){
        assertEquals("AA", user.type);
    }

    @Test
    public void userConstructorCreditCheck(){
        assertEquals(23.45, user.credit, 0.01);
    }

    @Test
    public void writeUserShouldEqual(){
        assertEquals("admin           AA 000023.45", user.writeUser());
    }
}