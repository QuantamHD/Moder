package com.moderco.utility;

import android.util.Log;

import org.apache.http.HttpResponse;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Flaque on 2/1/15.
 */
public class JsonReader {

    public static JSONObject parse(HttpResponse response, boolean log) {
        //Parse the JSON
        BufferedReader reader;
        try {
            reader = new BufferedReader(new InputStreamReader(
                    response.getEntity().getContent(), "UTF-8"));
            StringBuilder builder = new StringBuilder();
            for (String line = null; (line = reader.readLine()) != null; ) {
                builder.append(line).append("\n");
            }

            if (log) Log.d("ServerResponse", builder.toString());

            JSONObject jObject = new JSONObject(builder.toString());
            return jObject;
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return null; //it went wrong - this is generally bad.
    }

    public static int getCode(HttpResponse response, boolean log) {
        JSONObject jObject = parse(response, log);
        try {
            return jObject.getInt("ResponseCode"); //Assigns code
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return -1; //Problems
    }


}
