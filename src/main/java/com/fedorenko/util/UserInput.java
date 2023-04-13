package com.fedorenko.util;

import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;


public class UserInput {
    private static final BufferedReader BUFFERED_READER = new BufferedReader(new InputStreamReader(System.in));

    @SneakyThrows
    public static int menu(String[] names) {
        int userChoice = -1;
        do {
            System.out.println("Enter the option from menu.");
            for (int i = 0; i < names.length; i++) {
                System.out.println(i + " " + names[i]);
            }
            final String userInput = BUFFERED_READER.readLine();
            if (!StringUtils.isNumeric(userInput)) {
                continue;
            }
            userChoice = Integer.parseInt(userInput);
        } while (userChoice < 0 || userChoice >= names.length);
        return userChoice;
    }

    @SneakyThrows
    public static String getString(final String advertisement) {
        System.out.println(advertisement);
        return BUFFERED_READER.readLine();
    }

    @SneakyThrows
    public static Float getFloat(final String advertisement) {
        System.out.println(advertisement);
        while (true) {
            final String userInput = BUFFERED_READER.readLine();
            try {
               return  Float.valueOf(userInput);
            } catch (Exception e) {
                System.out.println("Entered value cannot be converted to grade. Try again.");
            }
        }
    }
}
