# Challenge 08 — Authentication, Account Locking & Time-Controlled Testing (JUnit 5)

## Objective
Build a clean, testable authentication workflow in pure Java:
- Login using username + password
- Passwords are stored as hashes (no plain-text)
- Failed login attempts are tracked per user
- Accounts are temporarily locked after repeated failures
- Unlock happens automatically after a lock duration
- Full JUnit 5 coverage with deterministic time-based tests (no Thread.sleep)

---

## Key Concepts Demonstrated
- Service + Repository separation (clean architecture)
- Password hashing (SHA-256) and hashed comparisons
- Exception-driven authentication flow
- Per-user state tracking (failed attempts & lock expiry)
- Time abstraction using `TimeProvider` for reliable unit tests
- JUnit 5 tests for success, failure, edge cases, lock/unlock behavior

---

## Components

### AuthService (challenge08.service.AuthService)
Responsible for:
- Validating login input (null/blank)
- Checking lock state before authentication
- Loading user from `UserRepository`
- Hashing incoming password and comparing with stored hash
- Tracking failed attempts per user
- Locking the account after `MAX_LOGIN_ATTEMPTS`
- Unlocking automatically when lock duration expires
- Resetting attempts on successful login

### AuthenticatedUser (challenge08.model.AuthenticatedUser)
A safe DTO returned on successful login:
- username
- email
- age  
  ✅ Password is never returned.

---

## Exceptions
- `InvalidCredentialsException` — wrong password or invalid input (null/blank)
- `UserNotFoundException` — username not found
- `AccountLockedException` — account locked due to multiple failed attempts, or still locked

---

## Locking Rules
- Failed attempts are tracked **per user** (case-insensitive).
- After 3 failed attempts, the account is locked for 60 seconds (demo value).
- While locked, login attempts throw `AccountLockedException`.
- After lock duration passes, the account unlocks automatically.
- Successful login resets the attempt counter and clears any lock state.

---

## TimeProvider (Why it exists)
Authentication lock/unlock logic depends on time.
Using the real clock directly (`System.currentTimeMillis()`) makes tests slow and flaky.

This challenge introduces `TimeProvider`:
- `SystemTimeProvider` uses the real system time in production
- `FakeTimeProvider` lets tests fast-forward time instantly

This enables deterministic tests for lock/unlock without `Thread.sleep()`.

---

## JUnit Test Coverage
The test suite covers:
- ✅ Successful login with correct credentials
- ❌ Invalid password
- ❌ User not found
- ❌ Null/blank username or password
- 🔒 Lock after 3 failed attempts
- 🔓 Unlock after lock duration (via fake time)
- 🔁 Attempts reset after successful login
- 🧩 Case-insensitive username login
- 🧠 Per-user lock tracking (one user’s failures don’t affect others)

---

## Run Tests
```bash
mvn test


#Author 
Daoud Shagiwal
Dublin, Ireland