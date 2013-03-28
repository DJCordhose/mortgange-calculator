package eu.zeigermann.mortgage.server.customer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.google.appengine.api.memcache.MemcacheService;

import eu.zeigermann.mortgage.server.MortgageData;

import static com.google.appengine.api.memcache.MemcacheServiceFactory.*;
@SuppressWarnings("all")
public class MemcacheCustomerService implements CustomerService {
	private static final String CUSTOMER = "customer";
	private static MemcacheService memcacheService = getMemcacheService();

	@Override
	public Collection<Customer> getAll() {
		return getCustomerMap().values();
	}
	

	@Override
	public Customer get(int id) {
		Map<Integer, Customer> customerMap = getCustomerMap();
		return customerMap.get(id);
	}
	
	@Override
	public synchronized void save(Customer customer) {
		Map<Integer, Customer> customerMap = getCustomerMap();
		customerMap.put(customer.id, customer);
		putCustomerMap(customerMap);
	}
	
	@Override
	public synchronized void delete(Customer customer) {
		Map<Integer, Customer> customerMap = getCustomerMap();
		customerMap.remove(customer.id);
		putCustomerMap(customerMap);
	}
	
	private synchronized Map<Integer, Customer> getCustomerMap() {
		Map<Integer, Customer> customerMap = (Map<Integer, Customer>) memcacheService.get(CUSTOMER);
		if (customerMap == null) {
			customerMap = new HashMap<Integer, Customer>();
			memcacheService.put(CUSTOMER, customerMap);
		}
		return customerMap;
	}

	private synchronized void putCustomerMap(Map<Integer, Customer> customerMap) {
			memcacheService.put(CUSTOMER, customerMap);
	}
}
