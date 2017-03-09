package com.neakit.chatapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Chhaileng on 2/28/17.
 */

public class SignUp extends AppCompatActivity {


    private EditText etUsername, etEmail, etPassword, etRePassword;
    private TextView tvLogin;
    private Button btnSignUp;
    public List<String> registeredEmail = new ArrayList<String>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        this.setTitle("SignUp");

        etUsername = (EditText) findViewById(R.id.etUsername);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etRePassword = (EditText) findViewById(R.id.etRePassword);

        tvLogin = (TextView) findViewById(R.id.tvLogin);

        btnSignUp = (Button) findViewById(R.id.btnSignUp);

        JsonArrayRequest reqst = new JsonArrayRequest(
                Request.Method.GET,
                "http://api.chhaileng.info/signin.php?key=java-is-love-20170228",
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            registeredEmail.clear();
                            for ( int i = 0; i < response.length(); i++ ) {
                                JSONObject obj = response.getJSONObject(i);
                                registeredEmail.add(obj.getString("user_email"));

                            }
                        } catch ( Exception ex ) {
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
        MySingleton.getInstance(SignUp.this).addToRequestQueue(reqst);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // check email
                boolean exist = false;
                for (String mail : registeredEmail) {
                    if (mail.equals(etEmail.getText().toString())){
                        // Toast.makeText(SignUp.this, "Email is exist", Toast.LENGTH_SHORT).show();
                        exist = true;
                        break;

                    }
                }

                if (exist == false) {
                    if (etPassword.getText().toString().length() >= 6) {
                        if (etPassword.getText().toString().equals(etRePassword.getText().toString())) {
                            String url = "http://api.chhaileng.info/signup.php?key=javaproject-chat23L2JLJ32JG39H2";

                            StringRequest request = new StringRequest(
                                    Request.Method.POST,
                                    url,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            // Toast.makeText(SignUp.this, response + "", Toast.LENGTH_LONG).show();
                                            if (response.contains("success")) {
                                                // startActivity(new Intent(SignUp.this,Login.class));
                                                onBackPressed();
                                                Toast.makeText(SignUp.this, "Success", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    },
                                    new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            Toast.makeText(SignUp.this, error.getMessage(), Toast.LENGTH_LONG).show();
                                        }
                                    }
                            ) {
                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError {
                                    Map<String, String> params = new HashMap<String, String>();
                                    params.put("username", etUsername.getText().toString());
                                    params.put("email", etEmail.getText().toString());
                                    params.put("password", Encryption.md5(etPassword.getText().toString()));

                                    return params;
                                }
                            };
                            MySingleton.getInstance(SignUp.this).addToRequestQueue(request);
                        } else {
                            Toast.makeText(SignUp.this, "Password Not match!!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(SignUp.this, "Password must be more than 6 characters length", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(SignUp.this, "Email Address is already registered.", Toast.LENGTH_SHORT).show();
                }
            }
        });



        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // startActivity(new Intent(SignUp.this, Login.class) );
                onBackPressed();
            }
        });

    }
    //end of onCreate method


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


}// end of class
