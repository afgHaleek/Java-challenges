package challenge12;

import challenge12.core.JobExecutor;
import challenge12.core.JobManager;
import challenge12.model.Job;
import challenge12.model.JobStatus;
import org.junit.jupiter.api.Test;

import java.util.concurrent.Callable;

import static org.junit.jupiter.api.Assertions.*;

class JobExecutorCancelTest {

    @Test
    void shouldCancelRunningJob() throws Exception {
        JobManager manager = new JobManager();
        JobExecutor executor = new JobExecutor(1, manager);

        Job<Void> job = new Job<>(
                "job-1",
                "sleep job",
                () -> {
                    Thread.sleep(5000); // long job
                    return null;
                }
        );

        executor.submit(job);

        // cancel quickly
        boolean cancelled = executor.cancel("job-1");

        assertTrue(cancelled);
        assertEquals(JobStatus.CANCELLED, manager.getStatus("job-1"));

        executor.shutdown();
    }

    @Test
    void cancelShouldReturnFalseWhenJobDoesNotExist() {
        JobManager manager = new JobManager();
        JobExecutor executor = new JobExecutor(1, manager);

        assertFalse(executor.cancel("missing-job"));

        executor.shutdown();
    }
}
