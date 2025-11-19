package challenge03;

import java.util.Scanner;

public class NumberClassification {
    public static void main(String... args) {
        System.out.println("########### NUMBER CLASSIFICATION PROGRAM ###########\n");
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter an integer number: ");
        int num = sc.nextInt();


        if (isPositive(num)) {
            System.out.println("Number is positive");
            if (isPrime(num)) System.out.println("The number is prime");
            if (isEven(num)) {
                System.out.println("The number is even.");
            } else {
                System.out.println("The number is odd.");
            }
        } else if (isNegative(num)) {
            System.out.println("Number is negative");
        } else if(num == 0) System.out.println("The number is zero.");
    }

    public static boolean isPrime(int n) {
        if (n <= 1) {
            return false;
        }

        for (int i = 2; i < n; i++) {
            if (n % i == 0) return false;
        }

        return true;
    }

    public static boolean isPositive(int n) {
        return n > 0;
    }

    public static boolean isNegative(int n) {
        return n < 0;
    }

    public static boolean isEven(int n) {
        return n % 2 == 0;
    }


}
