package challenge09.security;

import java.util.Random;

public class SystemRandomProvider implements RandomProvider{

    private final Random random = new Random();

    @Override
    public int nextInt(int min, int max) {
        return random.nextInt(max - min + 1) + min;
    }
}
