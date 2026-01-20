package challenge14.model;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

public record Job(
        String id,
        String payload,
        Instant createdAt,
        boolean poison
) implements Serializable {

    public Job(String payload) {
        this(
                UUID.randomUUID().toString(),
                payload,
                Instant.now(),
                false
        );
    }

    public static Job poisonPill() {
        return new Job("POISON", "POISON", Instant.now(), true);
    }

    public boolean isPoison() {
        return poison;
    }
}
