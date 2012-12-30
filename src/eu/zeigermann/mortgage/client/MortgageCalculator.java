package eu.zeigermann.mortgage.client;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.i18n.client.NumberFormat;

public class MortgageCalculator {

	static String formatAsMoney(double money) {
		NumberFormat format = NumberFormat.getFormat("##0.00");
		String string = format.format(money);
		return string;
	}

	public final static class MortgageResult {

		public String principle;
		public String payments;
		public String monthly;
		public String total;

	}

	public final static class MortgageJSResult extends JavaScriptObject {
		protected MortgageJSResult() {

		}

		public static native MortgageJSResult create(String principle,
				String payments, String monthly, String total) /*-{
	        return {
	            principle: principle,
	            total: total,
	            payments: payments,
	            monthly: monthly
	        };
		}-*/;
	}

	public MortgageJSResult calculateMortgageJsni(double price, double down,
			double interest, double term) {
		MortgageResult result = calculateMortgage(price, down, interest, term);
		return MortgageJSResult.create(result.principle, result.payments, result.monthly, result.total);
	}

	public MortgageResult calculateMortgage(double price, double down,
			double interest, double term) {
		MortgageResult result = new MortgageResult();
		double monthylyInterestRate = (interest / 100.0) / 12.0;
		double principle = price - down;
		double payments = term * 12;
		double monthly = principle * monthylyInterestRate
				/ (1 - Math.pow(1 + monthylyInterestRate, (-1 * payments)));
		double roundedMonthly = Math.round(monthly * 100.0) / 100.0;
		result.principle = formatAsMoney(principle);
		result.payments = Integer.toString((int) payments);
		result.monthly = formatAsMoney(roundedMonthly);
		result.total = formatAsMoney(roundedMonthly * payments);
		return result;
	}

}
