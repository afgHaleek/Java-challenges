package challenge12;

import challenge12.core.JobExecutor;
import challenge12.core.JobManager;
import challenge12.model.Job;
import challenge12.model.JobStatus;

import java.util.concurrent.TimeUnit;

public class DemoMain {

    public static void main(String[] args) throws Exception {
        JobManager manager = new JobManager();
        JobExecutor executor = new JobExecutor(3, manager);

        System.out.println("Submitting jobs...");

        //Job 1: quick
        executor.submit(new Job<>(
                "job-1",
                "quick job",
                () -> "Done fast"
        ));

        //Job 2: slow job
        executor.submit(new Job<>(
                "job-2",
                "slow job",
                () -> {
                    Thread.sleep(5000);
                    return "Done slow";
                }
        ));

        //Job 3: failing job
        executor.submit(new Job<>(
                "job-3",
                "failing job",
                () -> {
                    throw new IllegalStateException("sth went wrong");
                }
        ));

        Thread.sleep(500);

        System.out.println("\nStatuses snapshot:");
        manager.getSnapshotStatuses().forEach((id, status) ->
                        System.out.println(id + " -> " + status)
                );

        System.out.println("\nCancelling job-2...");
        boolean cancelled = executor.cancel("job-2");
        System.out.println("Cancelled job-2: " + cancelled);

        //wait upto to 2 sec for job 1 and job 3 to settle
        System.out.println("\nWaiting for results...");
        executor.awaitResult("job-1", 2, TimeUnit.SECONDS);
        executor.awaitResult("job-3", 2, TimeUnit.SECONDS);

        System.out.println("\n Results snapshot:");
        manager.getSnapshotResults().forEach((id, result)-> {
            if (result == null) return;
            System.out.println(id + " -> " + result.status()
                    + " value=" + result.value()
                    + " error=" + (result.error() == null ? "none" : result.error().getMessage())
            );
        });

        System.out.println("\nCounts:");
        System.out.println("SUCCESS: " + manager.countByStatus(JobStatus.SUCCESS));
        System.out.println("FAILED: " + manager.countByStatus(JobStatus.FAILED));
        System.out.println("CANCELLED: " + manager.countByStatus(JobStatus.CANCELLED));

        System.out.println("\nShutting down gracefully...");
        executor.shutdownGracefully(2, TimeUnit.SECONDS);
        System.out.println("Shutdown complete.");
    }
}
