package pl.pwr.edu.bus.client;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {

    private Socket server;
    private PrintWriter outputWriter;
    private BufferedReader inputBuffer;

    public void runClient() {
        try {
            server = new Socket("127.0.0.1", 8080);
            inputBuffer = new BufferedReader(new InputStreamReader(server.getInputStream()));
            outputWriter = new PrintWriter(server.getOutputStream(), true);
            sendRequestForKeys();
            String read = read();
            System.out.println(read);

        } catch (IOException e) {
            System.err.println(e);
            e.printStackTrace();
        }
    }

    private void sendRequestForKeys() {
        Gson gson = new Gson();
        ImmutableMap<String, String> request = ImmutableMap.of("request", "keys");
        outputWriter.println(gson.toJson(request));
    }

    public String read() {
        String line = null;
        try {
            line = inputBuffer.readLine();
        } catch(IOException e) {
            System.err.println(e);
            e.printStackTrace();
        }
        return line;
    }

    public static void main(String[] args) {
        Client client = new Client();
        client.runClient();
    }
}
