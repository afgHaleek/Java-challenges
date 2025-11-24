package challenge05;

import java.util.Scanner;

public class StringManipulation {

/* <<<<<<<<<<<<<<  ✨ Windsurf Command ⭐ >>>>>>>>>>>>>>>> */
    /**
     * Returns a formatted string containing all non-space characters in the input string,
     * with each character followed by a space and a newline character at the end,
     * followed by the total number of non-space characters.
     *
     * @param s the input string
     * @return the formatted string
     */
/* <<<<<<<<<<  a4eb31bb-19ee-4a9d-a322-6d3e19acaa19  >>>>>>>>>>> */
    public static String countCharsFormatted(String s) {
        int noOfChars = 0;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == ' ') continue;

            sb.append(c).append(" ");
            noOfChars++;
        }

        if (!sb.isEmpty()) {
            sb.setLength(sb.length() - 1);
        }

        sb.append("\nTotal: ").append(noOfChars);
        return sb.toString();

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

/* <<<<<<<<<<<<<<  ✨ Windsurf Command ⭐ >>>>>>>>>>>>>>>> */
    /**
     * Converts a given string to uppercase by manually iterating
     * through each character and subtracting 32 from the ASCII value
     * of each character.
     *
     * @param s the string to convert
     * @return the converted string
     */
/* <<<<<<<<<<  8ac58748-dd75-477c-869c-485666baf177  >>>>>>>>>>> */
    public static String manualToUpper(String s) {
        char[] chars = s.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] >= 'a' && chars[i] <= 'z') {
                chars[i] = (char) (chars[i] - 32);
            }
        }

        return new String(chars);
    }

/* <<<<<<<<<<<<<<  ✨ Windsurf Command ⭐ >>>>>>>>>>>>>>>> */
    /**
     * Converts a given string to lowercase by manually iterating
     * through each character and adding 32 to the ASCII value
     * of each character.
     *
     * @param s the string to convert
     * @return the converted string
     */
/* <<<<<<<<<<  fdc29e96-e7cd-4a68-a195-f001460739d6  >>>>>>>>>>> */
    public static String manualToLower(String s) {
        char[] chars = s.toCharArray();
        for (int i=0; i < chars.length; i++) {
            if (chars[i] >= 'A' && chars[i] <= 'Z' ) {
                chars[i] = (char) (chars[i] + 32);
            }
        }

        return new String(chars);
    }

/* <<<<<<<<<<<<<<  ✨ Windsurf Command ⭐ >>>>>>>>>>>>>>>> */
    /**
     * Counts the number of words in a given string.
     *
     * The method uses a state machine to track when entering/exiting words.
     * It iterates through each character in the string, and if the character
     * is not a space, it increments the count and sets the inWord flag to true.
     * If the character is a space, it resets the inWord flag to false.
     *
     * @param s the string to count words in
     * @return the number of words in the string
     */
/* <<<<<<<<<<  e08a6a80-8b79-44ff-b4f4-c904bec980d4  >>>>>>>>>>> */
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
