package com.example.trivia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.graphics.Color;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.trivia.data.Repository;
import com.example.trivia.databinding.ActivityMain2Binding;
import com.example.trivia.databinding.ActivityMainBinding;
import com.example.trivia.model.Question;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import static com.example.trivia.Score.getScore;

public class MainActivity2 extends AppCompatActivity {

    List<Question> questionList;
    private ActivityMain2Binding binding;
    private int currentQuestionIndex = 0;
    private int scoreCounter;

    {
        scoreCounter = 0;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        binding= DataBindingUtil.setContentView(this,R.layout.activity_main2);

        questionList = new Repository().getQuestions(questionArrayList -> {

                   binding.questionTextView2.setText(questionArrayList.get(currentQuestionIndex)
                            .getAnswer());

                    updateCounter(questionArrayList);
                }

        );


        binding.buttonNext2.setOnClickListener(view -> {

            currentQuestionIndex = (currentQuestionIndex + 1) % questionList.size();
            updateQuestion();

            binding.buttonPrev2.setOnClickListener(v -> {
                int i=currentQuestionIndex;
                if(i>0)
                    currentQuestionIndex = (currentQuestionIndex -1 ) % questionList.size();
                updateQuestion();

            });

        });
        binding.buttonTrue2.setOnClickListener(view -> {
            checkAnswer(true);
            addpoint();
            updateQuestion();


        });
        binding.buttonFalse2.setOnClickListener(view -> {
            checkAnswer(false);
            deductpoint();
            updateQuestion();

        });


    }

    private void checkAnswer(boolean userChoseCorrect) {
        boolean answer = questionList.get(currentQuestionIndex).isAnswerTrue();
        int snackMessageId = 0;
        if (userChoseCorrect == answer) {
            snackMessageId = R.string.correct_answer;
            fadeAnimation();
            nextquestion();

        } else {
            snackMessageId = R.string.incorrect;
            shakeAnimation();
            nextquestion();

        }
        Snackbar.make(binding.cardView2, snackMessageId, Snackbar.LENGTH_SHORT)
                .show();

    }

    private void updateCounter(ArrayList<Question> questionArrayList) {
        binding.textViewOutOf2.setText(String.format(getString(R.string.text_formatted),
                currentQuestionIndex, questionArrayList.size()));
    }

    private void addpoint()
    {
        int i = this.scoreCounter +10;
        this.scoreCounter = i;
        if (i > 0) {
            Score.setScore(i);
            binding.scoreTextview2.setText("Score: " + String.valueOf(getScore()));
            return;
        }
        this.scoreCounter = 0;
        Score.setScore(0);
        binding.scoreTextview2.setText("Score: " + String.valueOf(getScore()));
    }

    private void deductpoint()
    {
        int i = this.scoreCounter - 10;
        this.scoreCounter = i;
        if (i > 0) {
            Score.setScore(i);
            binding.scoreTextview2.setText("Score: " + String.valueOf(getScore()));
            return;
        }
        this.scoreCounter = 0;
        Score.setScore(0);
        binding.scoreTextview2.setText("Score: " + String.valueOf(getScore()));
    }

    private void nextquestion()
    {
        currentQuestionIndex = (currentQuestionIndex + 1) % questionList.size();
        updateQuestion();

    }

    private void fadeAnimation() {
        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
        alphaAnimation.setDuration(300);
        alphaAnimation.setRepeatCount(1);
        alphaAnimation.setRepeatMode(Animation.REVERSE);

        binding.cardView2.setAnimation(alphaAnimation);

        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                binding.questionTextView2.setTextColor(Color.GREEN);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                binding.questionTextView2.setTextColor(Color.WHITE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


    }

    private void updateQuestion() {
        String question = questionList.get(currentQuestionIndex).getAnswer();
        binding.questionTextView2.setText(question);
        updateCounter((ArrayList<Question>) questionList);
    }

    private void shakeAnimation() {
        Animation shake = AnimationUtils.loadAnimation(MainActivity2.this,
                R.anim.shake_animation);
        binding.cardView2.setAnimation(shake);

        shake.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                binding.questionTextView2.setTextColor(Color.RED);

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                binding.questionTextView2.setTextColor(Color.WHITE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


    }


}