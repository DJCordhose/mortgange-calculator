package eu.zeigermann.mortgage.server.customer;

import java.util.Collection;

import eu.zeigermann.mortgage.server.MortgageData;

public interface CustomerService {

	public Customer get(int id);
	
	public Collection<Customer> getAll();

	public void save(Customer customer);

	public void delete(Customer customer);

}