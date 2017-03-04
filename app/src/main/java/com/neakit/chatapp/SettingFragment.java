package com.neakit.chatapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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

//                if (position == 0) {
//                    AlertDialog.Builder askProfile = new AlertDialog.Builder(getContext());
//                    askProfile.setTitle("Enter new name: ");
//
//                    final EditText profile = new EditText(getContext());
//                    profile.setText(tvProfileName.getText().toString());
//                    askProfile.setView(profile);
//
//                    askProfile.setPositiveButton("Change", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialogInterface, int i) {
//                            tvProfileName.setText(profile.getText().toString());
//                            defaultName = profile.getText().toString();
//
//                        }
//                    });
//
//                    askProfile.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                        @Override
//                        public  void onClick(DialogInterface dialogInterface, int i) {
//                            // bleh
//                        }
//                    });
//
//                    askProfile.show();
//
//                }
                if (position == 0) {
                    Toast.makeText(getContext(), "Change Password", Toast.LENGTH_SHORT).show();
                }
                else if (position == 1) {
                    //Toast.makeText(getContext(), "About", Toast.LENGTH_SHORT).show();
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
                    Intent intent = new Intent(getContext(),DeveloperArea.class);
                    startActivity(intent);
                }


            }
        });


        return v;
    }
}
