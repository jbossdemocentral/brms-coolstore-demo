package com.redhat.coolstore.service;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;

import junit.framework.Assert;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.redhat.coolstore.DefaultDeployment;
import com.redhat.coolstore.Product;
import com.redhat.coolstore.PromoEvent;
import com.redhat.coolstore.ShoppingCart;
import com.redhat.coolstore.ShoppingCartItem;

@RunWith(Arquillian.class)
public class ShoppingCartServiceTest {

	@Deployment
    public static WebArchive deployment() throws IllegalArgumentException, FileNotFoundException {
        return DefaultDeployment.deployment();
    }
	
	@Inject
	private ShoppingCart shoppingCart;
		
	@Inject
	private ShoppingCartService shoppingCartService;	
	
	@Inject 
	PromoService promoService; 
	
	@Inject
	ProductService productService; 
	
	@Before
	public void clearPromos() {
		
		promoService.setPromotions(new HashSet<PromoEvent>());
		
	}
				
	@Test
	public void priceShoppingCartEmpty() {
		
		Assert.assertEquals(0, shoppingCart.getCartItemTotal(), 0);
		Assert.assertEquals(0, shoppingCart.getShippingTotal(), 0);
		Assert.assertEquals(0, shoppingCart.getCartTotal(), 0);
		
		shoppingCartService.priceShoppingCart(shoppingCart);
		
		Assert.assertEquals(0, shoppingCart.getCartItemTotal(), 0);
		Assert.assertEquals(0, shoppingCart.getShippingTotal(), 0);
		Assert.assertEquals(0, shoppingCart.getCartTotal(), 0);
		
	}
	
	@Test
	public void priceShoppingCartOneItem() {
		
		Assert.assertEquals(0, shoppingCart.getCartItemTotal(), 0);
		Assert.assertEquals(0, shoppingCart.getShippingTotal(), 0);
		Assert.assertEquals(0, shoppingCart.getCartTotal(), 0);
		
		ShoppingCartItem sci = new ShoppingCartItem();
		Product p = new Product();
		p.setItemId("123");
		p.setPrice(10.00);
		sci.setQuantity(2);
		sci.setItemId(p.getItemId());
		sci.setPrice(p.getPrice());
		
		productService.setProducts(Arrays.asList(p));
		
		shoppingCart.getShoppingCartItemList().add(sci);
		shoppingCart.setCartItemTotal(shoppingCart.getCartItemTotal() + 1);
		
		shoppingCartService.priceShoppingCart(shoppingCart);
		
		Assert.assertEquals(20, shoppingCart.getCartItemTotal(), 0);
		Assert.assertEquals(2.99, shoppingCart.getShippingTotal(), 0);
		Assert.assertEquals(22.99, shoppingCart.getCartTotal(), 0.001);
				
	}
		
	@Test
	public void priceShoppingCartMultipleItem() {
		
		Assert.assertEquals(0, shoppingCart.getCartItemTotal(), 0);
		Assert.assertEquals(0, shoppingCart.getShippingTotal(), 0);
		Assert.assertEquals(0, shoppingCart.getCartTotal(), 0);
		
		ShoppingCartItem sci1 = new ShoppingCartItem();
		Product p1 = new Product();
		p1.setItemId("123");
		p1.setPrice(9.99);
		sci1.setQuantity(3);
		sci1.setItemId(p1.getItemId());
		sci1.setPrice(p1.getPrice());
		
		ShoppingCartItem sci2 = new ShoppingCartItem();
		Product p2 = new Product();
		p2.setItemId("234");
		p2.setPrice(6.77);
		sci2.setQuantity(1);
		sci2.setItemId(p2.getItemId());
		sci2.setPrice(p2.getPrice());
		
		ShoppingCartItem sci3 = new ShoppingCartItem();
		Product p3 = new Product();
		p3.setItemId("345");
		p3.setPrice(2.00);		
		sci3.setQuantity(2);
		sci3.setItemId(p3.getItemId());
		sci3.setPrice(p3.getPrice());
		
		shoppingCart.getShoppingCartItemList().add(sci1);
		shoppingCart.setCartItemTotal(shoppingCart.getCartItemTotal() + 1);
		
		shoppingCart.getShoppingCartItemList().add(sci2);
		shoppingCart.setCartItemTotal(shoppingCart.getCartItemTotal() + 1);
		
		shoppingCart.getShoppingCartItemList().add(sci3);
		shoppingCart.setCartItemTotal(shoppingCart.getCartItemTotal() + 1);
		
		productService.setProducts(Arrays.asList(p1, p2, p3));
		
		shoppingCartService.priceShoppingCart(shoppingCart);
		
		Assert.assertEquals(40.74, shoppingCart.getCartItemTotal(), 0.001);
		Assert.assertEquals(4.99, shoppingCart.getShippingTotal(), 0);
		Assert.assertEquals(45.73, shoppingCart.getCartTotal(), 0.001);
				
	}

