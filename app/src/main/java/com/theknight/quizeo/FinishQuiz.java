package com.theknight.quizeo;

import static com.theknight.quizeo.Score.getScore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.rpc.context.AttributeContext;
import com.theknight.quizeo.data.Repository;
import com.theknight.quizeo.databinding.ActivityFinishQuizBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.theknight.quizeo.model.Question;

public class FinishQuiz extends AppCompatActivity {

    private ActivityFinishQuizBinding binding;
    private Button logout,credit,play_again;
    private TextView correct_answer,incorrect_answer;

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish_quiz);
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        binding = DataBindingUtil.setContentView(this, R.layout.activity_finish_quiz);
        int ans = getScore()/10;
        correct_answer = findViewById(R.id.correct);
        incorrect_answer = findViewById(R.id.incorrect);

        binding.finishScore.setText("Final Score : " + String.valueOf(getScore()));
        correct_answer.setText("Correct : " + String.valueOf(ans));
        int total = 10-ans;
        incorrect_answer.setText("Incorrect : " + String.valueOf(total));

        logout = findViewById(R.id.Logoutbutton);
        credit = findViewById(R.id.about);
        play_again = findViewById(R.id.option);
        play_again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                startActivity(new Intent(FinishQuiz.this,OptionActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                finish();

            }
        });


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(user != null){
                    firebaseAuth.signOut();
                    startActivity(new Intent(FinishQuiz.this,FrontActivity.class));
                    finishAffinity();
                    Toast.makeText(FinishQuiz.this, "Logged Out", Toast.LENGTH_SHORT).show();


                }
                else{
                    Toast.makeText(FinishQuiz.this, "Something went wrong", Toast.LENGTH_SHORT).show();


                }






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
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialogbg1);
        ImageView close = dialog.findViewById(R.id.imageView);
        TextView tx = dialog.findViewById(R.id.owner);
        Button source = dialog.findViewById(R.id.source);
        source.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.github.com/SmartApps-Ind/Quizeo/tree/v0.1.3"));
                startActivity(i);

            }
        });

        tx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tx.setTextColor(Color.CYAN);

                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.github.com/W1LDN16H7"));
                startActivity(i);
                tx.setTextColor(Color.GREEN);
            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}