package util;

import java.util.ArrayList;
import java.util.List;

public final class RandomGenerator {

    public final static char[] DIGITS = { '0', '1', '2', '3', '4', '5', '6',
            '7', '8', '9' };
    public final static char[] ALPHA_UPPER = { 'A', 'B', 'C', 'D', 'E', 'F',
            'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S',
            'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };
    public final static char[] ALPHA_LOWER = { 'a', 'b', 'c', 'd', 'e', 'f',
            'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's',
            't', 'u', 'v', 'w', 'x', 'y', 'z' };
    public final static char[] PUNCT = { '.', '_', '-', '$', '%', '#', '@',
            '!', '+', '=' };

    public static String generateDigitString(int length)
    {
        char[][] chars = { DIGITS };
        return generateString(length, chars, new ArrayList<Character>());
    }

    public static String generateAlphaUpperString(int length)
    {
        char[][] chars = { ALPHA_UPPER };
        return generateString(length, chars, new ArrayList<Character>());
    }

    public static String generateAlphaNumericString(int length)
    {
        char[][] chars = { ALPHA_UPPER, ALPHA_LOWER, DIGITS };
        return generateString(length, chars, new ArrayList<Character>());
    }

    // Sirve para generar passwords con todos los caracteres
    public static String generateString(int length)
    {
        char[][] chars = { DIGITS, ALPHA_UPPER, ALPHA_LOWER, PUNCT };
        return generateString(length, chars, new ArrayList<Character>());
    }

    // Genera un password sin caracteres que pueden ser ambiguos como 1,l,o,O y
    // 0
    public static String generatePassword(int length)
    {
        char[][] chars = { DIGITS, ALPHA_UPPER, ALPHA_LOWER };
        List<Character> not = new ArrayList<Character>();
        not.add('1');
        not.add('l');
        not.add('i');
        not.add('I');
        not.add('o');
        not.add('O');
        not.add('0');
        return generateString(length, chars, not);
    }

    public static String generateString(int maxlength, char[][] chars, List<Character> not)
    {
        String gen = "";
        while (gen.length() < maxlength)
        {
            int idx1 = (int) (Math.random() * chars.length);
            int idx2 = (int) (Math.random() * chars[idx1].length);

            char c = chars[idx1][idx2];

            if (!not.contains(c))
            {
                gen += chars[idx1][idx2];
            }
        }
        return gen;
    }
}
