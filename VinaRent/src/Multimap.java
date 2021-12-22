import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Multimap<K,V> {
	private Map<K, Collection<V>> multimap;
	public Multimap() {
		multimap = new HashMap<>();
	}
	
	public void put(K key, V value) {
		if (multimap.get(key) == null)
			multimap.put(key, new ArrayList<V>());
		multimap.get(key).add(value);
	}
	
	public void putIfAbsent(K key, V value) {
		if (multimap.get(key) == null)
			multimap.put(key, new ArrayList<V>());
		
		if (!multimap.get(key).contains(value))
			multimap.get(key).add(value);
	}
	
	public Collection<V> get(K key) {
		return multimap.get(key);
	}
	
	public Set<K> keySet() {
		return multimap.keySet();
	}
	
	public Set<Map.Entry<K, Collection<V>>> entrySet() {
		return multimap.entrySet();
	}
	
	public Collection<Collection<V>> values() {
		return multimap.values();
	}
	
	public boolean containsKey(Object key) {
		return multimap.containsKey(key);
	}
	
	public Collection<V> remove(Object key) {
		return multimap.remove(key);
	}
	
	public int size() {
		int size = 0;
		for (Collection<V> value : multimap.values())
			size += value.size();
		return size;
	}
	
	public boolean isEmpty() {
		return multimap.isEmpty();
	}
	
	public void clear() {
		multimap.clear();
	}
	
	public boolean remove(K key, V value) {
		if (multimap.get(key) != null)
			return multimap.get(key).remove(value);
		return false;
	}
	
	public boolean replace(K key, V oldValue, V newValue) {
		if (multimap.get(key) != null) {
			if (multimap.get(key).remove(oldValue))
				return multimap.get(key).add(newValue);
		}
		return false;
	}
}
