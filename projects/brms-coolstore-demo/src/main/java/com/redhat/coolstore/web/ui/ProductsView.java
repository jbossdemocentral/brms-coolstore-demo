package com.redhat.coolstore.web.ui;

import java.util.Set;

import javax.inject.Inject;

import org.vaadin.teemu.VaadinIcons;

import com.redhat.coolstore.model.Product;
import com.redhat.coolstore.service.ProductService;
import com.redhat.coolstore.web.ui.converter.StringPropertyValueGenerator;
import com.redhat.coolstore.web.ui.events.UpdateShopppingCartEvent;
import com.redhat.coolstore.web.ui.util.Formatter;
import com.vaadin.cdi.UIScoped;
import com.vaadin.data.Item;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.GeneratedPropertyContainer;
import com.vaadin.data.util.IndexedContainer;
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

		final String CAPTION_PROPERTY = "caption";

		GeneratedPropertyContainer gContainer = new GeneratedPropertyContainer(
				new IndexedContainer(productService.getProducts()));

		gContainer.addGeneratedProperty(CAPTION_PROPERTY,
				new StringPropertyValueGenerator() {

					/**
					 * 
					 */
					private static final long serialVersionUID = -4215128943792053652L;

					@Override
					public String getValue(Item item, Object itemId,
							Object propertyId) {
						Product product = (Product) itemId;
						return product.getName() + " ("
								+ Formatter.formatPrice(product.getPrice())
								+ ")";
					}
				});

		options.setContainerDataSource(gContainer);
		options.setMultiSelect(true);
		options.setItemCaptionPropertyId(CAPTION_PROPERTY);
		options.addStyleName(ValoTheme.OPTIONGROUP_LARGE);
		options.addValueChangeListener(new ValueChangeListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = -962057120964581840L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				updateControlButtons();
			}
		});

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
			options.setValue(options.getItemIds());
		} else {
			options.setValue(null);
		}
	}

	@SuppressWarnings("unchecked")
	private Set<Product> getSelectedProducts() {
		return (Set<Product>) options.getValue();
	}

	private void updateControlButtons() {
		@SuppressWarnings("unchecked")
		int size = ((Set<Product>) options.getValue()).size();
		if (size == 0) {
			checkAllButton.setEnabled(true);
			uncheckAllButton.setEnabled(false);

			addToCartButton.setEnabled(false);
		} else if (size == options.getItemIds().size()) {
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
