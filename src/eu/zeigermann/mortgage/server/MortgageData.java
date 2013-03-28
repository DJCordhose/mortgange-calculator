package eu.zeigermann.mortgage.server;

import java.io.Serializable;
import java.math.BigDecimal;

import eu.zeigermann.mortgage.server.mortgage.HasId;

@SuppressWarnings("serial")
public final class MortgageData implements HasId, Serializable {
	public int id = -1;
	public String name;
	public BigDecimal price;
	public BigDecimal down;
	public BigDecimal interest;
	public BigDecimal term;
	@Override
	public int getId() {
		return id;
	}
}