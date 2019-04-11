package com.example.tree;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Register extends AppCompatActivity {

    private TextView hasAcct;
    private EditText userNameInput;
    private EditText userEmailInput;
    private EditText userPasswordInput;
    private Button register;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private ProgressBar progressBar;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        auth = FirebaseAuth.getInstance();
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        hasAcct = findViewById(R.id.acctLabel);
        register = findViewById(R.id.register);
        hasAcct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext() , Login.class));
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String userName;
                final String email;
                final String password;

                //Regex pattern
                Pattern p = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
                Matcher m;

                userNameInput = findViewById(R.id.rUserNameInput);
                userEmailInput =  findViewById(R.id.rEmailInput);
                userPasswordInput = findViewById(R.id.rPasswordInput);
                userName = userNameInput.getText().toString();
                email = userEmailInput.getText().toString();
                password = userPasswordInput.getText().toString();

                //First check username
                m = p.matcher(userName);
                boolean invalid = m.find();
                if(invalid || userName.equals("") || userName.length() < 5){
                    TextView t = (TextView)findViewById(R.id.rUserNameLabel);
                    t.setTextColor(Color.RED);
                    Toast.makeText(getApplicationContext(), "Only use letters and numbers for username", Toast.LENGTH_SHORT).show();
                    return;
                }

                m = p.matcher(email);
                //invalid = m.find();

                if(email.equals("") || !email.contains("@") || !email.contains(".")){
                    TextView t = (TextView)findViewById(R.id.rEmailLabel);
                    t.setTextColor(Color.RED);
                    Toast.makeText(getApplicationContext(), "Invalid email", Toast.LENGTH_SHORT).show();
                    return;
                }

                m = p.matcher(password);
                invalid = m.find();

                if(password.length() < 6){
                    TextView t = (TextView)findViewById(R.id.rPasswordLabel);
                    t.setTextColor(Color.RED);
                    Toast.makeText(getApplicationContext(), "Invalid Password", Toast.LENGTH_SHORT).show();
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);

                //Creating the new user
                auth.createUserWithEmailAndPassword(email , password).addOnCompleteListener(Register.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful()){
                            Toast.makeText(Register.this , "Registration Failed" , Toast.LENGTH_LONG).show();

                        }else{
                            User u = new User(userName , email , password);
                            Leaf you = new Leaf(userName , email , password);

                            UserInfo.user = u;
                            user = auth.getCurrentUser();
                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(userName).build();
                            user.updateProfile(profileUpdates);

                            //Add the user to the database
                            mDatabase.child("users").child(UIDGenerator.generateUID()).setValue(user);
                            mDatabase.child("leaves").child(UIDGenerator.generateUID()).setValue(you);

                            user.sendEmailVerification().addOnCompleteListener( Register.this, new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(Register.this , "Verify your email!" , Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(Register.this , Login.class));
                                    }else{
                                        Toast.makeText(Register.this , "Verification email failed to send. Please check your email and try again." , Toast.LENGTH_LONG).show();
                                    }
                                }
                            });



                        }
                    }
                });





            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }
}
