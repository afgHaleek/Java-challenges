package challenge07.service;

import challenge06.exceptions.InvalidAgeException;
import challenge07.exception.*;
import challenge07.model.User;
import challenge07.repository.UserRepository;
import challenge06.service.UserValidationService;
import challenge06.exceptions.*;

import java.util.ArrayList;
import java.util.List;

public class UserRegistrationService {


    UserRepository repository;
    UserValidationService validationService;

    public UserRegistrationService(UserRepository repository, UserValidationService validationService) {
        this.repository = repository;
        this.validationService = validationService;
    }

    public void register(String username, int age, String email, String password) {
        List<String> errors = new ArrayList<>();

        try {
            validationService.validateUsername(username);
        } catch (InvalidUsernameException e) {
            errors.add(e.getMessage());
        }

        try {
           validationService.validateAge(age);
       } catch (InvalidAgeException e) {
           errors.add(e.getMessage());
       }

        try {
            validationService.validateEmail(email);
        } catch (InvalidEmailException e) {
            errors.add(e.getMessage());
        }

        try {
            validationService.validatePassword(password);
        } catch (WeakPasswordException e) {
            errors.add(e.getMessage());
        }


        if (!errors.isEmpty()) {
            throw new UserRegistrationException("Registration failed due to validation errors: " + String.join(", ", errors));
        }

        if (repository.findByUsername(username).isPresent()) {
            throw new UserAlreadyExistsException("User already exists!");
        }

        User user = new User(username, password, email, age);



      repository.save(user);

    }
}