	@Test
	public void priceShoppingCartMultipleTimes() {
		
		Assert.assertEquals(0, shoppingCart.getCartItemTotal(), 0);
		Assert.assertEquals(0, shoppingCart.getShippingTotal(), 0);
		Assert.assertEquals(0, shoppingCart.getCartTotal(), 0);
		
		ShoppingCartItem sci = new ShoppingCartItem();
		Product p = new Product();
		p.setItemId("123");
		p.setPrice(10.00);
		sci.setQuantity(2);
		sci.setItemId(p.getItemId());
		sci.setPrice(p.getPrice());
		
		shoppingCart.getShoppingCartItemList().add(sci);
		shoppingCart.setCartItemTotal(shoppingCart.getCartItemTotal() + 1);
		
		productService.setProducts(Arrays.asList(p));
		
		shoppingCartService.priceShoppingCart(shoppingCart);
		
		Assert.assertEquals(20, shoppingCart.getCartItemTotal(), 0);
		Assert.assertEquals(2.99, shoppingCart.getShippingTotal(), 0);
		Assert.assertEquals(22.99, shoppingCart.getCartTotal(), 0.001);
		
		shoppingCartService.priceShoppingCart(shoppingCart);
		
		Assert.assertEquals(20, shoppingCart.getCartItemTotal(), 0);
		Assert.assertEquals(2.99, shoppingCart.getShippingTotal(), 0);
		Assert.assertEquals(22.99, shoppingCart.getCartTotal(), 0.001);
				
	}
	
	@Test
	public void priceShoppingCartWithCartPromoTest() {
		
		//cart total 0
		Assert.assertEquals(0, shoppingCart.getCartItemTotal(), 0);
		
		shoppingCartService.priceShoppingCart(shoppingCart);
		
		Assert.assertEquals(0, shoppingCart.getCartItemPromoSavings(), 0);
		
		Set<PromoEvent> promotionSet = new HashSet<PromoEvent>();
		
		PromoEvent p1 = new PromoEvent("123", .25);
		
		promotionSet.add(p1);
		
		promoService.setPromotions(promotionSet);
		
		ShoppingCartItem sci = new ShoppingCartItem();
		sci.setQuantity(1);
		
		Product p = new Product();
		p.setItemId("234");
		p.setPrice(10.00);
		
		sci.setItemId(p.getItemId());
		sci.setPrice(p.getPrice());
		
		shoppingCart.getShoppingCartItemList().add(sci);
		shoppingCart.setCartItemTotal(shoppingCart.getCartItemTotal() + 1);
		
		productService.setProducts(Arrays.asList(p));
						
		Assert.assertEquals(0, shoppingCart.getCartItemTotal(), 0);
		Assert.assertEquals(0, shoppingCart.getCartItemPromoSavings(), 0);
						
		shoppingCartService.priceShoppingCart(shoppingCart);
		
		Assert.assertEquals(10, shoppingCart.getCartItemTotal(), 0);
		Assert.assertEquals(0, shoppingCart.getCartItemPromoSavings(), 0);
		
		p.setItemId("123");
		sci.setItemId(p.getItemId());
		
		shoppingCartService.priceShoppingCart(shoppingCart);	
			
		Assert.assertEquals(7.5, shoppingCart.getCartItemTotal(), 0);
		Assert.assertEquals(-2.5, shoppingCart.getCartItemPromoSavings(), 0);
				
		shoppingCartService.priceShoppingCart(shoppingCart);	
						
		Assert.assertEquals(7.5, shoppingCart.getCartItemTotal(), 0);
		Assert.assertEquals(-2.5, shoppingCart.getCartItemPromoSavings(), 0);
		
	}
	
	@Test
	public void priceShoppingCartWithShippingPromoTest() {
				
		ShoppingCartItem sci = new ShoppingCartItem();
		sci.setQuantity(1);
		
		Product p = new Product();
		p.setItemId("456");
		p.setPrice(74.99);
		
		sci.setItemId(p.getItemId());
		sci.setPrice(p.getPrice());
		
		shoppingCart.getShoppingCartItemList().add(sci);
		shoppingCart.setCartItemTotal(shoppingCart.getCartItemTotal() + 1);
		
		
		productService.setProducts(Arrays.asList(p));
						
		Assert.assertEquals(0, shoppingCart.getShippingTotal(), 0);
		Assert.assertEquals(0, shoppingCart.getShippingPromoSavings(), 0);
						
		shoppingCartService.priceShoppingCart(shoppingCart);

		Assert.assertEquals(6.99, shoppingCart.getShippingTotal(), 0);
		Assert.assertEquals(0, shoppingCart.getShippingPromoSavings(), 0);
		
		p.setPrice(75.00);
		
		shoppingCartService.priceShoppingCart(shoppingCart);	
					
		Assert.assertEquals(0, shoppingCart.getShippingTotal(), 0);
		Assert.assertEquals(-8.99, shoppingCart.getShippingPromoSavings(), 0);
			
		
	}
}
