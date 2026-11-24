/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.recursivedigitoperations;

import java.util.Scanner;

/**
 *
 * @author carlo
 */
public class RecursiveDigitOperations {

    public static int sumOfDigits(int n) {
        return n == 0 ? 0 : n % 10 + sumOfDigits(n / 10);
    }

    public static int reverseNumber(int n, int p) {
        return n == 0 ? 0 : (n % 10) * (int) Math.pow(10, p) + reverseNumber(n / 10, p - 1);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a whole number: ");
        int number = scanner.nextInt();
        int digits = (int) Math.log10(number);

        System.out.println("Sum of digits = " + sumOfDigits(number));
        System.out.println("Reverse = " + reverseNumber(number, digits));
        scanner.close();
    }
}
