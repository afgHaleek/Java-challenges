package challenge12.core;

import challenge12.model.Job;
import challenge12.model.JobResult;

import java.util.concurrent.*;

public class JobExecutor {

    private final ExecutorService executor;
    private final JobManager jobManager;

    private volatile boolean accepting = true;

    public JobExecutor(int workers, JobManager jobManager) {
        this.executor =  Executors.newFixedThreadPool(workers);
        this.jobManager = jobManager;
    }


    public <T> String submit(Job<T> job) {

        if (!accepting) {
            throw new RejectedExecutionException("JobExecutor is shutting down; no longer accepting new jobs.");
        }

        String jobId = job.id();

        //mark job as pending
        jobManager.markPending(jobId);

        //warp job logic so we can track state
        Future<?> future = executor.submit(() -> {
            jobManager.markRunning(jobId);

            try {
                T value = job.task().call();
                jobManager.markSuccess(jobId, JobResult.success(value));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                jobManager.markCancelled(jobId);
            }
            catch (Exception e) {
                jobManager.markFailed(jobId, JobResult.failed(e));
            }
        });

        //store future so we can cancel later
        jobManager.registerFuture(jobId, future);

        return jobId;
    }

    public void shutdown() {
        executor.shutdown();
    }

    public boolean cancel(String jobId) {
        Future<?> future = jobManager.getFuture(jobId);

        if (future == null) {
            return false; //job does not exist or not tracked
        }

        //if already done we can not cancel
        if (future.isDone()) {
            return false;
        }

        boolean cancelled = future.cancel(true);

        if (cancelled) {
            jobManager.markCancelled(jobId);
        }

        return cancelled;
    }

    //Graceful shutdown: stop accepting + let jobs finish
    public void shutdownGracefully(long timeout, TimeUnit unit) {
        accepting = false;

        executor.shutdown(); // no new tasks accepted

        try {
            if (!executor.awaitTermination(timeout, unit)) {
                // did not finish in time -> force shutdown
                shutdownNow();
            }
        } catch (InterruptedException e) {
            shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

    // force shutdown: interrupt running jobs and return queued jobs
    public void shutdownNow() {
        accepting = false;

        //returns jobs that never started
        executor.shutdownNow();
    }

    public boolean isAccepting() {
        return accepting;
    }

    public JobResult<?> awaitResult(String jobId, long timeout, TimeUnit timeUnit) throws TimeoutException, InterruptedException {
        Future<?> future = jobManager.getFuture(jobId);

        if (future == null) return null;

        try {
            future.get(timeout, timeUnit); // await for completion
        } catch (ExecutionException ignored) {
            //result is already recorded in JobManager as FAILED
        } catch (CancellationException ignored) {
            // result is recorded as CANCELLED
        }

        return jobManager.getResult(jobId);
    }
}
