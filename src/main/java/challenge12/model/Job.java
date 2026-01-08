package challenge12.model;

import java.util.concurrent.Callable;

public record Job<T>(
        String id,
        String description,
        Callable<T> task
) {
}
