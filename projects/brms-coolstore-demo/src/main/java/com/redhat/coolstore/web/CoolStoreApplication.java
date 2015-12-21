package com.redhat.coolstore.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.annotation.WebServlet;

import com.redhat.coolstore.model.Product;
import com.redhat.coolstore.model.ShoppingCart;
import com.redhat.coolstore.model.ShoppingCartItem;
import com.redhat.coolstore.web.ui.ProductsView;
import com.redhat.coolstore.web.ui.ShoppingCartView;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@Theme("coolstoretheme")
@Title("Red Hat Cool Store")
@Widgetset("com.redhat.coolstore.web.CoolStoreApplicationWidgetset")
public class CoolStoreApplication extends UI implements ClickListener {

	// extends AbstractCdiApplication implements ClickListener {
		
	// @WebServlet(urlPatterns = "/*", initParams = @WebInitParam(name =
	// "application", value = "com.redhat.coolstore.web.CoolStoreApplication"))
	// public static class CoolStoreApplicationServlet extends
	// CdiApplicationServlet {
	// }

	@WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
	@VaadinServletConfiguration(ui = CoolStoreApplication.class, productionMode = false)
	public static class Servlet extends VaadinServlet {

		/**
		 * 
		 */
		private static final long serialVersionUID = -2364529823031560380L;
	}
	
	private static final long serialVersionUID = 8436561253049378320L;

	private static Embedded logo = new Embedded("", new ThemeResource("./images/logo.png"));
	
	private ProductsView productView = new ProductsView(this);
	
	private ShoppingCartView shoppingCartView = new ShoppingCartView(this);
	
	private ShoppingCart shoppingCart = new ShoppingCart();
		
	// @Inject
	// private ShoppingCartService shoppingCartService;

	@Override
	public void init(VaadinRequest request) {

		VerticalLayout vl = new VerticalLayout();
		vl.setHeight("100%");
		vl.setWidth("80em");
		vl.setMargin(true);
		vl.setSpacing(true);

		vl.addComponent(logo);

		HorizontalSplitPanel hsp = new HorizontalSplitPanel();
		hsp.setWidth("100%");
		hsp.setHeightUndefined();

		hsp.setFirstComponent(productView);
		hsp.setSplitPosition(76);
		hsp.setSecondComponent(shoppingCartView);

		vl.addComponent(hsp);
		vl.setExpandRatio(hsp, 1);

		setContent(vl);
	}

	@Override
	public void buttonClick(ClickEvent event) {

		if ( event.getButton() == productView.getAddToCartButton()) {
									
			//TODO: FIX when cart is applying same promo each time cart is calculated.  This is workaround.
			//if ( productView.getSelectedProducts().size() > 0 ) {
			
				Notification.show("Adding item(s) to cart.");
									
				addItemsToShoppingCart();
				
			// shoppingCartService.priceShoppingCart(shoppingCart);
				
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
			
			shoppingCartItemMap.put(sci.getProduct().getItemId(), sci);
			
		}
		
		
		if ( productsToAddList != null && productsToAddList.size() > 0 ) {
			
			for (Product p : productsToAddList) {
			
				if ( shoppingCartItemMap.containsKey(p.getItemId())) {
					
					ShoppingCartItem sci = shoppingCartItemMap.get(p.getItemId());
					
					sci.setQuantity(sci.getQuantity() + 1);
					
				} else {
					
					ShoppingCartItem sci = new ShoppingCartItem();
					
					sci.setProduct(p);
					
					sci.setQuantity(1);
					
					shoppingCart.addShoppingCartItem(sci);
					
					shoppingCartItemMap.put(p.getItemId(), sci);
					
				}
			
			}
			
		}
		
		
	}

}
