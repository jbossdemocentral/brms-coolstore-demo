package com.redhat.coolstore.factmodel;

public class PromoEvent {
	
	private String itemId;
	
	private double percentOff;

	public PromoEvent() {
		
	}
	
	public PromoEvent(String itemId, double percentOff) {
		super();
		this.itemId = itemId;
		this.percentOff = percentOff;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public double getPercentOff() {
		return percentOff;
	}

	public void setPercentOff(double percentOff) {
		this.percentOff = percentOff;
	}
	
	

}
