package com.redhat.coolstore.web.ui.components;

import com.redhat.coolstore.model.ShoppingCart;
import com.redhat.coolstore.web.ui.util.Formatter;
import com.vaadin.data.Binder;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.themes.ValoTheme;
import org.vaadin.viritin.fields.LabelField;

public class ShoppingCartLine extends HorizontalLayout {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6465756664279342573L;

	private static final String LABEL_WIDTH = "8em";

	public ShoppingCartLine(String caption, LabelField<String> value, Binder<ShoppingCart> binder) {
		this(caption, value, binder, false);
	}

	public ShoppingCartLine(String caption, LabelField<String> value, Binder<ShoppingCart> binder,
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

		binder.forMemberField(value).withConverter(toModel -> {
			if (toModel.charAt(0) == '-') {
				return -1 * Double.parseDouble(toModel.substring(2));
			} else {
				return Double.parseDouble(toModel.substring(1));
			}
		}, Formatter::formatPrice);
		value.setWidth(LABEL_WIDTH);

		addComponent(label);
		addComponent(value);
		setComponentAlignment(value, Alignment.MIDDLE_RIGHT);
	}
}
