package com.redhat.coolstore.web.ui.events;

import java.util.Set;

/**
 * Simple event to notify shopping cart about updates
 * 
 */
import com.redhat.coolstore.model.Product;

public class UpdateShopppingCartEvent {

	private Set<Product> selectedProducts;

	public UpdateShopppingCartEvent(Set<Product> selectedProducts) {
		this.selectedProducts = selectedProducts;
	}

	public Set<Product> getSelectedProducts() {
		return selectedProducts;
	}
}
