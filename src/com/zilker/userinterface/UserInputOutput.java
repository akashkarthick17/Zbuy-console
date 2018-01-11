package com.zilker.userinterface;

import java.util.logging.*;

import com.zilker.bean.*;

import java.util.*;

public class UserInputOutput {

	Logger logger = Logger.getLogger(Init.class.getName());

	Scanner scanner = new Scanner(System.in);

	public void welcomeMessage() {

		logger.log(Level.INFO, "Welcome to Zbuy App \n");

	}

	public int loginOrRegister() {

		logger.log(Level.INFO, "\n 1. Login \n 2. Register \n 3. Exit");

		int type = scanner.nextInt();

		return type;

	}

	public String getUserName() {

		logger.log(Level.INFO, "\n Enter User Name :");

		String userName = scanner.next();

		return userName;

	}

	public String getPassword() {

		logger.log(Level.INFO, "\n Enter Password :");
		String password = scanner.next();

		return password;

	}

	public void printLoginError() {

		logger.log(Level.INFO, "\n Invalid Login Credentials... \n Check your UserName and Password.");

	}

	public void printLoginSuccess() {

		logger.log(Level.INFO, "\n Successfully Logged In...");

	}

	public void printRegisterSuccess() {

		logger.log(Level.INFO, "\n Successfully Registered...");

	}

	public UserRegister getUserDetails() {

		UserRegister userDetails = new UserRegister();

		String userName, password, name, phoneNumber, location;

		logger.log(Level.INFO, "\n Enter User Name(Email-Id) :");
		userName = scanner.next();
		logger.log(Level.INFO, "\n Enter Password : ");
		password = scanner.next();
		logger.log(Level.INFO, "\n Enter Your Name : ");
		name = scanner.next();
		logger.log(Level.INFO, "\n Enter Your Phone Number : ");
		phoneNumber = scanner.next();
		logger.log(Level.INFO, "\n Enter Your Location(City) : ");
		location = scanner.next();

		try {

			userDetails.setUserName(userName);
			userDetails.setPassword(password);
			userDetails.setName(name);
			userDetails.setPhoneNumber(phoneNumber);
			userDetails.setLocation(location);
			
			// print Successful
			System.out.println("Registered Successfull.");
			return userDetails;
			
		} catch (Exception e) {

			System.out.println("Exception Occured " + e);
		}

		

		return null;

	}

	public void printCustomerWelcome(String userIdentityName) {

		// logger.log(Level.INFO , "\n Welcome "+userIdentityName);
		System.out.println("Welcome " + userIdentityName);

	}

	public int getChoiceFromCustomer() {
		System.out.println("1. Purchase by Shop.");
		System.out.println("2. Purchase by Grocery Item.");
		System.out.println("3. Your Orders.");
		System.out.println("4. Accont Settings.");
		System.out.println("5. Exit.");

		int choice = scanner.nextInt();
		return choice;

	}

	public float getLatitude() {
		System.out.println("Enter Latitude:");
		return scanner.nextFloat();
	}

	public float getLongitude() {
		System.out.println("Enter Longitude");
		return scanner.nextFloat();
	}

	public String getOneNearByShops(List<GroceryShop> listOfShops) {

		System.out.println("Select one Shop from the list");
		for (GroceryShop shop : listOfShops) {
			System.out.println("Shop-ID : " + shop.getGroceryId() + " \t Shop-Name : " + shop.getGroceryName());
		}

		System.out.println();
		System.out.println("Enter the Shop-Id : ");
		return scanner.next();

	}

	public List<GroceryOrder> getItemListToPlaceOrder() {

		List<GroceryOrder> groceryOrderList = new ArrayList<>();
		GroceryOrder groceryOrder;

		System.out.println("Please Enter Item Id and it's Quantity to Place Order. Enter 1 after completing");

		int itemId, quantity;

		do {
			System.out.println(" Enter Item Id : ");
			itemId = scanner.nextInt();

			if (itemId != 1) {
				System.out.println("Enter Quantity : ");
				quantity = scanner.nextInt();

				groceryOrder = new GroceryOrder();
				groceryOrder.setItemId(itemId);

				groceryOrder.setQuantity(quantity);

				groceryOrderList.add(groceryOrder);

			}

		} while (itemId != 1);

		return groceryOrderList;
	}

	public int getConfirmation() {

		System.out.println("Enter 1 to Place the Order OR 2 to Cancel the order");

		return scanner.nextInt();
	}

	public void orderPlacedMessage() {

		System.out.println("Your Order Placed Sucessfully....");
	}

	public void orderCancelledMessage() {

		System.out.println("Your Order has been Cancelled...");
	}

	public String getExpanOrderDetails() {

		return scanner.next();

	}

	// Owner Login

	public int getChoiceFromOwner() {

		System.out.println("1. Orders.");
		System.out.println("2. Add Grocery Item.");
		System.out.println("3. Update Grocery Item.");
		System.out.println("4. Delete Grocery Item.");
		System.out.println("5. View Grocery Item.");
		System.out.println("6. Log Out.");

		int choice = scanner.nextInt();
		return choice;

	}

	public List<GroceryItems> getDetailsToAddItem() {

		String itemName, unit;
		double price;
		int quantity, itemId;
		int max = 10000;
		int min = 1000;
		String condition = "";
		Random random = new Random();

		List<GroceryItems> groceryItemsToAddList = new ArrayList<>();
		GroceryItems groceryItemsToAdd;

		do {

			groceryItemsToAdd = new GroceryItems();

			itemId = min + random.nextInt(max);
			System.out.println("Item Id : " + itemId);

			System.out.println("Enter Item Name : ");
			itemName = scanner.next();
			System.out.println("Quantity : ");
			quantity = scanner.nextInt();
			System.out.println("Unit : ");
			unit = scanner.next();
			System.out.println("Price : ");
			price = scanner.nextDouble();

			groceryItemsToAdd.setItemId(itemId);
			groceryItemsToAdd.setItemName(itemName);
			groceryItemsToAdd.setItemPrice(price);
			;
			groceryItemsToAdd.setQuantity(quantity);
			groceryItemsToAdd.setUnit(unit);

			groceryItemsToAddList.add(groceryItemsToAdd);

			System.out.println("Enter next to continue or Exit to terminate.");
			condition = scanner.next();

		} while (condition.equalsIgnoreCase("next"));

		return groceryItemsToAddList;

	}
}
