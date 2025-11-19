package challenge05;

import java.util.Scanner;

public class StringManipulation {
    public static void main(String... args) {
        System.out.println("########### STRING MANIPULATION AND ANALYSIS PROGRAM ###########");

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter string (sentence or word) : ");
        String string = sc.nextLine();

        if (string.isEmpty()) System.out.println("you entered empty string!");
        countAndDisplayTheNumberOfChars(string);
        System.out.println("string reversed: " + reverseString(string));
        System.out.println("is Palindrome : " + manualIsPalindrome(string));
        System.out.println("Manual to upper: " + manualToUpper(string));
        System.out.println("manual to lower: "+ manualToLower(string));
        System.out.println("No of words in a string: " + countWords(string));


    }

    public static void countAndDisplayTheNumberOfChars(String s) {
        int noOfChars = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == ' ') continue;
            System.out.print(s.charAt(i) + " ");
            noOfChars++;
        }

        System.out.println("\nTotal number of chars in the string: " + noOfChars);

    }




/* <<<<<<<<<<<<<<  ✨ Windsurf Command ⭐ >>>>>>>>>>>>>>>> */
    /**
     * Reverses a given string.
     *
     * This method takes a string s, converts it into a character array,
     * and then reverses the array in-place by swapping characters from
     * the left and right ends of the array, moving towards the center.
     *
     * @param s the string to reverse
     * @return the reversed string
     */
/* <<<<<<<<<<  5c6546c5-37c2-4911-afd0-4045135d504f  >>>>>>>>>>> */
    public static String reverseString(String s) {
          char[] chars = s.toCharArray();
          int left = 0, right = chars.length - 1 ;

          while (left < right) {
              char temp = chars[left];
              chars[left] = chars[right];
              chars[right] = temp;

              left++;
              right--;
          }


          return new String(chars);

    }

    public static String manualToUpper(String s) {
        char[] chars = s.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] >= 'a' && chars[i] <= 'z') {
                chars[i] = (char) (chars[i] - 32);
            }
        }

        return new String(chars);
    }

    public static String manualToLower(String s) {
        char[] chars = s.toCharArray();
        for (int i=0; i < chars.length; i++) {
            if (chars[i] >= 'A' && chars[i] <= 'Z' ) {
                chars[i] = (char) (chars[i] + 32);
            }
        }

        return new String(chars);
    }

    public static int countWords(String s) {
        int count = 0;
        boolean inWord = false;

        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) != ' ') {
                if (!inWord) {
                    count++;
                    inWord = true;
                }
            } else {
                inWord = false;
            }
        }

        return count;
    }


/* <<<<<<<<<<<<<<  ✨ Windsurf Command ⭐ >>>>>>>>>>>>>>>> */
    /**
     * Checks if a given string is a palindrome by comparing characters from
     * the left and right ends of the string, moving towards the center.
     *
     * @param s the string to check
     * @return true if the string is a palindrome, false otherwise
     */
/* <<<<<<<<<<  e6cac70c-84c3-4964-ae04-4dc9dfad2892  >>>>>>>>>>> */
    public static boolean manualIsPalindrome(String s) {
        int left = 0, right = s.length() - 1;
        while (left < right) {
            if (s.charAt(left) != s.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }

        return true;
    }






}
