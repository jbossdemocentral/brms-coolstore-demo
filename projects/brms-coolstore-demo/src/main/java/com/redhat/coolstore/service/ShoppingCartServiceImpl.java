package com.redhat.coolstore.service;

import java.util.Map;

import javax.ejb.Stateless;
import javax.enterprise.inject.Alternative;
import javax.inject.Inject;

import com.redhat.coolstore.Product;
import com.redhat.coolstore.ShoppingCart;
import com.redhat.coolstore.ShoppingCartItem;

@Alternative
@Stateless
public class ShoppingCartServiceImpl implements ShoppingCartService {
	
	@Inject
	ShippingService ss;
	
	@Inject
	PromoService ps;
	
	@Inject
	ProductService pp;
	
	public void priceShoppingCart(ShoppingCart sc) {
						
		if ( sc != null ) {
			
			initShoppingCartForPricing(sc);
			
			if ( sc.getShoppingCartItemList() != null && sc.getShoppingCartItemList().size() > 0) {
			
				ps.applyCartItemPromotions(sc);
				
				for (ShoppingCartItem sci : sc.getShoppingCartItemList()) {
					
					sc.setCartItemPromoSavings(sc.getCartItemPromoSavings() + (sci.getPromoSavings() * sci.getQuantity()));
					sc.setCartItemTotal(sc.getCartItemTotal() + (sci.getPrice() * sci.getQuantity()));
					
				}
				
				ss.calculateShipping(sc);				
				
			}
			
			ps.applyShippingPromotions(sc);
			
			sc.setCartTotal(sc.getCartItemTotal() + sc.getShippingTotal());
		
		}
		
	}

	private void initShoppingCartForPricing(ShoppingCart sc) {

		sc.setCartItemTotal(0d);
		sc.setCartItemPromoSavings(0d);
		sc.setShippingTotal(0d);
		sc.setShippingPromoSavings(0d);
		sc.setCartTotal(0d);
		
		Map<String, Product> productMap = pp.getProductMap();
		
		for (ShoppingCartItem sci : sc.getShoppingCartItemList()) {
			
			Product p = productMap.get(sci.getItemId());
			
			//if product exist, create new product to reset price
			if ( p != null ) {
			
				sci.setItemId(p.getItemId());
				sci.setName(p.getName());
				sci.setPrice(p.getPrice());
				sci.setQuantity(sci.getQuantity() + 1);
			}
			
			sci.setPromoSavings(0d);
			
		}
		
		
	}
	
}
