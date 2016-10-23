package pl.pwr.edu.bus.commons;

import java.math.BigInteger;


/**
 * Created by rafal on 22.10.16.
 */
public class MessageEncoderFactory {

    public final String CAESAR = "caesar";
    public final String XOR = "xor";

    public MessageEncoder create(String type) {
        type = type.toLowerCase().trim();
        if (type.equals(CAESAR)) {
            return new CaesarEncoder();
        } else if (type.equals(XOR)) {
            return new XorEncoder();
        }else
            return new NoneEncoder();
    }

}
