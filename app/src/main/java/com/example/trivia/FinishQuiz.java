package com.example.trivia;

import static com.example.trivia.Score.getScore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.trivia.databinding.ActivityFinishQuizBinding;
import com.example.trivia.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FinishQuiz extends AppCompatActivity {

    private ActivityFinishQuizBinding binding;
    private Button logout;

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish_quiz);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_finish_quiz);

        binding.finishScore.setText("Your Final Score is : " + String.valueOf(getScore()));

        logout = findViewById(R.id.Logoutbutton);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(user!= null && firebaseAuth != null) {
                    firebaseAuth.signOut();
                }
                    startActivity(new Intent(FinishQuiz.this,FrontActivity.class));
                    finish();

            }
        });
    }
}