package com.redhat.coolstore.web.ui.converter;

import com.vaadin.data.Item;
import com.vaadin.data.util.PropertyValueGenerator;

public abstract class StringPropertyValueGenerator extends
		PropertyValueGenerator<String> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 511915040469735826L;

	@Override
	abstract public String getValue(Item item, Object itemId, Object propertyId);

	@Override
	public Class<String> getType() {
		return String.class;
	}

}
