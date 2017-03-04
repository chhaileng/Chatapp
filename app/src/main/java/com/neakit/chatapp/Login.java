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

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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

        this.setTitle("Sign In");

        tvCreateNewAccount = (TextView)findViewById(R.id.tvCreateNewAccount);
        etEmail = (EditText)findViewById(R.id.etEmail);
        etPassword = (EditText)findViewById(R.id.etPassword);
        btnSignIn = (Button)findViewById(R.id.btnSignUp);

        final DatabaseHandler db = new DatabaseHandler(this);

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

        }

        // Developer account Default

//        etEmail.setText("a");
//        etPassword.setText("a");

        // End of Developer

        final String url = "http://api.chhaileng.info/signin.php?key=java-is-love-20170228";

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
                                                && md5(etPassword.getText().toString()).equals(obj.getString("user_password"))) {


                                            Intent intent = new Intent(Login.this,MainActivity.class);
                                            intent.putExtra("user_name",obj.getString("user_name"));
                                            startActivity(intent);

                                            //startActivity(new Intent(Login.this, MainActivity.class));
                                            Toast.makeText(Login.this, "Welcome "+obj.getString("user_name"), Toast.LENGTH_LONG).show();
                                            isSignIn = true;

                                                // add new
                                                db.addUser(new User(obj.getString("user_id"),
                                                                    obj.getString("user_name"),
                                                                    obj.getString("user_email"),
                                                                    etPassword.getText().toString()));


                                            break;
                                        }
                                    }
                                    if (isSignIn == false){
                                        Toast.makeText(Login.this, "Incorrect Email or Password", Toast.LENGTH_SHORT).show();
                                    }

                                } catch ( Exception ex ) {
                                    Toast.makeText(Login.this, "What?", Toast.LENGTH_SHORT).show();
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

        tvCreateNewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, SignUp.class) );
                //Toast.makeText(Login.this, "Siup", Toast.LENGTH_SHORT).show();
            }
        });


    }
    // end onCreate method


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//***Change Here***
        startActivity(intent);
        finish();
        System.exit(0);
    }

    public static final String md5(final String s) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest
                    .getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
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
