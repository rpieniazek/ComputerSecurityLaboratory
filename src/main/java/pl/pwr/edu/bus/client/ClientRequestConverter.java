package pl.pwr.edu.bus.client;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.math.BigInteger;
import java.util.Map;
import java.util.Map.Entry;

public class ClientRequestConverter {


    private final ClientRequestCommand requestCommand;

    public ClientRequestConverter(ClientRequestCommand requestCommand) {
        this.requestCommand = requestCommand;
    }

    public void resolveKeys(String requestLine) {
        Map<String, String> requestAsMap = convertMessageFromJson(requestLine);
        for (Entry<String, String> entry : requestAsMap.entrySet()) {
            System.out.println(entry.getKey() + "/" + entry.getValue());
            resolveSingleRequest(entry);
        }
    }

    public Map<String, String> convertMessageFromJson(String request) {
        Gson gson = new Gson();
        Type stringStringMap = new TypeToken<Map<String, String>>() {
        }.getType();
        System.out.println(request);
        return gson.fromJson(request, stringStringMap);
    }

    private void resolveSingleRequest(Entry<String, String> entry) {
        String key = entry.getKey();
        String value = entry.getValue();
        if (key.startsWith("p")) {
            requestCommand.setP(new BigInteger(value));
        } else if (key.startsWith("g")) {
            requestCommand.setG(new BigInteger(value));
        } else if (key.startsWith("b")) {
            requestCommand.storeB(new BigInteger(value));
        } else if (key.startsWith("msg")) {
            requestCommand.processMessage(value);
        }
    }
}
