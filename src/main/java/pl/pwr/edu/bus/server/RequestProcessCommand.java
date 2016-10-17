package pl.pwr.edu.bus.server;

import java.math.BigInteger;

/**
 * Created by rafal on 15.10.16.
 */
public interface RequestProcessCommand {

    void sendKeys();

    void setEncoding();

    void processMessage(String line);

    void storeA(BigInteger a);
}
