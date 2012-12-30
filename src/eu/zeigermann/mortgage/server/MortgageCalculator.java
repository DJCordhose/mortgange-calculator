package eu.zeigermann.mortgage.server;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class MortgageCalculator {
	public final static class MortgageResult {
		public BigDecimal principle;
		public BigDecimal total;
		public BigDecimal payments;
		public BigDecimal monthly;

		public String formatPrinciple() {
			return principle.setScale(2, RoundingMode.HALF_EVEN).toPlainString();
		}

		public String formatTotal() {
			return total.setScale(2, RoundingMode.HALF_EVEN).toPlainString();
		}

		public String formatPayments() {
			return payments.setScale(0, RoundingMode.HALF_EVEN).toPlainString();
		}

		public String formatMonthly() {
			return monthly.setScale(2, RoundingMode.HALF_EVEN).toPlainString();
		}
		
		public String stringify() {
			return "{\"principle\": \"" + formatPrinciple() + "\", \"total\": \"" + formatTotal()
					+ "\", \"payments\": \"" + formatPayments() + "\", \"monthly\": \"" + formatMonthly()
					+ "\"}";
		}

	}

	public final static class MortgageData {
		public BigDecimal price;
		public BigDecimal down;
		public BigDecimal interest;
		public BigDecimal term;
	}

	public MortgageResult calculateMortgage(MortgageData data) {
		MathContext mc = MathContext.DECIMAL128;
		MortgageResult result = new MortgageResult();
		BigDecimal monthylyInterestRate = data.interest.divide(
				BigDecimal.valueOf(100 * 12), mc);
		BigDecimal principle = data.price.subtract(data.down);
		int payments = data.term.intValueExact() * 12;

		BigDecimal monthly = principle.multiply(monthylyInterestRate).divide(
				BigDecimal.valueOf(1).subtract(
						BigDecimal.valueOf(1).divide(
								BigDecimal.valueOf(1).add(monthylyInterestRate)
										.pow(payments), mc)), mc);
		BigDecimal roundedMonthly = monthly.setScale(2, RoundingMode.HALF_EVEN);
		result.total = roundedMonthly.multiply(BigDecimal.valueOf(payments));
		result.principle = principle;
		result.monthly = roundedMonthly;
		result.payments = BigDecimal.valueOf(payments);
		return result;
	}

}
