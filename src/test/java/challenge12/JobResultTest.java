package challenge12;

import challenge12.core.JobExecutor;
import challenge12.core.JobManager;
import challenge12.model.Job;
import challenge12.model.JobResult;
import challenge12.model.JobStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public class JobResultTest {

    @Test
    void successFactoryShouldCreateSuccessResult() {
        JobResult<Integer> result = JobResult.success(42);
        Assertions.assertEquals(JobStatus.SUCCESS, result.status());
        assertEquals(42, result.value());
        assertNull(result.error());

    }

    @Test
    void failedFactoryShouldCreateFailedResult() {
        RuntimeException ex = new RuntimeException("boom");
        JobResult<Void> result = JobResult.failed(ex);

        assertEquals(JobStatus.FAILED, result.status());
        assertNull(result.value());
        assertEquals(ex, result.error());
    }

    @Test
    void cancelledFactoryShouldCreateCancelledResult() {
        JobResult<String> result = JobResult.cancelled();

        assertEquals(JobStatus.CANCELLED, result.status());
        assertNull(result.value());
        assertNull(result.error());
    }

    @Test
    void shouldStoreSuccessResult_whenJobCompletes() throws Exception {
        JobManager manager = new JobManager();
        JobExecutor executor = new JobExecutor(2, manager);

        executor.submit(new Job<>(
                "job-success",
                "returns value",
                () -> "OK"
        ));

        // wait a bit for completion
        JobResult<?> result = executor.awaitResult("job-success", 2, TimeUnit.SECONDS);

        assertNotNull(result);
        assertEquals(JobStatus.SUCCESS, result.status());
        assertEquals("OK", result.value());
        assertNull(result.error());

        executor.shutdownGracefully(2, TimeUnit.SECONDS);
    }

    @Test
    void shouldStoreFailedResult_whenJobThrowsException() throws Exception {
        JobManager manager = new JobManager();
        JobExecutor executor = new JobExecutor(2, manager);

        executor.submit(new Job<>(
                "job-fail",
                "throws error",
                () -> { throw new IllegalStateException("boom"); }
        ));

        JobResult<?> result = executor.awaitResult("job-fail", 2, TimeUnit.SECONDS);

        assertNotNull(result);
        assertEquals(JobStatus.FAILED, result.status());
        assertNull(result.value());
        assertNotNull(result.error());
        assertEquals("boom", result.error().getMessage());

        executor.shutdownGracefully(2, TimeUnit.SECONDS);
    }
}
