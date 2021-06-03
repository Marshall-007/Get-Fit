package com.example.getfit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class Menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        ImageButton Goalsbtn = (ImageButton) findViewById(R.id.btnGoals);
        ImageButton Mealsbtn = (ImageButton) findViewById(R.id.btnMeals);
        ImageButton Fittnessbtn = (ImageButton) findViewById(R.id.btnFitness);
        ImageButton Challangesbtn = (ImageButton) findViewById(R.id.BtnChallanges);


        Goalsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Intent is used to transfer to another activity
                Intent intent = new Intent(getApplicationContext(), PersonalInfo.class);
                startActivity(intent);

            }
        });

        Mealsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Intent is used to transfer to another activity
                Intent intent = new Intent(getApplicationContext(), ListOfMeals.class);
                startActivity(intent);

            }
        });

        Fittnessbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Intent is used to transfer to another activity
                Intent intent = new Intent(getApplicationContext(), FitnessTracker.class);
                startActivity(intent);

            }
        });

        Challangesbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Intent is used to transfer to another activity
                Intent intent = new Intent(getApplicationContext(), camera.class);
                startActivity(intent);

            }
        });







    }
}