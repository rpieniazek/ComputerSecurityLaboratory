package pl.pwr.edu.bus.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;

/**
 * Created by rafal on 10.10.16.
 */
public class ClientHandler implements Runnable {

    private Socket socket;
    private PrintWriter outputWriter;
    private BufferedReader inputBuffer;
    private AuthenticationService authenticationService;

    public ClientHandler(Socket socket) {
        System.out.println("Client handler created ");
        this.socket = socket;
        authenticationService = new AuthenticationService();
    }

    @Override
    public void run() {
        System.out.println("Client handler started ");
        handleMessage();
    }

    private void handleMessage() {
        try {
            inputBuffer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            outputWriter = new PrintWriter(socket.getOutputStream(), true);
            String readLine = readLine();
            resolveMessage(readLine.toLowerCase());
        } catch (IOException e) {
            System.err.println(e);
            e.printStackTrace();
        }
    }

    private void resolveMessage(String readedLine) {
        if (readedLine.startsWith("request")) {
            sendKeys();
        } else if (readedLine.startsWith("encoding")) {
            setEncoding();
        } else if (readedLine.startsWith("msg")) {
            processMessage(readedLine);
        }
    }

    private void processMessage(String readedLine) {
        System.out.println(readedLine);
    }

    private void setEncoding() {
        throw new UnsupportedOperationException("not yet implemented");
    }

    private void sendKeys() {
        outputWriter.print(authenticationService.generateKeys());
    }

    private String readLine() {
        String line = null;
        try {
            line = inputBuffer.readLine();
        } catch (SocketException e) {
            System.out.println("Client disconnected, session ended");
        } catch (IOException e) {
            System.err.println(e);
            e.printStackTrace();
        }
        return line;
    }


}
