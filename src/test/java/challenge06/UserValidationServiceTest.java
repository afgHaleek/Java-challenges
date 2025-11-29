package challenge06;

import challenge06.exceptions.*;
import challenge06.model.User;
import challenge06.service.UserValidationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserValidationServiceTest {

    private UserValidationService service;

    @BeforeEach
    void setUp() {
        service = new UserValidationService();
    }

    @Test
    void shouldNotThrowException_WhenAgeIsValid() {
        assertDoesNotThrow(() -> {
            service.validateAge(25); // 25 is valid
        });
    }

    @Test
    void shouldThrowInvalidAgeException_whenAgeTooYoung() {
        InvalidAgeException exception = assertThrows(InvalidAgeException.class, () -> {
            service.validateAge(16);
        });

        assertEquals("Age must be between 18-120", exception.getMessage());
    }

    @Test
    void shouldThrowInvalidAgeException_WhenAgeTooOld() {
        InvalidAgeException exception = assertThrows(InvalidAgeException.class, () -> {
            service.validateAge(150); // 150 is too old!
        });
        assertEquals("Age must be between 18-120", exception.getMessage());
    }

    // ===== EMAIL VALIDATION TESTS =====

    @Test
    void shouldNotThrowException_WhenEmailIsValid() {
        assertDoesNotThrow(() -> {
            service.validateEmail("test@example.com"); // Good email!
        });
    }

    @Test
    void shouldThrowInvalidEmailException_WhenEmailHasNoAtSymbol() {
        InvalidEmailException exception = assertThrows(InvalidEmailException.class, () -> {
            service.validateEmail("bademail.com"); // Missing @ - bad!
        });
        assertEquals("Invalid Email format", exception.getMessage());
    }

    @Test
    void shouldThrowInvalidEmailException_WhenEmailHasNoDomain() {
        InvalidEmailException exception = assertThrows(InvalidEmailException.class, () -> {
            service.validateEmail("test@"); // No domain - bad!
        });
        assertEquals("Invalid Email format", exception.getMessage());
    }

    @Test
    void shouldThrowInvalidEmailException_WhenEmailIsNull() {
        InvalidEmailException exception = assertThrows(InvalidEmailException.class, () -> {
            service.validateEmail(null); // Nothing there - bad!
        });
        assertEquals("Invalid Email format", exception.getMessage());
    }

    // ===== PASSWORD VALIDATION TESTS =====

    @Test
    void shouldNotThrowException_WhenPasswordIsStrong() {
        assertDoesNotThrow(() -> {
            service.validatePassword("Secure123"); // 8+ chars, has letter, has number - good!
        });
    }

    @Test
    void shouldThrowWeakPasswordException_WhenPasswordTooShort() {
        WeakPasswordException exception = assertThrows(WeakPasswordException.class, () -> {
            service.validatePassword("short"); // Only 5 letters - bad!
        });
        assertEquals("Password must be at least 8 characters", exception.getMessage());
    }

    @Test
    void shouldThrowWeakPasswordException_WhenPasswordHasNoNumbers() {
        WeakPasswordException exception = assertThrows(WeakPasswordException.class, () -> {
            service.validatePassword("password"); // Only letters - bad!
        });
        assertEquals("password must contain at least 1 letter and 1 digit", exception.getMessage());
    }

    @Test
    void shouldThrowWeakPasswordException_WhenPasswordHasNoLetters() {
        WeakPasswordException exception = assertThrows(WeakPasswordException.class, () -> {
            service.validatePassword("12345678"); // Only numbers - bad!
        });
        assertEquals("password must contain at least 1 letter and 1 digit", exception.getMessage());
    }

    @Test
    void shouldThrowWeakPasswordException_WhenPasswordIsNull() {
        WeakPasswordException exception = assertThrows(WeakPasswordException.class, () -> {
            service.validatePassword(null); // Nothing there - bad!
        });
        assertEquals("Password must be at least 8 characters", exception.getMessage());
    }

    // ===== USERNAME VALIDATION TESTS =====

    @Test
    void shouldNotThrowException_WhenUsernameIsValid() {
        assertDoesNotThrow(() -> {
            service.validateUsername("john_doe"); // Good username!
        });
    }

    @Test
    void shouldThrowInvalidUsernameException_WhenUsernameIsEmpty() {
        InvalidUsernameException exception = assertThrows(InvalidUsernameException.class, () -> {
            service.validateUsername(""); // Empty - bad!
        });
        assertEquals("Username can not be empty", exception.getMessage());
    }

    @Test
    void shouldThrowInvalidUsernameException_WhenUsernameHasSpaces() {
        InvalidUsernameException exception = assertThrows(InvalidUsernameException.class, () -> {
            service.validateUsername("john doe"); // Space in middle - bad!
        });
        assertEquals("Username can not contain spaces", exception.getMessage());
    }

    @Test
    void shouldThrowInvalidUsernameException_WhenUsernameTooShort() {
        InvalidUsernameException exception = assertThrows(InvalidUsernameException.class, () -> {
            service.validateUsername("ab"); // Only 2 chars - bad!
        });
        assertEquals("Username must be at least 3 characters", exception.getMessage());
    }

    @Test
    void shouldThrowInvalidUsernameException_WhenUsernameIsNull() {
        InvalidUsernameException exception = assertThrows(InvalidUsernameException.class, () -> {
            service.validateUsername(null); // Nothing there - bad!
        });
        assertEquals("Username can not be empty", exception.getMessage());
    }

    // ===== COMPLETE REGISTRATION TESTS =====

    @Test
    void shouldRegisterUser_WhenAllFieldsAreValid() throws UserRegistrationException {
        // This should work perfectly and give us back a user
        User user = service.registerUser("john_doe", 25, "john@test.com", "Secure123");

        assertNotNull(user); // We got something back!
        assertEquals("john_doe", user.username()); // Check username is right
        assertEquals(25, user.age()); // Check age is right
        assertEquals("john@test.com", user.email()); // Check email is right
    }

    @Test
    void shouldThrowUserRegistrationException_WhenMultipleFieldsInvalid() {
        // Many things wrong - should tell us ALL the problems
        UserRegistrationException exception = assertThrows(UserRegistrationException.class, () -> {
            service.registerUser("", 16, "bademail", "weak");
        });

        String message = exception.getMessage();
        // Should mention ALL the problems
        assertTrue(message.contains("Registration Failed"));
        assertTrue(message.contains("Username can not be empty"));
        assertTrue(message.contains("Age must be between 18-120"));
        assertTrue(message.contains("Invalid Email format"));
        assertTrue(message.contains("Password must be at least 8 characters"));
    }

    @Test
    void shouldThrowUserRegistrationException_WhenOneFieldInvalid() {
        // Only one thing wrong - should mention just that
        UserRegistrationException exception = assertThrows(UserRegistrationException.class, () -> {
            service.registerUser("gooduser", 25, "bademail", "GoodPass123");
        });

        assertTrue(exception.getMessage().contains("Invalid Email format"));
        assertFalse(exception.getMessage().contains("Age must be between")); // Age was good!
    }
}
