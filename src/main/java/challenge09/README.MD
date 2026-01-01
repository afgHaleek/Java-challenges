# Challenge 09 — Two-Factor Authentication (OTP) with Time-Controlled Testing

## Objective
Extend the authentication system with a **Two-Factor Authentication (2FA)** flow using **One-Time Passwords (OTP)**.

The goal of this challenge is to design and test a **secure, multi-step authentication process** in pure Java, without frameworks, while keeping the code clean, testable, and deterministic.

---

## Overview
Authentication is split into two steps:

1. **Password authentication**
    - Username and password are verified using the existing `AuthService`
    - Account lock rules from Challenge 08 still apply

2. **OTP verification**
    - A 6-digit OTP is generated
    - OTP expires after a fixed duration
    - OTP can only be used once
    - Final authentication succeeds only after OTP verification

This design mirrors how real-world systems implement 2FA.

---

## Architecture

### Core Services
- **AuthService (Challenge 08)**  
  Handles username/password authentication and account lockout rules.

- **OtpService (Challenge 09)**  
  Responsible for:
    - Generating OTP codes
    - Creating OTP sessions
    - Enforcing OTP expiration
    - Preventing OTP reuse

- **TwoFactorAuthService (Challenge 09)**  
  Orchestrates the full login flow:
    - Starts login using password authentication
    - Creates an OTP session
    - Verifies OTP
    - Returns the final authenticated user

Each service has a single responsibility and is fully testable in isolation.

---

## Key Concepts Demonstrated
- Two-step authentication (password + OTP)
- One-time, time-limited security tokens
- Separation of concerns between authentication stages
- Dependency injection (no static calls)
- Time abstraction for deterministic tests
- Random abstraction for predictable OTP testing
- Clean exception-driven control flow
- In-memory session and token management

---

## OTP Rules
- OTP is **6 digits** (100000–999999)
- OTP expires after **60 seconds** (demo value)
- OTP is **one-time use**
- Expired or invalid OTPs are rejected
- Each login attempt gets its own session

---

## Time & Random Abstraction

### TimeProvider
OTP expiration relies on time, but the system does **not** depend directly on the system clock.

- `SystemTimeProvider` → used in production
- `FakeTimeProvider` → used in tests to fast-forward time instantly

This avoids `Thread.sleep()` and makes tests fast and reliable.

### RandomProvider
OTP generation uses an injected random provider:

- `SystemRandomProvider` → real randomness
- `FakeRandomProvider` → deterministic OTP values for tests

---

## Testing Strategy

### Unit Tests
- `OtpServiceTest`
    - OTP generation
    - OTP verification success
    - Invalid OTP handling
    - Expired OTP handling
    - One-time use enforcement
    - Invalid session handling

### Integration-Style Tests
- `TwoFactorAuthServiceTest`
    - Full login + OTP success flow
    - Wrong password rejection
    - Wrong OTP rejection
    - OTP expiration handling
    - OTP reuse prevention
    - Session isolation

All tests are **deterministic**, fast, and do not rely on real time or randomness.

---

## Why This Design
- Keeps authentication logic extensible without modifying previous challenges
- Avoids shared mutable state
- Handles multiple concurrent login attempts safely
- Reflects real-world backend authentication patterns
- Fully testable without external dependencies

---

## How to Run Tests
```bash
mvn test
