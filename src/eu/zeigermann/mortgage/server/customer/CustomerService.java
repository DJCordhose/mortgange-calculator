package eu.zeigermann.mortgage.server.customer;

import java.util.Collection;

public interface CustomerService {

	public Collection<Customer> getAll();

	public void save(Customer customer);

	public void delete(Customer customer);

}