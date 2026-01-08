package challenge12.model;

public record JobResult<T>(
        JobStatus status,
        T value,
        Throwable error
) {

    public static <T> JobResult<T> success(T value) {
        return new JobResult<>(JobStatus.SUCCESS, value, null);
    }

    public static <T> JobResult<T> failed(Throwable error) {
        return new JobResult<>(JobStatus.FAILED, null, error);
    }

    public static <T> JobResult<T> cancelled() {
        return new JobResult<>(JobStatus.CANCELLED, null, null);
    }
}
