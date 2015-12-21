package com.redhat.coolstore.web.ui;

import java.text.DecimalFormat;

import com.redhat.coolstore.model.ShoppingCart;
import com.redhat.coolstore.web.CoolStoreApplication;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

public class ShoppingCartView extends Panel {

	private static final String labelWidth = "8em";

	private String buttonWidth = "7em";
	
	private DecimalFormat df = new DecimalFormat("'$'0.00");
	
	private CoolStoreApplication app = null;
			
	private Button checkoutButton;
	
	private Button clearButton;
		
	private Label subtotalValue;
	
	private Label cartPromoValue;
	
	private Label shippingValue;
	
	private Label shippingPromoValue;
	
	private Label cartTotalValue;
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 962893447423474540L;

	public ShoppingCartView(CoolStoreApplication app) {

		super();
		
		this.app = app;

		VerticalLayout vl = new VerticalLayout();
		vl.setMargin(true);
		vl.setSpacing(true);

		Label inventoryLabel = new Label("Shopping Cart:");
		
		inventoryLabel.addStyleName(ValoTheme.LABEL_H1);
		
		vl.addComponent(inventoryLabel);

		vl.addComponent(getCartLine("Subtotal:", subtotalValue));
		vl.addComponent(getCartLine("Promotion:", cartPromoValue, true));
		vl.addComponent(getCartLine("Shipping:", shippingValue));
		vl.addComponent(getCartLine("Promotion:", shippingPromoValue, true));
		vl.addComponent(getCartLine("Cart Total:", cartTotalValue));

		HorizontalLayout hl = new HorizontalLayout();
		hl.addStyleName(ValoTheme.LAYOUT_HORIZONTAL_WRAPPING);
		hl.setSpacing(true);

		checkoutButton = new Button("Checkout");
		checkoutButton.addListener(app);		
		checkoutButton.setWidth(buttonWidth);
		checkoutButton.setEnabled(false);
		hl.addComponent(checkoutButton);
		
		clearButton = new Button("Clear");
		clearButton.addListener(app);
		clearButton.setWidth(buttonWidth);
		hl.addComponent(clearButton);
		
		vl.addComponent(hl);
		
		setContent(vl);
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

	public void updateShoppingCart(ShoppingCart sc) {
		
		if ( sc != null ) {
			
			subtotalValue.setValue(df.format(sc.getCartItemTotal()));
			cartPromoValue.setValue(df.format(sc.getCartItemPromoSavings()));
			shippingValue.setValue(df.format(sc.getShippingTotal()));
			shippingPromoValue.setValue(df.format(sc.getShippingPromoSavings()));
			cartTotalValue.setValue(df.format(sc.getCartTotal()));
			
		}
		
	}
	
}
