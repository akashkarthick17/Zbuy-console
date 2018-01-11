package com.zilker.userinterface;

import com.zilker.databasecontroller.LoginManager;
import com.zilker.bean.UserDetails;
import com.zilker.bean.UserRegister;

public class Init {

	public static void main(String args[]) {

		UserInputOutput userIO = new UserInputOutput();
		LoginManager loginManager = new LoginManager();
		UserRegister userRegister = new UserRegister();
		int loginOrReg;
		String userName, password;
		UserDetails userDetails = new UserDetails();

		do {
			// Welcome Message
			userIO.welcomeMessage();

			// Choose Login or Register
			loginOrReg = userIO.loginOrRegister();

			// Login if 1 else Register
			if (loginOrReg == 1) {

				// Get Login Credentials
				userName = userIO.getUserName();
				password = userIO.getPassword();

				// Validating Login Credentials
				if (loginManager.isUserValid(userName, password)) {

					userIO.printLoginSuccess();

					userDetails = loginManager.retrieveUserDetails(userName);

					// Switch User based on User Type.
					if (userDetails.getUserType().equals("customer")) {

						CustomerPage customer = new CustomerPage(userDetails);
						customer.initialActivity();

					} else if (userDetails.getUserType().equals("owner")) {

						OwnerPage owner = new OwnerPage(userDetails);
						owner.initialActivity();

					}

				} else {

					userIO.printLoginError();

				}

			} else if (loginOrReg == 2) {

				// Get User Details and Register
				userRegister = userIO.getUserDetails();

				// Register User in the Database
				if (userRegister != null) {
					loginManager.registerUser(userRegister);
				}

			} else if (loginOrReg == 3) {
				System.out.println("Good Bye...");
			}
		} while (loginOrReg != 3);

	}

}
