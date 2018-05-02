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
    * The Sandwich object takes in 6 parameters:
    *   Name
    *   place of origin
    *   description
    *   image
    *   ingredients
    *   also known as
*
*   This method will parse a string array found in the strings.xml resource file
*   to create the parameters for the sandwich object */
    public static Sandwich parseSandwichJson(String json) throws JSONException {
        final String KEY_NAME = "name";
        final String KEY_PLACE_OF_ORIGIN = "placeOfOrigin";
        final String KEY_DESCRIPTION = "description";
        final String KEY_IMAGE = "image";
        final String KEY_MAIN = "mainName";
        final String KEY_INGREDIENTS = "ingredients";
        final String KEY_ALSO = "alsoKnownAs";

        // Create sandwich json object so we can parse the parameters
        JSONObject sandwich = new JSONObject(json);
        // Create a name json object so we can parse the name parameter
        JSONObject name = sandwich.getJSONObject(KEY_NAME);

        // Parse the name parameter
        String mainName = name.getString(KEY_MAIN);
        // Parse the place of origin parameter
        String origin = sandwich.getString(KEY_PLACE_OF_ORIGIN);
        // Parse the description parameter
        String description = sandwich.getString(KEY_DESCRIPTION);
        // Parse the image url
        String image = sandwich.getString(KEY_IMAGE);

        // The ingredients are stored in a json array. Put the ingredients in a list
        List<String> ingredients = jsonArrayToList(sandwich.getJSONArray(KEY_INGREDIENTS));

        // Put also known as in a list
        List<String> alsoKnownAs = jsonArrayToList(name.getJSONArray(KEY_ALSO));

        // Return a sandwich with all the parsed parameters
        return new Sandwich(mainName, alsoKnownAs, origin, description, image, ingredients);
    }

    // This method goes through a JSONArray and turns it into a list
    private static List<String> jsonArrayToList (JSONArray jsonArray) {
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