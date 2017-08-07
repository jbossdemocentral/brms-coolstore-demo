package com.redhat.coolstore.web.ui;

import com.redhat.coolstore.model.Product;
import com.redhat.coolstore.service.ProductService;
import com.redhat.coolstore.web.ui.events.UpdateShopppingCartEvent;
import com.redhat.coolstore.web.ui.util.Formatter;
import com.vaadin.cdi.UIScoped;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CheckBoxGroup;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import javax.inject.Inject;
import java.util.List;
import java.util.Set;

@UIScoped
public class ProductsView extends AbstractView {

	@Inject
	private ProductService productService;

	@Inject
	private javax.enterprise.event.Event<UpdateShopppingCartEvent> updateCart;

	private Button addToCartButton = new Button();

	private Button checkAllButton = new Button();

	private Button uncheckAllButton = new Button();

	private CheckBoxGroup<Product> options = new CheckBoxGroup<>();

	private List<Product> products;

	/**
	 * 
	 */
	private static final long serialVersionUID = 962893447423474540L;

	@Override
	protected void createLayout(VerticalLayout layout) {

		products = productService.getProducts();

		options.setItems(products);
		options.setItemCaptionGenerator(product -> product.getName() + " ("
				+ Formatter.formatPrice(product.getPrice())
				+ ")");
		options.addStyleName(ValoTheme.OPTIONGROUP_LARGE);
		options.addValueChangeListener(evt -> updateControlButtons());

		layout.addComponent(options);
	}

	@Override
	protected String getViewHeader() {
		return "Products";
	}

	@Override
	protected void createControllerButtons() {
		createButton(addToCartButton, "Add To Cart", VaadinIcons.CART_O);
		addToCartButton.setClickShortcut(KeyCode.ENTER);
		addToCartButton.addStyleName(ValoTheme.BUTTON_FRIENDLY);

		createButton(checkAllButton, "Check All", VaadinIcons.CHECK_SQUARE_O);

		createButton(uncheckAllButton, "Uncheck All", VaadinIcons.THIN_SQUARE);

		updateControlButtons();
	}

	private Button getAddToCartButton() {
		return addToCartButton;
	}

	private Button getCheckAllButton() {
		return checkAllButton;
	}

	private Button getUncheckAllButton() {
		return uncheckAllButton;
	}

	private void checkAllBoxes(boolean select) {
		if (select) {
			products.forEach(options::select);
		} else {
			options.deselectAll();
		}
	}

	private Set<Product> getSelectedProducts() {
		return options.getValue();
	}

	private void updateControlButtons() {

		int size = options.getValue().size();
		if (size == 0) {
			checkAllButton.setEnabled(true);
			uncheckAllButton.setEnabled(false);

			addToCartButton.setEnabled(false);
		} else if (size == products.size()) {
			checkAllButton.setEnabled(false);
			uncheckAllButton.setEnabled(true);

			addToCartButton.setEnabled(true);
		} else {
			checkAllButton.setEnabled(true);
			uncheckAllButton.setEnabled(true);

			addToCartButton.setEnabled(true);
		}
	}

	@Override
	public void buttonClick(ClickEvent event) {

		if (event.getButton() == getAddToCartButton()) {

			Notification.show("Adding item(s) to cart.");

			updateCart
					.fire(new UpdateShopppingCartEvent(getSelectedProducts()));

			checkAllBoxes(false);
		} else if (event.getButton() == getCheckAllButton()) {

			checkAllBoxes(true);
		} else if (event.getButton() == getUncheckAllButton()) {

			checkAllBoxes(false);
		}
	}
}
