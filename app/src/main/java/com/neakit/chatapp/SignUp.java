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
import com.android.volley.toolbox.StringRequest;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Chhaileng on 2/28/17.
 */

public class SignUp extends AppCompatActivity {


    private EditText etUsername, etEmail, etPassword, etRePassword;
    private TextView tvLogin;
    private Button btnSignUp;

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

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etPassword.getText().toString().length() >= 6) {
                    if (etPassword.getText().toString().equals(etRePassword.getText().toString())) {
//                        Toast.makeText(SignUp.this, "GOGOG", Toast.LENGTH_SHORT).show();
                        String url = "http://api.chhaileng.info/signup.php?key=javaproject-chat23L2JLJ32JG39H2";

                        StringRequest request = new StringRequest(
                                Request.Method.POST,
                                url,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        // Toast.makeText(SignUp.this, response + "", Toast.LENGTH_LONG).show();
                                        if (response.contains("success")){
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
                        ){
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> params = new HashMap<String, String>();
                                params.put("username",etUsername.getText().toString());
                                params.put("email",etEmail.getText().toString());
                                params.put("password",Encryption.md5(etPassword.getText().toString()));

                                return params;
                            }
                        };


                        MySingleton.getInstance(SignUp.this).addToRequestQueue(request);


                    }
                    else {
                        Toast.makeText(SignUp.this, "Password Not match!!", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(SignUp.this, "Password must be more than 6 characters length", Toast.LENGTH_SHORT).show();
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


}// end of class
