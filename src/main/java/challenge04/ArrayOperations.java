package challenge04;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ArrayOperations {

    public static void main(String... args) {

        System.out.println("########## PROGRAM FOR ARRAY OPERATIONS ##########");
        Scanner sc = new Scanner(System.in);

        int[] numbers = new int[5];

        System.out.println("Enter 5 Integers: ");
        for (int i = 0; i < numbers.length; i++) {
            try {
                System.out.print("Enter number " + (i + 1 ) + ": ");
                numbers[i] = sc.nextInt();
            } catch (InputMismatchException ex) {
                throw new InputMismatchException("please enter number");
            }

        }


        System.out.println("Array Operation results: \n");
        System.out.println(Arrays.toString(numbers));
        System.out.println("sum: " + sumOfArray(numbers));
        System.out.println("Maximum: " + maxNumInArray(numbers));
        System.out.println("Minimum: " + minNumInArray(numbers));
        double averageOfArray = (double) sumOfArray(numbers) / numbers.length;
        System.out.println("Average: " + averageOfArray);



    }

    public static int sumOfArray(int[] n) {
        int result = 0;
        for (int i = 0; i < n.length; i++) {
            result += n[i];
        }

        return result;
    }

    public static int maxNumInArray(int[] n) {
        int max = n[0];
        for (int i = 0; i < n.length; i++) {
            if (max < n[i]) {
                max = n[i];
            }
        }

        return max;
    }

    public static int minNumInArray(int[] n) {
        int min = n[0];
        for(int i = 0; i < n.length; i++) {
            if (min > n[i]) {
                min = n[i];
            }
        }

        return min;
    }
}
