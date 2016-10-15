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
            Socket socket = listener.accept();
            System.out.println("listening for connection");
            new Thread(new ClientHandler(socket)).start();
        }
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
