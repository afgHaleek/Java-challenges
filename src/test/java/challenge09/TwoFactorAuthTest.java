package challenge09;


import challenge06.service.UserValidationService;
import challenge07.repository.InMemoryUserRepository;
import challenge07.repository.UserRepository;
import challenge07.service.UserRegistrationService;
import challenge08.FakeTimeProvider;
import challenge08.exception.InvalidCredentialsException;
import challenge08.model.AuthenticatedUser;
import challenge08.security.TimeProvider;
import challenge08.service.AuthService;
import challenge09.exception.InvalidOtpException;
import challenge09.exception.OtpExpiredException;
import challenge09.model.AuthSession;
import challenge09.security.FakeRandomProvider;
import challenge09.security.RandomProvider;
import challenge09.service.OtpService;
import challenge09.service.TwoFactorAuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TwoFactorAuthTest {

    private UserRepository userRepository;
    private UserRegistrationService registrationService;
    private FakeTimeProvider fakeTime;
    private FakeRandomProvider fakeRandom;
    private AuthService authService;
    private OtpService otpService;
    private TwoFactorAuthService twoFactorAuthService;

    @BeforeEach
    void setUp() {
        userRepository = new InMemoryUserRepository();
        registrationService = new UserRegistrationService(userRepository, new UserValidationService());

        fakeTime = new FakeTimeProvider(0L);
        fakeRandom = new FakeRandomProvider(123456);

        authService = new AuthService(userRepository, fakeTime);
        otpService = new OtpService(fakeRandom, fakeTime);
        twoFactorAuthService = new TwoFactorAuthService(authService, otpService);

        // Create a real user (password will be hashed by your registration service)
        registrationService.register("khan", 25, "khan@gmail.com", "pass1234");
    }

    @Test
    void shouldCompleteTwoFactorLoginSuccessfully() {
        AuthSession session = twoFactorAuthService.startLogin("khan", "pass1234");
        assertNotNull(session);

        AuthenticatedUser user = twoFactorAuthService.verifyOtp(session.sessionId(), 123456);

        assertNotNull(user);
        assertEquals("khan", user.username());
        assertEquals("khan@gmail.com", user.email());
        assertEquals(25, user.age());
    }

    @Test
    void shouldThrowInvalidCredentials_whenPasswordIncorrect() {
        assertThrows(InvalidCredentialsException.class, () ->
                twoFactorAuthService.startLogin("khan", "wrong")
        );
    }

    @Test
    void shouldThrowInvalidOtp_whenOtpIncorrect() {
        AuthSession session = twoFactorAuthService.startLogin("khan", "pass1234");

        assertThrows(InvalidOtpException.class, () ->
                twoFactorAuthService.verifyOtp(session.sessionId(), 111111)
        );
    }

    @Test
    void shouldThrowOtpExpired_whenOtpExpired() {
        AuthSession session = twoFactorAuthService.startLogin("khan", "pass1234");

        fakeTime.advanceMillis(60_000);

        assertThrows(OtpExpiredException.class, () ->
                twoFactorAuthService.verifyOtp(session.sessionId(), 123456)
        );
    }

    @Test
    void shouldNotAllowOtpReuse_afterSuccessfulVerification() {
        AuthSession session = twoFactorAuthService.startLogin("khan", "pass1234");

        assertDoesNotThrow(() ->
                twoFactorAuthService.verifyOtp(session.sessionId(), 123456)
        );

        // OTP session removed => second attempt should fail
        assertThrows(InvalidOtpException.class, () ->
                twoFactorAuthService.verifyOtp(session.sessionId(), 123456)
        );
    }

    @Test
    void shouldThrowInvalidOtp_whenVerifyingSessionWithoutPendingLogin() {
        // Never called startLogin => no pendingBySessionId entry
        // Should fail even if OTP service would also fail
        assertThrows(InvalidOtpException.class, () ->
                twoFactorAuthService.verifyOtp("random-session", 123456)
        );
    }
}
