package com.example.trivia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    private Button loginButton;
    private Button createAcc;
    private EditText loginemail;
    private EditText loginpassword;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button button = (Button) findViewById(R.id.loginButton);
        loginemail = findViewById(R.id.emaillogin);
        loginpassword = findViewById(R.id.loginpassword);

        mAuth = FirebaseAuth.getInstance();




        Button button2 = (Button) findViewById(R.id.createaccountbutton);
        this.createAcc = button;
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Login.this.startActivity(new Intent(Login.this, createAccount.class));
            }
        });

        button.setOnClickListener(v -> {
            loginUser();
        });
    }

    private void loginUser() {
        String email = loginemail.getText().toString();
        String password = loginpassword.getText().toString();

        if(TextUtils.isEmpty(email))
        {
            loginemail.setError("Email connot be empty");
            loginemail.requestFocus();

        }
        else if(TextUtils.isEmpty(password))
        {
            loginpassword.setError("Enter password");
            loginpassword.requestFocus();

        }
        else
            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if(task.isSuccessful())
                    {
                        Toast.makeText(Login.this,"Login Successful",Toast.LENGTH_LONG).show();
                        startActivity(new Intent(Login.this,OptionActivity.class));
                    }
                    else
                    {
                        Toast.makeText(Login.this,"Login Error",Toast.LENGTH_LONG).show();
                    }

                }
            });
    }
}