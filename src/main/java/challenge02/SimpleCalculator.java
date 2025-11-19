package challenge02;

import java.util.Scanner;

public class SimpleCalculator {
    public static void main(String[] args) {
        System.out.println("##################### SIMPLE CALCULATOR #####################");

        Scanner sc = new Scanner(System.in);
        double num1;
        double num2;
        double result;
        String operation;

        System.out.print("Enter first number: ");
        num1 = sc.nextDouble();

        System.out.print("Enter second number: ");
        num2 = sc.nextDouble();

        System.out.print("Choose operation (+, -, *, /): ");
        operation = sc.next();

        switch (operation) {
            case "+" -> {
                result = num1 + num2;
                System.out.println("The sum of " + num1 + " and " + num2 + " is : " + result);
            }

            case "-" -> {
                result = num1 - num2;
                System.out.printf("The subtraction of %f and %f is %s", num1, num2, result);
            }

            case "*" -> {
                result = num1 * num2;
                System.out.printf("The multiplication of %f and %f is %s", num1, num2, result);
            }

            case "/" -> {
                if (num2 == 0) throw new ArithmeticException("Can not divide by zero");
                result = num1 / num2;
                System.out.printf("the division of %f and %f is %s", num1, num2, result);
            }

            default -> {
                System.out.println("Wrong operation choice");
            }
        }


    }
}
