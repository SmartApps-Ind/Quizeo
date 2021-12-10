package com.theknight.quizeo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.theknight.quizeo.controller.Engine;
import com.theknight.quizeo.data.Repository;
import com.theknight.quizeo.databinding.ActivityMainBinding;
import com.theknight.quizeo.model.Question;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import static com.theknight.quizeo.Score.getScore;

public class MainActivity extends AppCompatActivity {

    List<Question> questionList;
    private ActivityMainBinding binding;
    private int currentQuestionIndex = 0;
    private int scoreCounter;
    private Button finishbutton;


    {
        scoreCounter = 0;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        questionList = new Repository().getQuestions(questionArrayList -> {

                    binding.questionTextView.setText(questionArrayList.get(currentQuestionIndex)
                            .getAnswer());

                    updateCounter(questionArrayList);
                }

        );



        binding.buttonNext.setOnClickListener(view -> {
            Engine.setButton(binding.buttonNext,R.drawable.next,R.drawable.next_down);


            currentQuestionIndex = (currentQuestionIndex + 1) % questionList.size();
            updateQuestion();

            binding.buttonPrev.setOnClickListener(v -> {
                Engine.setButton(binding.buttonPrev,R.drawable.prev,R.drawable.prev_down);

                int i=currentQuestionIndex;
                if(i>0)
                currentQuestionIndex = (currentQuestionIndex -1 ) % questionList.size();
                updateQuestion();

            });

        });
        binding.buttonTrue.setOnClickListener(view -> {
            Engine.setButton(binding.buttonTrue,R.drawable.true_bg,R.drawable.down_true);


            checkAnswer(true);

            updateQuestion();


        });
        binding.buttonFalse.setOnClickListener(view -> {
            Engine.setButton(binding.buttonFalse,R.drawable.false_bg,R.drawable.down_red);


            checkAnswer(false);

            updateQuestion();

        });
        binding.finishButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Engine.setButton(binding.finishButton,R.drawable.finish_bg,R.drawable.down_finish);

                MainActivity.this.startActivity(new Intent(MainActivity.this, FinishQuiz.class));
            }
        });


    }

    private void checkAnswer(boolean userChoseCorrect) {
        boolean answer = questionList.get(currentQuestionIndex).isAnswerTrue();
        int snackMessageId = 0;
        if (userChoseCorrect == answer) {
            snackMessageId = R.string.correct_answer;
            fadeAnimation();
            nextquestion();
            addpoint();

        } else {
            snackMessageId = R.string.incorrect;
            shakeAnimation();
            nextquestion();
            deductpoint();
            doVibrate(100);

        }
        Snackbar.make(binding.cardView, snackMessageId, Snackbar.LENGTH_SHORT)
                .show();

    }

    private void updateCounter(ArrayList<Question> questionArrayList) {
        binding.textViewOutOf.setText(String.format(getString(R.string.text_formatted),
                currentQuestionIndex, questionArrayList.size()));
    }

    private void addpoint()
    {
        int i = this.scoreCounter +10;
        this.scoreCounter = i;
        if (i > 0) {
            Score.setScore(i);
            binding.scoreTextview.setText("Score: " + String.valueOf(getScore()));
            return;
        }
        this.scoreCounter = 0;
        Score.setScore(0);
        binding.scoreTextview.setText("Score: " + String.valueOf(getScore()));
    }

    private void deductpoint()
    {
        int i = this.scoreCounter - 10;
        this.scoreCounter = i;
        if (i > 0) {
            Score.setScore(i);
            binding.scoreTextview.setText("Score: " + String.valueOf(getScore()));
            return;
        }
        this.scoreCounter = 0;
        Score.setScore(0);
        binding.scoreTextview.setText("Score: " + String.valueOf(getScore()));
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

        binding.cardView.setAnimation(alphaAnimation);

        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                binding.questionTextView.setTextColor(Color.GREEN);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                binding.questionTextView.setTextColor(Color.WHITE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


    }

    private void updateQuestion() {
        String question = questionList.get(currentQuestionIndex).getAnswer();
        binding.questionTextView.setText(question);
        updateCounter((ArrayList<Question>) questionList);
    }

    private void shakeAnimation() {
        Animation shake = AnimationUtils.loadAnimation(MainActivity.this,
                R.anim.shake_animation);
        binding.cardView.setAnimation(shake);

        shake.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                binding.questionTextView.setTextColor(Color.RED);

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                binding.questionTextView.setTextColor(Color.WHITE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });





    }

    public void doVibrate(long time){
        Vibrator v = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            v.vibrate(VibrationEffect.createOneShot(time,VibrationEffect.DEFAULT_AMPLITUDE));
        }
        else{
            v.vibrate(time);
        }

    }


}