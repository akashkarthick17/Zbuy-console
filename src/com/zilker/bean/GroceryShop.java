package com.zilker.bean;

public class GroceryShop implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private String GroceryId, GroceryName;

	public GroceryShop() {
		super();
	}

	public String getGroceryId() {
		return GroceryId;
	}

	public void setGroceryId(String groceryId) {
		GroceryId = groceryId;
	}

	public String getGroceryName() {
		return GroceryName;
	}

	public void setGroceryName(String groceryName) {
		GroceryName = groceryName;
	}

}
