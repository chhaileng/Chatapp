package com.neakit.chatapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Chhaileng on 2/28/17.
 */

public class Login extends AppCompatActivity {

    private final String LOG = "SignIn";

    private TextView tvCreateNewAccount;
    private EditText etEmail, etPassword;
    private Button btnSignIn;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        this.setTitle("SignIn");

        tvCreateNewAccount = (TextView)findViewById(R.id.tvCreateNewAccount);
        etEmail = (EditText)findViewById(R.id.etEmail);
        etPassword = (EditText)findViewById(R.id.etPassword);
        btnSignIn = (Button)findViewById(R.id.btnSignUp);

        final DatabaseHandler db = new DatabaseHandler(this);

        // check user in local database
        if (countUser() == 1) {
            List<User> aa = db.getAllUsers();
            String user="";
            for (User a : aa) {
                user = a.getName();
                break;
            }

            Intent intent = new Intent(Login.this,MainActivity.class);
            startActivity(intent);
            Toast.makeText(this, "Welcome "+ user, Toast.LENGTH_SHORT).show();

            //Intent intent = new Intent(Login.this,DeveloperArea.class);
            //startActivity(intent);


        }


        final String url = "http://api.chhaileng.info/signin.php?key=java-is-love-20170228";

        // Login button press
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                                        if (etEmail.getText().toString().equals(obj.getString("user_email"))
                                                && Encryption.md5(etPassword.getText().toString()).equals(obj.getString("user_password"))) {

                                            Intent intent = new Intent(Login.this,MainActivity.class);
                                            intent.putExtra("user_name",obj.getString("user_name"));
                                            startActivity(intent);

                                            Toast.makeText(Login.this, "Welcome "+obj.getString("user_name"), Toast.LENGTH_LONG).show();
                                            isSignIn = true;

                                                // save session to local db
                                                db.addUser(new User(obj.getString("user_id"),
                                                                    obj.getString("user_name"),
                                                                    obj.getString("user_email"),
                                                                    obj.getString("user_password")));
                                            break;
                                        }
                                    }
                                    if (isSignIn == false){
                                        Toast.makeText(Login.this, "Incorrect Email or Password", Toast.LENGTH_SHORT).show();
                                        etPassword.setText("");
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

                            }
                        }
                );
                MySingleton.getInstance(Login.this).addToRequestQueue(request);
            }
        });

        // Sign up click
        tvCreateNewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, SignUp.class) );
            }
        });
    }
    // end onCreate method


    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    public int countUser(){
        DatabaseHandler db = new DatabaseHandler(this);
        List<User> aa = db.getAllUsers();
        int i = 0;
        for (User cn : aa) {
            i++;
        }
        return i;
    }
}
