package com.redhat.coolstore.factmodel;

import java.math.BigDecimal;

public class ShoppingCartItem {
	
	private String itemId;
	
	private String name;

	private double price;
	
	private double promoSavings;

	private int quantity;
	
	private ShoppingCart shoppingCart;

	public ShoppingCartItem() {
		
	}
	
	public ShoppingCartItem(String itemId, String name, double price,
			int quantity, ShoppingCart shoppingCart) {
		super();
		this.itemId = itemId;
		this.name = name;
		this.price = price;
		this.quantity = quantity;
		this.shoppingCart = shoppingCart;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = (new BigDecimal(price)).setScale(2, BigDecimal.ROUND_HALF_EVEN).doubleValue();
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public ShoppingCart getShoppingCart() {
		return shoppingCart;
	}

	public void setShoppingCart(ShoppingCart shoppingCart) {
		this.shoppingCart = shoppingCart;
	}
	
	public double getPromoSavings() {
		return promoSavings;
	}

	public void setPromoSavings(double promoSavings) {
		this.promoSavings = (new BigDecimal(promoSavings)).setScale(2, BigDecimal.ROUND_HALF_EVEN).doubleValue();
	}

	@Override
	public String toString() {
		return "ShoppingCartItem [itemId=" + itemId + ", name=" + name
				+ ", price=" + price + ", promoSavings=" + promoSavings
				+ ", quantity=" + quantity + ", shoppingCart=" + shoppingCart
				+ "]";
	}
			
}
