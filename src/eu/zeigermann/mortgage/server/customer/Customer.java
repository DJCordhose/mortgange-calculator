package eu.zeigermann.mortgage.server.customer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import eu.zeigermann.mortgage.server.MortgageData;

@SuppressWarnings("serial")
public class Customer implements Serializable {
	public int id;
	public String name;
	public List<MortgageData> mortgages = new ArrayList<MortgageData>();
	
	public Customer() {
		
	}
	
	public Customer(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

}
