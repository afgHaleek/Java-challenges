package challenge06.service;

import challenge06.exceptions.*;
import challenge06.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserValidationService {
    public void validateAge(int age) throws InvalidAgeException {
        if (age < 18 || age > 120) {
            throw new InvalidAgeException("Age must be between 18-120");
        }
    }

    public void validateEmail(String email) throws InvalidEmailException {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

        if (email == null || !email.matches(emailRegex)) {
            throw new InvalidEmailException("Invalid Email format");
        }
    }

    public void validatePassword(String password) throws WeakPasswordException {
        if (password == null || password.length() < 8) {
            throw new WeakPasswordException("Password must be at least 8 characters");
        }

//        using regexp
//        boolean hasLetter = password.matches(".*[a-zA-Z].*");
//        boolean hasDigit = password.matches(".*\\d.*");
//        checking manually

        boolean hasLetter = false;
        boolean hasDigit = false;

        for (char c : password.toCharArray()) {
            if (Character.isLetter(c)) {
                hasLetter = true;
            } else if (Character.isDigit(c)) {
                hasDigit = true;
            }

            if (hasLetter && hasDigit) {
                break;
            }

        }

        if (!hasLetter || !hasDigit) {
            throw new WeakPasswordException("password must contain at least 1 letter and 1 digit");
        }
    }

    public void validateUsername(String username) throws InvalidUsernameException {
        if (username == null || username.trim().isEmpty()) {
            throw new InvalidUsernameException("Username can not be empty");
        }

        if (username.contains(" ")) {
            throw new InvalidUsernameException("Username can not contain spaces");
        }

        if (username.length() < 3) {
            throw new InvalidUsernameException("Username must be at least 3 characters");
        }
    }

    public User registerUser(String username, int age, String email, String password) throws UserRegistrationException {
        List<String> errors = new ArrayList<>();

        try {
            validateAge(age);
        } catch (InvalidAgeException e) {
            errors.add(e.getMessage());
        }

        try {
            validateEmail(email);
        } catch (InvalidEmailException e) {
            errors.add(e.getMessage());
        }

        try {
            validatePassword(password);
        } catch (WeakPasswordException e) {
            errors.add(e.getMessage());
        }

        try {
            validateUsername(username);
        } catch (InvalidUsernameException e) {
            errors.add(e.getMessage());
        }

        if (!errors.isEmpty()) {
            String errorMessage = String.join(", ", errors);
            throw new UserRegistrationException("Registration Failed: " + errorMessage);
        }

        return new User(username, age, email, password);
    }
}
