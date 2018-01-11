package com.zilker.bean;

import com.zilker.exceptionhandling.InvalidPhoneNumberException;
import com.zilker.exceptionhandling.InvalidUserNameException;

public class UserRegister implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private String userName, password, Name, PhoneNumber, location;

	// Customer Login
	public UserRegister() {
		super();
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) throws InvalidUserNameException {

		if (userName.matches("([a-z0-9]+)[@]([a-z]+)[.][a-z]+")) {

			this.userName = userName;
		} else {

			throw new InvalidUserNameException("User Name is not Valid.");

		}

	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getPhoneNumber() {
		return PhoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) throws InvalidPhoneNumberException {

		if (phoneNumber.matches("([0-9]+){10}")) {

			this.PhoneNumber = phoneNumber;
		} else {

			throw new InvalidPhoneNumberException("Phone Number is not Valid.");
		}

	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

}
