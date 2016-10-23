package pl.pwr.edu.bus.commons;

import java.math.BigInteger;

public class XorEncoder implements MessageEncoder {

    @Override
    public String encode(String encode, BigInteger secret) {
        return new String(xorWithKey(encode.getBytes(),intToByteArray(secret.intValue())));
    }

    @Override
    public String decode(String decode, BigInteger secret) {
        return new String(xorWithKey(decode.getBytes(),intToByteArray(secret.intValue())));
    }

    private byte[] xorWithKey(byte[] message, byte[] key) {
        byte[] out = new byte[message.length];
        for (int i = 0; i < message.length; i++) {
            out[i] = (byte) (message[i] ^ key[key.length-1]);
        }
        return out;
    }

    private byte[] intToByteArray(int value) {
        return new byte[] {
                (byte)(value >>> 24),
                (byte)(value >>> 16),
                (byte)(value >>> 8),
                (byte)value};
    }
}
