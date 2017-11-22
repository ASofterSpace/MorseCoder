package com.asofterspace.apps.universalconverter;

import com.asofterspace.apps.universalconverter.backend.coders.RomanNumeralDecoder;
import com.asofterspace.apps.universalconverter.backend.coders.RomanNumeralEncoder;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Unit test concerning the Roman numeral encoder and decoder classes
 *
 * @author Moya (a softer space, 2017)
 */
public class RomanNumeralCoderUnitTest {

    @Test
    public void encodingAndDecodingIsConsistent() throws Exception {

        String numbers = "178";
        String numerals = (new RomanNumeralEncoder()).encodeNumbersIntoRomanNumerals(numbers);
        String outcome = (new RomanNumeralDecoder()).decodeRomanNumeralsIntoNumbers(numerals);
        assertEquals(numbers, outcome);

        numerals = "XCIX";
        numbers = (new RomanNumeralDecoder()).decodeRomanNumeralsIntoNumbers(numerals);
        outcome = (new RomanNumeralEncoder()).encodeNumbersIntoRomanNumerals(numbers);
        assertEquals(numerals, outcome);

        numerals = "XCVIII";
        numbers = (new RomanNumeralDecoder()).decodeRomanNumeralsIntoNumbers(numerals);
        outcome = (new RomanNumeralEncoder()).encodeNumbersIntoRomanNumerals(numbers);
        assertEquals(numerals, outcome);
    }

    @Test
    public void unusualEncodingCanBeDecoded() throws Exception {

        String numerals = "IC";
        String outcome = (new RomanNumeralDecoder()).decodeRomanNumeralsIntoNumbers(numerals);
        assertEquals("99", outcome);

        numerals = "IIM";
        outcome = (new RomanNumeralDecoder()).decodeRomanNumeralsIntoNumbers(numerals);
        assertEquals("998", outcome);
    }

    @Test
    public void checkThatNothingBadHappens() throws Exception {

        String numbers = "oh my!";
        String outcome = (new RomanNumeralEncoder()).encodeNumbersIntoRomanNumerals(numbers);
        assertEquals("N/A", outcome);

        String numerals = "just some random letters...";
        (new RomanNumeralDecoder()).decodeRomanNumeralsIntoNumbers(numerals);
        // it here does not matter what actually comes out - but just that something comes
        // out instead of an exception being thrown ;)
    }
}