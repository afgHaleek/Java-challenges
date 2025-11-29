# Challenge 06 - Exception Handling & JUnit Testing

## Objective
Create a testable UserValidationService with comprehensive exception handling and full JUnit test coverage.

## Solution Overview
The program implements a complete user registration system with:
1. **Custom Exceptions** for specific business rules
2. **Pure Validation Logic** without I/O dependencies
3. **Comprehensive JUnit Tests** covering all scenarios
4. **Exception Aggregation** to report multiple validation errors

## Concepts Demonstrated
- Custom exception creation and handling
- Test-driven development (TDD) with JUnit
- Input validation with business rules
- Exception chaining and aggregation
- Record classes for immutable data
- Separation of concerns (logic vs I/O)

## Custom Exceptions
- `InvalidAgeException` - Age must be 18-120 years
- `InvalidEmailException` - Email format validation
- `WeakPasswordException` - Password strength rules
- `InvalidUsernameException` - Username format validation
- `UserRegistrationException` - Wrapper for multiple errors

## Validation Rules

### Age Validation
- Must be between 18 and 120 years

### Email Validation
- Uses regex: `^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$`
- Requires @ symbol and proper domain format

### Password Validation
- Minimum 8 characters
- At least 1 letter and 1 digit
- Manual character-by-character checking

### Username Validation
- Cannot be empty or null
- No spaces allowed
- Minimum 3 characters

## Code Structure

### UserValidationService Methods
- `validateAge(int age)` - Throws InvalidAgeException
- `validateEmail(String email)` - Throws InvalidEmailException
- `validatePassword(String password)` - Throws WeakPasswordException
- `validateUsername(String username)` - Throws InvalidUsernameException
- `registerUser()` - Aggregates all validations, returns User or throws UserRegistrationException

### User Record
```java
public record User(String username, int age, String email, String password)