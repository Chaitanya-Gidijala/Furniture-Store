package com.foryou.webapp.currency;

import java.text.DecimalFormat;

import org.springframework.stereotype.Component;

@Component
public class IndianCurrencyFormatter {
	// Method to format price into Indian currency format
		public static String formatPriceToIndianCurrency(double price) {
			// Format price into Indian currency format
			DecimalFormat indianCurrencyFormat = new DecimalFormat("\u20B9#,##,##0.00");
			return indianCurrencyFormat.format(price);
		}

		// Method to check if the price is a valid number
		public static boolean isValidPrice(String price) {
			try {
				Double.parseDouble(price);
				return true;
			} catch (NumberFormatException e) {
				return false;
			}
		}
}

