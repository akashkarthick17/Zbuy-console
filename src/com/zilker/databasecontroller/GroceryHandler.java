package com.zilker.databasecontroller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Scanner;

import com.zilker.utility.DatabaseConnection;
import com.zilker.bean.GroceryItems;

public class GroceryHandler {

	Statement statement;
	PreparedStatement preparedstatement;
	ResultSet resultSet;
	DatabaseConnection dbConnection = new DatabaseConnection();
	final String CUSTOMER = "customer";

	// Get Connection
	Connection connection;

	public void retrieveGroceryOrders(String GroceryId) {
		System.out.println("Enter Order Id for Order Details...OR Enter exit");

		System.out.println();

		System.out.format("%20s %15s %15s", "Order Id", "Ordered On", "Status");
		System.out.println();
		System.out.println("---------------------------------------------------------------");

		try {
			connection = dbConnection.getConnection();

			preparedstatement = connection
					.prepareStatement("SELECT OrderId, orderDate,Status FROM orders WHERE GroceryId=?");
			preparedstatement.setString(1, GroceryId);
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

	public String displayGroceryOrderDetails(String orderId, String groceryId) {
		System.out.format("%15s %15s %15s %15s %15s", "Item Id", "Item Name", "Quantity", "Units", "Price");
		System.out.println();
		System.out
				.println("------------------------------------------------------------------------------------------");

		try {
			connection = dbConnection.getConnection();

			preparedstatement = connection.prepareStatement(
					"SELECT i.itemid,i.itemname,o.quantity,i.units,o.unitprice FROM orderdetails o,items i WHERE o.itemId=i.itemId AND o.orderId=? and o.groceryid=?");
			preparedstatement.setString(1, orderId);
			preparedstatement.setString(2, groceryId);
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

		System.out.println("Enter Order Id for Order Details or Enter exit");
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);

		return scanner.next();
	}

	public void addGroceryItemToDatabase(List<GroceryItems> groceryItemsList, String groceryId) {

		String itemName, unit;
		int quantity, itemId;
		double price;

		try {
			connection = dbConnection.getConnection();

			for (GroceryItems groceryItem : groceryItemsList) {

				itemName = groceryItem.getItemName();
				itemId = groceryItem.getItemId();
				price = groceryItem.getItemPrice();
				quantity = groceryItem.getQuantity();
				unit = groceryItem.getUnit();
				
				
				System.out.println("Insert... "+itemId+" "+itemName);

				preparedstatement = connection.prepareStatement("INSERT INTO ITEMS VALUES(?,?,?,?,?,?) ");
				preparedstatement.setInt(1, itemId);
				preparedstatement.setString(2, itemName);
				preparedstatement.setDouble(3, price);
				preparedstatement.setInt(4, quantity);
				preparedstatement.setString(5, unit);
				preparedstatement.setString(6, groceryId);
				preparedstatement.executeUpdate();

			}

			System.out.println("Items inserted Successfully....");

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			closeConnection();
		}

	}

	public void retrieveGroceryItems(String groceryId) {

		System.out.format("%15s %15s %15s %15s %15s", "Item Id", "Item Name", "Quantity", "Units", "Price");
		System.out.println();
		System.out
				.println("------------------------------------------------------------------------------------------");

		try {

			connection = dbConnection.getConnection();

			preparedstatement = connection.prepareStatement("SELECT * FROM ITEMS WHERE groceryId=?");
			preparedstatement.setString(1, groceryId);
			resultSet = preparedstatement.executeQuery();

			while (resultSet.next()) {

				System.out.format("%15s %15s %15s %15s %15s", resultSet.getInt("ItemId"),
						resultSet.getString("ItemName"), resultSet.getInt("quantity"), resultSet.getString("units"),
						resultSet.getDouble("itemprice"));
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

	}

	public void deliverOrder(String orderId) {
		System.out.println("Enter Supplier Id to deliver: ");
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		String supplierId = scanner.next();

		try {

			connection = dbConnection.getConnection();

			preparedstatement = connection.prepareStatement("UPDATE order SET supplierid=?, status=?  WHERE orderid=?");
			preparedstatement.setString(1, supplierId);
			preparedstatement.setString(2, "Delivered");
			preparedstatement.setString(3, orderId);

			preparedstatement.executeUpdate();

			System.out.println("Order Will be Delivered Soon...");

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			closeConnection();
		}

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
