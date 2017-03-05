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

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Chhaileng on 3/4/17.
 */

public class ChangePassword extends AppCompatActivity {
    private EditText etPassword,etRePassword;
    private Button btnChange;
    private TextView tvCancel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password);
        this.setTitle("Change Password");

        etPassword = (EditText) findViewById(R.id.etPassword);
        etRePassword = (EditText) findViewById(R.id.etRePassword);
        btnChange = (Button) findViewById(R.id.btnChange);
        tvCancel = (TextView) findViewById(R.id.tvCancel);

        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!etPassword.getText().toString().equals("") || !etRePassword.getText().toString().equals("")) {
                    if (etPassword.getText().toString().length() >= 6) {
                        if (etPassword.getText().toString().equals(etRePassword.getText().toString())) {
                            String url = "http://api.chhaileng.info/ch_pass.php?key=bong-slanh-oun";
                            StringRequest request = new StringRequest(
                                    Request.Method.POST,
                                    url,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            if (response.contains("changed")) {
                                                DatabaseHandler db = new DatabaseHandler(ChangePassword.this);
                                                User aa = db.getUser(1);
                                                aa.setPassword(Encryption.md5(etRePassword.getText().toString()));
                                                db.updateUser(aa);
                                                onBackPressed();
                                                Toast.makeText(ChangePassword.this, "Password Changed", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    },
                                    new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            Toast.makeText(ChangePassword.this, error.getMessage(), Toast.LENGTH_LONG).show();
                                        }
                                    }
                            ) {
                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError {
                                    DatabaseHandler db = new DatabaseHandler(ChangePassword.this);
                                    User aa = db.getUser(1);
                                    String uid = aa.getUid();
                                    Map<String, String> params = new HashMap<String, String>();
                                    params.put("password", Encryption.md5(etPassword.getText().toString()));
                                    params.put("id", uid);

                                    return params;
                                }
                            };
                            MySingleton.getInstance(ChangePassword.this).addToRequestQueue(request);


                        } else {
                            Toast.makeText(ChangePassword.this, "Password Not match!!", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(ChangePassword.this, "Password must be more than 6 characters length", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(ChangePassword.this, "Enter new password", Toast.LENGTH_SHORT).show();
                }

            }
        });

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ChangePassword.this, "Cancel", Toast.LENGTH_SHORT).show();
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}
