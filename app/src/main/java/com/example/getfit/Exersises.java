package com.example.getfit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Exersises extends AppCompatActivity {

    android.widget.ViewFlipper viewFlipper;
    private Button next;
    private TextView Type;
    private Button previous;
    private Button Done;

    CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exersises);

        viewFlipper = (android.widget.ViewFlipper) findViewById(R.id.viewFlipper);
        next = (Button) findViewById(R.id.next);
        previous = (Button) findViewById(R.id.previous);
        Done = (Button) findViewById(R.id.Done);
        Done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Type.setText(" ");
                Intent intent = new Intent(Exersises.this, First.class);
                startActivity(intent);

            }
        });


        Type = (TextView) findViewById(R.id.txtGym);
        //cancel the old countDownTimer

        if (countDownTimer != null) {
            countDownTimer.cancel();
        }

        countDownTimer = new CountDownTimer(31000, 1000) {

            @Override
            public void onFinish() {
                Type.setText("Done!");
            }

            @Override
            public void onTick(long millisUntilFinished) {
                Type.setText("Seconds remaining: " + millisUntilFinished / 1000);
            }

        };  countDownTimer.start();



        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //cancel the old countDownTimer

                if(countDownTimer!=null){
                    countDownTimer.cancel();
                }

                countDownTimer = new CountDownTimer(31000, 1000) {

                    @Override
                    public void onFinish() {
                        Type.setText("Done!");
                    }

                    @Override
                    public void onTick(long millisUntilFinished) {
                        Type.setText("Seconds remaining: " + millisUntilFinished / 1000);
                    }

                };

                countDownTimer.start();
                viewFlipper.showNext();

            }
        });

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //cancel the old countDownTimer

                if( countDownTimer!=null){
                    countDownTimer.cancel();
                }

                countDownTimer = new CountDownTimer(31000, 1000) {

                    @Override
                    public void onFinish() {
                        Type.setText("Done!");
                    }

                    @Override
                    public void onTick(long millisUntilFinished) {
                        Type.setText("Seconds remaining: " + millisUntilFinished / 1000);
                    }

                };

                countDownTimer.start();

                viewFlipper.showPrevious();

            }
        });


    }
}