package com.redhat.coolstore.web.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import org.vaadin.teemu.VaadinIcons;

import com.redhat.coolstore.model.Product;
import com.redhat.coolstore.model.ShoppingCartItem;
import com.redhat.coolstore.service.ProductService;
import com.redhat.coolstore.web.ui.events.UpdateShopppingCartEvent;
import com.redhat.coolstore.web.ui.util.Formatter;
import com.vaadin.cdi.UIScoped;
import com.vaadin.data.Item;
import com.vaadin.data.util.GeneratedPropertyContainer;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.data.util.PropertyValueGenerator;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Notification;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@UIScoped
public class ProductsView extends AbstractView {

	@Inject
	private ProductService productService;

	@Inject
	private javax.enterprise.event.Event<UpdateShopppingCartEvent> updateCart;

	private Button addToCartButton = new Button();

	private Button checkAllButton = new Button();

	private Button uncheckAllButton = new Button();

	private OptionGroup options = new OptionGroup();

	/**
	 * 
	 */
	private static final long serialVersionUID = 962893447423474540L;

	@Override
	protected void createLayout(VerticalLayout layout) {

		GeneratedPropertyContainer gContainer = new GeneratedPropertyContainer(
				new IndexedContainer(productService.getProducts()));

		gContainer.addGeneratedProperty("caption",
				new PropertyValueGenerator<String>() {

					/**
					 * 
					 */
					private static final long serialVersionUID = 1952983658158929968L;

					@Override
					public String getValue(Item item, Object itemId,
							Object propertyId) {
						Product product = (Product) itemId;
						return product.getName() + " ("
								+ Formatter.formatPrice(product.getPrice())
								+ ")";
					}

					@Override
					public Class<String> getType() {
						return String.class;
					}
				});

		options.setContainerDataSource(gContainer);
		options.setMultiSelect(true);
		options.setItemCaptionPropertyId("caption");
		options.addStyleName(ValoTheme.OPTIONGROUP_LARGE);

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
			options.setValue(options.getItemIds());
		} else {
			options.setValue(null);
		}
	}

	@SuppressWarnings("unchecked")
	private Set<Product> getSelectedProducts() {
		return (Set<Product>) options.getValue();
	}

	private void addItemsToShoppingCart() {

		Set<Product> productsToAddList = getSelectedProducts();

		Map<String, ShoppingCartItem> shoppingCartItemMap = new HashMap<String, ShoppingCartItem>();

		for (ShoppingCartItem sci : getShoppingCart().getShoppingCartItemList()) {

			shoppingCartItemMap.put(sci.getProduct().getItemId(), sci);

		}

		if (productsToAddList != null && productsToAddList.size() > 0) {

			for (Product p : productsToAddList) {

				if (shoppingCartItemMap.containsKey(p.getItemId())) {

					ShoppingCartItem sci = shoppingCartItemMap.get(p
							.getItemId());

					sci.setQuantity(sci.getQuantity() + 1);

				} else {

					ShoppingCartItem sci = new ShoppingCartItem();

					sci.setProduct(p);

					sci.setQuantity(1);

					getShoppingCart().addShoppingCartItem(sci);

					shoppingCartItemMap.put(p.getItemId(), sci);

				}

			}

		}

	}

	@Override
	public void buttonClick(ClickEvent event) {

		if (event.getButton() == getAddToCartButton()) {

			// TODO: FIX when cart is applying same promo each time cart is
			// calculated. This is workaround.
			if (getSelectedProducts().size() > 0) {

				Notification.show("Adding item(s) to cart.");

				addItemsToShoppingCart();

				updateCart.fire(new UpdateShopppingCartEvent());

				checkAllBoxes(false);
			} else {

				Notification.show("Please select one or more items.");
			}

		} else if (event.getButton() == getCheckAllButton()) {

			checkAllBoxes(true);
		} else if (event.getButton() == getUncheckAllButton()) {

			checkAllBoxes(false);
		}
	}
}
