package com.theknight.quizeo;

import static com.theknight.quizeo.Score.getScore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.theknight.quizeo.databinding.ActivityFinishQuizBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FinishQuiz extends AppCompatActivity {

    private ActivityFinishQuizBinding binding;
    private Button logout,credit;

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish_quiz);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_finish_quiz);

        binding.finishScore.setText("Final Score : " + String.valueOf(getScore()));

        logout = findViewById(R.id.Logoutbutton);
        credit = findViewById(R.id.about);

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
        credit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });
    }

    public void showDialog(){
        Dialog dialog = new Dialog(this,R.style.DiaglogStyle);
        dialog.setContentView(R.layout.credit_popup);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialogbg);
        ImageView close = dialog.findViewById(R.id.imageView);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}