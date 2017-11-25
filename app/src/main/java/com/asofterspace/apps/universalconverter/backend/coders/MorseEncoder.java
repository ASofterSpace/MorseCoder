package com.asofterspace.apps.universalconverter.backend.coders;

/**
 * A class that can encode text into Morse code
 *
 * @author Moya (a softer space, 2017)
 */
public class MorseEncoder {

    /**
     * Translates a given text into its Morse code representation
     * @param textToTranslate the text that is to be translated
     * @return a representation of the text in Morse code
     */
    public String translateToMorseCode(String textToTranslate) {

        String input = textToTranslate.toUpperCase();

        StringBuilder result = new StringBuilder();

        for (int i = 0; i < input.length(); i++){

            // space between letters: three units
            if (i > 0) {
                result.append("   ");
            }

            switch (input.charAt(i)) {
                // space between words: seven units
                // (so three before the space, three after, and one for the space itself)
                case ' ':
                    result.append(" ");
                    break;
                case '\n':
                    result.append("\n");
                    break;
                case 'A':
                    result.append("•-");
                    break;
                case 'B':
                    result.append("-•••");
                    break;
                case 'C':
                    result.append("-•-•");
                    break;
                case 'D':
                    result.append("-••");
                    break;
                case 'E':
                    result.append("•");
                    break;
                case 'F':
                    result.append("••-•");
                    break;
                case 'G':
                    result.append("--•");
                    break;
                case 'H':
                    result.append("••••");
                    break;
                case 'I':
                    result.append("••");
                    break;
                case 'J':
                    result.append("•---");
                    break;
                case 'K':
                    result.append("-•-");
                    break;
                case 'L':
                    result.append("•-••");
                    break;
                case 'M':
                    result.append("--");
                    break;
                case 'N':
                    result.append("-•");
                    break;
                case 'O':
                    result.append("---");
                    break;
                case 'P':
                    result.append("•--•");
                    break;
                case 'Q':
                    result.append("--•-");
                    break;
                case 'R':
                    result.append("•-•");
                    break;
                case 'S':
                    result.append("•••");
                    break;
                case 'T':
                    result.append("-");
                    break;
                case 'U':
                    result.append("••-");
                    break;
                case 'V':
                    result.append("•••-");
                    break;
                case 'W':
                    result.append("•--");
                    break;
                case 'X':
                    result.append("-••-");
                    break;
                case 'Y':
                    result.append("-•--");
                    break;
                case 'Z':
                    result.append("--••");
                    break;
                case '0':
                    result.append("-----");
                    break;
                case '1':
                    result.append("•----");
                    break;
                case '2':
                    result.append("••---");
                    break;
                case '3':
                    result.append("•••--");
                    break;
                case '4':
                    result.append("••••-");
                    break;
                case '5':
                    result.append("•••••");
                    break;
                case '6':
                    result.append("-••••");
                    break;
                case '7':
                    result.append("--•••");
                    break;
                case '8':
                    result.append("---••");
                    break;
                case '9':
                    result.append("----•");
                    break;
                default:
                    result.append("?");
            }
        }

        return result.toString();
    }
}