package com.fiuba.diner.util;

import java.text.DecimalFormat;

public class Formatter {

	public static final String getPriceFormat(Double price) {
		String priceFormatted = null;
		DecimalFormat formatter = new DecimalFormat("0.00");
		priceFormatted = "$" + formatter.format(price);
		return priceFormatted;
	}

}
