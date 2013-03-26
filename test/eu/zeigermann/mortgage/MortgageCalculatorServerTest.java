package eu.zeigermann.mortgage;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import eu.zeigermann.mortgage.server.MortgageCalculator;
import eu.zeigermann.mortgage.server.MortgageData;
import eu.zeigermann.mortgage.server.MortgageResult;

public class MortgageCalculatorServerTest {

	MortgageCalculator calculator;
	MortgageResult result;
	@Before
	public void initialize() {
		calculator = new MortgageCalculator();
		MortgageData data = new MortgageData();
		data.down = BigDecimal.valueOf(10);
		data.interest = BigDecimal.valueOf(7.5);
		data.term = BigDecimal.valueOf(30);
		data.price = BigDecimal.valueOf(200000);
		result = calculator.calculateMortgage(data);
	}
	
	@Test
	public void principle() {
		assertEquals("199990.00", result.formatPrinciple());
	}
	
	@Test
	public void total() {
		assertEquals("503409.60", result.formatTotal());
	}
	
	@Test
	public void payments() {
		assertEquals("360", result.formatPayments());
	}
	
	@Test
	public void monthly() {
		assertEquals("1398.36", result.formatMonthly());
	}

}
