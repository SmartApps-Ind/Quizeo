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

public class FinishQuiz extends AppCompatActivity {

    private ActivityFinishQuizBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish_quiz);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_finish_quiz);

        binding.finishScore.setText("Your Final Score is : " + String.valueOf(getScore()));


    }
}