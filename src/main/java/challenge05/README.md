# Challenge 05 - String Manipulation and Analysis

## Objective
Create a program that performs various string operations using **manual implementations** without relying on built-in String methods like `split()`, `toUpperCase()`, or `toLowerCase()`.

## Solution Overview
The program successfully implements all string operations manually:
1. **Character counting** (excluding spaces)
2. **String reversal** using character swapping
3. **Palindrome checking** with two-pointer technique
4. **Uppercase conversion** using ASCII arithmetic
5. **Lowercase conversion** using ASCII arithmetic
6. **Word counting** with state machine logic

## Concepts Demonstrated
- Manual string manipulation using character arrays
- ASCII arithmetic for case conversion
- Two-pointer algorithms (reversal, palindrome)
- State machines (word counting)
- In-place array modifications
- Character-by-character processing

## Manual Implementations

### Character Counting
```java
// Counts non-space characters by iterating through string
if (s.charAt(i) == ' ') continue;
noOfChars++;

// Uses two-pointer swap technique
char temp = chars[left];
chars[left] = chars[right];
chars[right] = temp;

// ASCII arithmetic: 'a'=97, 'A'=65 (difference=32)
chars[i] = (char) (chars[i] - 32); // lowercase → uppercase
chars[i] = (char) (chars[i] + 32); // uppercase → lowercase

// State machine: track when entering/exiting words
if (!inWord) { count++; inWord = true; }

// Compare characters from both ends
if (s.charAt(left) != s.charAt(right)) return false;

#EXAMPLE OUTPUT:
Enter string (sentence or word) : Hello World

H e l l o W o r l d 
Total number of chars in the string: 10
string reversed: dlroW olleH
is Palindrome : false
Manual to upper: HELLO WORLD
manual to lower: hello world
No of words in a string: 2


#Technical Notes
All implementations work at the character level for educational purposes
Handles edge cases: empty strings, multiple spaces, mixed case
Efficient algorithms with O(n) time complexity
Demonstrates fundamental computer science concepts


#Key Learning Points
Understanding ASCII character encoding
Algorithmic thinking for string processing
Memory-efficient in-place operations
State machine design patterns