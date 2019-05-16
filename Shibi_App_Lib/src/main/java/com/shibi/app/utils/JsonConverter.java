package com.shibi.app.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import java.util.List;

/**
 * Created by User on 06/06/2018.
 */
public class JsonConverter {

    public static <T> T getElement(String value, Class<T> clazz) {
        return new Gson().fromJson(value, clazz);
    }

    public static <T> T[] getElements(String value, Class<T[]> clazz) {
        return new Gson().fromJson(value, clazz);
    }

    public static <T> List<T> getList(String value, Class<List<T>> clazz) {
        return new Gson().fromJson(value, clazz);
    }

    public static <T> String getJson(T element) {
        return new Gson().toJson(element);
    }

    public static <T> String getJsonRecursive(T element) {
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString;
        try {
            jsonInString = mapper.writeValueAsString(element);
            return jsonInString;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
}
