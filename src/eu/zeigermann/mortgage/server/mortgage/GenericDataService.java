package eu.zeigermann.mortgage.server.mortgage;

import java.util.Collection;

public interface GenericDataService<T extends HasId> {

	public T get(int id);
	
	public Collection<T> getAll();

	public void save(T mortgage);
	
	public void delete(int id);

}