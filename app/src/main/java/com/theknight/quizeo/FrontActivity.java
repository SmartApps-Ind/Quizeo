package com.theknight.quizeo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.theknight.quizeo.controller.Connection;

public class FrontActivity extends AppCompatActivity {
    private Button startButton;
    private ProgressBar progressBar;
    private TextView textView;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser currentUser;




    //Firestore connection


    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_front);
        firebaseAuth = FirebaseAuth.getInstance();



        final ProgressBar progress = (ProgressBar) findViewById(R.id.check);
        final Button button = (Button) findViewById(R.id.start_button);






        if(progress != null){

            TextView text = (TextView) findViewById(R.id.set);
            if(button !=null){
                button.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        text.setText("");
                        text.setTextColor(0);

                        progress.setVisibility(View.VISIBLE);
                        Handler h = new Handler();
                        h.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if(Connection.checkConnection(FrontActivity.this)){

                                    progress.setVisibility(View.INVISIBLE);
                                    text.setText(R.string.connected);
                                    currentUser = firebaseAuth.getCurrentUser();
                                    Toast.makeText(FrontActivity.this, "Connected", Toast.LENGTH_SHORT).show();
                                    if(currentUser != null){
                                        startActivity(new Intent(FrontActivity.this,OptionActivity.class));


                                    }
                                    else{
                                        FrontActivity.this.startActivity(new Intent(FrontActivity.this, Login.class));


                                    }



//




                                }else{
                                    if(text != null){
                                        progress.setVisibility(View.INVISIBLE);

                                        text.setText(R.string.notConnected);
                                        text.setTextColor(Color.RED);
                                        Toast.makeText(FrontActivity.this, "Please check your internet connection", Toast.LENGTH_SHORT).show();





                                    }





                                }

                            }
                        },2000);





                    }
                });

            }

        }






    }
}