package challenge07;

import challenge06.exceptions.UserRegistrationException;
import challenge06.service.UserValidationService;
import challenge07.exception.UserAlreadyExistsException;
import challenge07.model.User;
import challenge07.repository.InMemoryUserRepository;
import challenge07.repository.UserRepository;
import challenge07.security.PasswordHasher;
import challenge07.service.UserRegistrationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserRegistrationServiceTest {
    UserRepository repository;
    UserRegistrationService registrationService;

    @BeforeEach
    void setUp() {
          repository = new InMemoryUserRepository();
        UserValidationService validationService = new UserValidationService();
        registrationService = new UserRegistrationService(repository, validationService);
    }

    @Test
    void shouldRegisterUser_whenValidCredentials() {
        registrationService.register("khan", 25, "khan@gmail.com", "pass1234");

        assertTrue(repository.findByUsername("khan").isPresent());
        assertEquals(1, repository.findAll().size());
    }

    @Test
    void shouldThrowUserAlreadyExists_whenUsernameIsTaken() {
        registrationService.register("khan", 25, "khan@gmail.com", "pass1234");

        assertThrows(UserAlreadyExistsException.class, () -> registrationService.register("khan", 25, "khan@gmail.com", "pass1234"));
    }

    @Test
    void shouldNotSaveUser_whenEmailInvalid() {
        assertThrows(UserRegistrationException.class, () ->
                registrationService.register("khan", 25, "bademail", "pass1234")
        );

        assertEquals(0, repository.findAll().size());
        assertFalse(repository.findByUsername("khan").isPresent());
    }

    @Test
    void shouldAggregateErrors_whenMultipleFieldsInvalid() {
        UserRegistrationException ex = assertThrows(UserRegistrationException.class, () ->
                registrationService.register("", 16, "bad", "weak")
        );

        String msg = ex.getMessage();
        assertTrue(msg.contains("Username"));
        assertTrue(msg.contains("Age"));
        assertTrue(msg.contains("Email"));
        assertTrue(msg.contains("Password"));

        assertEquals(0, repository.findAll().size());
    }

    @Test
    void shouldTreatUsernameAsCaseInsensitive_forDuplicates() {
        registrationService.register("khan", 25, "khan@gmail.com", "pass1234");

        assertThrows(UserAlreadyExistsException.class, () ->
                registrationService.register("KHAN", 30, "khan2@gmail.com", "pass1234")
        );
    }

    @Test
    void shouldRegisterMultipleDifferentUsers() {
        registrationService.register("khan", 25, "khan@gmail.com", "pass1234");
        registrationService.register("ali", 30, "ali@gmail.com", "pass1234");

        assertEquals(2, repository.findAll().size());
        assertTrue(repository.findByUsername("ali").isPresent());
    }

    @Test
    void shouldThrowValidationException_whenUsernameNull_evenIfRepoCheckWouldFail() {
        assertThrows(UserRegistrationException.class, () ->
                registrationService.register(null, 25, "a@b.com", "pass1234")
        );
        assertEquals(0, repository.findAll().size());
    }

    @Test
    void findAllShouldReturnDefensiveCopy() {
        registrationService.register("khan", 25, "khan@gmail.com", "pass1234");

        var list = repository.findAll();
        list.clear(); // try to mutate returned list

        // repository should still have the user
        assertEquals(1, repository.findAll().size());
    }

    @Test
    void shouldSaveCorrectUserData() {
        registrationService.register("khan", 25, "khan@gmail.com", "pass1234");

        User saved = repository.findByUsername("khan").orElseThrow();
        assertEquals("khan", saved.username());
        assertEquals(25, saved.age());
        assertEquals("khan@gmail.com", saved.email());
        String hashedPass = PasswordHasher.hash("pass1234");
        assertEquals(hashedPass, saved.password());
    }

    @Test
    void duplicateRegistrationShouldNotChangeRepo() {
        registrationService.register("khan", 25, "khan@gmail.com", "pass1234");

        assertThrows(UserAlreadyExistsException.class, () ->
                registrationService.register("khan", 25, "khan@gmail.com", "pass1234")
        );

        assertEquals(1, repository.findAll().size());
    }






}
