package challenge14.app;

import challenge14.model.Job;
import challenge14.queue.JobQueue;
import challenge14.store.JobStore;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RestoreFlowTest {

    @TempDir
    Path tempDir;

    @Test
    void shutdownAndRestoreShouldKeepPendingJobs() throws Exception {
        Path file = tempDir.resolve("pending_jobs.ser");

        JobQueue queue = new JobQueue(50);
        JobStore store = new JobStore(file);

        //simulate pending jobs during shutdown
        queue.submit(new Job("A"));
        queue.submit(new Job("B"));


        List<Job> remaining = queue.drainAll();
        store.save(remaining);

        //simulate restart
        List<Job> loaded = store.load();
        assertEquals(2, loaded.size());

        JobQueue restoredQueue = new JobQueue(50);
        for (Job job : loaded) {
            restoredQueue.submit(job);
        }

        store.clear();

        assertEquals(2, restoredQueue.size());
        assertTrue(store.load().isEmpty());
    }
}
