package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    private static final String DESCRIPTION = "description";
    private static final String NAME = "name";
    private static final String MAIN_NAME = "mainName";
    private static final String ALSO_KOWN_AS = "alsoKnownAs";
    private static final String PLACE_OF_ORIGIN = "placeOfOrigin";
    private static final String IMAGE = "image";
    private static final String INGREDIENTS = "ingredients";

    public static Sandwich parseSandwichJson(String json) {
        Sandwich result = new Sandwich();

        try {
            JSONObject object = new JSONObject(json);
            JSONObject nameJsonObject = object.getJSONObject(NAME);
            result.setMainName(nameJsonObject.getString(MAIN_NAME));
            JSONArray alsoKnownAs = nameJsonObject.getJSONArray(ALSO_KOWN_AS);
            result.setAlsoKnownAs(getList(alsoKnownAs));
            JSONArray ingredients = object.getJSONArray(INGREDIENTS);
            result.setIngredients(getList(ingredients));
            result.setDescription(object.getString(DESCRIPTION));
            result.setPlaceOfOrigin(object.getString(PLACE_OF_ORIGIN));
            result.setImage(object.getString(IMAGE));
            result.setDescription(object.getString(DESCRIPTION));

        } catch (JSONException e) {
            e.printStackTrace();
        }


        return result;
    }

    private static List<String> getList(JSONArray jsonArray) throws JSONException {
        int count = 0;
        List<String> list = new ArrayList<>(jsonArray.length());
        while (count < jsonArray.length()) {
            list.add(jsonArray.getString(count));
            count++;
        }
        return list;
    }
}
