package challenge09.service;

import challenge08.exception.InvalidCredentialsException;
import challenge08.model.AuthenticatedUser;
import challenge08.service.AuthService;
import challenge09.exception.InvalidOtpException;
import challenge09.model.AuthSession;

import java.util.HashMap;
import java.util.Map;

public class TwoFactorAuthService {

    private final AuthService authService;
    private final OtpService otpService;

    // Store pending authenticated users per sessionId
    private final Map<String, AuthenticatedUser> pendingBySessionId = new HashMap<>();

    public TwoFactorAuthService(AuthService authService, OtpService otpService) {
        this.authService = authService;
        this.otpService = otpService;
    }

    public AuthSession startLogin(String username, String password) {
        if (username == null || password == null || username.isBlank() || password.isBlank()) {
            throw new InvalidCredentialsException("username and password cannot be null/blank");
        }

        // Step 1: password login (may throw invalid creds/lock/user not found)
        AuthenticatedUser authenticatedUser = authService.login(username, password);

        // Step 2: create OTP session
        AuthSession session = otpService.createOtpSession(username);

        // Tie the authenticated user to THIS session
        pendingBySessionId.put(session.sessionId(), authenticatedUser);

        return session;
    }

    public AuthenticatedUser verifyOtp(String sessionId, int code) {
        if (sessionId == null || sessionId.isBlank() || code < 100000 || code > 999999) {
            throw new InvalidOtpException("Invalid OTP format or range");
        }

        // This will throw if invalid/expired/not found
        otpService.verifyOtp(sessionId, code);

        // Return the correct user for this session
        AuthenticatedUser user = pendingBySessionId.remove(sessionId);

        // If user is null, it means session not started or already verified
        if (user == null) {
            throw new InvalidOtpException("No pending login for this session");
        }

        return user;
    }
}
