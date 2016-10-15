package pl.pwr.edu.bus.server;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.net.Socket;
import java.net.SocketException;
import java.util.Map;

/**
 * Created by rafal on 10.10.16.
 */
public class ClientHandler implements Runnable, RequestProcessCommand {

    private Socket socket;
    private PrintWriter outputWriter;
    private BufferedReader inputBuffer;
    private AuthenticationService authenticationService;
    private RequestConverter requestConverter;

    public ClientHandler(Socket socket) {
        System.out.println("Client handler created ");
        this.socket = socket;
        authenticationService = new AuthenticationService();
        requestConverter = new RequestConverter(this);
    }

    @Override
    public void run() {
        System.out.println("Client handler started ");
        handleRequest();
    }

    private void handleRequest() {
        try {
            inputBuffer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            outputWriter = new PrintWriter(socket.getOutputStream(), true);
            String requestLine = readLine();
            requestConverter.resolveRequestsType(requestLine);
        } catch (IOException e) {
            System.err.println(e);
            e.printStackTrace();
        }
    }

    public void processMessage(String readedLine) {
        System.out.println("server side:" + readedLine);
        outputWriter.println("echo" + readedLine);
    }

    public void setEncoding() {
        throw new UnsupportedOperationException("not yet implemented");
    }

    public void sendKeys() {
        outputWriter.println(authenticationService.generateKeys());
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
