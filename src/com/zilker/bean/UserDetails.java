package com.zilker.bean;

public class UserDetails implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private String userName, userId, userIdentityName, userType, Location;

	public UserDetails() {
		super();
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserIdentityName() {
		return userIdentityName;
	}

	public void setUserIdentityName(String userIdentityName) {
		this.userIdentityName = userIdentityName;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getLocation() {
		return Location;
	}

	public void setLocation(String location) {
		Location = location;
	}

}
