package challenge08;

import challenge06.service.UserValidationService;
import challenge07.exception.UserNotFoundException;
import challenge07.model.User;
import challenge07.repository.InMemoryUserRepository;
import challenge07.repository.UserRepository;
import challenge07.service.UserRegistrationService;
import challenge08.exception.AccountLockedException;
import challenge08.exception.InvalidCredentialsException;
import challenge08.model.AuthenticatedUser;
import challenge08.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AuthServiceTest {


    UserRepository userRepository;
    UserRegistrationService registrationService;
    AuthService authService;
    UserValidationService validationService;
    FakeTimeProvider fakeTime;

    @BeforeEach
    void setUp() {
        userRepository = new InMemoryUserRepository();
        validationService = new UserValidationService();
        registrationService = new UserRegistrationService(userRepository, validationService);
        fakeTime = new FakeTimeProvider(0L);
        authService = new AuthService(userRepository, fakeTime);
    }

    @Test
    void shouldLoginSuccessfully_whenCredentialsCorrect() {
        User user = registrationService.register("testuser", 25, "test@example.com", "test1234");
        AuthenticatedUser authenticatedUser = authService.login(user.username(), "test1234");

        assertNotNull(authenticatedUser);
        assertEquals("testuser" , authenticatedUser.username());
        assertEquals("test@example.com", authenticatedUser.email());
        assertEquals(25, authenticatedUser.age());
    }

    @Test
    void shouldThrowInvalidCredentials_whenPasswordIncorrect() {
        registrationService.register("test", 25, "test@example.com", "pass1234");

        assertThrows(InvalidCredentialsException.class, () -> authService.login("test", "wrong"));
    }

    @Test
    void shouldLockAccount_afterThreeFailedAttempts() {
        registrationService.register("khan", 25, "khan@gmail.com", "pass1234");

        assertThrows(InvalidCredentialsException.class, () -> authService.login("khan", "wrong1"));
        assertThrows(InvalidCredentialsException.class, () -> authService.login("khan", "wrong2"));
        assertThrows(AccountLockedException.class, () -> authService.login("khan", "wrong3"));

        assertThrows(AccountLockedException.class, () -> authService.login("khan", "pass1234"));
    }

    @Test
    void failedAttemptsShouldNotAffectOtherUsers() {
        registrationService.register("khan", 25, "khan@gmail.com", "pass1234");
        registrationService.register("ali", 30, "ali@gmail.com", "pass1234");

        assertThrows(InvalidCredentialsException.class, () -> authService.login("khan", "wrong1"));
        assertThrows(InvalidCredentialsException.class, () -> authService.login("khan", "wrong2"));
        assertThrows(AccountLockedException.class, () -> authService.login("khan", "wrong3"));

        // ali should still be able to login
        assertDoesNotThrow(() -> authService.login("ali", "pass1234"));
    }

    @Test
    void shouldThrowUserNotFound_whenUsernameDoesNotMatch() {
        registrationService.register("test", 25, "test@example.com", "pass1234");

        assertThrows(UserNotFoundException.class, () -> authService.login("nonexistent", "pass1234"));
    }

    @Test
    void successfulLoginShouldResetAttempts_afterMultipleFailures() {
        registrationService.register("khan", 25, "khan@gmail.com", "pass1234");

        // 2 failed attempts
        assertThrows(InvalidCredentialsException.class, () -> authService.login("khan", "wrong1"));
        assertThrows(InvalidCredentialsException.class, () -> authService.login("khan", "wrong2"));

        // successful login should reset attempts back to 0
        assertDoesNotThrow(() -> authService.login("khan", "pass1234"));

        // now a wrong password should be treated as "first failure" again (not lock)
        assertThrows(InvalidCredentialsException.class, () -> authService.login("khan", "wrong3"));
    }

    @Test
    void shouldLogin_whenUsernameCaseDiffers() {
        registrationService.register("khan", 25, "khan@gmail.com", "pass1234");

        AuthenticatedUser user = authService.login("KHAN", "pass1234");

        assertEquals("khan", user.username()); // stored username
    }

    @Test
    void shouldThrowInvalidCredentials_whenUsernameOrPasswordBlank() {
        assertThrows(InvalidCredentialsException.class, () -> authService.login("", "pass"));
        assertThrows(InvalidCredentialsException.class, () -> authService.login("user", ""));
        assertThrows(InvalidCredentialsException.class, () -> authService.login("   ", "pass"));
        assertThrows(InvalidCredentialsException.class, () -> authService.login("user", "   "));
    }

    @Test
    void shouldThrowInvalidCredentials_whenUsernameOrPasswordNull() {
        assertThrows(InvalidCredentialsException.class, () -> authService.login(null, "pass"));
        assertThrows(InvalidCredentialsException.class, () -> authService.login("user", null));
    }

    @Test
    void shouldUnlockAfterLockDuration() {
        registrationService.register("khan", 25, "khan@gmail.com", "pass1234");

        // 3 failed attempts => lock
        assertThrows(InvalidCredentialsException.class, () -> authService.login("khan", "wrong1"));
        assertThrows(InvalidCredentialsException.class, () -> authService.login("khan", "wrong2"));
        assertThrows(AccountLockedException.class, () -> authService.login("khan", "wrong3")); // locked on 3rd

        // Still locked now
        assertThrows(AccountLockedException.class, () -> authService.login("khan", "pass1234"));

        // Move time forward by 60 seconds
        fakeTime.advanceMillis(60_000);

        // Now should be able to login
        assertDoesNotThrow(() -> authService.login("khan", "pass1234"));
    }




}
