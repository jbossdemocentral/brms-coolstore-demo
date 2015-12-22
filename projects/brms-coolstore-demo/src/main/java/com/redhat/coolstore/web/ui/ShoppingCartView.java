package com.redhat.coolstore.web.ui;

import java.text.DecimalFormat;

import org.vaadin.teemu.VaadinIcons;

import com.redhat.coolstore.model.ShoppingCart;
import com.vaadin.cdi.UIScoped;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@UIScoped
public class ShoppingCartView extends AbstractView {

	private static final String labelWidth = "8em";
	
	private DecimalFormat df = new DecimalFormat("'$'0.00");

	private Button checkoutButton = new Button();

	private Button clearButton = new Button();

	private Label subtotalValue;
	
	private Label cartPromoValue;
	
	private Label shippingValue;
	
	private Label shippingPromoValue;
	
	private Label cartTotalValue;

	/**
	 * 
	 */
	private static final long serialVersionUID = 962893447423474540L;

	@Override
	protected void createLayout(VerticalLayout layout) {

		Label inventoryLabel = new Label("Shopping Cart:");
		
		inventoryLabel.addStyleName(ValoTheme.LABEL_H1);
		
		layout.addComponent(inventoryLabel);

		layout.addComponent(getCartLine("Subtotal:", subtotalValue));
		layout.addComponent(getCartLine("Promotion:", cartPromoValue, true));
		layout.addComponent(getCartLine("Shipping:", shippingValue));
		layout.addComponent(getCartLine("Promotion:", shippingPromoValue, true));
		layout.addComponent(getCartLine("Cart Total:", cartTotalValue));

		HorizontalLayout hl = new HorizontalLayout();
		hl.addStyleName(ValoTheme.LAYOUT_HORIZONTAL_WRAPPING);
		hl.setSpacing(true);

		createButton(hl, checkoutButton, "Checkout",
				VaadinIcons.FLAG_CHECKERED, true);
		checkoutButton.setEnabled(false);

		createButton(hl, clearButton, "Clear", VaadinIcons.CLOSE, false);

		layout.addComponent(hl);
	}

	private HorizontalLayout getCartLine(String caption, Label value) {
		return getCartLine(caption, value, false);
	}

	private HorizontalLayout getCartLine(String caption, Label value,
			boolean isSecondary) {
		HorizontalLayout hl = new HorizontalLayout();
		hl.setWidth("100%");

		Label label = new Label(caption);
		if (isSecondary) {
			label.addStyleName(ValoTheme.LABEL_LIGHT);
		} else {
			label.addStyleName(ValoTheme.LABEL_BOLD);
		}
		label.setWidth(labelWidth);

		value = new Label();
		value.setValue(df.format(0));
		value.setWidth(labelWidth);

		hl.addComponent(label);
		hl.addComponent(value);
		hl.setComponentAlignment(value, Alignment.MIDDLE_RIGHT);

		return hl;
	}

	public Button getCheckoutButton() {
		return checkoutButton;
	}

	public Button getClearButton() {
		return clearButton;
	}

	public void updateShoppingCart() {

		ShoppingCart sc = getShoppingCart();

		if ( sc != null ) {

			subtotalValue.setValue(df.format(sc.getCartItemTotal()));
			cartPromoValue.setValue(df.format(sc.getCartItemPromoSavings()));
			shippingValue.setValue(df.format(sc.getShippingTotal()));
			shippingPromoValue.setValue(df.format(sc.getShippingPromoSavings()));
			cartTotalValue.setValue(df.format(sc.getCartTotal()));
		}
	}

	@Override
	public void buttonClick(ClickEvent event) {
		// if (event.getButton() == shoppingCartView.getClearButton())
		updateShoppingCart();
	}
}
