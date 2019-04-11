package com.example.tree;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class Leaves extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private DatabaseReference mDatabase;
    private ArrayList<Leaf> usersList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaves);

        recyclerView = findViewById(R.id.usersList);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        usersList = new ArrayList<Leaf>();

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("leaves").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Leaf u = child.getValue(Leaf.class);
                    if(!u.getName().equals(UserInfo.user.getName()) && !usersList.contains(u)) {
                        usersList.add(u);
                    }

                }

                mAdapter = new UsersAdapter(usersList);
                recyclerView.setAdapter(mAdapter);
               /* for(Message m: messagesList){
                    if (m.getUserName().equals(UserInfo.user.getName())){
                        addMessageBox("You: \n" + m.getMessage() , 1);
                    }else{
                        addMessageBox(m.getUserName() + " \n" + m.getMessage() , 2);
                    }
                }*/
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        mDatabase.child("leaves").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<Leaf> newUsers = new ArrayList<Leaf>();
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Leaf m = child.getValue(Leaf.class);
                    newUsers.add(m);
                }
                for (int i = usersList.size(); i < newUsers.size(); i++) {
                    if(!usersList.contains(newUsers.get(i)) && !newUsers.get(i).getName().equals(UserInfo.user.getName())) {
                        usersList.add(newUsers.get(i));
                    }
                }
                mAdapter.notifyDataSetChanged();

               /* for(Message m: messagesList){
                    if (m.getUserName().equals(UserInfo.user.getName())){
                        addMessageBox("You: \n" + m.getMessage() , 1);
                    }else{
                        addMessageBox(m.getUserName() + " \n" + m.getMessage() , 2);
                    }
                }*/
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
