package com.zilker.userinterface;

import com.zilker.databasecontroller.GroceryHandler;
import com.zilker.bean.*;

import java.util.List;
import java.util.Scanner;

public class OwnerPage {

	UserDetails ownerDetails = new UserDetails();
	UserInputOutput userIO = new UserInputOutput();

	List<GroceryItems> groceryItemList;
	GroceryHandler groceryHandler = new GroceryHandler();

	public OwnerPage(UserDetails userDetails) {
		super();
		this.ownerDetails = userDetails;
	}

	public void initialActivity() {

		int choice;

		// Print Customer Welcome Message....
		userIO.printCustomerWelcome(ownerDetails.getUserIdentityName());
		do {
			choice = userIO.getChoiceFromOwner();

			switch (choice) {
			case 1: {

				groceryHandler.retrieveGroceryOrders(ownerDetails.getUserId());

				String ExpandOrderDetails = userIO.getExpanOrderDetails();

				String deliver = "";

				// Customer Order Details.....
				if (!ExpandOrderDetails.equalsIgnoreCase("exit")) {

					String selectOrderId = ExpandOrderDetails;

					do {
						selectOrderId = groceryHandler.displayGroceryOrderDetails(selectOrderId,
								ownerDetails.getUserId());

					} while (!selectOrderId.equalsIgnoreCase("exit"));

					System.out.println("Exiting...");

					do {
						System.out.println("Enter deliver to Devlier Order or Enter Exit");
						@SuppressWarnings("resource")
						Scanner scanner = new Scanner(System.in);
						deliver = scanner.next();

						if (deliver.equals("deliver")) {

							System.out.println("Enter Order Id : ");
							String inputOrderId = scanner.next();
							groceryHandler.deliverOrder(inputOrderId);

						}
					} while (!deliver.equalsIgnoreCase("exit"));

				}

				break;
			}
			case 2: {

				// Get Item Name , Quantity , Unit, Price from the Owner.
				groceryItemList = userIO.getDetailsToAddItem();

				// Add Items to the Database
				groceryHandler.addGroceryItemToDatabase(groceryItemList, ownerDetails.getUserId());

				break;
			}
			case 3: {
				break;
			}
			case 4: {
				break;
			}
			case 5: {

				// Retrieve All Items from the shop
				groceryHandler.retrieveGroceryItems(ownerDetails.getUserId());

				break;
			}
			case 6: {
				
				System.out.println("Thank You for using Zbuy");
				break;
			}
			}

		} while (choice!=6);

		return ;
	}

}
