import org.junit.Test;
import static org.junit.Assert.*;

//String[] trans = {"04", "item_name", "admin", "admin", "00011"};

public class TransactionClassTest {
	private Transaction transaction;
	private AvailableItemFile itemFile = new AvailableItemFile("resources/availableItems.txt");
	private UserAccountFile accountFile = new UserAccountFile("resources/userAccounts.txt");
	
	@Test
	public void isValidTransaction_nullInput() {
		String[] transInfo = null;
		transaction = new Transaction(transInfo);
		assertEquals(false, transaction.isValidTransaction(itemFile, accountFile));
	}
	
	@Test
	public void isValidTransaction_notEnoughInfo() {
		String[] transInfo = {"01"};;
		transaction = new Transaction(transInfo);
		assertEquals(false, transaction.isValidTransaction(itemFile, accountFile));
	}

	@Test
	public void isValidTransaction_createUser() {
		String[] transInfo = {"01", "new_user", "FS", "10.0"};;
		transaction = new Transaction(transInfo);
		assertEquals(false, transaction.isValidTransaction(itemFile, accountFile));
	}

	@Test
	public void isValidTransaction_deleteUser() {
		String[] transInfo = {"02", "", "FS", "10.0"};;
		transaction = new Transaction(transInfo);
		assertEquals(false, transaction.isValidTransaction(itemFile, accountFile));
	}

	@Test
	public void isValidTransaction_addCredit() {
		String[] transInfo = {"06", "", "FS", "10.0"};;
		transaction = new Transaction(transInfo);
		assertEquals(false, transaction.isValidTransaction(itemFile, accountFile));
	}

	@Test
	public void isValidTransaction_endOfSession() {
		String[] transInfo = {"00", ""};;
		transaction = new Transaction(transInfo);
		assertEquals(false, transaction.isValidTransaction(itemFile, accountFile));
	}

	@Test
	public void isValidTransaction_emptyUserType() {
		String[] transInfo = {"06", "new_user", "", "10.0"};;
		transaction = new Transaction(transInfo);
		assertEquals(false, transaction.isValidTransaction(itemFile, accountFile));
	}

	@Test
	public void isValidTransaction_invalidUserType() {
		String[] transInfo = {"06", "new_user", "CC", "10.0"};;
		transaction = new Transaction(transInfo);
		assertEquals(false, transaction.isValidTransaction(itemFile, accountFile));
	}

	@Test
	public void isValidTransaction_emptyCredit() {
		String[] transInfo = {"06", "new_user", "FS", ""};;
		transaction = new Transaction(transInfo);
		assertEquals(false, transaction.isValidTransaction(itemFile, accountFile));
	}

	@Test
	public void isValidTransaction_invalidCredit() {
		String[] transInfo = {"06", "new_user", "FS", "hi3.5"};;
		transaction = new Transaction(transInfo);
		assertEquals(false, transaction.isValidTransaction(itemFile, accountFile));
	}

	@Test
	public void isValidTransaction_emptyBuyerName() {
		String[] transInfo = {"05", "", "admin", "3.5"};;
		transaction = new Transaction(transInfo);
		assertEquals(false, transaction.isValidTransaction(itemFile, accountFile));
	}

	@Test
	public void isValidTransaction_invalidBuyerName() {
		String[] transInfo = {"05", "stranger", "admin", "3.5"};;
		transaction = new Transaction(transInfo);
		assertEquals(false, transaction.isValidTransaction(itemFile, accountFile));
	}

	@Test
	public void isValidTransaction_emptySellerName() {
		String[] transInfo = {"05", "new_user", "", "3.5"};;
		transaction = new Transaction(transInfo);
		assertEquals(false, transaction.isValidTransaction(itemFile, accountFile));
	}

	@Test
	public void isValidTransaction_invalidSellerName() {
		String[] transInfo = {"05", "new_user", "stranger", "3.5"};;
		transaction = new Transaction(transInfo);
		assertEquals(false, transaction.isValidTransaction(itemFile, accountFile));
	}

	@Test
	public void isValidTransaction_emptyRefundCredit() {
		String[] transInfo = {"05", "new_user", "admin", ""};;
		transaction = new Transaction(transInfo);
		assertEquals(false, transaction.isValidTransaction(itemFile, accountFile));
	}

