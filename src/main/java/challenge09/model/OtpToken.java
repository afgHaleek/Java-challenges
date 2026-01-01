package challenge09.model;

public record OtpToken(String sessionId, String username, int code, long expiresAt) {
}
