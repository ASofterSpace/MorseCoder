package com.asofterspace.apps.universaltranslator.backend.coders;

/**
 * A class that can encode numbers into Roman numerals
 *
 * @author Moya (a softer space, 2017)
 */
public class RomanNumeralEncoder {

    /**
     * Encodes numbers into Roman numerals
     *
     * @param numbers  A string full of numbers (in digits, not letters ^^)
     * @return A string containing the Roman numerals corresponding to the input
     */
    public String encodeNumbersIntoRomanNumerals(String numbers) {

        StringBuilder result = new StringBuilder();

        try {
            Integer input = Integer.valueOf(numbers);

            int ones = input % 10;
            input = input / 10;
            int tens = input % 10;
            input = input / 10;
            int hundreds = input % 10;
            input = input / 10;
            int thousands = input % 10;

            result.append(intToRoman(thousands, "M", "MMMMM", "MMMMMMMMMM"));
            result.append(intToRoman(hundreds, "C", "D", "M"));
            result.append(intToRoman(tens, "X", "L", "C"));
            result.append(intToRoman(ones, "I", "V", "X"));

            return result.toString();

        } catch (NumberFormatException e) {

            return "";
        }
    }

    private String intToRoman(int amount, String oneStr, String fiveStr, String tenStr) {

        switch (amount) {
            case 1:
                return oneStr;
            case 2:
                return oneStr+oneStr;
            case 3:
                return oneStr+oneStr+oneStr;
            case 4:
                return oneStr+fiveStr;
            case 5:
                return fiveStr;
            case 6:
                return fiveStr+oneStr;
            case 7:
                return fiveStr+oneStr+oneStr;
            case 8:
                return fiveStr+oneStr+oneStr+oneStr;
            case 9:
                return oneStr+tenStr;
        }

        return "";
    }
}
