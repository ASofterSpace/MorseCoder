package com.asofterspace.apps.universaltranslator;

import com.asofterspace.apps.universaltranslator.backend.coders.Base64Decoder;
import com.asofterspace.apps.universaltranslator.backend.coders.Base64Encoder;
import com.asofterspace.apps.universaltranslator.backend.coders.RomanNumeralDecoder;
import com.asofterspace.apps.universaltranslator.backend.coders.RomanNumeralEncoder;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Unit test concerning the base64 encoder and decoder classes
 *
 * @author Moya (a softer space, 2017)
 */
public class Base64CoderUnitTest {

    @Test
    public void encoderIsWorking() throws Exception {

        // A quote from Thomas Hobbes' Leviathan - taken from the wikipedia article on base64:
        String text = "Man is distinguished, not only by his reason, but by this singular " +
                "passion from other animals, which is a lust of the mind, that by a perseverance " +
                "of delight in the continued and indefatigable generation of knowledge, exceeds " +
                "the short vehemence of any carnal pleasure.";
        String base64EncodedText = (new Base64Encoder()).encodeIntoBase64(text);

        assertEquals("TWFuIGlzIGRpc3Rpbmd1aXNoZWQsIG5vdCBvbmx5IGJ5IGhpcyByZWFzb24sIGJ1\r\n" +
                "dCBieSB0aGlzIHNpbmd1bGFyIHBhc3Npb24gZnJvbSBvdGhlciBhbmltYWxzLCB3\r\n" +
                "aGljaCBpcyBhIGx1c3Qgb2YgdGhlIG1pbmQsIHRoYXQgYnkgYSBwZXJzZXZlcmFu\r\n" +
                "Y2Ugb2YgZGVsaWdodCBpbiB0aGUgY29udGludWVkIGFuZCBpbmRlZmF0aWdhYmxl\r\n" +
                "IGdlbmVyYXRpb24gb2Yga25vd2xlZGdlLCBleGNlZWRzIHRoZSBzaG9ydCB2ZWhl\r\n" +
                "bWVuY2Ugb2YgYW55IGNhcm5hbCBwbGVhc3VyZS4=", base64EncodedText);
    }

    @Test
    public void encodingAndDecodingIsConsistent() throws Exception {

        String text = "Just like, you know, some example text!";
        String base64EncodedText = (new Base64Encoder()).encodeIntoBase64(text);
        String decodedText = (new Base64Decoder()).decodeFromBase64(base64EncodedText);

        assertEquals(text, decodedText);
    }

}