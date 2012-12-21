package com.redhat.coolstore.service;

import java.io.FileNotFoundException;

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
import com.redhat.coolstore.model.ShoppingCart;

@RunWith(Arquillian.class)
public class ShippingServiceTest {

	@Deployment
    public static WebArchive deployment() throws IllegalArgumentException, FileNotFoundException {
        return DefaultDeployment.deployment();
    }
	
	@Inject
	private ShoppingCart shoppingCart;
		
	@Inject
	private ShippingService shippingService;	
	
	@Before
	public void setUp() throws Exception {
		
	}

	@After
	public void tearDown() throws Exception {
		
	}

	@Test
	public void calculateShippingTest() {
		
		//0
		Assert.assertEquals(0, shoppingCart.getShippingTotal(), 0);
		
		shippingService.calculateShipping(shoppingCart);
		
		Assert.assertEquals(2.99, shoppingCart.getShippingTotal(), 0);

		//0.01 - 24.99
		shoppingCart.setCartItemTotal(0.01);
		
		shippingService.calculateShipping(shoppingCart);
		
		Assert.assertEquals(2.99, shoppingCart.getShippingTotal(), 0);
		
		shoppingCart.setCartItemTotal(24.99);
		
		shippingService.calculateShipping(shoppingCart);
		
		Assert.assertEquals(2.99, shoppingCart.getShippingTotal(), 0);
		
		//25 - 49.99
		shoppingCart.setCartItemTotal(25.00);
		
		shippingService.calculateShipping(shoppingCart);
		
		Assert.assertEquals(4.99, shoppingCart.getShippingTotal(), 0);
		
		shoppingCart.setCartItemTotal(49.99);
		
		shippingService.calculateShipping(shoppingCart);
		
		Assert.assertEquals(4.99, shoppingCart.getShippingTotal(), 0);
		
		//50 - 74.99
		shoppingCart.setCartItemTotal(50.00);
		
		shippingService.calculateShipping(shoppingCart);
		
		Assert.assertEquals(6.99, shoppingCart.getShippingTotal(), 0);
		
		shoppingCart.setCartItemTotal(74.99);
		
		shippingService.calculateShipping(shoppingCart);
		
		Assert.assertEquals(6.99, shoppingCart.getShippingTotal(), 0);
		
		//75 - 99.99
		shoppingCart.setCartItemTotal(75.00);
		
		shippingService.calculateShipping(shoppingCart);
		
		Assert.assertEquals(8.99, shoppingCart.getShippingTotal(), 0);
		
		shoppingCart.setCartItemTotal(99.99);
		
		shippingService.calculateShipping(shoppingCart);
		
		Assert.assertEquals(8.99, shoppingCart.getShippingTotal(), 0);
		
		// > 100
		shoppingCart.setCartItemTotal(100.00);
		
		shippingService.calculateShipping(shoppingCart);
		
		Assert.assertEquals(10.99, shoppingCart.getShippingTotal(), 0);
					
	}
		
}
