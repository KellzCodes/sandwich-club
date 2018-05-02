package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {
    private static final String TAG = JsonUtils.class.getName();

    /* The sandwich object takes in a JSON string. Six parameters make up one
    * sandwich.
    * This method throws a JSONException
    *
    * Returns a Sandwich object
    * */
    public static Sandwich parseSandwichJson(String json) throws JSONException {
        final String KEY_NAME = "name";
        final String KEY_PLACE_OF_ORIGIN = "placeOfOrigin";
        final String KEY_DESCRIPTION = "description";
        final String KEY_IMAGE = "image";
        final String KEY_MAIN = "mainName";
        final String KEY_INGREDIENTS = "ingredients";
        final String KEY_ALSO = "alsoKnownAs";

        JSONObject sandwich = new JSONObject(json);
        JSONObject name = sandwich.getJSONObject(KEY_NAME);

        String origin = sandwich.getString(KEY_PLACE_OF_ORIGIN);
        String description = sandwich.getString(KEY_DESCRIPTION);
        String image = sandwich.getString(KEY_IMAGE);

        String mainName = name.getString(KEY_MAIN);

        List<String> ingredients = getArrayObject(sandwich.getJSONArray(KEY_INGREDIENTS));
        List<String> alsoKnownAs = getArrayObject(name.getJSONArray(KEY_ALSO));

        return new Sandwich(mainName, alsoKnownAs, origin, description, image, ingredients);
    }

    private static List<String> getArrayObject (JSONArray jsonArray) {
        List<String> list = new ArrayList<>();
        try {
            int arrayLength = jsonArray.length();
            for (int i = 0; i < arrayLength; i++) {
                list.add(jsonArray.getString(i));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }
}