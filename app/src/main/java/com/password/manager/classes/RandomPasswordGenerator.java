package com.password.manager.classes;

import java.util.Random;

/**
 * Created by Clemens on 27.11.2014.
 */
public class RandomPasswordGenerator {
    private static final String[] things =
            {
                    "ABCDEFGHIJKLMNOPQRSTUVWXYZ",
                    "abcdefghijklmnopqrstuvwxyz",
                    "0123456789",
                    "!*_+-/;:"
            };


    public static String generatePassword(int minLen, int maxLen, int noOfCAPSAlpha, int noOfDigits, int noOfSplChars) {
        if (minLen > maxLen)
            throw new IllegalArgumentException("Min. Length > Max. Length!");
        if ((noOfCAPSAlpha + noOfDigits + noOfSplChars) > minLen)
            throw new IllegalArgumentException("Min. Length should be at least sum of (CAPS, DIGITS, SPL CHARS) Length!");
        Random rnd = new Random();
        int len = rnd.nextInt(maxLen - minLen + 1) + minLen;
        StringBuilder stringBuilder = new StringBuilder();

        int []cnos = {
                0,  // current count of alphas
                0,  // current count of CAPS alphas
                0,  // current count of digits
                0   // current count of special chars
        };

        for (int i = 0; i < len; i++) {
            int r = generateDigit(rnd, cnos, len - noOfCAPSAlpha - noOfDigits - noOfSplChars, noOfCAPSAlpha, noOfDigits, noOfSplChars);
            String s = things[r];
            int c = rnd.nextInt(s.length());
            stringBuilder.append(s.charAt(c));
        }

        return stringBuilder.toString();
    }

    private static int generateDigit(Random rnd, int[] cnos, int noOfAlpha, int noOfCAPSAlpha, int noOfDigits, int noOfSplChars)
    {
        int r = rnd.nextInt(4);
        if(cnos[r] < noOfAlpha && r == 0){
            cnos[r]++;
            return r;
        } else if(cnos[r] < noOfCAPSAlpha && r == 1){
            cnos[r]++;
            return r;
        } else if(cnos[r] < noOfDigits && r == 2){
            cnos[r]++;
            return r;
        } else if(cnos[r] < noOfSplChars && r == 3){
            cnos[r]++;
            return r;
        } else {
            generateDigit(rnd, cnos, noOfAlpha, noOfCAPSAlpha, noOfDigits, noOfSplChars);
        }

        return 0;
    }
}
