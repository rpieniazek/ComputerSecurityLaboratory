package pl.pwr.edu.bus.server;

import com.google.common.collect.ImmutableSet;
import com.google.gson.Gson;
import com.sun.javafx.collections.UnmodifiableListSet;

import java.io.Serializable;
import java.util.Collections;

/**
 * Created by rafal on 10.10.16.
 */
public class AuthenticationService {

    public String generateKeys(){
        Gson gson = new Gson();
        ImmutableSet<? extends Serializable> set = ImmutableSet.of("p", 11, "q", 17);
        return gson.toJson(set);
    };
}
