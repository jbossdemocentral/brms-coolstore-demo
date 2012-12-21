package com.redhat.coolstore.factmodel;

import java.math.BigDecimal;

public class ShoppingCart {

	private double cartItemTotal;
	
	private double cartItemPromoSavings;
	
	private double shippingTotal;
	
	private double shippingPromoSavings;
	
	private double cartTotal;

	public double getCartItemTotal() {
		return cartItemTotal;
	}

	public void setCartItemTotal(double cartItemTotal) {
			
		this.cartItemTotal = (new BigDecimal(cartItemTotal)).setScale(2, BigDecimal.ROUND_HALF_EVEN).doubleValue();
			
	}

	public double getShippingTotal() {
		return shippingTotal;
	}

	public void setShippingTotal(double shippingTotal) {
		this.shippingTotal = shippingTotal;
	}

	public double getCartTotal() {
		return cartTotal;
	}

	public void setCartTotal(double cartTotal) {
				
		this.cartTotal = (new BigDecimal(cartTotal)).setScale(2, BigDecimal.ROUND_HALF_EVEN).doubleValue();
		
	}
	
	public double getCartItemPromoSavings() {
		return cartItemPromoSavings;
	}

	public void setCartItemPromoSavings(double cartItemPromoSavings) {
		this.cartItemPromoSavings = (new BigDecimal(cartItemPromoSavings)).setScale(2, BigDecimal.ROUND_HALF_EVEN).doubleValue();
	}

	public double getShippingPromoSavings() {
		return shippingPromoSavings;
	}

	public void setShippingPromoSavings(double shippingPromoSavings) {
		this.shippingPromoSavings = (new BigDecimal(shippingPromoSavings)).setScale(2, BigDecimal.ROUND_HALF_EVEN).doubleValue();
	}

	@Override
	public String toString() {
		return "ShoppingCart [cartItemTotal=" + cartItemTotal
				+ ", cartItemPromoSavings=" + cartItemPromoSavings
				+ ", shippingTotal=" + shippingTotal
				+ ", shippingPromoSavings=" + shippingPromoSavings
				+ ", cartTotal=" + cartTotal + "]";
	}
				
}
