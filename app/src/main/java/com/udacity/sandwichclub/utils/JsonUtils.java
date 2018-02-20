package com.udacity.sandwichclub.utils;

import android.util.Log;
import com.udacity.sandwichclub.model.Sandwich;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonUtils {

    private static final String LOG_TAG = JsonUtils.class.getSimpleName();

    private static final String NAME = "name";
    private static final String MAIN_NAME = "mainName";
    private static final String ALSO_KNOWN_AS = "alsoKnownAs";
    private static final String PLACE_OF_ORIGIN = "placeOfOrigin";
    private static final String DESCRIPTION = "description";
    private static final String IMAGE = "image";
    private static final String INGREDIENTS = "ingredients";

    public static Sandwich parseSandwichJson(String json) {

        Sandwich sandwich = null;
        List<String> alsoKnownAs;
        List<String> ingredients;

        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(json);
            JSONObject name = jsonObject.getJSONObject(NAME);
            String mainName = name.getString(MAIN_NAME);
            JSONArray alsoKnownAsJson = name.getJSONArray(ALSO_KNOWN_AS);
            alsoKnownAs = convertToList(alsoKnownAsJson);
            String placeOfOrigin = jsonObject.getString(PLACE_OF_ORIGIN);
            String description = jsonObject.getString(DESCRIPTION);
            String image = jsonObject.getString(IMAGE);
            JSONArray ingredientsJson = jsonObject.getJSONArray(INGREDIENTS);
            ingredients = convertToList(ingredientsJson);

            sandwich = new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);

        } catch (JSONException e) {
            Log.e(LOG_TAG, "Exception occurred while parsing JSON", e);
    }
        return sandwich;
    }

    private static List<String> convertToList(JSONArray jsonArray) throws JSONException {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            list.add(jsonArray.getString(i));
        }
        return list;
    }
}
