package eu.zeigermann.mortgage.server;

import java.io.Serializable;
import java.math.BigDecimal;

@SuppressWarnings("serial")
public final class MortgageData implements Serializable {
	public String name;
	public BigDecimal price;
	public BigDecimal down;
	public BigDecimal interest;
	public BigDecimal term;
}