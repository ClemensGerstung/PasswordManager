package com.password.manager.core.handler;

/**
 * Created by Clemens on 31.01.2015.
 */
public class PasswordSafetyHandler {

    private static final String[] things =
            {
                    "abcdefghijklmnopqrstuvwxyz",
                    "ABCDEFGHIJKLMNOPQRSTUVWXYZ",
                    "0123456789",
                    "!*_+-/;:%&$§()[]{}\\~\'|<>^°#@"
            };

    public static boolean isSafe(String password, int minCountOfSmallAlphas, int minCountOfBigAlphas, int minCountOfNumbers, int minCountOfSpecials, int minLength) {
        if(password.length() < minLength) return false;
        int countOfSmallAlphas = 0;
        int countOfBigAlphas = 0;
        int countOfNumbers = 0;
        int countOfSpecials = 0;

        for(char c : password.toCharArray()){
            if(things[0].contains(Character.toString(c))) countOfSmallAlphas++;
            if(things[1].contains(Character.toString(c))) countOfBigAlphas++;
            if(things[2].contains(Character.toString(c))) countOfNumbers++;
            if(things[3].contains(Character.toString(c))) countOfSpecials++;
        }

        return countOfBigAlphas >= minCountOfBigAlphas && countOfSmallAlphas >= minCountOfSmallAlphas && countOfNumbers >= minCountOfNumbers && countOfSpecials >= minCountOfSpecials;
    }
}
