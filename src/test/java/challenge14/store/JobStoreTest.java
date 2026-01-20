package challenge14.store;

import challenge14.model.Job;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JobStoreTest {

    @TempDir
    Path tempDir;

    @Test
    void saveThenLoadShouldRestoreJobs() {
        Path file = tempDir.resolve("pending-jobs.ser");
        JobStore store = new JobStore(file);

        List<Job> jobs = List.of(
                new Job("A"),
                new Job("B"),
                new Job("C")
        );

        store.save(jobs);

        List<Job> loaded = store.load();

        assertEquals(3, loaded.size());
        assertEquals("A", loaded.get(0).payload());
        assertEquals("B", loaded.get(1).payload());
        assertEquals("C", loaded.get(2).payload());
    }

    @Test
    void loadShouldReturnEmptyListWhenFileDoesNotExist() {
        Path file = tempDir.resolve("non-existent.ser");
        JobStore store = new JobStore(file);

        assertTrue(store.load().isEmpty());
    }

    @Test
    void clearShouldDeleteFile() {
        Path file = tempDir.resolve("pending-jobs.ser");
        JobStore store = new JobStore(file);

        store.save(List.of(new Job("A")));

        store.clear();

        assertFalse(tempDir.resolve("pending-jobs.ser").toFile().exists());
    }
}
