package challenge08;

import challenge08.security.TimeProvider;

public class FakeTimeProvider implements TimeProvider {

    private long currentTime;

    public FakeTimeProvider(long startTime) {
        this.currentTime = startTime;
    }

    @Override
    public long now() {
        return currentTime;
    }

    public void advanceMillis(long millis) {
        currentTime += millis;
    }
}
