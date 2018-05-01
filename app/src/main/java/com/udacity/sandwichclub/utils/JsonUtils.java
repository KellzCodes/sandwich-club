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

    /*
    * This method takes in a String json parameter
    * Returns a sandwich object
    * throws a JSONException
    * */
    public static Sandwich parseSandwichJson(String jSon) throws JSONException {
        final String KEY_NAME = "name";
        final String KEY_PLACE_OF_ORIGIN = "placeOfOrigin";
        final String KEY_DESCRIPTION = "description";
        final String KEY_IMAGE = "image";
        final String KEY_MAIN = "mainName";
        final String KEY_INGREDIENTS = "ingredients";
        final String KEY_ALSO = "alsoKnownAs";

        JSONObject sandwich = new JSONObject(jSon);

        // Get the name of the JSON object
        JSONObject name = sandwich.getJSONObject(KEY_NAME);

        // Get the origin of the JSON object
        String origin = sandwich.getString(KEY_PLACE_OF_ORIGIN);

        // Get the description of the JSON object
        String description = sandwich.getString(KEY_DESCRIPTION);

        // Get the image of the JSON object
        String image = sandwich.getString(KEY_IMAGE);

        // Get the main name
        String mainName = name.getString(KEY_MAIN);

        List<String> ingredients = getArrayObject(sandwich.getJSONArray(KEY_INGREDIENTS));
        List<String> alsoKnownAs = getArrayObject(name.getJSONArray(KEY_ALSO));

        return new Sandwich(mainName, alsoKnownAs, origin, description, image, ingredients);
    }

    /*
    * This method takes in a JSON array
    * Returns a list
    * */
    private static List<String> getArrayObject(JSONArray array) {
        List<String> list = new ArrayList<>();
        try {
            int x = array.length();
            for (int y = 0; y < x; y++){
                list.add(array.getString(y));
            }
        } catch (JSONException e) {
            Log.e(TAG, "Error", e);
        }

        return list;
    }
}
