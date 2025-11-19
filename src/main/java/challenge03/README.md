# Challenge 03 - Number Classification

## Objective
Create a program that classifies an integer based on various properties including sign, parity, and primality.

## Solution Overview
The program successfully:
1. Prompts the user for an integer input
2. Classifies the number as positive, negative, or zero
3. Determines if the number is even or odd
4. Checks if positive numbers are prime
5. Displays the results in a clean format

## Concepts Demonstrated
- Conditional statements (if-else)
- Method creation and modular programming
- Basic arithmetic operations
- Looping constructs (for primality check)
- Scanner class for user input

## Code Structure
- `isPrime(int n)`: Checks if a number is prime
- `isPositive(int n)`: Determines if a number is positive
- `isNegative(int n)`: Determines if a number is negative
- `isEven(int n)`: Checks if a number is even
- `main()`: Handles user input and coordinates the classification logic

## Example Output

Enter an integer number: 17
Number is positive
The number is prime
The number is odd.



## How to Run
1. Compile: `javac NumberClassification.java`
2. Execute: `java NumberClassification`
3. Enter an integer when prompted

## Notes
- The solution correctly handles all integer cases including positive, negative, and zero
- Prime number validation includes proper edge case checking
- The code is well-organized with separate methods for each classification task