package eu.zeigermann.mortgage.client;

import com.google.gwt.junit.client.GWTTestCase;

public class MortgageCalculatorTest extends GWTTestCase {

	MortgageCalculator calculator;
	MortgageCalculator.MortgageResult result;

	protected void gwtSetUp() {
		calculator = new MortgageCalculator();
		result = calculator.calculateMortgage(200000, 10, 7.5, 30);
	}
	
	public void testPrinciple() {
		assertEquals("199990.00", result.principle);
	}
	
	public void testTotal() {
		assertEquals("503409.60", result.total);
	}
	
	public void testPayments() {
		assertEquals("360", result.payments);
	}
	
	public void testMonthly() {
		assertEquals("1398.36", result.monthly);
	}

	@Override
	public String getModuleName() {
		return "eu.zeigermann.mortgage.Mortgage_Calculator";
	}

}
