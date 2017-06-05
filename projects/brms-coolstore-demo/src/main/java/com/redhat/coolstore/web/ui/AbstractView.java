package com.redhat.coolstore.web.ui;

import com.redhat.coolstore.model.ShoppingCart;
import com.vaadin.cdi.UIScoped;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Panel;
import com.vaadin.ui.themes.ValoTheme;
import com.vaadin.v7.ui.HorizontalLayout;
import com.vaadin.v7.ui.Label;
import com.vaadin.v7.ui.VerticalLayout;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

@UIScoped
public abstract class AbstractView extends Panel implements ClickListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2404594287924999036L;

	@Inject
	private ShoppingCart shoppingCart;

	private VerticalLayout layout;
	private HorizontalLayout controllerLayout;

	private static final String BUTTON_WIDTH = "10em";

	public AbstractView() {
		super();

		setHeight("23em");
		layout = new VerticalLayout();
		layout.setMargin(true);
		layout.setSpacing(true);

		setContent(layout);
	}

	@PostConstruct
	private void init() {
		Label headerLabel = new Label(getViewHeader() + ":");
		headerLabel.addStyleName(ValoTheme.LABEL_H1);
		layout.addComponent(headerLabel);

		createLayout(layout);

		controllerLayout = new HorizontalLayout();
		controllerLayout.addStyleName(ValoTheme.LAYOUT_HORIZONTAL_WRAPPING);
		controllerLayout.setSpacing(true);

		createControllerButtons();

		layout.addComponent(controllerLayout);
	}

	protected void resetShoppingCart() {
		shoppingCart.setCartItemPromoSavings(0D);
		shoppingCart.setCartItemTotal(0D);
		shoppingCart.setCartTotal(0D);
		shoppingCart.setShippingPromoSavings(0D);
		shoppingCart.setShippingTotal(0D);
		shoppingCart.resetShoppingCartItemList();
	}

	protected ShoppingCart getShoppingCart() {
		return shoppingCart;
	}

	protected abstract String getViewHeader();

	protected abstract void createLayout(VerticalLayout layout);

	protected abstract void createControllerButtons();

	protected void createButton(Button button, String caption, VaadinIcons icon) {

		button.addClickListener(this);
		button.setCaption(caption);
		button.setIcon(icon);
		button.setWidth(BUTTON_WIDTH);

		controllerLayout.addComponent(button);
	}
}
