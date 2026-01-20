package challenge14.queue;

import challenge14.model.Job;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JobQueueTest {

    @Test
    void drainAllShouldRemoveEverythingFromQueue() throws Exception {
        JobQueue queue = new JobQueue(10);

        queue.submit(new Job("A"));
        queue.submit(new Job("B"));
        queue.submit(new Job("C"));

        assertEquals(3, queue.size());

        List<Job> drained = queue.drainAll();

        assertEquals(3, drained.size());
        assertEquals(0, queue.size());

        assertEquals("A", drained.get(0).payload());
        assertEquals("B", drained.get(1).payload());
        assertEquals("C", drained.get(2).payload());

    }
}
