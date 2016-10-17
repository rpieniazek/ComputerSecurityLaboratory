package pl.pwr.edu.bus.client;

import java.math.BigInteger;

/**
 * Created by rafal on 15.10.16.
 */
public interface ClientRequestCommand {
    void setP(BigInteger p);
    void setG(BigInteger g);

    void processMessage(String readLine);

    void storeB(BigInteger b);
}
