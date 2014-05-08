package com.redhat.coolstore.service;

import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;

import junit.framework.Assert;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.redhat.coolstore.DefaultDeployment;
import com.redhat.coolstore.model.Product;
import com.redhat.coolstore.model.ShoppingCartItem;
import com.redhat.coolstore.model.Promotion;
import com.redhat.coolstore.model.ShoppingCart;

@RunWith(Arquillian.class)
public class PromoServiceTest {

	@Deployment
    public static WebArchive deployment() throws IllegalArgumentException, FileNotFoundException {
        return DefaultDeployment.deployment();
    }
	
	@Inject
	private ShoppingCart shoppingCart;
		
	@Inject
	private PromoService promoService;	
	
	@Before
	public void setUp() throws Exception {
		
	}

	@After
	public void tearDown() throws Exception {
		
	}

	@Test
	public void freeShippingPromoTest() {
		
		//cart total 0
		Assert.assertEquals(0, shoppingCart.getCartItemTotal(), 0);
		
		promoService.applyShippingPromotions(shoppingCart);
		
		Assert.assertEquals(0, shoppingCart.getShippingTotal(), 0);
		
		//cart total 74.99
		shoppingCart.setCartItemTotal(74.99);
		shoppingCart.setShippingTotal(4.99);
		
		promoService.applyShippingPromotions(shoppingCart);
		
		Assert.assertEquals(4.99, shoppingCart.getShippingTotal(), 0);
		
		//cart total 75
		shoppingCart.setCartItemTotal(75);
		shoppingCart.setShippingTotal(4.99);
		
		promoService.applyShippingPromotions(shoppingCart);
		
		Assert.assertEquals(0, shoppingCart.getShippingTotal(), 0);
		
					
	}
	
	@Test
	public void cartPromoTest() {
		
		//cart total 0
		Assert.assertEquals(0, shoppingCart.getCartItemTotal(), 0);
		
		promoService.applyCartItemPromotions(shoppingCart);
		
		Assert.assertEquals(0, shoppingCart.getCartItemPromoSavings(), 0);
		
		Set<Promotion> promotionSet = new HashSet<Promotion>();
		
		Promotion p1 = new Promotion("123", .25);
		
		promotionSet.add(p1);
		
		promoService.setPromotions(promotionSet);
		
		ShoppingCartItem sci = new ShoppingCartItem();
		sci.setQuantity(1);
		
		Product p = new Product();
		p.setItemId("234");
		p.setPrice(10.00);
		
		sci.setProduct(p);
		
		shoppingCart.addShoppingCartItem(sci);
		
		Assert.assertEquals(10, sci.getProduct().getPrice(), 0);
		Assert.assertEquals(0, sci.getPromoSavings(), 0);
		
		promoService.applyCartItemPromotions(shoppingCart);		
		
		Assert.assertEquals(10, sci.getProduct().getPrice(), 0);
		Assert.assertEquals(0, sci.getPromoSavings(), 0);
		
		p.setItemId("123");
		
		promoService.applyCartItemPromotions(shoppingCart);		
					
		Assert.assertEquals(10, sci.getProduct().getPrice(), 0);
		Assert.assertEquals(7.5, sci.getPrice(), 0);
		Assert.assertEquals(-2.5, sci.getPromoSavings(), 0);
		
	}
		
}
