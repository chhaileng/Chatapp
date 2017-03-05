package com.neakit.chatapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Chhaileng on 2/27/17.
 */

public class SettingFragment extends Fragment {


    private static String[] items = {"Change Password",
                                     "About",
                                     "Logout",
                                     "Developer Area"};
    ListView lstSetting;
    private TextView tvProfileName;

    String defaultName = "Chhaileng";




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.setting_fragment, container,false);

        DatabaseHandler db = new DatabaseHandler(getContext());
        User a = db.getUser(1);
        defaultName = a.getName();

        getActivity().setTitle("Setting");

        tvProfileName = (TextView) v.findViewById(R.id.tvProfileName);
        tvProfileName.setText(defaultName);



        lstSetting = (ListView) v.findViewById(R.id.lstView);

        lstSetting.setAdapter(new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,items));

        lstSetting.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (position == 0) {
                    final AlertDialog.Builder changePw = new AlertDialog.Builder(getContext());
                    changePw.setTitle("Enter your old password");
                    final EditText old = new EditText(getContext());
                    old.setHint("Old Password");
                    old.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    old.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    changePw.setView(old);

                    changePw.setPositiveButton("Next", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            DatabaseHandler db = new DatabaseHandler(getContext());
                            User aa = db.getUser(1);
                            if (Encryption.md5(old.getText().toString()).equals(aa.getPassword())) {
                                Intent intent = new Intent(getContext(),ChangePassword.class);
                                startActivity(intent);
                            }
                            else {
                                Toast.makeText(getContext(), "Incorrect Password", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                    changePw.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public  void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(getContext(), "Password is not changed", Toast.LENGTH_SHORT).show();
                        }
                    });

                    changePw.show();
                }
                else if (position == 1) {
                    Intent intent = new Intent(getContext(),About.class);
                    startActivity(intent);
                }
                else if (position == 2) {
                    Toast.makeText(getContext(), "Logout", Toast.LENGTH_SHORT).show();
                    DatabaseHandler db = new DatabaseHandler(getContext());
                    List<User> dd = db.getAllUsers();
                    for (User cn : dd) {
                        if (cn.getID() == 1) {
                            db.deleteUser(cn);
                        }
                    }

                    Intent intent = new Intent(getContext(),Login.class);
                    startActivity(intent);
                }
                else if (position == 3) {
                    AlertDialog.Builder askAuth = new AlertDialog.Builder(getContext());
                    askAuth.setTitle("Enter Developer Password");
                    final EditText pw = new EditText(getContext());
                    pw.setHint("Password");
                    pw.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    pw.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    askAuth.setView(pw);

                    askAuth.setPositiveButton("Enter", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if (Encryption.md5(pw.getText().toString()).equals("2a16a013045dae9a66401eb607faf1c6")){
                                Intent intent = new Intent(getContext(),DeveloperArea.class);
                                startActivity(intent);
                            }
                            else
                                Toast.makeText(getContext(), "Developer Password Incorrect", Toast.LENGTH_SHORT).show();
                        }
                    });

                    askAuth.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public  void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });

                    askAuth.show();
                }
            }
        });
        return v;
    }
    // end override method
}
