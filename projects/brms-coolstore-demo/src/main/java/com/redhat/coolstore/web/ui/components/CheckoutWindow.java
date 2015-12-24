package com.redhat.coolstore.web.ui.components;

import java.util.List;

import com.redhat.coolstore.model.ShoppingCartItem;
import com.redhat.coolstore.web.ui.converter.StringPropertyValueGenerator;
import com.redhat.coolstore.web.ui.util.Formatter;
import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.GeneratedPropertyContainer;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.FooterCell;
import com.vaadin.ui.Grid.FooterRow;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;

public class CheckoutWindow extends Window {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8504668339755922310L;

	private static final String PROPERTY_PRODUCT_NAME = "productName";
	private static final String PROPERTY_PRODUCT_PRICE = "productPrice";
	private static final String PROPERTY_QUANTITY = "quantity";
	private static final String PROPERTY_PRODUCT_TOTAL = "productTotal";

	private static Double totalPrice;
	private FooterCell gridFooterTotal;

	private void updateGridFooterTotal(Double price) {
		totalPrice += price;
		gridFooterTotal.setText(Formatter.formatPrice(totalPrice));
	}

	public CheckoutWindow(List<ShoppingCartItem> shoppingCartItems) {

		totalPrice = 0D;

		BeanItemContainer<ShoppingCartItem> container = new BeanItemContainer<ShoppingCartItem>(
				ShoppingCartItem.class, shoppingCartItems);
		GeneratedPropertyContainer gContainer = new GeneratedPropertyContainer(
				container);
		gContainer.addGeneratedProperty("productName",
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
		gContainer.addGeneratedProperty("productPrice",
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
		gContainer.addGeneratedProperty("productTotal",
				new StringPropertyValueGenerator() {

					/**
					 * 
					 */
					private static final long serialVersionUID = 1033121306154164199L;

					@Override
					public String getValue(Item item, Object itemId,
							Object propertyId) {
						ShoppingCartItem scItem = (ShoppingCartItem) itemId;
						Double currentPrice = scItem.getProduct().getPrice()
								* scItem.getQuantity();
						updateGridFooterTotal(currentPrice);
						return Formatter.formatPrice(currentPrice);
					}
				});

		Grid grid = new Grid(gContainer);
		grid.setSizeFull();

		grid.removeAllColumns();
		grid.addColumn(PROPERTY_PRODUCT_NAME);
		grid.addColumn(PROPERTY_PRODUCT_PRICE);
		grid.addColumn(PROPERTY_QUANTITY);
		grid.getColumn(PROPERTY_QUANTITY).setSortable(false);
		grid.addColumn(PROPERTY_PRODUCT_TOTAL);

		FooterRow gridFooter = grid.appendFooterRow();

		gridFooter.join(PROPERTY_PRODUCT_NAME, PROPERTY_PRODUCT_PRICE,
				PROPERTY_QUANTITY).setText("Order Total:");

		gridFooterTotal = gridFooter.getCell(PROPERTY_PRODUCT_TOTAL);

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
