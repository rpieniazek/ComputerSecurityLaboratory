package pl.pwr.edu.bus.commons;

import java.math.BigInteger;

/**
 * Created by rafal on 22.10.16.
 */
public interface MessageEncoder {

    String encode(String encode, BigInteger secret);
    String decode(String decode, BigInteger secret);

}
