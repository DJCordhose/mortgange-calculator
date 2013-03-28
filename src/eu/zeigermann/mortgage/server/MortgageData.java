package eu.zeigermann.mortgage.server;

import java.io.Serializable;
import java.math.BigDecimal;

@SuppressWarnings("serial")
public final class MortgageData implements Serializable {
	public int id = -1;
	public int customerId = -1;
	public String name;
	public BigDecimal price;
	public BigDecimal down;
	public BigDecimal interest;
	public BigDecimal term;
}