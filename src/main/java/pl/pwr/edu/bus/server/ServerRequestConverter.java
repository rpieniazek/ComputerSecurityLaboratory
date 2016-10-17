package pl.pwr.edu.bus.server;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.math.BigInteger;
import java.util.Map;

public class ServerRequestConverter {

    public static final String REQUEST = "request";
    private final RequestProcessCommand command;

    public ServerRequestConverter(RequestProcessCommand command) {
        this.command = command;
    }

    public void resolveRequestsType(String requestLine) {
        Map<String, String> requestAsMap = convertMessageFromJson(requestLine);
        requestAsMap.entrySet()
                .forEach((entry) -> resolveSingleRequest(entry));
    }

    private Map<String, String> convertMessageFromJson(String request) {
        Gson gson = new Gson();
        Type stringStringMap = new TypeToken<Map<String, String>>() {
        }.getType();
        return gson.fromJson(request, stringStringMap);
    }

    private void resolveSingleRequest(Map.Entry<String, String> entry) {
        String key = entry.getKey();
        String value = entry.getValue();

        if (key.startsWith(REQUEST)) {
            command.sendKeys();
        }else if(key.startsWith("a")){
            command.storeA(new BigInteger(value));
        }else if (key.startsWith("encoding")) {
            command.setEncoding();
        } else if (key.startsWith("msg")) {
            command.processMessage(value);
        }
    }
}
