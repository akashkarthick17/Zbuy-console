package com.zilker.bean;

public class GroceryOrder implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private int itemId, quantity;
	private double itemPrice;

	public GroceryOrder() {
		super();
	}

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getItemPrice() {
		return itemPrice;
	}

	public void setItemPrice(double itemPrice) {
		this.itemPrice = itemPrice;
	}

}
