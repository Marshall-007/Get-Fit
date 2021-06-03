package com.example.getfit;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.PriorityQueue;

public class IbnCulculator extends AppCompatActivity {



        private EditText weight;
        private EditText height;
        private TextView results;
        private  String Bmiresults;
        private String calculations;
        private Button btnResults ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ibn_culculator);


        weight =(EditText)findViewById(R.id.txtWeight);
            height =(EditText)findViewById(R.id.txtHeight);

         btnResults= (Button) findViewById(R.id.btnGetResults);

         btnResults.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 calculateBMI();
                 showItemDialog(IbnCulculator.this);
             }
         });




        }


    public float bmi =0;

        public void calculateBMI() {

            String weightw = weight.getText().toString();
            String heighth = height.getText().toString();

            float weightValue = Float.parseFloat(weightw);
            float heightValue = Float.parseFloat(heighth)/ 100;

             bmi = weightValue /(heightValue * heightValue);

            if(bmi < 16){
                Bmiresults="You are Serevely Under weight";

            }else if (bmi <18.5){
                Bmiresults ="You are Under weight ";

            }else if(bmi >= 18.5 &&  bmi <= 24.5){
                Bmiresults="You are Normal weight ";

            }else if ( bmi >= 25 && bmi <=29.5){

                Bmiresults=" You are OverWeight";

            }else if (bmi >= 30){
                Bmiresults="You are Obese";
            }

        }
        private void showItemDialog(Context c){



            AlertDialog dialog = new AlertDialog.Builder(c)
                    .setTitle("BMI Results")
                    .setMessage( calculations = "Results: " + bmi + "\n" + Bmiresults)
                    .setNegativeButton("Ok",null)
                    .create();
                    dialog.show();





              }
    }

