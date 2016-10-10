package pl.pwr.edu.bus.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class ServerImpl implements Server {

    private ServerSocket listener;

    ServerImpl(short port) {
        try {
            listener = new ServerSocket(port);
        } catch (IOException e) {
            System.err.println(e);
            e.printStackTrace();
        }
    }

    @Override
    public void run() throws IOException {
        System.out.println("Server started.. listening for connections");
        while (true) {
            Socket client = listener.accept();
            new Thread(() -> handleMessage(client)).start();
        }
    }

    private void handleMessage(Socket socket) {
        try {
            BufferedReader inputBuffer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter outputWriter = new PrintWriter(socket.getOutputStream(), true);
            readLine(inputBuffer);
        } catch (IOException e) {
            System.err.println(e);
            e.printStackTrace();
        }
    }

    public String readLine(BufferedReader inputBuffer) {
        String line = null;
        try {
            line = inputBuffer.readLine();
        } catch (SocketException e) {
            System.out.println("Log: Client disconnected, session ended");
        } catch (IOException e) {
            System.err.println(e);
            e.printStackTrace();
        }
        return line;
    }

    public static void main(String[] args) {
        final short PORT = 8080;

        Server server = new ServerImpl(PORT);
        try {
            server.run();
        } catch (IOException e) {
            System.err.print(e);
            e.printStackTrace();
        }
    }
}
