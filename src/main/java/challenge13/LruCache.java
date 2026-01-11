package challenge13;

import java.util.LinkedHashMap;
import java.util.Map;

public class LruCache<K, V> {
    private final int capacity;
    private final LinkedHashMap<K, V> map;

    public LruCache(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("capacity must be > 0");
        }

        this.capacity = capacity;

        this.map = new LinkedHashMap<>(capacity, 0.75f, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
                return size() > LruCache.this.capacity;
            }
        };
    }

    public synchronized V get(K key) {
        return map.get(key);
    }

    public synchronized void put(K key, V value) {
        map.put(key, value);
    }

    public synchronized int size() {
        return map.size();
    }

    public synchronized boolean containsKey(K key) {
        return map.containsKey(key);
    }

    public synchronized V remove(K key) {
        return map.remove(key);
    }

    public synchronized void clear() {
        map.clear();
    }

    public int capacity() {
        return capacity;
    }

    public synchronized boolean isEmpty() {
        return map.isEmpty();
    }

    public synchronized Map<K, V> snapshot() {
        return new LinkedHashMap<>(map);
    }


}
