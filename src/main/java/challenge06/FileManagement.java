package challenge06;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Scanner;

public class FileManagement {
    public static void main(String... args) {
        System.out.println("File Operations Program");
        Scanner sc = new Scanner(System.in);
        sc.nextLine();
        System.out.print("Enter text to write to file: ");
        String text = sc.nextLine();

        createAndWriteFile("test.txt", text);
        System.out.print("File Content: ");
        readFromFile("test.txt");

        copyFile("test.txt");

        System.out.print("Enter text to append: ");
        String appendText = sc.nextLine();

        appendToFile("test.txt", appendText);
        System.out.println("Text appended succcessfully");
        readFromFile("test.txt");



    }


    public static void createAndWriteFile(String filename, String contents) {
        File myFile = new File(filename);

        try {
            if (myFile.createNewFile()) {
                System.out.println("File created with name: " + myFile.getName());
            } else {
                System.out.println("File already exists: " + myFile.getName());
            }

            if (!contents.isEmpty()) {
                FileWriter writer = new FileWriter(filename);
                writer.write(contents);
                writer.close();
                System.out.println("Text written to file successfully!");
            }

        } catch (IOException e) {
            System.out.println("\nSomething went wrong " + e.getMessage());
        }


    }

    public static void readFromFile(String filename) {
        File myFile = new File(filename);

        try (Scanner myReader = new Scanner(myFile)) {
            while (myReader.hasNext()) {
              String data = myReader.nextLine();
              System.out.println(data);
            }

        } catch (FileNotFoundException e)  {
            System.out.println("\nsth went wrong " + e.getMessage());
        }
    }

    public static void copyFile(String filename){
        File src = new File(filename);
        File dest = new File("copy_test.txt");

        try {
            if (!src.exists()) {
                System.out.println("Source file does not exist!");
                return;
            }
            Files.copy(src.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
            System.out.println("\nFile copied to: " + dest.getName());
            displayFileInfo(dest);
        } catch (IOException e) {
            System.out.println("sth went wrong " + e.getMessage());
        }

    }

    public static void displayFileInfo(File file) {
        System.out.println("File size: " + file.length());
        System.out.println("File path: " + file.getAbsolutePath());
    }

    public static void  appendToFile(String filename, String appendText) {
        try {

            if (new File(filename).exists()) {
                BufferedWriter out = new BufferedWriter(new FileWriter(filename, true));
                out.write(appendText);
                out.close();
            }


        } catch (IOException e) {
            System.out.println("sth went wrong " + e.getMessage());
        }

    }
}
