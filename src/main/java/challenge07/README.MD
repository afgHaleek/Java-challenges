ava Challenges – Challenge 07
User Registration Workflow (In-Memory Persistence + JUnit)
📌 Overview

This challenge extends the previous validation-only logic into a realistic user registration workflow.
It simulates how backend services work in real applications by separating responsibilities into repository, service, and validation layers, while remaining pure Java (no frameworks).

The focus is on:

Clean architecture

Business rule enforcement

Exception-driven control flow

Full JUnit 5 test coverage

🎯 Objectives

Implement a complete user registration service

Simulate persistence using an in-memory repository

Prevent duplicate user registration

Reuse validation logic from Challenge 06

Ensure all logic is fully testable with JUnit

🏗️ Architecture & Design
Layers
Service Layer
↓
Repository Layer (Interface)
↓
In-Memory Implementation (Fake persistence)

#Key Design Principles

Separation of concerns

Dependency injection via constructors

Repository pattern

No static state

No I/O (console, scanner, etc.)

Test-first mindset


🧩 Business Rules
User Registration Rules

Username must be valid (reused from Challenge 06)

Age must be between 18 and 120

Email must follow a valid format

Password must meet strength requirements

Username must be unique (case-insensitive)

Persistence Rules

Users are stored in memory using List<User>

Data exists only during program execution

Repository returns Optional<User> for safe lookups

findAll() returns a defensive copy

🧪 Testing Strategy (JUnit 5)
Covered Scenarios

✅ Successful user registration
✅ Duplicate username detection
✅ Case-insensitive username handling
✅ Single validation failure (no persistence)
✅ Multiple validation errors aggregation
✅ Repository state integrity
✅ Defensive copy behavior

Example Test Cases
shouldRegisterUser_whenValidCredentials()
shouldThrowUserAlreadyExists_whenUsernameIsTaken()
shouldAggregateErrors_whenMultipleFieldsInvalid()
shouldNotSaveUser_whenValidationFails()


All tests pass and run under JUnit Jupiter (JUnit 5) using Maven Surefire.

🔍 Why In-Memory Repository?

The in-memory repository acts as a fake database:

Allows fast, isolated unit tests

Avoids database complexity

Makes business logic fully testable

Can later be replaced with JDBC / JPA without changing service logic

🚀 Skills Demonstrated

Core Java (records, Optional, collections)

Exception handling and aggregation

Clean service-layer design

Repository pattern

JUnit 5 testing

Maven project structure

Backend-ready thinking (Spring-compatible design)


📎 How to Run Tests
mvn test

👤 Author

Daoud Shagiwal
Computer Science Graduate | Java Backend Developer
📍 Dublin, Ireland