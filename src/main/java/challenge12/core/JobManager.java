package challenge12.core;

import challenge12.model.JobResult;
import challenge12.model.JobStatus;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;

public class JobManager {
    //jobId -> status
    private final Map<String, JobStatus> jobStatuses = new ConcurrentHashMap<>();

    //jobId -> future (execution handle)
    private final Map<String, Future<?>> futures = new ConcurrentHashMap<>();

    //results success/failure/cancelled
    private final Map<String, JobResult<?>> results = new ConcurrentHashMap<>();


    public JobStatus getStatus(String jobId) {
        return jobStatuses.get(jobId);
    }

    public JobResult<?> getResult(String jobId) {
        return results.get(jobId);
    }

    public Future<?> getFuture(String jobId) {
        return futures.get(jobId);
    }

    public Set<String> getAllJobIds() {
        return Collections.unmodifiableSet(jobStatuses.keySet());
    }

    public Map<String, JobStatus> getSnapshotStatuses() {
        //snapshot copy because that caller does not depend on live map changing under them
        return new HashMap<>(jobStatuses);
    }

    public Map<String, JobResult<?>> getSnapshotResults() {
        return new HashMap<>(results);
    }

    public long countByStatus(JobStatus status) {
        return jobStatuses.values().stream()
                .filter(s -> s == status).count();
    }

    public boolean exists(String jobId) {
        return jobStatuses.containsKey(jobId);
    }


    //write methods

    public void markPending(String jobId) {
        jobStatuses.put(jobId, JobStatus.PENDING);
    }

    public void markRunning(String jobId) {
        jobStatuses.put(jobId, JobStatus.RUNNING);
    }

    public void markSuccess(String jobId, JobResult<?> result) {
        jobStatuses.put(jobId, JobStatus.SUCCESS);
        results.put(jobId, result);
    }

    public void markFailed(String jobId, JobResult<?> result) {
        jobStatuses.put(jobId, JobStatus.FAILED);
        results.put(jobId, result);
    }

    public void markCancelled(String jobId) {
        jobStatuses.put(jobId, JobStatus.CANCELLED);
        results.put(jobId, JobResult.cancelled());
    }

    public void registerFuture(String jobId, Future<?> future) {
        futures.put(jobId, future);
    }
}
