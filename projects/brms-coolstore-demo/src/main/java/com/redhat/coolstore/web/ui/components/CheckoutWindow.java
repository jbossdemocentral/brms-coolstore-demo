package com.redhat.coolstore.web.ui.components;

import com.redhat.coolstore.model.ShoppingCart;
import com.redhat.coolstore.model.ShoppingCartItem;
import com.redhat.coolstore.web.ui.converter.StringPropertyValueGenerator;
import com.redhat.coolstore.web.ui.util.Formatter;
import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.GeneratedPropertyContainer;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.FooterRow;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;

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

		BeanItemContainer<ShoppingCartItem> container = new BeanItemContainer<ShoppingCartItem>(
				ShoppingCartItem.class, shoppingCart.getShoppingCartItemList());
		GeneratedPropertyContainer gContainer = new GeneratedPropertyContainer(
				container);
		gContainer.addGeneratedProperty(PROPERTY_PRODUCT_NAME,
				new StringPropertyValueGenerator() {

					/**
					 * 
					 */
					private static final long serialVersionUID = 5244484082955210867L;

					@Override
					public String getValue(Item item, Object itemId,
							Object propertyId) {
						return ((ShoppingCartItem) itemId).getProduct()
								.getName();
					}
				});
		gContainer.addGeneratedProperty(PROPERTY_UNIT_PRICE,
				new StringPropertyValueGenerator() {

					/**
					 * 
					 */
					private static final long serialVersionUID = 6259100641253129452L;

					@Override
					public String getValue(Item item, Object itemId,
							Object propertyId) {
						return Formatter
								.formatPrice(((ShoppingCartItem) itemId)
										.getProduct().getPrice());
					}
				});
		gContainer.addGeneratedProperty(PROPERTY_PRODUCT_TOTAL,
				new StringPropertyValueGenerator() {

					/**
					 * 
					 */
					private static final long serialVersionUID = 1033121306154164199L;

					@Override
					public String getValue(Item item, Object itemId,
							Object propertyId) {
						ShoppingCartItem scItem = (ShoppingCartItem) itemId;
						return Formatter.formatPrice(scItem.getProduct()
								.getPrice() * scItem.getQuantity());
					}
				});

		Grid grid = new Grid(gContainer);
		grid.setSizeFull();

		grid.setColumns(PROPERTY_PRODUCT_NAME, PROPERTY_UNIT_PRICE,
				PROPERTY_QUANTITY, PROPERTY_PRODUCT_TOTAL);

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
