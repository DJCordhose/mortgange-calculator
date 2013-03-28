package eu.zeigermann.mortgage.server.mortgage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import eu.zeigermann.mortgage.server.MortgageData;

@SuppressWarnings("all")
public class GenericMapServiceImpl<T extends HasId> implements GenericDataService<T> {
	private final Map<Integer, T> data = new ConcurrentHashMap<Integer, T>();

	@Override
	public Collection<T> getAll() {
		return data.values();
	}
	
	@Override
	public void save(T object) {
		data.put(object.getId(), object);
	}
	
	@Override
	public void delete(int id) {
		data.remove(id);
	}

	@Override
	public T get(int id) {
		return data.get(id);
	}

}
