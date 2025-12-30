package challenge08.service;

import challenge07.exception.UserNotFoundException;
import challenge07.model.User;
import challenge07.repository.UserRepository;
import challenge07.security.PasswordHasher;
import challenge08.exception.AccountLockedException;
import challenge08.exception.InvalidCredentialsException;
import challenge08.model.AuthenticatedUser;
import challenge08.security.TimeProvider;

import java.util.HashMap;
import java.util.Map;

public class AuthService {

    private final  UserRepository userRepository;
    private final Map<String, Integer> attemptsByUser = new HashMap<>();
    private final Map<String, Long> lockedUntilByUser = new HashMap<>();
    private static final int MAX_LOGIN_ATTEMPTS = 3;
    private static final long LOCK_DURATION_MS = 60_000; // 1 MINUTE (DEMO)
    private final TimeProvider timeProvider;

    public AuthService(UserRepository userRepository, TimeProvider timeProvider) {
        this.userRepository = userRepository;
        this.timeProvider = timeProvider;
    }

    public AuthenticatedUser login(String username, String password) {

        if (username == null || password == null || username.isBlank() || password.isBlank()) {
            throw new InvalidCredentialsException("Username and password cannot be blank");
        }

        String key = username.trim().toLowerCase();
        String normalizedUsername = username.trim().toLowerCase();
        long now = timeProvider.now();


        Long lockedUntil = lockedUntilByUser.get(key);

        if (lockedUntil != null) {
            if (now < lockedUntil) {
                long secondsLeft = (lockedUntil - now + 999) / 1000;
                throw new AccountLockedException("Account locked for " + secondsLeft + " seconds");
            } else {
                lockedUntilByUser.remove(key);
                attemptsByUser.remove(key);
            }
        }

        User user = userRepository.findByUsername(normalizedUsername)
                .orElseThrow(() -> new UserNotFoundException("User Not found!"));

        String hashedPass = PasswordHasher.hash(password);

        if (!user.password().equals(hashedPass)) {
           int attempts = attemptsByUser.getOrDefault(key, 0) + 1;
           attemptsByUser.put(key, attempts);

           if (attempts >= MAX_LOGIN_ATTEMPTS) {
               lockedUntilByUser.put(key, now + LOCK_DURATION_MS);
               throw new AccountLockedException("Account locked for " + LOCK_DURATION_MS / 1000 + " seconds");
           }

           throw new InvalidCredentialsException("Invalid credentials!");
        }

        attemptsByUser.remove(key);
        lockedUntilByUser.remove(key);
        return new AuthenticatedUser(user.username(), user.email(), user.age());
    }
}
