package com.idea.permission.util;

import java.util.Date;
import java.util.Random;

public class PasswordUtil {

    private final static String[] word = {
            "a", "b", "c", "d","e", "f", "g",
            "h", "j", "k", "m","n",
            "p", "q", "r", "s","t",
            "u", "v", "w", "x","y", "z",
            "A", "B", "C", "D","E", "F", "G",
            "H", "J", "K", "M","N",
            "P", "Q", "R", "S","T",
            "U", "V", "W", "X","Y", "Z"
    };

    private final static String[] num = {
            "2", "3", "4", "5", "6", "7", "8", "9",
    };

    public static String generateRandomPassowrd() {
        StringBuilder sb = new StringBuilder();
        Random random = new Random(new Date().getTime());
        int length = random.nextInt(3) + 8;
        for (int i = 0; i < length; i++) {
            if (random.nextFloat() < 0.3) {
                sb.append(num[random.nextInt(num.length)]);
            } else {
                sb.append(word[random.nextInt(word.length)]);
            }
        }
        return sb.toString();
    }
}
