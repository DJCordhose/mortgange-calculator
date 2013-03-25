package eu.zeigermann.mortgage.server.customer;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Customer implements Serializable {
	public int id;
	public String name;

	public Customer() {
		
	}
	
	public Customer(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

}
