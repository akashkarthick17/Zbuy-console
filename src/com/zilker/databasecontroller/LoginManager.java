package com.zilker.databasecontroller;

import com.zilker.utility.DatabaseConnection;
import com.zilker.bean.UserDetails;
import com.zilker.bean.UserRegister;

import java.sql.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginManager {

	Statement statement;
	PreparedStatement preparedstatement;
	ResultSet resultSet;
	DatabaseConnection dbConnection = new DatabaseConnection();
	final String CUSTOMER = "customer";

	Connection connection;

	public boolean isUserValid(String userName, String password) {

		String tempPassword = "";

		try {

			// Get Connection
			connection = dbConnection.getConnection();

			statement = connection.createStatement();

			resultSet = statement.executeQuery("SELECT password FROM login where userName='" + userName + "'");

			if (resultSet.next()) {

				tempPassword = resultSet.getString("Password");
			}

		} catch (SQLException e) {

			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			closeConnection();
		}

		if (tempPassword.equals(password)) {

			return true;
		}

		return false;
	}

	public String getUserType(String userName) {

		String userType = "";

		try {
			// Get Connection
			connection = dbConnection.getConnection();

			// connection = dbConnection.getConnection();
			statement = connection.createStatement();

			resultSet = statement.executeQuery("SELECT userType FROM login where userName='" + userName + "'");

			if (resultSet.next()) {

				userType = resultSet.getString("UserType");

			}

		} catch (SQLException e) {

			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			closeConnection();
		}

		return userType;
	}

	public void registerUser(UserRegister userDetails) {

		String userName, password, name, phoneNumber, location;

		userName = userDetails.getUserName();
		password = userDetails.getPassword();
		name = userDetails.getName();
		phoneNumber = userDetails.getPhoneNumber();
		location = userDetails.getLocation();

		try {
			// Get Connection
			connection = dbConnection.getConnection();

			// calculate the Hash Value for UserName to create User Id
			MessageDigest mDigest = MessageDigest.getInstance("SHA-1");
			byte[] result = mDigest.digest(userName.getBytes());
			StringBuffer hash = new StringBuffer();
			for (int i = 0; i < result.length; i++) {
				hash.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
			}

			// System.out.println( hash.toString());

			// Insert into login table
			preparedstatement = connection.prepareStatement("INSERT INTO LOGIN VALUES (?,?,?,?)");

			preparedstatement.setString(1, hash.toString());
			preparedstatement.setString(2, userName);
			preparedstatement.setString(3, password);
			preparedstatement.setString(4, CUSTOMER);

			preparedstatement.executeUpdate();

			// Insert into customer table
			preparedstatement = connection.prepareStatement("INSERT INTO CUSTOMER VALUES (?,?,?,?)");

			preparedstatement.setString(1, hash.toString());
			preparedstatement.setString(2, name);
			preparedstatement.setString(3, phoneNumber);
			preparedstatement.setString(4, location);

			preparedstatement.executeUpdate();

		} catch (SQLException e) {

			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {

			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			closeConnection();
		}

	}

	public UserDetails retrieveUserDetails(String userName) {

		UserDetails customerDetails = new UserDetails();

		String userIdentityName, userId, userType, location;

		try {
			// Get Connection
			connection = dbConnection.getConnection();

			// connection = dbConnection.getConnection();
			statement = connection.createStatement();

			resultSet = statement.executeQuery("SELECT UserType FROM login WHERE UserName='" + userName + "'");

			resultSet.next();
			String getUserType = resultSet.getString("UserType");

			if (getUserType.equals("customer")) {

				resultSet = statement.executeQuery(
						"SELECT * FROM customer,login where customer.CustomerId = login.UserId  and UserName ='"
								+ userName + "'");
				if (resultSet.next()) {
					userIdentityName = resultSet.getString("CustomerName");
					userType = resultSet.getString("UserType");
					userId = resultSet.getString("CustomerId");
					location = resultSet.getString("CustomerLocation");

					customerDetails.setUserIdentityName(userIdentityName);
					customerDetails.setUserId(userId);
					customerDetails.setUserName(userName);
					customerDetails.setUserType(userType);
					customerDetails.setLocation(location);

				}

			} else {
				resultSet = statement.executeQuery(
						"SELECT * FROM grocery,login where grocery.GroceryId = login.UserId  and UserName ='" + userName
								+ "'");

				if (resultSet.next()) {
					userIdentityName = resultSet.getString("GroceryName");
					userType = resultSet.getString("UserType");
					userId = resultSet.getString("GroceryId");
					location = resultSet.getString("GroceryLocation");

					customerDetails.setUserIdentityName(userIdentityName);
					customerDetails.setUserId(userId);
					customerDetails.setUserName(userName);
					customerDetails.setUserType(userType);
					customerDetails.setLocation(location);

				}

			}

		} catch (SQLException e) {

			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			closeConnection();
		}

		return customerDetails;
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
