package com.asofterspace.apps.universaltranslator;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)

@Suite.SuiteClasses({
        Base64CoderUnitTest.class,
        BinaryCoderUnitTest.class,
        MorseCoderUnitTest.class,
        RomanNumeralCoderUnitTest.class,
})

/**
 * A test suite running all the unit tests
 *
 * @author Moya (a softer space, 2017)
 */
public class AllUnitTests {
}
