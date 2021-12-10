package com.theknight.quizeo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.theknight.quizeo.controller.Engine;

public class OptionActivity extends AppCompatActivity {
    private Button gotobutton,next;
    private Button gotobutton2;
    private RadioButton java,python;
    private RadioGroup groups;


    private String currenyUserId;
    private String currentUserName;
    private String lang="java";

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();




    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_option);
        next = findViewById(R.id.next);

        java =  findViewById(R.id.go_to_quiz);
        python =  findViewById(R.id.go_to_quiz2);











//        Button button = (Button) findViewById(R.id.go_to_quiz);
//        this.gotobutton = button;
        java.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                    Engine.setButton(java,R.drawable.java,R.drawable.down_java);


                    return true;
                }
                if(motionEvent.getAction()==MotionEvent.ACTION_BUTTON_RELEASE){
                    java.setBackgroundResource(R.drawable.selected);

                }
                return false;
            }
        });
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                lang="java";
//
//            }
//        });
//
//        Button button2 = (Button) findViewById(R.id.go_to_quiz2);
//        this.gotobutton2 = button2;
        python.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                    Engine.setButton(python,R.drawable.python,R.drawable.down_python);


                    return true;
                }
                if(motionEvent.getAction()==MotionEvent.ACTION_BUTTON_RELEASE){
                    python.setBackgroundResource(R.drawable.selected);

                }
                return false;
            }
        });

//        button2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                lang="python";
//
//            }
//        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(java.isChecked()){
                    lang="java";
                }
                if(python.isChecked()){
                    lang = "python";
                }


                launchActivity(lang);
                Log.d("Language",lang);
                Engine.setButton(next,R.drawable.next,R.drawable.next_down);
            }
        });








    }
    public void launchActivity(String lang){
        if(lang.equals("java")){
            OptionActivity.this.startActivity(new Intent(OptionActivity.this, MainActivity.class));



        }
        else if(lang.equals("python")){
            OptionActivity.this.startActivity(new Intent(OptionActivity.this, MainActivity2.class));



        }
        if (!(lang.equals("java") || lang.equals("python"))){
            Toast.makeText(OptionActivity.this, "Please select one", Toast.LENGTH_SHORT).show();
        }

    }




}