package pl.pwr.edu.bus.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by rafal on 10.10.16.
 */
public class Client {

    private Socket server;
    private PrintWriter outputWriter;
    private BufferedReader inputBuffer;
    private String username;


    public void runClient() {
        try {
            server = new Socket("127.0.0.1", 8080);
            inputBuffer = new BufferedReader(new InputStreamReader(server.getInputStream()));
            outputWriter = new PrintWriter(server.getOutputStream());
            sendTestMessage();
        } catch (IOException e) {
            System.err.println(e);
            e.printStackTrace();
        }
    }

    private void sendTestMessage() {
        outputWriter.print("msg Hello world");
    }

    public static void main(String[] args) {
        Client client = new Client();
        client.runClient();
    }
}
