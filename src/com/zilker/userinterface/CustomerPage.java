package com.zilker.userinterface;

import java.util.List;

import com.zilker.databasecontroller.CustomerHandler;
import com.zilker.utility.GenerateOrderId;
import com.zilker.bean.GroceryOrder;
import com.zilker.bean.GroceryShop;
import com.zilker.bean.UserDetails;

public class CustomerPage {

	UserDetails customerDetails;
	GroceryOrder groceryOrder;
	CustomerHandler customerHandler = new CustomerHandler();
	String orderId;

	UserInputOutput userIO = new UserInputOutput();

	public CustomerPage(UserDetails customerDetails) {
		super();
		this.customerDetails = customerDetails;
	}

	public void initialActivity() {

		int choice;

		// Print Customer Welcome Message....
		userIO.printCustomerWelcome(customerDetails.getUserIdentityName());

		do {

			// Display Customer Options.
			choice = userIO.getChoiceFromCustomer();

			switch (choice) {
			case 1: {

				double latitude = userIO.getLatitude();
				double longitude = userIO.getLongitude();

				// Get the near by shops to the User
				List<GroceryShop> listOfShops = customerHandler.displayNearByGroceryShops(latitude, longitude,
						customerDetails.getLocation());
				if (!listOfShops.isEmpty()) {

					// Select one Shop from the list
					String shopId = userIO.getOneNearByShops(listOfShops);

					// Display Grocery Items from the shop
					customerHandler.displayGroceryItems(shopId);

					// Select Grocery Items from the list with Quantity
					List<GroceryOrder> groceryOrderList = userIO.getItemListToPlaceOrder();

					// Generate Unique Order Id for the customer
					orderId = GenerateOrderId.getUniqueOrderId();

					// Get Confirmation from the User
					int confirmation = userIO.getConfirmation();

					if (confirmation == 1) {
						// Place the Order
						customerHandler.placeOrder(orderId, groceryOrderList, customerDetails.getUserId(), shopId);
						userIO.orderPlacedMessage();
					} else {
						userIO.orderCancelledMessage();
					}

				} else {
					System.out.println("There is no Shops Available near by ");
				}

				break;
			}
			case 2: {

				break;
			}
			case 3: {

				// Customer Orders......
				customerHandler.displayCustomerOrders(customerDetails.getUserId());

				String ExpandOrderDetails = userIO.getExpanOrderDetails();

				// Customer Order Details.....
				if (!ExpandOrderDetails.equalsIgnoreCase("exit")) {

					String selectOrderId = ExpandOrderDetails;

					do {
						selectOrderId = customerHandler.displayCustomerOrderDetails(selectOrderId,
								customerDetails.getUserId());
					} while (!selectOrderId.equalsIgnoreCase("exit"));

					System.out.println("Exiting...");

				}

				break;
			}
			case 4: {

				break;
			}
			case 5: {
				System.out.println("Thank You for using this App....");

			}

			}

		} while (choice != 5);

		return;
	}
}
