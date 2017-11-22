package com.asofterspace.apps.universalconverter;

import com.asofterspace.apps.universalconverter.backend.coders.BinaryDecoder;
import com.asofterspace.apps.universalconverter.backend.coders.BinaryEncoder;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Unit test concerning the binary encoder and decoder classes
 *
 * @author Moya (a softer space, 2017)
 */
public class BinaryCoderUnitTest {

    @Test
    public void encoderIsWorking() throws Exception {

        String text = "16";
        String result = (new BinaryEncoder()).encodeIntoBinaryString(text);

        assertEquals("10000", result);
    }

    @Test
    public void decoderIsWorking() throws Exception {

        String text = "0x01";
        int result = (new BinaryDecoder()).decodeFromBinary(text);

        assertEquals(1, result);
    }

    @Test
    public void encodingAndDecodingIsConsistent() throws Exception {

        String text = "27398";
        String binaryString = (new BinaryEncoder()).encodeIntoBinaryString(text);
        String decodedText = (new BinaryDecoder()).decodeFromBinaryIntoStr(binaryString);

        assertEquals(text, decodedText);

        text = "11001101";
        decodedText = (new BinaryDecoder()).decodeFromBinaryIntoStr(text);
        binaryString = (new BinaryEncoder()).encodeIntoBinaryString(decodedText);

        assertEquals(text, binaryString);
    }

}