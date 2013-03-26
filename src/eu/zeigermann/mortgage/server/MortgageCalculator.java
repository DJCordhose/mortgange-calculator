package eu.zeigermann.mortgage.server;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class MortgageCalculator {
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
