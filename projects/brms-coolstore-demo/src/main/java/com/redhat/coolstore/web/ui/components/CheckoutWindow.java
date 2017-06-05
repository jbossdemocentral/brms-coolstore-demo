package com.redhat.coolstore.web.ui.components;

import com.redhat.coolstore.model.ShoppingCart;
import com.redhat.coolstore.model.ShoppingCartItem;
import com.redhat.coolstore.web.ui.util.Formatter;
import com.vaadin.ui.Grid;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;
import com.vaadin.ui.components.grid.FooterRow;

public class CheckoutWindow extends Window {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8504668339755922310L;

	private static final String PROPERTY_PRODUCT_NAME = "productName";
	private static final String PROPERTY_UNIT_PRICE = "unitPrice";
	private static final String PROPERTY_QUANTITY = "quantity";
	private static final String PROPERTY_PRODUCT_TOTAL = "productTotal";

	public CheckoutWindow(ShoppingCart shoppingCart) {

		Grid<ShoppingCartItem> grid = new Grid<>();
		grid.setItems(shoppingCart.getShoppingCartItemList());
		grid.addColumn(item -> item.getProduct().getName()).setCaption("Product Name").setId(PROPERTY_PRODUCT_NAME);
		grid.addColumn(item -> item.getProduct().getPrice()).setCaption("Unit Price").setId(PROPERTY_UNIT_PRICE);
		grid.addColumn(ShoppingCartItem::getQuantity).setCaption("Quantity").setId(PROPERTY_QUANTITY);
		grid.addColumn(item -> Formatter.formatPrice(item.getProduct().getPrice() * item.getQuantity()))
				.setCaption("Product Total").setId(PROPERTY_PRODUCT_TOTAL);
		grid.setSizeFull();

		FooterRow gridFooter = grid.appendFooterRow();
		gridFooter.join(PROPERTY_PRODUCT_NAME, PROPERTY_UNIT_PRICE,
				PROPERTY_QUANTITY).setText(
				"Total: [Promotion(s) "
						+ Formatter.formatPrice(shoppingCart
								.getCartItemPromoSavings()) + "]");
		gridFooter.getCell(PROPERTY_PRODUCT_TOTAL).setText(
				Formatter.formatPrice(shoppingCart.getCartItemTotal()));

		gridFooter = grid.appendFooterRow();
		gridFooter.join(PROPERTY_PRODUCT_NAME, PROPERTY_UNIT_PRICE,
				PROPERTY_QUANTITY).setText(
				"Shipping Total: [Promotion(s) "
						+ Formatter.formatPrice(shoppingCart
								.getShippingPromoSavings()) + "]");
		gridFooter.getCell(PROPERTY_PRODUCT_TOTAL).setText(
				Formatter.formatPrice(shoppingCart.getShippingTotal()));

		gridFooter = grid.appendFooterRow();
		gridFooter.join(PROPERTY_PRODUCT_NAME, PROPERTY_UNIT_PRICE,
				PROPERTY_QUANTITY).setText("Order Total:");
		gridFooter.getCell(PROPERTY_PRODUCT_TOTAL).setText(
				Formatter.formatPrice(shoppingCart.getCartTotal()));

		setCaption("<h2>Thank you for your order!</h2>");
		setCaptionAsHtml(true);
		center();
		setResizable(false);
		setDraggable(false);
		setModal(true);
		setWidth(4 * UI.getCurrent().getWidth() / 5, UI.getCurrent()
				.getWidthUnits());
		setHeight(4 * UI.getCurrent().getHeight() / 5, UI.getCurrent()
				.getHeightUnits());

		setContent(grid);
	}
}
