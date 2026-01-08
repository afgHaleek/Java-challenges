package challenge12;

import challenge12.core.JobExecutor;
import challenge12.core.JobManager;
import challenge12.model.Job;
import org.junit.jupiter.api.Test;

import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class JobExecutorShutdownTest {

    @Test
    void shouldRejectNewJobsAfterShutdownStarts() {
        JobManager manager = new JobManager();
        JobExecutor executor = new JobExecutor(1, manager);

        executor.shutdownGracefully(1, TimeUnit.SECONDS);

        Job<Void> job = new Job<>("job-x", "test", () -> null);

        assertThrows(RejectedExecutionException.class, () -> executor.submit(job));
    }

    @Test
    void gracefulShutdownShouldFinishShortJobs() {
        JobManager manager = new JobManager();
        JobExecutor executor = new JobExecutor(1, manager);

        Job<Void> job = new Job<>("job-1", "short job", () -> {
            Thread.sleep(100);
            return null;
        });

        executor.submit(job);

        executor.shutdownGracefully(2, TimeUnit.SECONDS);

        assertFalse(executor.isAccepting());
        // If shutdown finished, test passes without hanging.
    }
}
