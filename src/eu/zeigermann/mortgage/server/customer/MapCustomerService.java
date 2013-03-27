package eu.zeigermann.mortgage.server.customer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@SuppressWarnings("all")
public class MapCustomerService implements CustomerService {
	private static Map<Integer, Customer> customers = new ConcurrentHashMap<Integer, Customer>();

	@Override
	public Collection<Customer> getAll() {
		return customers.values();
	}
	
	@Override
	public void save(Customer customer) {
		customers.put(customer.id, customer);
	}
	
	@Override
	public void delete(Customer customer) {
		customers.remove(customer.id);
	}

	@Override
	public Customer get(int id) {
		return customers.get(id);
	}
}