	@Test
	public void isValidTransaction_invalidRefundCredit() {
		String[] transInfo = {"05", "new_user", "admin", "hi5.5"};;
		transaction = new Transaction(transInfo);
		assertEquals(false, transaction.isValidTransaction(itemFile, accountFile));
	}

	@Test
	public void isValidTransaction_emptyItemName() {
		String[] transInfo = {"03", "", "admin", "5", "0.1"};;
		transaction = new Transaction(transInfo);
		assertEquals(false, transaction.isValidTransaction(itemFile, accountFile));
	}

	@Test
	public void isValidTransaction_emptyAdSellerName() {
		String[] transInfo = {"03", "item_name", "", "5", "0.1"};;
		transaction = new Transaction(transInfo);
		assertEquals(false, transaction.isValidTransaction(itemFile, accountFile));
	}

	@Test
	public void isValidTransaction_invalidAdSellerName() {
		String[] transInfo = {"03", "item_name", "stranger", "5", "0.1"};;
		transaction = new Transaction(transInfo);
		assertEquals(false, transaction.isValidTransaction(itemFile, accountFile));
	}

	@Test
	public void isValidTransaction_invalidAdDays() {
		String[] transInfo = {"03", "item_name", "admin", "-5", "0.1"};;
		transaction = new Transaction(transInfo);
		assertEquals(false, transaction.isValidTransaction(itemFile, accountFile));
	}

	@Test
	public void isValidTransaction_invalidMinAd() {
		String[] transInfo = {"03", "item_name", "admin", "5", "hi0.1"};;
		transaction = new Transaction(transInfo);
		assertEquals(false, transaction.isValidTransaction(itemFile, accountFile));
	}

	@Test
	public void isValidTransaction_notPositiveMinAd() {
		String[] transInfo = {"03", "item_name", "admin", "5", "-0.1"};;
		transaction = new Transaction(transInfo);
		assertEquals(false, transaction.isValidTransaction(itemFile, accountFile));
	}

	@Test
	public void isValidTransaction_emptyBidItemName() {
		String[] transInfo = {"04", "", "admin", "new_user", "5.2"};;
		transaction = new Transaction(transInfo);
		assertEquals(false, transaction.isValidTransaction(itemFile, accountFile));
	}

	@Test
	public void isValidTransaction_invalidBidItemName() {
		String[] transInfo = {"04", "strange item", "admin", "new_user", "5.2"};;
		transaction = new Transaction(transInfo);
		assertEquals(false, transaction.isValidTransaction(itemFile, accountFile));
	}

	@Test
	public void isValidTransaction_emptyBidSellerName() {
		String[] transInfo = {"04", "item_name", "", "new_user", "5.2"};;
		transaction = new Transaction(transInfo);
		assertEquals(false, transaction.isValidTransaction(itemFile, accountFile));
	}

	@Test
	public void isValidTransaction_invalidBidSellerName() {
		String[] transInfo = {"04", "item_name", "stranger", "new_user", "5.2"};;
		transaction = new Transaction(transInfo);
		assertEquals(false, transaction.isValidTransaction(itemFile, accountFile));
	}

	@Test
	public void isValidTransaction_emptyBidBuyerName() {
		String[] transInfo = {"04", "item_name", "admin", "", "5.2"};;
		transaction = new Transaction(transInfo);
		assertEquals(false, transaction.isValidTransaction(itemFile, accountFile));
	}

	@Test
	public void isValidTransaction_invalidBidBuyerName() {
		String[] transInfo = {"04", "item_name", "admin", "stranger", "5.2"};;
		transaction = new Transaction(transInfo);
		assertEquals(false, transaction.isValidTransaction(itemFile, accountFile));
	}

	@Test
	public void isValidTransaction_invalidBidAmount1() {
		String[] transInfo = {"04", "item_name", "admin", "new_user", "hi5.2"};;
		transaction = new Transaction(transInfo);
		assertEquals(false, transaction.isValidTransaction(itemFile, accountFile));
	}

	@Test
	public void isValidTransaction_invalidBidAmount2() {
		String[] transInfo = {"04", "item_name", "admin", "new_user", "-5.2"};;
		transaction = new Transaction(transInfo);
		assertEquals(false, transaction.isValidTransaction(itemFile, accountFile));
	}


	@Test
	public void isValidTransaction_aValidBid() {
		String[] transInfo = {"04", "item_name", "admin", "new_user", "5.2"};;
		transaction = new Transaction(transInfo);
		assertEquals(true, transaction.isValidTransaction(itemFile, accountFile));
	}
}
