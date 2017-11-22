package com.asofterspace.apps.universalconverter.backend.coders;

import java.util.ArrayList;
import java.util.List;

/**
 * A class that can encode text into Base64
 *
 * @author Moya (a softer space, 2017)
 */
public class Base64Encoder {

    /**
     * Encodes text into base64 (and as there are a million base64 standards out there: we are
     * trying to achieve compatibility with both the Original Base64 for PEM and the current Base64
     * for MIME)
     * @param inputText  The text to be encoded into base64
     * @return The input text encoded in base64
     */
    public String encodeIntoBase64(String inputText) {

        List<Integer> numbers = new ArrayList<>();

        int offset = 0;
        int buffer = 0;

        // the numbers are arranged in such a way:
        // inputText: aa aa aa aa bb bb bb bb cc cc cc cc | characters a, b and c in ASCII
        // numbers:   00 00 00 11 11 11 22 22 22 33 33 33 | base64 numbers 0, 1, 2 and 3

        for (char thisChar : inputText.toCharArray()) {
            switch (offset) {
                case 0:
                    numbers.add(thisChar >>> 2);
                    // use 11b, which however is not a notation Java understands, so let's write
                    // it in hexadecimal instead: 0x3
                    buffer = thisChar & 0x3;
                    offset = 2;
                    break;

                case 2:
                    buffer = buffer << 4;
                    int newChar = thisChar >>> 4;
                    numbers.add(buffer | newChar);
                    // use 1111b, which however is not a notation Java understands, so let's write
                    // it in hexadecimal instead: 0xF
                    buffer = thisChar & 0xF;
                    offset = 4;
                    break;

                case 4:
                    buffer = buffer << 2;
                    newChar = thisChar >>> 6;
                    numbers.add(buffer | newChar);

                    // use 111111b, which however is not a notation Java understands, so let's write
                    // it in hexadecimal instead: 0x3F
                    newChar = thisChar & 0x3F;
                    numbers.add(newChar);
                    offset = 0;
                    break;
            }
        }

        switch (offset) {
            case 2:
                numbers.add(buffer << 4);
                numbers.add(0x40);
                numbers.add(0x40);
                break;
            case 4:
                numbers.add(buffer << 2);
                numbers.add(0x40);
                break;
        }

        // initiate an array that is definitely big enough (numbers.size() * (1+1/64) should do it,
        // but this is easier to calculate and we only need the memory for this short time...)
        char[] base64characters = new char[(numbers.size() * 2) + 2];

        // note that we have base64 chars on 0 .. 63, but then the padding char = on 64
        char[] numToChar = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L',
                'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c',
                'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
                'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+',
                '/', '='};

        int off = 0;

        for (int i = 0; i < numbers.size(); i++) {
            base64characters[i+off] = numToChar[numbers.get(i)];

            // add newline after 64 characters, as demanded by old base64 specs (which is still
            // compatible with new specs though)
            if (i % 64 == 63) {
                off++;
                base64characters[i+off] = '\r';
                off++;
                base64characters[i+off] = '\n';
            }
        }

        String result = new String(base64characters);

        result = result.substring(0, numbers.size()+off);

        // get rid of trailing linefeed...
        if (result.endsWith("\r\n")) {
            result = result.substring(0, result.length() - 2);
        }

        return result;
    }
}
