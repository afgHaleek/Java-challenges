package challenge08.security;

public class SystemTimeProvider implements TimeProvider{

    @Override
    public long now() {
        return System.currentTimeMillis();
    }
}
