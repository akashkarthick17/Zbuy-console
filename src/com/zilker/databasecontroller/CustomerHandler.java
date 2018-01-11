package com.zilker.databasecontroller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.zilker.utility.CoordinatesToDistance;
import com.zilker.utility.DatabaseConnection;
import com.zilker.utility.DateFormat;
import com.zilker.bean.GroceryItems;
import com.zilker.bean.GroceryOrder;
import com.zilker.bean.GroceryShop;

public class CustomerHandler {

	Statement statement;
	PreparedStatement preparedstatement;
	ResultSet resultSet;
	DatabaseConnection dbConnection = new DatabaseConnection();
	final String CUSTOMER = "customer";

	// Get Connection
	Connection connection;

	public List<GroceryShop> displayNearByGroceryShops(double userLatitude, double userLongitude, String location) {

		List<GroceryShop> listOfShops = new ArrayList<>();

		try {
			connection = dbConnection.getConnection();

			statement = connection.createStatement();
			resultSet = statement.executeQuery("SELECT * FROM GROCERY where GroceryLocation='" + location + "'");
			while (resultSet.next()) {

				double groceryLatitude = resultSet.getFloat("GroceryLatitude");
				double groceryLongitude = resultSet.getFloat("GroceryLongitude");

				if (CoordinatesToDistance.distanceInKilometers(userLatitude, groceryLatitude, groceryLongitude,
						userLongitude) <= 5) {

					GroceryShop groceryShop = new GroceryShop();

					groceryShop.setGroceryId(resultSet.getString("GroceryId"));
					groceryShop.setGroceryName(resultSet.getString("GroceryName"));

					listOfShops.add(groceryShop);

				}

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			closeConnection();
		}

		return listOfShops;
	}

	public void displayGroceryItems(String shopId) {

		// GroceryItems groceryItem = new GroceryItems();

		String itemId, itemName, itemUnits;
		int itemQuantity;
		float itemPrice;

		try {
			connection = dbConnection.getConnection();
			statement = connection.createStatement();
			resultSet = statement.executeQuery("SELECT * FROM items where GroceryId=" + shopId);

			System.out.format("%10s %15s %10s %10s %10s", "Item Id", "Item Name", "Quantity", "Units", "Price");
			System.out.println();
			System.out.println("---------------------------------------------------------------");

			while (resultSet.next()) {

				itemId = resultSet.getString("itemId");
				itemName = resultSet.getString("ItemName");
				itemPrice = resultSet.getFloat("ItemPrice");
				itemQuantity = resultSet.getInt("Quantity");
				itemUnits = resultSet.getString("Units");

				System.out.format("%10s %15s %10s %10s %10s", itemId, itemName, itemQuantity, itemUnits, itemPrice);
				System.out.println();

			}
			System.out.println("---------------------------------------------------------------");

		} catch (SQLException e) {

			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			closeConnection();
		}

	}

	public void placeOrder(String orderId, List<GroceryOrder> groceryOrderList, String customerId, String groceryId) {

		String orderDate = DateFormat.getCurrentFormattedDate();

		try {

			connection = dbConnection.getConnection();

			preparedstatement = connection.prepareStatement("INSERT INTO orders VALUES(?,?,?,?,?,?)");
			preparedstatement.setString(1, orderId);
			preparedstatement.setString(2, orderDate);
			preparedstatement.setString(3, "Pending");
			preparedstatement.setString(4, customerId);
			preparedstatement.setString(5, groceryId);
			preparedstatement.setNull(6, Types.VARCHAR);

			preparedstatement.executeUpdate();

			for (GroceryOrder groceryOrder : groceryOrderList) {

				preparedstatement = null;
				preparedstatement = connection
						.prepareStatement("SELECT * FROM items WHERE ItemId=" + groceryOrder.getItemId());
				resultSet = preparedstatement.executeQuery();

				resultSet.next();

				double itemPrice = resultSet.getDouble("ItemPrice");
				double totalQuantityPrice = groceryOrder.getQuantity() * itemPrice;

				groceryOrder.setItemPrice(totalQuantityPrice);

			}

			for (GroceryOrder groceryOrder : groceryOrderList) {
				preparedstatement = null;
				preparedstatement = connection.prepareStatement("INSERT INTO orderdetails VALUES(?,?,?,?)");
				preparedstatement.setString(1, orderId);
				preparedstatement.setInt(2, groceryOrder.getItemId());
				preparedstatement.setInt(3, groceryOrder.getQuantity());
				preparedstatement.setDouble(4, groceryOrder.getItemPrice());

				preparedstatement.executeUpdate();

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			closeConnection();
		}

	}

	public void displayCustomerOrders(String customerId) {

		System.out.println("Enter Order Id for Order Details...OR Enter exit");

		System.out.println();

		System.out.format("%20s %15s %15s", "Order Id", "Ordered On", "Status");
		System.out.println();
		System.out.println("---------------------------------------------------------------");

		try {

			connection = dbConnection.getConnection();

			preparedstatement = connection
					.prepareStatement("SELECT OrderId, orderDate,Status FROM orders WHERE customerId=?");

			preparedstatement.setString(1, customerId);
			resultSet = preparedstatement.executeQuery();

			while (resultSet.next()) {

				System.out.format("%20s %15s %15s", resultSet.getString(1), resultSet.getString(2),
						resultSet.getString(3));
				System.out.println();

			}
			System.out.println("---------------------------------------------------------------");

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			closeConnection();
		}

	}

	public String displayCustomerOrderDetails(String orderId, String customerId) {

		System.out.format("%15s %15s %15s %15s %15s", "Item Id", "Item Name", "Quantity", "Units", "Price");
		System.out.println();
		System.out
				.println("------------------------------------------------------------------------------------------");

		try {

			connection = dbConnection.getConnection();

			preparedstatement = connection.prepareStatement(
					"SELECT i.itemid,i.itemname,o.quantity,i.units,o.unitprice FROM orderdetails o,items i WHERE o.itemId=i.itemId AND o.orderId=?");

			preparedstatement.setString(1, orderId);

			resultSet = preparedstatement.executeQuery();

			while (resultSet.next()) {

				System.out.format("%15s %15s %15s %15s %15s", resultSet.getInt("ItemId"),
						resultSet.getString("ItemName"), resultSet.getInt("quantity"), resultSet.getString("units"),
						resultSet.getDouble("UnitPrice"));
				System.out.println();

			}
			System.out.println(
					"------------------------------------------------------------------------------------------");

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			closeConnection();
		}

		System.out.println("Enter Order Id for Order Details...OR Enter exit");
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);

		return scanner.next();
	}

	public List<GroceryItems> getItemsAvailable(String setItemName) {

		int itemId;
		double itemPrice;
		int quantity;
		String units, itemName;

		GroceryItems groceryItem;
		List<GroceryItems> groceryItemList = new ArrayList<>();

		try {

			preparedstatement = connection.prepareStatement("SELECT * FROM ITEMS WHERE ItemName LIKE '%?%'");
			preparedstatement.setString(1, setItemName);
			resultSet = preparedstatement.executeQuery();

			while (resultSet.next()) {

				groceryItem = new GroceryItems();

				itemId = resultSet.getInt("itemId");
				itemName = resultSet.getString("itemName");
				itemPrice = resultSet.getDouble("itemPrice");
				quantity = resultSet.getInt("quantity");
				units = resultSet.getString("units");

				groceryItem.setItemId(itemId);
				groceryItem.setItemName(itemName);
				groceryItem.setItemPrice(itemPrice);
				groceryItem.setQuantity(quantity);
				groceryItem.setUnit(units);

				groceryItemList.add(groceryItem);
			}

			return groceryItemList;

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}

	public void closeConnection() {

		try {
			connection.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
