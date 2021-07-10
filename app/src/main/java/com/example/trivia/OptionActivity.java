package com.example.trivia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class OptionActivity extends AppCompatActivity {
    private Button gotobutton;
    private Button gotobutton2;

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




    }
}