package com.redhat.coolstore.web.ui.converter;

import java.util.Locale;

import com.redhat.coolstore.web.ui.util.Formatter;
import com.vaadin.data.util.converter.Converter;

public class DoubleStringConverter implements Converter<String, Double> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6298718219039252789L;

	@Override
	public Double convertToModel(String value,
			Class<? extends Double> targetType, Locale locale)
			throws com.vaadin.data.util.converter.Converter.ConversionException {
		if (value.charAt(0) == '-') {
			return -1 * Double.parseDouble(value.substring(2));
		} else {
			return Double.parseDouble(value.substring(1));
		}
	}

	@Override
	public String convertToPresentation(Double value,
			Class<? extends String> targetType, Locale locale)
			throws com.vaadin.data.util.converter.Converter.ConversionException {
		return Formatter.formatPrice(value);
	}

	@Override
	public Class<Double> getModelType() {
		return Double.class;
	}

	@Override
	public Class<String> getPresentationType() {
		return String.class;
	}
}