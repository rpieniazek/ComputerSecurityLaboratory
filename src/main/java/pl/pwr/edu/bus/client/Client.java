package pl.pwr.edu.bus.client;

import com.google.gson.Gson;
import pl.pwr.edu.bus.commons.MessageEncoder;
import pl.pwr.edu.bus.commons.NoneEncoder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.Socket;
import java.util.Base64;
import java.util.Random;

import static com.google.common.collect.ImmutableMap.of;

public class Client implements ClientRequestCommand {

    private Socket server;
    private PrintWriter outputWriter;
    private BufferedReader inputBuffer;
    private final Gson gson;
    private final ClientRequestConverter clientRequestConverter;
    private BigInteger p;
    private BigInteger g;
    private BigInteger a;
    private BigInteger b;
    private BigInteger secret;
    private MessageEncoder encoder;

    public Client() {
        clientRequestConverter = new ClientRequestConverter(this);
        gson = new Gson();
        encoder = new NoneEncoder();
    }

    @Override
    public void setP(BigInteger p) {
        this.p = p;
        if (this.g != null)
            calculateAndSendA();
    }

    @Override
    public void setG(BigInteger g) {
        this.g = g;
        if (this.p != null)
            calculateAndSendA();
    }

    @Override
    public void processMessage(String message) {
        System.out.printf("raw message: %s\n", message);
        String base64decoded = new String(Base64.getDecoder().decode(message));
        String decodedMessage = encoder.decode(base64decoded, secret);
        System.out.println(decodedMessage);
    }

    @Override
    public void storeB(BigInteger B) {
        this.b = B;
        calculateSecret();
    }

    private void calculateSecret() {
        secret = b.modPow(a, p);
        System.out.println(String.format("Secret calculated for by Client:[%d]", secret));
    }

    public void runClient() {
        try {
            server = new Socket("127.0.0.1", 8080);
            inputBuffer = new BufferedReader(new InputStreamReader(server.getInputStream()));
            outputWriter = new PrintWriter(server.getOutputStream(), true);
            sendRequestForKeys();

            while (true) {
                String readLine = readLine();
                System.out.println(String.format("Read line %s", readLine));
                clientRequestConverter.resolveKeys(readLine);
            }
        } catch (IOException e) {
            System.err.println(e);
            e.printStackTrace();
        }
    }

    private void calculateAndSendA() {
        Random random = new Random();
        this.a = BigInteger.valueOf(random.nextLong());
        BigInteger A = g.modPow(a, p);
        outputWriter.println(gson.toJson(of("a", A)));
    }

    private void sendRequestForKeys() {
        outputWriter.println(gson.toJson(of("request", "keys")));
    }

    private String readLine() {
        String line = null;
        try {
            line = inputBuffer.readLine();
        } catch (IOException e) {
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
