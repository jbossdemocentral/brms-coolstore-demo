package com.redhat.coolstore.web.ui.util;

import java.text.DecimalFormat;

public class Formatter {

	private static final DecimalFormat DF = new DecimalFormat("'$'0.00");

	public static String formatPrice(double value) {
		return DF.format(value);
	}
}
