package challenge02;

import java.util.Scanner;

public class SimpleCalculator {
   public double calculate(double num1, double num2, String operation) {
       switch (operation) {
           case "+" -> {
               return num1 + num2;
           }

           case "-" -> {
               return num1 - num2;
           }

           case "*" -> {
               return num1 * num2;
           }

           case "/" -> {
               if (num2 == 0) throw new ArithmeticException("can not divide by zero");
               return num1 / num2;
           }

           default -> throw new IllegalArgumentException("Invalid operation: " + operation);
       }
   }
}
