package com.redhat.coolstore.web.ui.components;

import org.vaadin.viritin.fields.LabelField;

import com.redhat.coolstore.web.ui.converter.DoubleStringConverter;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.themes.ValoTheme;

public class ShoppingCartLine extends HorizontalLayout {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6465756664279342573L;

	private static final String LABEL_WIDTH = "8em";

	public ShoppingCartLine(String caption, LabelField<String> value) {
		this(caption, value, false);
	}

	public ShoppingCartLine(String caption, LabelField<String> value,
			boolean isSecondary) {
		super();

		setWidth("100%");

		Label label = new Label(caption);
		if (isSecondary) {
			label.addStyleName(ValoTheme.LABEL_LIGHT);
		} else {
			label.addStyleName(ValoTheme.LABEL_BOLD);
		}
		label.setWidth(LABEL_WIDTH);

		value.setConverter(new DoubleStringConverter());
		value.setWidth(LABEL_WIDTH);

		addComponent(label);
		addComponent(value);
		setComponentAlignment(value, Alignment.MIDDLE_RIGHT);
	}
}
