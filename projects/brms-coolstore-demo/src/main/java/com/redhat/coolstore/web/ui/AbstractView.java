package com.redhat.coolstore.web.ui;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import com.redhat.coolstore.model.Product;
import com.redhat.coolstore.model.ShoppingCart;
import com.redhat.coolstore.model.ShoppingCartItem;
import com.redhat.coolstore.service.ShoppingCartService;
import com.vaadin.cdi.UIScoped;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

@UIScoped
public abstract class AbstractView extends Panel implements ClickListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2404594287924999036L;

	private ShoppingCart shoppingCart = new ShoppingCart();

	@Inject
	private ShoppingCartService shoppingCartService;

	private VerticalLayout layout;

	public AbstractView() {
		super();

		layout = new VerticalLayout();
		layout.setMargin(true);
		layout.setSpacing(true);

		setContent(layout);
	}

	@PostConstruct
	private void init() {
		createLayout(layout);
	}

	protected abstract void createLayout(VerticalLayout layout);

	private void addItemsToShoppingCart() {

		List<Product> productsToAddList = null; // productView.getSelectedProducts();

		Map<String, ShoppingCartItem> shoppingCartItemMap = new HashMap<String, ShoppingCartItem>();

		for (ShoppingCartItem sci : shoppingCart.getShoppingCartItemList()) {

			shoppingCartItemMap.put(sci.getProduct().getItemId(), sci);

		}

		if (productsToAddList != null && productsToAddList.size() > 0) {

			for (Product p : productsToAddList) {

				if (shoppingCartItemMap.containsKey(p.getItemId())) {

					ShoppingCartItem sci = shoppingCartItemMap.get(p
							.getItemId());

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

	@Override
	public void buttonClick(ClickEvent event) {

		// if (event.getButton() == productView.getAddToCartButton()) {
		//
		// // TODO: FIX when cart is applying same promo each time cart is
		// // calculated. This is workaround.
		// // if ( productView.getSelectedProducts().size() > 0 ) {
		//
		// Notification.show("Adding item(s) to cart.");
		//
		// addItemsToShoppingCart();
		//
		// shoppingCartService.priceShoppingCart(shoppingCart);
		//
		// shoppingCartView.updateShoppingCart(shoppingCart);
		//
		// productView.checkAllBoxes(false);
		//
		// /*
		// * } else {
		// *
		// * getMainWindow().showNotification("Please select one or more items."
		// * );
		// *
		// * }
		// */
		//
		// } else if (event.getButton() == productView.getCheckAllButton()) {
		//
		// productView.checkAllBoxes(true);
		//
		// } else if (event.getButton() == productView.getUncheckAllButton()) {
		//
		// productView.checkAllBoxes(false);
		//
		// } else if (event.getButton() == shoppingCartView.getClearButton()) {
		//
		// shoppingCart = new ShoppingCart();
		//
		// shoppingCartView.updateShoppingCart(shoppingCart);
		//
		// }

	}
}
