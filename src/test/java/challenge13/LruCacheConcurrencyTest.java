package challenge13;

import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class LruCacheConcurrencyTest {

    @Test
    void concurrentAccessShouldNotCorruptCache() throws Exception {
        LruCache<Integer, String> cache = new LruCache<>(50);

        int threads = 10;
        int operationsPerThread = 200;

        ExecutorService pool = Executors.newFixedThreadPool(threads);
        CountDownLatch latch = new CountDownLatch(threads);

        for (int t = 0; t < threads; t++) {
            int threadId = t;

            pool.submit(() -> {
                try {
                    for (int i = 0; i < operationsPerThread; i++) {
                        int key = (threadId * 100) + i;
                        cache.put(key, "v" + key);
                        cache.get(key);
                    }
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        pool.shutdown();

        assertTrue(cache.size() <= 50);
    }
}
