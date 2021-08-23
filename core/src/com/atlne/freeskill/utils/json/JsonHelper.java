package com.atlne.freeskill.utils.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;

public class JsonHelper {

    private transient Gson gson = new GsonBuilder()
            .serializeNulls()
            .setPrettyPrinting()
            .enableComplexMapKeySerialization()
            .create();

    public String serialise(Object object, Class<?> type) {
        return gson.toJson(object, type);
    }

    public <T> T deserialise(String json, Type type) {
        return gson.fromJson(json, type);
    }
}
