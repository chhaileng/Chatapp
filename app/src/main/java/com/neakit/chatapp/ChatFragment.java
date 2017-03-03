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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Chhaileng on 2/27/17.
 */

public class ChatFragment extends Fragment {



    private Button btnAddRoom;
    private EditText etRoomName;

    private ListView lstView;
    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<String> list_of_rooms = new ArrayList<>();

    private String name = "Chhaileng";



    //name = getIntent().getExtras().get("user_name").toString();

    private DatabaseReference root = FirebaseDatabase.getInstance().getReference().getRoot();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.chat_fragment, container,false);

        getActivity().setTitle("Public Chat Group");


        if (countUser()==0){
            Intent intent = new Intent(getContext(),Login.class);
            startActivity(intent);
        }


        btnAddRoom = (Button) v.findViewById(R.id.btnAddRoom);
        etRoomName = (EditText) v.findViewById(R.id.etRoomName);
        lstView = (ListView) v.findViewById(R.id.lstView);

        arrayAdapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,list_of_rooms);

        lstView.setAdapter(arrayAdapter);



        //create chat room
        btnAddRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!etRoomName.getText().toString().equals("")) {
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put(etRoomName.getText().toString(), "");
                    root.updateChildren(map);
                    etRoomName.setText("");
                }
                else {
                    Toast.makeText(getContext(), "Enter group name!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        etRoomName.setText("");

        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Set<String> set = new HashSet<String>();
                Iterator i = dataSnapshot.getChildren().iterator();

                while (i.hasNext()) {
                    set.add(((DataSnapshot)i.next()).getKey());
                }
                list_of_rooms.clear();
                list_of_rooms.addAll(set);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        lstView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getContext(),ChatRoom.class);
                intent.putExtra("room_name",((TextView)view).getText().toString());
                intent.putExtra("user_name",name);
                startActivity(intent);
            }
        });



        return v;
    }
    public int countUser(){
        DatabaseHandler db = new DatabaseHandler(getContext());
        List<User> aa = db.getAllUsers();
        int i = 0;
        for (User cn : aa) {
            i++;
        }
        return i;
    }
}
