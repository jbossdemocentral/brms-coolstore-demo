package com.redhat.coolstore.web.ui;

import javax.enterprise.event.Observes;

import org.vaadin.teemu.VaadinIcons;

import com.redhat.coolstore.model.ShoppingCart;
import com.redhat.coolstore.web.ui.events.UpdateShopppingCartEvent;
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

	private static final String LABEL_WIDTH = "8em";

	private Button checkoutButton = new Button();

	private Button clearButton = new Button();

	private Label subtotalValue = new Label();

	private Label cartPromoValue = new Label();

	private Label shippingValue = new Label();

	private Label shippingPromoValue = new Label();

	private Label cartTotalValue = new Label();

	/**
	 * 
	 */
	private static final long serialVersionUID = 962893447423474540L;

	@Override
	protected void createLayout(VerticalLayout layout) {

		layout.addComponent(getCartLine("Subtotal:", subtotalValue));
		layout.addComponent(getCartLine("Promotion:", cartPromoValue, true));
		layout.addComponent(getCartLine("Shipping:", shippingValue));
		layout.addComponent(getCartLine("Promotion:", shippingPromoValue, true));
		layout.addComponent(getCartLine("Cart Total:", cartTotalValue));
	}

	@Override
	protected String getViewHeader() {
		return "Shopping Cart";
	}

	@Override
	protected void createControllerButtons() {
		createButton(checkoutButton, "Checkout",
				VaadinIcons.FLAG_CHECKERED, true);
		checkoutButton.setEnabled(false);

		createButton(clearButton, "Clear", VaadinIcons.CLOSE,
				false);
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
		label.setWidth(LABEL_WIDTH);

		value.setValue(formatPrice(0f));
		value.setWidth(LABEL_WIDTH);

		hl.addComponent(label);
		hl.addComponent(value);
		hl.setComponentAlignment(value, Alignment.MIDDLE_RIGHT);

		return hl;
	}

	private Button getCheckoutButton() {
		return checkoutButton;
	}

	private Button getClearButton() {
		return clearButton;
	}

	public void updateShoppingCart(@Observes UpdateShopppingCartEvent event) {
		updateShoppingCart();
	}

	private void updateShoppingCart() {

		ShoppingCart sc = getShoppingCart();

		if (sc != null) {
			subtotalValue.setValue(formatPrice(sc.getCartItemTotal()));
			cartPromoValue.setValue(formatPrice(sc.getCartItemPromoSavings()));
			shippingValue.setValue(formatPrice(sc.getShippingTotal()));
			shippingPromoValue.setValue(formatPrice(sc
					.getShippingPromoSavings()));
			cartTotalValue.setValue(formatPrice(sc.getCartTotal()));
		}
	}

	private void clearShoppingCart() {
		String price = formatPrice(0);

		subtotalValue.setValue(price);
		cartPromoValue.setValue(price);
		shippingValue.setValue(price);
		shippingPromoValue.setValue(price);
		cartTotalValue.setValue(price);
	}

	@Override
	public void buttonClick(ClickEvent event) {
		if (event.getButton() == getClearButton()) {
			clearShoppingCart();
		} else if (event.getButton() == getCheckoutButton()) {
			updateShoppingCart();
		}
	}
}
