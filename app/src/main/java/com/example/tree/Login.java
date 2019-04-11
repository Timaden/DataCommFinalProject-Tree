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

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    EditText emailInput;
    EditText passwordInput;
    TextView forgotPassword;
    ProgressBar progressBar;
    TextView loginFailed;
    TextView registerMe;
    Button login;
    FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
  auth = FirebaseAuth.getInstance();
       if(auth.getCurrentUser() != null){
            //Setting the app user
            User user = new User(auth.getCurrentUser().getDisplayName() , auth.getCurrentUser().getEmail() ,"");
            UserInfo.user = user;
            startActivity(new Intent(getApplicationContext() , Leaves.class));
            finish();
        }
        emailInput = findViewById(R.id.lEmailInput);
        passwordInput = findViewById(R.id.lPasswordInput);
        forgotPassword = findViewById(R.id.forgotPasswordLabel);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        loginFailed = findViewById(R.id.loginFailed);


        //Setting the onclick listeners for login and don;t have an account.
        registerMe = findViewById(R.id.noAcctLabel);
        login = findViewById(R.id.login);

        registerMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Register.class));
            }
        });

        auth = FirebaseAuth.getInstance();


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                final String email = emailInput.getText().toString();
                final String password = passwordInput.getText().toString();

                if(email.isEmpty() || !email.contains("@") || !email.contains(".")){
                    TextView t = findViewById(R.id.lEmailLabel);
                    t.setTextColor(Color.RED);
                    Toast.makeText(Login.this, "Email is invalid", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(password.isEmpty() || password.length() < 6){
                    TextView t = findViewById(R.id.lPasswordLabel);
                    t.setTextColor(Color.RED);
                    Toast.makeText(Login.this, "Something is wrong with your password", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                auth = FirebaseAuth.getInstance();
                auth.signInWithEmailAndPassword(email , password).addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        progressBar.setVisibility(View.GONE);
                        if(!task.isSuccessful()){
                                Toast.makeText(Login.this, "Login Failed", Toast.LENGTH_LONG).show();
                                TextView t = findViewById(R.id.loginFailed);
                                t.setTextColor(Color.RED);
                                    t.setText("There is no account with these credentials.\n Please try again!" + "\n"  + email + ": " + password);
                                t.setVisibility(View.VISIBLE);
                            }else{
                            User user = new User(auth.getCurrentUser().getDisplayName() , email , password);
                            UserInfo.user = user;
                            startActivity(new Intent(getApplicationContext() , Leaves.class));
                        }
                        }

                });

            }
        });

        //Onclick listener for forgot your password
forgotPassword.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        String email = emailInput.getText().toString();
        if(email.isEmpty() || !email.contains("@") || !email.contains(".")){
            TextView t = findViewById(R.id.lEmailLabel);
            t.setTextColor(Color.RED);
            Toast.makeText(Login.this, "Email is invalid", Toast.LENGTH_SHORT).show();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    loginFailed.setTextColor(Color.GREEN);
                    loginFailed.setText("A link to reset your password has been sent to your email.");
                }else{
                    loginFailed.setTextColor(Color.RED);
                    loginFailed.setText("Failed to send password. Please check your email and try again");
                }
            }
        });
    }
});

    }
}
