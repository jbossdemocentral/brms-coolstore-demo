package com.redhat.coolstore.web.ui;

import javax.enterprise.event.Observes;

import org.vaadin.dialogs.ConfirmDialog;
import org.vaadin.teemu.VaadinIcons;
import org.vaadin.viritin.fields.LabelField;

import com.redhat.coolstore.model.ShoppingCart;
import com.redhat.coolstore.web.ui.converter.DoubleStringConverter;
import com.redhat.coolstore.web.ui.events.UpdateShopppingCartEvent;
import com.vaadin.cdi.UIScoped;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@UIScoped
public class ShoppingCartView extends AbstractView {

	private static final String LABEL_WIDTH = "8em";

	private Button checkoutButton = new Button();

	private Button clearButton = new Button();

	@PropertyId("cartItemTotal")
	private LabelField<String> subtotalValue = new LabelField<String>();

	@PropertyId("cartItemPromoSavings")
	private LabelField<String> cartPromoValue = new LabelField<String>();

	@PropertyId("shippingTotal")
	private LabelField<String> shippingValue = new LabelField<String>();

	@PropertyId("shippingPromoSavings")
	private LabelField<String> shippingPromoValue = new LabelField<String>();

	@PropertyId("cartTotal")
	private LabelField<String> cartTotalValue = new LabelField<String>();

	private FieldGroup fieldGroup;

	/**
	 * 
	 */
	private static final long serialVersionUID = 962893447423474540L;

	@Override
	protected void createLayout(VerticalLayout layout) {

		fieldGroup = new FieldGroup();
		fieldGroup.setItemDataSource(new BeanItem<ShoppingCart>(
				getShoppingCart()));
		fieldGroup.bindMemberFields(this);

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
		createButton(checkoutButton, "Checkout", VaadinIcons.FLAG_CHECKERED);
		checkoutButton.setEnabled(false);
		checkoutButton.addStyleName(ValoTheme.BUTTON_PRIMARY);

		createButton(clearButton, "Clear", VaadinIcons.CLOSE);
	}

	private HorizontalLayout getCartLine(String caption,
			LabelField<String> value) {
		return getCartLine(caption, value, false);
	}

	private HorizontalLayout getCartLine(String caption,
			LabelField<String> value,
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

		value.setConverter(new DoubleStringConverter());
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
		// BUG #4302 - Can not update the bean in BeanItem
		fieldGroup.setItemDataSource(new BeanItem<ShoppingCart>(
				getShoppingCart()));
	}

	private void clearShoppingCart() {
		ConfirmDialog d = ConfirmDialog.show(UI.getCurrent(),
				"Confirm Clearing Shopping Cart",
				"Are you sure you want to clear the shopping cart?", "Clear",
				"Keep", new ConfirmDialog.Listener() {

					/**
					 * 
					 */
					private static final long serialVersionUID = 8604235590327706594L;

					@Override
					public void onClose(ConfirmDialog dialog) {
						if (dialog.isConfirmed()) {
							// Confirmed to clear

							// BUG #4302 - Can not update the bean in BeanItem
							fieldGroup
									.setItemDataSource(new BeanItem<ShoppingCart>(
											new ShoppingCart()));
						} else {
							// Do nothing
						}
					}
				});

		d.getOkButton().addStyleName(ValoTheme.BUTTON_DANGER);
		d.getCancelButton().addStyleName(ValoTheme.BUTTON_LINK);
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
