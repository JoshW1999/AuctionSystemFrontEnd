/**
 * Transaction class is used to verify whether the transaction code from the
 * merged daily transaction is a valid transaction or not.
 */
class Transaction {
	String[] transInfo; // Transaction code that is split into array of strings

	/*
	 * Purpose: Constructor of transaction class 
	 * Input: String[] transInfo: List of strings of transaction code
	 * Output: None
	 */
	public Transaction(String[] transInfo) {
		this.transInfo = transInfo;
	}

	/*
	 * Purpose: Verify that the transInfo is a valid transaction 
	 * Input: 
	 * 		AvailableItemFile itemFile: object handling items
	 * 		UserAccountFile accountFile: object handling accounts
	 * Output: 
	 * 		Boolean check of the verification of the transaction
	 * code
	 */
	boolean isValidTransaction(AvailableItemFile itemFile, UserAccountFile accountFile) {
		if (transInfo == null) {
			System.out.print("Transaction info is null.");
			System.out.println("\nERROR: Transaction information can not be null.\n");
			return false;
		}

		if (transInfo.length < 2) {
			for(int i = 0; i < this.transInfo.length; i++) System.out.print(transInfo[i]+ " ");
			System.out.println("\nERROR: Missing parts of transaction information.\n");
			return false;
		}

		switch (transInfo[0]) {
		case "01":
			if(accountFile.userExists(transInfo[1])){
				for(int i = 0; i < this.transInfo.length; i++) System.out.print(transInfo[i]+ " ");
				System.out.println("\nERROR: Username already exists\n");
				return false;
			}
		case "02":
		case "06":
		case "00":
			// validate user name
			if (isNullOrEmpty(transInfo[1])) {
				for(int i = 0; i < this.transInfo.length; i++) System.out.print(transInfo[i]+ " ");
				System.out.println("\nERROR: Username can't be null or empty\n");
				return false;
			}

			// validate user type
			if (isNullOrEmpty(transInfo[2]) ) {
				for(int i = 0; i < this.transInfo.length; i++) System.out.print(transInfo[i]+ " ");
				System.out.println("\nERROR: User type can't be null or empty\n");
				return false;
			}

			if(!transInfo[2].equals("AA") && !transInfo[2].equals("FS")
					&& !transInfo[2].equals("BS") && !transInfo[2].equals("SS")){
				for(int i = 0; i < this.transInfo.length; i++) System.out.print(transInfo[i]+ " ");
				System.out.println("\nERROR: Invalid user type\n");	
				return false;
			}


			// validate available credit
			if (isNullOrEmpty(transInfo[3])){
				for(int i = 0; i < this.transInfo.length; i++) System.out.print(transInfo[i]+ " ");
				System.out.println("\nERROR: User credit can't empty\n");	
				return false;
			}

			if(!isValidNumber(transInfo[3])){
				for(int i = 0; i < this.transInfo.length; i++) System.out.print(transInfo[i]+ " ");
				System.out.println("\nERROR: Invalid user credit format\n");	
				return false;
			}
			break;
		case "05":
			// validate buyer's user name
			if (isNullOrEmpty(transInfo[1]) ) {
				for(int i = 0; i < this.transInfo.length; i++) System.out.print(transInfo[i]+ " ");
				System.out.println("\nERROR: Buyer username can't be null or empty\n");	
				return false;
			}

			if(!accountFile.userExists(transInfo[1])){
				for(int i = 0; i < this.transInfo.length; i++) System.out.print(transInfo[i]+ " ");
				System.out.println("\nERROR: Buyer does not exists\n");	
				return false;
			}

			// validate seller's user name
			if (isNullOrEmpty(transInfo[2])) {
				for(int i = 0; i < this.transInfo.length; i++) System.out.print(transInfo[i]+ " ");
				System.out.println("\nERROR: Seller username can't be null or empty\n");	
				return false;
			}

			if(!accountFile.userExists(transInfo[2])){
				for(int i = 0; i < this.transInfo.length; i++) System.out.print(transInfo[i]+ " ");
				System.out.println("\nERROR: Seller does not exists\n");	
				return false;
			}

			// validate refund credit
			if (isNullOrEmpty(transInfo[3])) {
				for(int i = 0; i < this.transInfo.length; i++) System.out.print(transInfo[i]+ " ");
				System.out.println("\nERROR: Refund credit can not be empty.\n");
				return false;
			}
			if(!isValidNumber(transInfo[3])){
				for(int i = 0; i < this.transInfo.length; i++) System.out.print(transInfo[i]+ " ");
				System.out.println("\nERROR: Invalid refund credit format\n");
				return false;
			}
			break;
			
		case "03":
			// validate item name
			if (isNullOrEmpty(transInfo[1])) {
				for(int i = 0; i < this.transInfo.length; i++) System.out.print(transInfo[i]+ " ");
				System.out.println("\nERROR: Item name can't be null or empty\n");
				return false;
			}

			// validate seller's user name
			if (isNullOrEmpty(transInfo[2])) {
				for(int i = 0; i < this.transInfo.length; i++) System.out.print(transInfo[i]+ " ");
				System.out.println("\nERROR: Advertise seller name can't be null or empty\n");
				return false;
			}
			if(!accountFile.userExists(transInfo[2])){
				for(int i = 0; i < this.transInfo.length; i++) System.out.print(transInfo[i]+ " ");
				System.out.println("\nERROR: Advertise seller does not exists\n");
				return false;
			}
			// validate number of days to auction
			if (!isValidPositiveInteger(transInfo[3])) {
				for(int i = 0; i < this.transInfo.length; i++) System.out.print(transInfo[i]+ " ");
				System.out.println("\nERROR: Number of advertise days has to be positive integer\n");
				return false;
			}
			// validate minimum bid
			if (!isValidNumber(transInfo[4])) {
				for(int i = 0; i < this.transInfo.length; i++) System.out.print(transInfo[i]+ " ");
				System.out.println("\nERROR: Invalid advertise format\n");
				return false;
			}
			if (Double.parseDouble(transInfo[4]) <= 0.0) {
				for(int i = 0; i < this.transInfo.length; i++) System.out.print(transInfo[i]+ " ");
				System.out.println("\nERROR: Minimum bid has to be positive\n");
				return false;
			}

			break;
		case "04":
			// validate item name
			if (isNullOrEmpty(transInfo[1])) {
				for(int i = 0; i < this.transInfo.length; i++) System.out.print(transInfo[i]+ " ");
				System.out.println("\nERROR: Bid item name can't be null or empty'\n");
				return false;
			}
			if(!itemFile.itemExists(transInfo[1])){
				for(int i = 0; i < this.transInfo.length; i++) System.out.print(transInfo[i]+ " ");
				System.out.println("\nERROR: Item does not exists\n");
				return false;
			}
			// validate seller's user name
			if (isNullOrEmpty(transInfo[2])) {
				for(int i = 0; i < this.transInfo.length; i++) System.out.print(transInfo[i]+ " ");
				System.out.println("\nERROR: Bid seller username can't be null or empty\n");
				return false;
			}
			if(!accountFile.userExists(transInfo[2])){
				for(int i = 0; i < this.transInfo.length; i++) System.out.print(transInfo[i]+ " ");
				System.out.println("\nERROR: Bid seller does not exists\n");
				return false;
			}
			// validate buyer's user name
			if (isNullOrEmpty(transInfo[3])) {
				for(int i = 0; i < this.transInfo.length; i++) System.out.print(transInfo[i]+ " ");
				System.out.println("\nERROR: Bid buyer username can't be null or empty\n");
				return false;
			}
			if(!accountFile.userExists(transInfo[3])){
				for(int i = 0; i < this.transInfo.length; i++) System.out.print(transInfo[i]+ " ");
				System.out.println("\nERROR: Bid buyer does not exists\n");
				return false;
			}
			// validate new bid
			if (!isValidNumber(transInfo[4]) || Double.parseDouble(transInfo[4]) <= 0.0) {
				for(int i = 0; i < this.transInfo.length; i++) System.out.print(transInfo[i]+ " ");
				System.out.println("\nERROR: Invalid bid amount.\n");
				return false;
			}

			break;
		}

		return true;
	}

	/*
	 * Purpose: Verify that a string is not null or empty
	 * Input: 
	 * 		String s: the string to be verified
	 * Output: 
	 * 		Boolean result of validation
	 * code
	 */
	private boolean isNullOrEmpty(String s) {
		if (s == null || s.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}

	/*
	 * Purpose: Verify that a string can be parsed into a number
	 * Input: 
	 * 		String s: the string to be verified
	 * Output: 
	 * 		Boolean result of validation
	 * code
	 */
	private boolean isValidNumber(String s) {
		try {
			Double.parseDouble(s);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

	/*
	 * Purpose: Verify that a string can be parsed into a positive integer
	 * Input: 
	 * 		String s: the string to be verified
	 * Output: 
	 * 		Boolean result of validation
	 * code
	 */
	private boolean isValidPositiveInteger(String s) {
		try {
			int value = Integer.parseInt(s);

			if (value <= 0) {
				return false;
			}
		} catch (NumberFormatException e) {
			return false;
		}

		return true;
	}

}
