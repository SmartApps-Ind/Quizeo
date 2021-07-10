package com.example.trivia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class FrontActivity extends AppCompatActivity {
    private Button startButton;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_front);
        Button button = (Button) findViewById(R.id.start_button);
        this.startButton = button;
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                FrontActivity.this.startActivity(new Intent(FrontActivity.this, OptionActivity.class));
            }
        });
    }
}