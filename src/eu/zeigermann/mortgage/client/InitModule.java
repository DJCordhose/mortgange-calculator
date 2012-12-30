package eu.zeigermann.mortgage.client;

import com.google.gwt.core.client.EntryPoint;

public class InitModule implements EntryPoint {

	@Override
	public void onModuleLoad() {
		MortgageCalculator mortgageCalculator = new MortgageCalculator();
		publish(mortgageCalculator);
	}
	
	private native void publish(MortgageCalculator mortgageCalculator) /*-{
		$wnd.gwtCalculateMortgage = $entry(mortgageCalculator.@eu.zeigermann.mortgage.client.MortgageCalculator::calculateMortgageJsni(DDDD));
	}-*/;

}
