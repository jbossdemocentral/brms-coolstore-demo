package com.redhat.coolstore.model;

public class ShoppingCartItem {
	
	private double price;
	private int quanity;
	private double promoSavings;
	private Product product;
	
	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public int getQuanity() {
		return quanity;
	}

	public void setQuanity(int quanity) {
		this.quanity = quanity;
	}

	public double getPromoSavings() {
		return promoSavings;
	}

	public void setPromoSavings(double promoSavings) {
		this.promoSavings = promoSavings;
	}

	@Override
	public String toString() {
		return "ShoppingCartItem [price=" + price + ", quanity=" + quanity
				+ ", promoSavings=" + promoSavings + ", product=" + product
				+ "]";
	}
		
	
}
