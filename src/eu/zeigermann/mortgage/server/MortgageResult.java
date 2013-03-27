package eu.zeigermann.mortgage.server;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

@SuppressWarnings("serial")
public final class MortgageResult implements Serializable {
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