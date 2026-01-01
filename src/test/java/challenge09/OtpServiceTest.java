package challenge09;

import challenge08.FakeTimeProvider;
import challenge08.security.TimeProvider;
import challenge09.exception.InvalidOtpException;
import challenge09.exception.OtpExpiredException;
import challenge09.model.AuthSession;
import challenge09.security.FakeRandomProvider;
import challenge09.security.RandomProvider;
import challenge09.service.OtpService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OtpServiceTest {

    private OtpService otpService;
    private FakeTimeProvider fakeTime;
    private FakeRandomProvider fakeRandom;

    @BeforeEach
    void setUp() {
        fakeTime = new FakeTimeProvider(0L);
        fakeRandom = new FakeRandomProvider(123456);
        otpService = new OtpService(fakeRandom, fakeTime);
    }

    @Test
    void shouldCreateOtpSession() {
        AuthSession session = otpService.createOtpSession("khan");

        assertNotNull(session);
        assertNotNull(session.sessionId());
        assertEquals("khan", session.username());
    }

    @Test
    void shouldVerifyOtpSuccessfully_whenCorrectAndNotExpired() {
        AuthSession session = otpService.createOtpSession("khan");

        assertTrue(otpService.verifyOtp(session.sessionId(), 123456));
    }

    @Test
    void shouldThrowInvalidOtp_whenCodeIsWrong() {
        AuthSession session = otpService.createOtpSession("khan");

        assertThrows(InvalidOtpException.class, () ->
                otpService.verifyOtp(session.sessionId(), 111111)
        );
    }

    @Test
    void shouldThrowOtpExpired_whenExpired() {
        AuthSession session = otpService.createOtpSession("khan");

        // OTP TTL is 60_000 ms; move time beyond expiry
        fakeTime.advanceMillis(60_000);

        assertThrows(OtpExpiredException.class, () ->
                otpService.verifyOtp(session.sessionId(), 123456)
        );
    }

    @Test
    void shouldNotAllowOtpReuse_afterSuccessfulVerification() {
        AuthSession session = otpService.createOtpSession("khan");

        assertTrue(otpService.verifyOtp(session.sessionId(), 123456));

        // One-time use: session/token removed, so second attempt must fail
        assertThrows(InvalidOtpException.class, () ->
                otpService.verifyOtp(session.sessionId(), 123456)
        );
    }

    @Test
    void shouldThrowInvalidOtp_whenSessionDoesNotExist() {
        assertThrows(InvalidOtpException.class, () ->
                otpService.verifyOtp("does-not-exist", 123456)
        );
    }

    @Test
    void shouldThrowInvalidOtp_whenSessionIdNullOrBlank_orCodeOutOfRange() {
        assertThrows(InvalidOtpException.class, () -> otpService.verifyOtp(null, 123456));
        assertThrows(InvalidOtpException.class, () -> otpService.verifyOtp("   ", 123456));
        assertThrows(InvalidOtpException.class, () -> otpService.verifyOtp("abc", 99999));   // too small
        assertThrows(InvalidOtpException.class, () -> otpService.verifyOtp("abc", 1_000_000)); // too large
    }

}
