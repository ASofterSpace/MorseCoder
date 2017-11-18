package com.asofterspace.apps.universaltranslator;

import com.asofterspace.apps.universaltranslator.backend.coders.MorseDecoder;
import com.asofterspace.apps.universaltranslator.backend.coders.MorseEncoder;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit test concerning the Morse encoder and decoder classes
 *
 * @author Moya (a softer space, 2017)
 */
public class MorseCoderUnitTest {

    @Test
    public void encodingAndDecodingIsConsistent() throws Exception {

        String allLetters = "The quick brown fox jumps over the lazy dog".toUpperCase();

        String allLettersMorse = (new MorseEncoder()).translateToMorseCode(allLetters);

        String allLettersUnmorse = (new MorseDecoder()).translateFromMorseCode(allLettersMorse);

        assertEquals(allLetters, allLettersUnmorse);


        String allDigits = "01 1701 27398 74205 74656";

        String allDigitsMorse = (new MorseEncoder()).translateToMorseCode(allDigits);

        String allDigitsUnmorse = (new MorseDecoder()).translateFromMorseCode(allDigitsMorse);

        assertEquals(allDigits, allDigitsUnmorse);
    }
}