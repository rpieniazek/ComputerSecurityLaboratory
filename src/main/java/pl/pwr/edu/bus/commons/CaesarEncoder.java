package pl.pwr.edu.bus.commons;

import java.math.BigInteger;

/**
 * Created by rafal on 22.10.16.
 */
public class CaesarEncoder implements MessageEncoder {

    @Override
    public String encode(String encode, BigInteger secret) {
        encode = encode.toLowerCase();
        String s = shiftMessage(encode, secret.mod(BigInteger.valueOf(26)).intValue());
        return s;
    }

    @Override
    public String decode(String decode, BigInteger secret) {
        decode = decode.toLowerCase();
        return shiftMessage(decode, -secret.mod(BigInteger.valueOf(26)).intValue());
    }

    private String shiftMessage(String msg, int shift) {
        String resultString = "";
        int len = msg.length();
        for (int x = 0; x < len; x++) {
            char currentChar = (char) (msg.charAt(x) + shift);
            if ((currentChar >= 'a') && (currentChar <= 'z')) {
                resultString += currentChar;
            } else {
                if (currentChar < 'a') {
                    resultString += (char) (msg.charAt(x) + (26 + shift));

                } else if (currentChar > 'z') {
                    resultString += (char) (msg.charAt(x) - (26 - shift));

                }
            }
        }
        return resultString;
    }
}
