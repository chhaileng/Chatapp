package com.neakit.chatapp;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Chhaileng on 3/2/17.
 */

public class DeveloperArea extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.developerarea);


        final TextView textContact = (TextView) findViewById(R.id.textContact);
        final EditText etName = (EditText) findViewById(R.id.etName);
        final EditText etEmail = (EditText) findViewById(R.id.etEmail);
        Button btnDelete = (Button) findViewById(R.id.btnDelete);
        Button btnUpdate = (Button) findViewById(R.id.btnUpdate);

        final DatabaseHandler db = new DatabaseHandler(this);

        List<User> aa = db.getAllUsers();
        textContact.setText("Local Database Viewer\n");

        for (User cn : aa) {
            textContact.append("No: " + cn.getID()+"\n");
            textContact.append("ID: " + cn.getUid()+"\n");
            textContact.append("Name: " + cn.getName()+"\n");
            textContact.append("Email: " + cn.getEmail()+"\n");
            textContact.append("Pass: " + cn.getPassword()+"\n");
            textContact.append("-------------------------------------------------------\n");


            String log = "Id: "+cn.getID()+" ,Name: " + cn.getName() + " ,Phone: " + cn.getEmail();
            Log.d("Data from db >>>> ", log);
        }

        Toast.makeText(this, "" + countUser(), Toast.LENGTH_LONG).show();

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<User> dd = db.getAllUsers();
                //textContact.setText("");
                for (User cn : dd) {
                    if (cn.getID() == Integer.parseInt(etName.getText().toString())) {
                        db.deleteUser(cn);
                    }
                }
                List<User> users = db.getAllUsers();
                textContact.setText("Local Database Viewer\n");
                for (User cn : users) {
                    textContact.append("No: " + cn.getID()+"\n");
                    textContact.append("ID: " + cn.getUid()+"\n");
                    textContact.append("Name: " + cn.getName()+"\n");
                    textContact.append("Email: " + cn.getEmail()+"\n");
                    textContact.append("Pass: " + cn.getPassword()+"\n");
                    textContact.append("-------------------------------------------------------\n");
                }
                etName.setText("");
                etEmail.setText("");

            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<User> users = db.getAllUsers();
                textContact.setText("Local Database Viewer\n");

                db.updateUser(new User(1,"1",etName.getText().toString(),etEmail.getText().toString(),"NewP@$sW0rD"));


                List<User> aa = db.getAllUsers();
                for (User cn : aa) {
                    textContact.append("No: " + cn.getID()+"\n");
                    textContact.append("ID: " + cn.getUid()+"\n");
                    textContact.append("Name: " + cn.getName()+"\n");
                    textContact.append("Email: " + cn.getEmail()+"\n");
                    textContact.append("Pass: " + cn.getPassword()+"\n");
                    textContact.append("-------------------------------------------------------\n");
                }

                etName.setText("");
                etEmail.setText("");

            }
        });

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
