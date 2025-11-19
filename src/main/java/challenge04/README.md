# Challenge 04 - Array Operations

## Objective
Create a program that performs various operations on an array of integers including sum, maximum, minimum, and average calculations with proper error handling.

## Solution Overview
The program successfully:
1. Prompts the user to input 5 integers with validation
2. Handles invalid input using try-catch blocks
3. Stores the values in an array
4. Performs four key operations on the array:
    - Calculates the sum of all elements
    - Finds the maximum value
    - Finds the minimum value
    - Calculates the average value
5. Displays the array contents and all computed results

## Concepts Demonstrated
- Array declaration and initialization
- Looping through arrays with for loops
- Method creation and modular programming
- Basic algorithm implementation (min/max finding)
- Type casting for accurate calculations
- Exception handling with try-catch blocks
- Input validation and error messaging
- Scanner class for user input
- Arrays.toString() for array display

## Code Structure
- `sumOfArray(int[] n)`: Calculates the sum of array elements
- `maxNumInArray(int[] n)`: Finds the maximum value in the array
- `minNumInArray(int[] n)`: Finds the minimum value in the array
- `main()`: Handles user input with error handling, method calls, and output display

## Error Handling
The program includes robust error handling:
- Catches `InputMismatchException` when non-integer values are entered
- Provides clear error messages to the user
- Uses exception handling to maintain program stability

## Example Output

########## PROGRAM FOR ARRAY OPERATIONS ##########
Enter 5 Integers:
Enter number 1: 23
Enter number 2: 45
Enter number 3: 56
Enter number 4: 3
Enter number 5: 34
Array Operation results:

[23, 45, 56, 3, 34]
sum: 161
Maximum: 56
Minimum: 3
Average: 32.2



## How to Run
1. Compile: `javac ArrayOperations.java`
2. Execute: `java ArrayOperations`
3. Enter 5 integers when prompted

## Notes
- The solution efficiently handles all required array operations
- Includes robust error handling for invalid input
- Proper type casting ensures accurate average calculation
- Clean, readable code with appropriate method separation
- The error handling could be enhanced further to allow re-entry of invalid values