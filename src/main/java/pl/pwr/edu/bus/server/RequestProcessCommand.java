package pl.pwr.edu.bus.server;

/**
 * Created by rafal on 15.10.16.
 */
public interface RequestProcessCommand {

    void sendKeys();

    void setEncoding();

    void processMessage(String line);
}
