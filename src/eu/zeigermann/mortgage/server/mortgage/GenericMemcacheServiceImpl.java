package eu.zeigermann.mortgage.server.mortgage;

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
public class GenericMemcacheServiceImpl<T extends HasId> implements GenericDataService<T> {
	private final String name;
	private final String globalIdCntName = "globalId";
	private final MemcacheService memcacheService = getMemcacheService();

	public GenericMemcacheServiceImpl(String name) {
		super();
		this.name = name;
	}

	@Override
	public Collection<T> getAll() {
		return getMap().values();
	}

	@Override
	public T get(int id) {
		Map<Integer, T> map = getMap();
		return map.get(id);
	}
	
	@Override
	public synchronized T save(T object) {
		Map<Integer, T> map = getMap();
		if (object.getId() == -1 || object.getId() == 0) {
			object.setId(nextCnt());
		}
		map.put(object.getId(), object);
		putMap(map);
		return object;
	}
	
	@Override
	public synchronized void delete(int id) {
		Map<Integer, T> map = getMap();
		map.remove(id);
		putMap(map);
	}
	
	private synchronized Map<Integer, T> getMap() {
		Map<Integer, T> map = (Map<Integer, T>) memcacheService.get(name);
		if (map == null) {
			map = new HashMap<Integer, T>();
			memcacheService.put(name, map);
		}
		return map;
	}

	private synchronized void putMap(Map<Integer, T> map) {
			memcacheService.put(name, map);
	}

	private synchronized int nextCnt() {
		Integer cnt = (Integer) memcacheService.get(globalIdCntName);
		if (cnt == null) {
			cnt = 0;
		}
		cnt++;
		memcacheService.put(name, cnt);
		return cnt;
	}
	

}
