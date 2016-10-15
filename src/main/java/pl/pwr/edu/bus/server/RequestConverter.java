package pl.pwr.edu.bus.server;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Map;

/**
 * Created by rafal on 15.10.16.
 */
public class RequestConverter {

    public static final String REQUEST = "request";
    private final RequestProcessCommand command;

    public RequestConverter(RequestProcessCommand command) {
        this.command = command;
    }

    public void resolveRequestsType(String requestLine) {
        Map<String, String> requestAsMap = convertMessageFromJson(requestLine);
        requestAsMap.keySet()
                .stream()
                .forEach((readedLine) -> resolveSingleRequest(readedLine));
    }

    private Map<String, String> convertMessageFromJson(String request) {
        Gson gson = new Gson();
        Type stringStringMap = new TypeToken<Map<String, String>>() {
        }.getType();
        return gson.fromJson(request, stringStringMap);
    }

    private void resolveSingleRequest(String readedLine) {
        if (readedLine.startsWith(REQUEST)) {
            command.sendKeys();
        } else if (readedLine.startsWith("encoding")) {
            command.setEncoding();
        } else if (readedLine.startsWith("msg")) {
            command.processMessage(readedLine);
        }
    }


}
