package com.redhat.coolstore.web.ui;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import com.redhat.coolstore.model.ShoppingCart;
import com.vaadin.cdi.UIScoped;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

@UIScoped
public abstract class AbstractView extends Panel implements ClickListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2404594287924999036L;

	@Inject
	private ShoppingCart shoppingCart;

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

	protected ShoppingCart getShoppingCart() {
		return shoppingCart;
	}

	protected abstract void createLayout(VerticalLayout layout);
}
