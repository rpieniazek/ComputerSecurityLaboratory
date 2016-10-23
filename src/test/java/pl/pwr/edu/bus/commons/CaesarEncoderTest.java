package pl.pwr.edu.bus.commons;

import org.junit.Before;
import org.junit.Test;

import java.math.BigInteger;

import static org.junit.Assert.*;

/**
 * Created by rafal on 23.10.16.
 */
public class CaesarEncoderTest {

    public static final String PLAIN = "abcxyz";
    public static final String ENCODED = "defabc";
    public static final int SHIFT = 3;
    private CaesarEncoder encoder;

    @Before
    public void setUp() throws Exception {
        encoder = new CaesarEncoder();
    }

    @Test
    public void shouldEncodeTestMessage() throws Exception {
        String encoded = encoder.encode(PLAIN, BigInteger.valueOf(SHIFT));
        assertEquals(ENCODED, encoded);
    }

    @Test
    public void shouldDecodeTestMessage() throws Exception {
        String decoded = encoder.decode(ENCODED, BigInteger.valueOf(SHIFT));
        assertEquals(PLAIN, decoded);

    }
}