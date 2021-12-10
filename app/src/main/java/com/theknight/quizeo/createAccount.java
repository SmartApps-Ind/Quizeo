package com.theknight.quizeo;


import android.content.Intent;
import android.os.Bundle;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class createAccount extends AppCompatActivity {

    private Button createAcctButton,signin;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser currentUser;
    DatabaseReference reference;



    //Firestore connection
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private CollectionReference collectionReference = db.collection("Users");



    private EditText emailEditText,name;
    private EditText passwordEditText,confirm;
    private EditText userNameEditText;
    private final String emailpattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    boolean success = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        firebaseAuth = FirebaseAuth.getInstance();

        createAcctButton = findViewById(R.id.createaccountbutton2);
        emailEditText = findViewById(R.id.ac_emaillogin);
        passwordEditText = findViewById(R.id.conferm);
        userNameEditText = findViewById(R.id.acc_username);
        name = findViewById(R.id.name);
        signin = findViewById(R.id.signin);
        confirm = findViewById(R.id.conferm);


        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(createAccount.this,
                        Login.class);

                startActivity(intent);

            }
        });

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                currentUser = firebaseAuth.getCurrentUser();

                if (currentUser != null) {
                    //user is already loggedin..
                }else {
                    //no user yet...
                }

            }
        };


        createAcctButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(emailEditText.getText().toString())
                        && !TextUtils.isEmpty(passwordEditText.getText().toString())
                        && !TextUtils.isEmpty(userNameEditText.getText().toString()) && !TextUtils.isEmpty(name.getText().toString())
                && !TextUtils.isEmpty(confirm.getText().toString()) && passwordEditText.getText().toString().equals(confirm.getText().toString())) {

                    String email = emailEditText.getText().toString().trim();
                    String password = passwordEditText.getText().toString().trim();
                    String username = userNameEditText.getText().toString().trim();
                    String name_user = name.getText().toString().trim();


                    createUserEmailAccount(name_user,email, password, username);
//                    signUp(username,password,email);
//                    if(launch){
//
//                    }else{
//                        Toast.makeText(createAccount.this, "Failed", Toast.LENGTH_SHORT).show();
//                        Log.d("Debug","Launch Failed");
//
//                    }


                }else {
                    Toast.makeText(createAccount.this,
                            "Empty Fields Not Allowed",
                            Toast.LENGTH_LONG)
                            .show();
                    emailEditText.setError("Email can not be empty");
                    passwordEditText.setError("password can not be empty");
                    emailEditText.requestFocus();
                    passwordEditText.requestFocus();
                    userNameEditText.setError("password can not be empty");
                    userNameEditText.requestFocus();
                }



            }
        });

    }

    private void signUp(String username,String password,String email,String user) {
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                    assert firebaseUser != null;
                    String userid = firebaseUser.getUid();
                    reference = FirebaseDatabase.getInstance().getReference("Users").child(userid);

                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put("id", userid);
                    hashMap.put("username", username);
                    hashMap.put("name",user);

                    reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {

                                Intent i = new Intent(createAccount.this, OptionActivity.class);
                                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                i.putExtra("username",username);
                                i.putExtra("userId", userid);
                                finish();

                                startActivity(i);
                            }
                        }
                    });

                } else {
                    Toast.makeText(createAccount.this, "Invalid Email or Password", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }



    private boolean createUserEmailAccount(String s, String email, String password, String username) {
        if (!TextUtils.isEmpty(email)
                && !TextUtils.isEmpty(password)
                && !TextUtils.isEmpty(username)) {



            firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {

                            currentUser = firebaseAuth.getCurrentUser();
                            assert currentUser != null;
                            final String currentUserId = currentUser.getUid();

                            //Create a user Map so we can create a user in the User collection
                            Map<String, String> userObj = new HashMap<>();
                            userObj.put("userId", currentUserId);
                            userObj.put("username", username);
                            userObj.put("name",s);



                            //save to our firestore database
                            collectionReference.add(userObj)
                                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                        @Override
                                        public void onSuccess(DocumentReference documentReference) {
                                            success = true;
                                            Toast.makeText(createAccount.this, "Account created successfully", Toast.LENGTH_SHORT).show();

                                            Intent intent = new Intent(createAccount.this,
                                                    OptionActivity.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            Log.d("Debug","Launch Success");


//                        finish();

                                            startActivity(intent);




                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(createAccount.this,  e.toString(), Toast.LENGTH_SHORT).show();
                                            success = false;
                                            Log.d("Debug","Launch Failed");






                                        }
                                    });
//


                        }else {
                            //something went wrong
                            Toast.makeText(createAccount.this, "Account Already Exist,Please Login instead", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(createAccount.this,
                                    Login.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);


                        }


                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(createAccount.this, "Account Already Exist" + e.toString(), Toast.LENGTH_SHORT).show();


                        }
                    });

        }else {
            Toast.makeText(createAccount.this, "5Invalid Email or Password", Toast.LENGTH_SHORT).show();
            success = false;


        }
        Log.d("Output Bool ",String.valueOf(success));
        return success;
    }

    @Override
    protected void onStart() {
        super.onStart();

        currentUser = firebaseAuth.getCurrentUser();
        firebaseAuth.addAuthStateListener(authStateListener);

    }
}