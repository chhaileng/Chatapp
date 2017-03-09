package com.neakit.chatapp;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chhaileng on 3/5/17.
 */

public class MyValidator {
    public static List<String> registerdEmail(final Context context) {
        String url ="http://api.chhaileng.info/signin.php?key=java-is-love-20170228";
        final List<String> emailList = new ArrayList<String>();
        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        try {
                            boolean isSignIn = false;
                            for ( int i = 0; i < response.length(); i++ ) {
                                JSONObject obj = response.getJSONObject(i);
                                emailList.add(obj.getString("user_email"));
                                Toast.makeText(context, i + " " + obj.getString("user_name"), Toast.LENGTH_SHORT).show();
                            }
                        } catch ( Exception ex ) {
                            //Toast.makeText(Login.this, "What?", Toast.LENGTH_SHORT).show();
                            ex.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //e
                    }
                }
        );
        MySingleton.getInstance(context).addToRequestQueue(request);
        return emailList;
    }
}
