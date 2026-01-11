package challenge13;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class LruCacheUtilityTest {

    @Test
    void removeShouldDeleteEntry() {
        LruCache<String, Integer> cache = new LruCache<>(2);

        cache.put("A", 1);
        assertEquals(1, cache.remove("A"));
        assertNull(cache.get("A"));
        assertEquals(0., cache.size());
    }

    @Test
    void clearShouldRemoveEveryThing() {
        LruCache<String, Integer> cache = new LruCache<>(2);

        cache.put("A", 1);
        cache.put("B", 2);

        cache.clear();

        assertTrue(cache.isEmpty());
        assertEquals(0, cache.size());
    }

    @Test
    void snapshotShouldReturnCopyNotLiveMap() {
        LruCache<String, Integer> cache = new LruCache<>(2);

        cache.put("A", 1);

        Map<String, Integer> snapshot = cache.snapshot();
        snapshot.put("B", 2);

        //should not affect original cache
        assertNull(cache.get("B"));
        assertEquals(1, cache.get("A"));
    }

    @Test
    void capacityMustBePositive() {
        assertThrows(IllegalArgumentException.class, () -> new LruCache<String, Integer>(0));
        assertThrows(IllegalArgumentException.class, () -> new LruCache<String, Integer>(-1));
    }
}
