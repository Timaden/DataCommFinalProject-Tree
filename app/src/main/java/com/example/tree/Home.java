package com.example.tree;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class Home extends AppCompatActivity {
    private FirebaseAuth auth;
    private Button signOutBtn;
    private Button send;
    LinearLayout layout;
    ScrollView scrollView;
    private DatabaseReference mDatabase , mDatabase2;
    private ArrayList<Message> messagesList;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    static String room = "privatechatrooms/";
    static String room2 = "privatechatrooms/";
    private FirebaseAuth.AuthStateListener authListener;
    private TextView chatRoomTitle;
    private ImageButton leavesBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        recyclerView = findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        chatRoomTitle = findViewById(R.id.chatRoomNameView);
        chatRoomTitle.setText(UserInfo.otherUserName);
        auth = FirebaseAuth.getInstance();
        scrollView = findViewById(R.id.scrollView);
        send = findViewById(R.id.send);
        signOutBtn = findViewById(R.id.signoutBtn);
        messagesList = new ArrayList<Message>();
        Toast.makeText(this , "loading messages..." , Toast.LENGTH_LONG).show();
        mDatabase = FirebaseDatabase.getInstance().getReference(room);
        mDatabase2 = FirebaseDatabase.getInstance().getReference(room2);
        leavesBtn = findViewById(R.id.leavesBtn);


        //Onclick for signout button
        leavesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext() , Leaves.class));
            }
        });

        //Single time load of all messages in User Current room
            mDatabase.child(room).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        Message m = child.getValue(Message.class);
                        messagesList.add(m);

                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        //Create the adapter after loading in messages
        mAdapter = new MessagesAdapter(messagesList);
        recyclerView.setAdapter(mAdapter);

        //Live load of messages on change on current user side
            mDatabase.addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                   //New found messages
                    ArrayList<Message> newMsgs = new ArrayList<Message>();
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        Message m = child.getValue(Message.class);
                        newMsgs.add(m);

                    }

                    //Adding the new messages to the messages list
                    for (int i = messagesList.size(); i < newMsgs.size(); i++) {
                        messagesList.add(newMsgs.get(i));
                    }
                mAdapter.notifyDataSetChanged();
                    //recyclerView.setAdapter(mAdapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            //Live load of messages changes on user being chatted with
            mDatabase2.addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    ArrayList<Message> newMsgs = new ArrayList<Message>();
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        Message m = child.getValue(Message.class);
                        newMsgs.add(m);

                    }

                    for (int i = messagesList.size(); i < newMsgs.size(); i++) {
                        messagesList.add(newMsgs.get(i));
                    }
                    //recyclerView.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        //Checking authentication
        if (auth.getCurrentUser() == null) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        } else {
            Toast.makeText(Home.this, "Welcome! " + auth.getCurrentUser().getDisplayName(), Toast.LENGTH_LONG).show();
        }
        //Load in data
        signOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
            }
        });
        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    startActivity(new Intent(Home.this, MainActivity.class));
                    finish();
                }
            }
        };

        //Sending the message
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText input = findViewById(R.id.userMsg);
                if (!input.getText().toString().equals("")) {
                    //Map<String , String> map = new HashMap<String , String>();
                    String message = input.getText().toString();
                    String userName = auth.getCurrentUser().getDisplayName();

                    Message m = new Message(userName, message);
                    mDatabase.push().setValue(m);
                    mDatabase2.push().setValue(m);

                    input.setText("");
                }
            }
        });


    }

    private void addMessageBox(String message, int type) {
        TextView textView = new TextView(Home.this);

        textView.setText(message);
        textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        RelativeLayout.LayoutParams lp1 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp2.weight = 1.0f;

        if (type == 1) {
            //lp1.addRule(RelativeLayout.CENTER_VERTICAL);
            lp1.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            lp1.leftMargin = 25;
            //lp2.gravity = Gravity.LEFT;
            textView.setBackgroundResource(R.drawable.left_wood_btn);
        } else {
            //lp1.addRule(RelativeLayout.CENTER_VERTICAL);
            lp1.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            lp1.rightMargin = 25;
            //lp2.gravity = Gravity.RIGHT;
            textView.setBackgroundResource(R.drawable.right_wood_btn);
        }
        textView.setLayoutParams(lp2);
        layout.addView(textView);
        scrollView.fullScroll(View.FOCUS_DOWN);
    }


    @Override
    public void onStart() {
        super.onStart();
        auth.addAuthStateListener(authListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (authListener != null) {
            auth.removeAuthStateListener(authListener);
        }
    }


    public String generateRandomColor() {
        String color;
        String[] range = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};
        String c0 = range[(int) (Math.random() * range.length)];
        String c1 = range[(int) (Math.random() * range.length)];
        String c2 = range[(int) (Math.random() * range.length)];
        String c3 = range[(int) (Math.random() * range.length)];
        String c4 = range[(int) (Math.random() * range.length)];
        String c5 = range[(int) (Math.random() * range.length)];
        String c6 = range[(int) (Math.random() * range.length)];
        String c7 = range[(int) (Math.random() * range.length)];

        color = "#" + c0 + c1 + c2 + c3 + c4 + c5 + c6 + c7;
        return color;
    }
}