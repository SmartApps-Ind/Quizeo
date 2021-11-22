package com.theknight.quizeo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class OptionActivity extends AppCompatActivity {
    private Button gotobutton;
    private Button gotobutton2;


    private String currenyUserId;
    private String currentUserName;

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();




    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_option);








        Button button = (Button) findViewById(R.id.go_to_quiz);
        this.gotobutton = button;
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                OptionActivity.this.startActivity(new Intent(OptionActivity.this, MainActivity.class));
            }
        });

        Button button2 = (Button) findViewById(R.id.go_to_quiz2);
        this.gotobutton2 = button2;
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                OptionActivity.this.startActivity(new Intent(OptionActivity.this, MainActivity2.class));
            }
        });







    }
}