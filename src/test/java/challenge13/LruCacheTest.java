package challenge13;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LruCacheTest {

    @Test
    void putShouldStoreAndGetShouldReturnValue() {
        LruCache<String, Integer> cache = new LruCache<>(2);

        cache.put("A", 1);

        assertEquals(1, cache.get("A"));
        assertEquals(1, cache.size());
    }

    @Test
    void shouldEvictLeastRecentlyUsed_whenCapacityExceeded() {
        LruCache<String, Integer> cache = new LruCache<>(2);

        cache.put("A", 1);
        cache.put("B", 2);

        //cache is full now. A is oldest, B is newest.
        cache.put("C", 3); // should evict A

        assertNull(cache.get("A"));
        assertEquals(2, cache.get("B"));
        assertEquals(3, cache.get("C"));
        assertEquals(2, cache.size());
    }

    @Test
    void getShouldRefreshUsageSoRecentlyAccessedItemIsNotEvicted() {
        LruCache<String, Integer> cache = new LruCache<>(2);

        cache.put("A", 1);
        cache.put("B", 2);

        //Access A, so A becomes "most recently used"
        assertEquals(1, cache.get("A"));

        //B is the least recently used
        cache.put("C", 3);

        assertNotNull(cache.get("A"));
        assertNull(cache.get("B"));
        assertEquals(3, cache.get("C"));
        assertEquals(1, cache.get("A"));
    }
}
