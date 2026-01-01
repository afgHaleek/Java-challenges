package challenge09.security;

public class FakeRandomProvider implements RandomProvider{

    private final int fixedValue;

    public FakeRandomProvider(int fixedValue) {
        this.fixedValue = fixedValue;
    }

    @Override
    public int nextInt(int min, int max) {
        return fixedValue;
    }
}
