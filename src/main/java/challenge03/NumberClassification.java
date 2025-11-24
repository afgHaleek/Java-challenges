package challenge03;

import java.util.Scanner;

public class NumberClassification {

    public static boolean isPrime(int n) {
        if (n <= 1) {
            return false;
        }

        for (int i = 2; i <= Math.sqrt(n); i++) {
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

    public static String classify(int n) {
        if (n == 0) return "zero";

        if (isPositive(n)) {
            StringBuilder result = new StringBuilder("positive");

            if (isPrime(n))
                result.append(", prime");

            if (isEven(n))
                result.append(", even");
            else
                result.append(", odd");

            return result.toString();
        }

        if (isNegative(n)) {
            return "negative";
        }

        return "unknown";
    }


}
