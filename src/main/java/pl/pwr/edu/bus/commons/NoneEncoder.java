package pl.pwr.edu.bus.commons;

import java.math.BigInteger;

/**
 * Created by rafal on 22.10.16.
 */
public class NoneEncoder implements MessageEncoder {

    @Override
    public String encode(String encode, BigInteger secret) {
        return encode;
    }

    @Override
    public String decode(String decode, BigInteger secret) {
        return decode;
    }
}
