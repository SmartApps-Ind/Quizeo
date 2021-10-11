package com.example.trivia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class createAccount extends AppCompatActivity implements View.OnClickListener {

    private TextView createaccount , createaccountButton;
    private EditText username , password;
    private AutoCompleteTextView email;
    private ProgressBar progressBar;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        mAuth = FirebaseAuth.getInstance();

        createaccountButton = (Button) findViewById(R.id.createaccountbutton);
        createaccountButton.setOnClickListener(this);

        username = findViewById(R.id.acc_username);
        password = findViewById(R.id.acc_loginpassword);
        email = findViewById(R.id.ac_emaillogin);
        progressBar = findViewById(R.id.ac_loginprogressbar);



    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.createaccountbutton:
                registerUser();
                break;
        }

    }

    private void registerUser() {

        String emailid = email.getText().toString().trim();
        String user = username.getText().toString().trim();
        String pass = password.getText().toString().trim();

        if(emailid.isEmpty())
        {
            email.setError("Email Required");
            email.requestFocus();
            return;
        }

        if(user.isEmpty())
        {
            username.setError("Name Required");
            username.requestFocus();
            return;
        }

        if(pass.isEmpty())
        {
            password.setError("Passwoed Required");
            password.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(emailid).matches())
        {
            email.setError("Email not valid");
            email.requestFocus();
            return;

        }

        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(emailid,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful())
                {
                    User user1 = new User(user,emailid);
                    FirebaseDatabase.getInstance().getReference("Users")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(user1).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(createAccount.this,"user created", Toast.LENGTH_LONG).show();
                                progressBar.setVisibility(View.GONE);
                            }
                            else{
                                Toast.makeText(createAccount.this,"failed to create user" , Toast.LENGTH_LONG).show();
                                progressBar.setVisibility(View.GONE);
                            }
                        }
                    });

                }

                else{
                    Toast.makeText(createAccount.this,"User Created" , Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                    createAccount.this.startActivity(new Intent(createAccount.this, OptionActivity.class));
                }
            }
        });



    }
}