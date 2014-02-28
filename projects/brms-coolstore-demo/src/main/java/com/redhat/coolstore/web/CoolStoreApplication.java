package com.redhat.coolstore.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

import org.vaadin.virkki.cdiutils.application.AbstractCdiApplication;
import org.vaadin.virkki.cdiutils.application.CdiApplicationServlet;

import com.redhat.coolstore.Product;
import com.redhat.coolstore.ShoppingCart;
import com.redhat.coolstore.ShoppingCartItem;
import com.redhat.coolstore.service.ShoppingCartService;
import com.redhat.coolstore.web.ui.ProductsView;
import com.redhat.coolstore.web.ui.ShoppingCartView;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.Reindeer;

public class CoolStoreApplication extends AbstractCdiApplication implements ClickListener {
		
	@WebServlet(urlPatterns = "/*", initParams = @WebInitParam(name = "application", value = "com.redhat.coolstore.web.CoolStoreApplication"))
    public static class CoolStoreApplicationServlet extends
            CdiApplicationServlet {

		/**
		 * Default serialization.
		 */
		private static final long serialVersionUID = 1L;
    }
	
	private static final long serialVersionUID = 8436561253049378320L;

	private static Embedded logo = new Embedded("", new ThemeResource("./images/logo.png"));
	
	private ProductsView productView = new ProductsView(this);
	
	private ShoppingCartView shoppingCartView = new ShoppingCartView(this);
	
	private ShoppingCart shoppingCart = new ShoppingCart();
		
	@Inject
	private ShoppingCartService shoppingCartService;
			
	@Override
	public void init() {
		
		Window mainWindow = new Window("Red Hat Cool Store");

		setTheme(Reindeer.THEME_NAME + "-ext");
		
		setMainWindow(mainWindow);
		
		showView(null);
								
	}
	
	public void showView(String viewName) {
		
		VerticalLayout vl = new VerticalLayout();
						
		vl.addComponent(logo);
		
		HorizontalLayout barLayout = new HorizontalLayout();
	
		vl.addComponent(barLayout);
		
		HorizontalSplitPanel hsp = new HorizontalSplitPanel();
				
		hsp.setFirstComponent(productView);
		hsp.setSplitPosition(76);
		hsp.setSecondComponent(shoppingCartView);		
		
		vl.addComponent(hsp);
				
		vl.setWidth("80em");
		
		getMainWindow().removeAllComponents();
		getMainWindow().addComponent(vl);
				
	}

	@Override
	public void buttonClick(ClickEvent event) {

		if ( event.getButton() == productView.getAddToCartButton()) {
									
			//TODO: FIX when cart is applying same promo each time cart is calculated.  This is workaround.
			//if ( productView.getSelectedProducts().size() > 0 ) {
			
				getMainWindow().showNotification("Adding item(s) to cart.");
									
				addItemsToShoppingCart();
				
				shoppingCartService.priceShoppingCart(shoppingCart);
				
				shoppingCartView.updateShoppingCart(shoppingCart);
				
				productView.checkAllBoxes(false);
			
			/*} else {
				
				getMainWindow().showNotification("Please select one or more items.");
				
			}*/
									
		} else if ( event.getButton() == productView.getCheckAllButton()) {
			
			productView.checkAllBoxes(true);
			
		} else if ( event.getButton() == productView.getUncheckAllButton()) {
			
			productView.checkAllBoxes(false);
			
		} else if ( event.getButton() == shoppingCartView.getClearButton() ) {
			
			shoppingCart = new ShoppingCart();
			
			shoppingCartView.updateShoppingCart(shoppingCart);
			
		}
		
	}

	private void addItemsToShoppingCart() {
				
		List<Product> productsToAddList = productView.getSelectedProducts();
		
		Map<String, ShoppingCartItem> shoppingCartItemMap = new HashMap<String, ShoppingCartItem>();
		
		for (ShoppingCartItem sci : shoppingCart.getShoppingCartItemList() ) {
			
			shoppingCartItemMap.put(sci.getItemId(), sci);
			
		}
		
		
		if ( productsToAddList != null && productsToAddList.size() > 0 ) {
			
			for (Product p : productsToAddList) {
			
				if ( shoppingCartItemMap.containsKey(p.getItemId())) {
					
					ShoppingCartItem sci = shoppingCartItemMap.get(p.getItemId());
					
					sci.setQuantity(sci.getQuantity() + 1);
					
				} else {
					
					ShoppingCartItem sci = new ShoppingCartItem();
					
					sci.setItemId(p.getItemId());
					sci.setPrice(p.getPrice());
					
					sci.setQuantity(1);
					
					shoppingCart.getShoppingCartItemList().add(sci);
					shoppingCart.setCartItemTotal(shoppingCart.getCartItemTotal() + 1);
					
					
					shoppingCartItemMap.put(p.getItemId(), sci);
					
				}
			
			}
			
		}
		
		
	}

}
