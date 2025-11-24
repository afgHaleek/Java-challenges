package challenge04;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ArrayOperations {

    public static int sumOfArray(int[] n) {
        int result = 0;
        for (int i = 0; i < n.length; i++) {
            result += n[i];
        }

        return result;
    }

    public static int maxNumInArray(int[] n) {

        if (n.length == 0) throw new IllegalArgumentException("Array can not be empty");

        int max = n[0];
        for (int i = 0; i < n.length; i++) {
            if (max < n[i]) {
                max = n[i];
            }
        }

        return max;
    }

    public static int minNumInArray(int[] n) {

        if (n.length == 0) throw new IllegalArgumentException("Array can not be empty");

        int min = n[0];
        for(int i = 0; i < n.length; i++) {
            if (min > n[i]) {
                min = n[i];
            }
        }

        return min;
    }

    public static double average(int[] n) {
        if (n.length == 0)
            throw new IllegalArgumentException("Array can not be empty");

        return (double) sumOfArray(n) / n.length;
    }
}
