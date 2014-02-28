package com.redhat.coolstore.service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.ejb.Singleton;

import com.redhat.coolstore.PromoEvent;
import com.redhat.coolstore.ShoppingCart;
import com.redhat.coolstore.ShoppingCartItem;

@Singleton
public class PromoService implements Serializable {

	private static final long serialVersionUID = 2088590587856645568L;

	private String name = null;
	
	private Set<PromoEvent> promotionSet = null;

	public PromoService() {
						
		promotionSet = new HashSet<PromoEvent>();
		
		promotionSet.add(new PromoEvent("329299", .25));
						
	}
			
	public void applyCartItemPromotions(ShoppingCart shoppingCart) {
		
		if ( shoppingCart != null && shoppingCart.getShoppingCartItemList().size() > 0 ) {
			
			Map<String, PromoEvent> promoMap = new HashMap<String, PromoEvent>(); 
			
			for (PromoEvent promo : getPromotions()) {
				
				promoMap.put(promo.getItemId(), promo);
				
			}
			
			for ( ShoppingCartItem sci : shoppingCart.getShoppingCartItemList() ) {
				
				String productId = sci.getItemId();
				
				PromoEvent promo = promoMap.get(productId);
				
				if ( promo != null ) {
				
					sci.setPromoSavings(sci.getPrice() * promo.getPercentOff() * -1);
					sci.setPrice(sci.getPrice() * (1-promo.getPercentOff()));
					
				}
							
			}
			
		}
		
	}
	
	public void applyShippingPromotions(ShoppingCart shoppingCart) {
		
		if ( shoppingCart != null ) {
			
			//PROMO: if cart total is greater than 75, free shipping
			if ( shoppingCart.getCartItemTotal() >= 75) {
				
				shoppingCart.setShippingPromoSavings(shoppingCart.getShippingTotal() * -1);
				shoppingCart.setShippingTotal(0d);
				
			}
			
		}
		
	}	
		
	public Set<PromoEvent> getPromotions() {
				
		if ( promotionSet == null ) {
			
			promotionSet = new HashSet<PromoEvent>();
			
		}
		
		return new HashSet<PromoEvent>(promotionSet);
		
	}
	
	public void setPromotions(Set<PromoEvent> promotionSet) {
		
		if ( promotionSet != null ) {
		
			this.promotionSet = new HashSet<PromoEvent>(promotionSet);
			
		} else {
			
			this.promotionSet = new HashSet<PromoEvent>();
			
		}
						
	}
	
	@Override
	public String toString() {
		return "PromoService [name=" + name + ", promotionSet=" + promotionSet
				+ "]";
	}
	
}
