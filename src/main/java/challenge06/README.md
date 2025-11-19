# Challenge 06 - File I/O Operations

## Objective
Create a program that performs various file operations including creating, reading, copying, and appending to files using Java's I/O classes.

## Solution Overview
The program successfully implements all file operations:
1. **File Creation & Writing** - Creates new files and writes user input
2. **File Reading** - Reads and displays file contents
3. **File Copying** - Copies files to new locations with overwrite protection
4. **File Appending** - Adds new content to existing files
5. **File Information** - Displays file size and absolute path

## Concepts Demonstrated
- File class for file operations
- FileWriter for writing files
- Scanner for reading files
- Files.copy() with StandardCopyOption for file copying
- BufferedWriter with append mode
- Exception handling for I/O operations
- File metadata (size, path)

## Methods Implemented

### createAndWriteFile()
- Creates new file if it doesn't exist
- Writes user content to file
- Handles file creation exceptions

### readFromFile()
- Uses Scanner to read file line by line
- Try-with-resources for automatic resource management
- Handles FileNotFoundException

### copyFile()
- Copies source file to destination
- Uses REPLACE_EXISTING to handle duplicate files
- Checks source file existence before copying

### appendToFile()
- Appends text to existing files using BufferedWriter
- FileWriter with 'true' parameter enables append mode
- Verifies file exists before appending

### displayFileInfo()
- Shows file size in bytes
- Displays absolute file path

## Example Output

File Operations Program

Enter text to write to file: this is khan
File already exists: test.txt
Text written to file successfully!
File Content: this is khan

File copied to: copy_test.txt
File size: 12
File path: C:\Users\Lenovo\Desktop\java\java-challenges\java-challenges\copy_test.txt
Enter text to append: hello jna
Text appended succcessfully
this is khanhello jna


## How to Run
1. Compile: `javac FileManagement.java`
2. Execute: `java FileManagement`
3. Follow the prompts to perform file operations

## Technical Notes
- Uses StandardCopyOption.REPLACE_EXISTING for robust file copying
- Try-with-resources ensures proper resource cleanup
- Absolute paths provide clear file location information
- Comprehensive exception handling for all I/O operations