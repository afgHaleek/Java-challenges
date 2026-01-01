package challenge09.service;

import challenge06.exceptions.InvalidUsernameException;
import challenge08.security.TimeProvider;
import challenge09.exception.InvalidOtpException;
import challenge09.exception.OtpExpiredException;
import challenge09.model.AuthSession;
import challenge09.model.OtpToken;
import challenge09.security.RandomProvider;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class OtpService {

    private static final long OTP_TTL_MS = 60_000;
    private static final int OTP_MIN = 100000;
    private static final int OTP_MAX = 999999;

    private final Map<String, OtpToken> tokenBySessionId = new HashMap<>();
    private final Map<String, AuthSession> sessionBySessionId = new HashMap<>();
    private final RandomProvider randomProvider;
    private final TimeProvider timeProvider;

    public OtpService(RandomProvider randomProvider, TimeProvider timeProvider) {
        this.randomProvider = randomProvider;
        this.timeProvider = timeProvider;
    }

    public AuthSession createOtpSession(String username) {
        if (username == null || username.trim().isEmpty()) {
            throw new InvalidUsernameException("invalid username");
        }

        String normalizedUsername = username.trim();
        String sessionId = UUID.randomUUID().toString();

        long now = timeProvider.now();
        long expiresAt = now + OTP_TTL_MS;

        int code = randomProvider.nextInt(OTP_MIN, OTP_MAX);

        AuthSession session = new AuthSession(sessionId, normalizedUsername);
        OtpToken token = new OtpToken(sessionId, normalizedUsername, code, expiresAt);

        // store both; token is used for verification, session is for tracking/cleanup
        sessionBySessionId.put(sessionId, session);
        tokenBySessionId.put(sessionId, token);

        return session;
    }

    public boolean verifyOtp(String sessionId, int code) {
        if (sessionId == null || sessionId.isBlank() || code < OTP_MIN || code > OTP_MAX) {
            throw new InvalidOtpException("invalid otp");
        }

        OtpToken token = tokenBySessionId.get(sessionId);
        if (token == null) {
            throw new InvalidOtpException("invalid otp");
        }

        long now = timeProvider.now();

        if (now >= token.expiresAt()) {
            // cleanup: expired tokens should not remain in memory
            tokenBySessionId.remove(sessionId);
            sessionBySessionId.remove(sessionId);
            throw new OtpExpiredException("Otp expired");
        }

        if (token.code() != code) {
            throw new InvalidOtpException("invalid otp");
        }

        // success: one-time use
        tokenBySessionId.remove(sessionId);
        sessionBySessionId.remove(sessionId);
        return true;
    }
}
