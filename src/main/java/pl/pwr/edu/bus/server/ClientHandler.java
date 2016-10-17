package pl.pwr.edu.bus.server;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.Socket;
import java.net.SocketException;

import static com.google.common.collect.ImmutableMap.of;


public class ClientHandler implements Runnable, RequestProcessCommand {

    private Socket socket;
    private PrintWriter outputWriter;
    private BufferedReader inputBuffer;
    private ServerRequestConverter serverRequestConverter;
    private BigInteger p;
    private BigInteger g;
    private BigInteger a;
    private BigInteger b;
    private BigInteger secret;
    public ClientHandler(Socket socket) {
        System.out.println("Client handler created ");
        this.socket = socket;
        serverRequestConverter = new ServerRequestConverter(this);
        this.p = BigInteger.valueOf(23L);
        this.g = BigInteger.valueOf(5L);
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
            while (true) {
                String requestLine = readLine();
                System.out.println(String.format("Readed line: [%s]", requestLine));
                serverRequestConverter.resolveRequestsType(requestLine);
            }
        } catch (IOException e) {
            System.err.println(e);
            e.printStackTrace();
        }
    }

    @Override
    public void processMessage(String readedLine) {
        System.out.println("server side:" + readedLine);
    }

    @Override
    public void storeA(BigInteger a) {
        this.a = a;
        calculateSecret();
    }

    private void calculateSecret() {
        secret = a.modPow(b,p);
        System.out.println(String.format("Secret calculated for test Client by Server:[%d]", secret));
    }

    public void setEncoding() {
        throw new UnsupportedOperationException("not yet implemented");
    }


    public void sendKeys() {
        Gson gson = new Gson();
        ImmutableMap<String, BigInteger> map = ImmutableMap.of("p", p, "g", g);
        String request = gson.toJson(map);
        outputWriter.println(request);
        outputWriter.flush();
        this.b = BigInteger.valueOf(15l);
        BigInteger B = g.modPow(b,p);

        outputWriter.println(gson.toJson(of("b", B)));
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
