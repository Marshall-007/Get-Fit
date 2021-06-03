package com.example.getfit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class PersonalInfo extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private TextView results;

    private TextView Kg;
    private TextView TKg;
    private TextView cm;
    private TextView Distence ;
    private  String Bmiresults;
    private String calculations;
    private Button change ;
    private Button Done ;
    private EditText weight;
    private EditText height;
    private EditText TargetWeight;
    private EditText TargetSteps;


    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;
    private DatabaseReference mCustomerDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info);
           // in this code we get the values from the string xml file Matrics array  to the spinner
        Spinner spinner = findViewById(R.id.spinner1);
                ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Matrics, android.R.layout.simple_spinner_item);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter);
                spinner.setOnItemSelectedListener(this);
              // TextViews

                Kg= (TextView)findViewById(R.id.txtKg);
                TKg= (TextView)findViewById(R.id.Tweight);
                cm= (TextView)findViewById(R.id.txtCm);
                results =(TextView)findViewById(R.id.txtBMIResult);
                Distence =(TextView)findViewById(R.id.txtDistence);
              //EditText
            weight = (EditText)findViewById(R.id.txtCurrentWeight) ;
            height = (EditText)findViewById(R.id.txtHeight) ;

            TargetSteps  = (EditText)findViewById(R.id.txtTargetSteps) ;

             TargetWeight = (EditText)findViewById(R.id.txtTargetWeight) ;

         final String  weight1 = weight.getText().toString();
         final  String height1 = height.getText().toString();
         final String  TargetSteps1 = TargetSteps.getText().toString();
         final  String  TargetWeight1 = TargetWeight.getText().toString();





        change = (Button) findViewById(R.id.btnSave);
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate();
                if (!validate()) {
                    Toast.makeText(PersonalInfo.this, "Please fill in all fields ", Toast.LENGTH_SHORT).show();

                }
                if (weight.length() != 0 && height.length() != 0) {
                    calculateBMI(weight.getText().toString(), height.getText().toString());
                    change.setVisibility(View.GONE);
                    Done.setVisibility(View.VISIBLE);
                    if (!TargetSteps1.isEmpty()) {
                        double steps = Double.valueOf(TargetSteps1.toString());
                        double Miters = steps * 0.768;
                        Distence.setText(String.valueOf("You Will have to walk " + Miters + " m"));
                    }
                }
                }


        });
        Done = (Button) findViewById(R.id.btnDone);
        Done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                calculateBMI(weight.getText().toString(),height.getText().toString());
                Toast.makeText(getBaseContext(), " Your Phisiological info has been saved to the database", Toast.LENGTH_LONG).show();

                Intent intent = new Intent(getApplicationContext(), First.class);
                startActivity(intent);

            }
        });


    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String text = adapterView.getItemAtPosition(i).toString();
        if (text.equals("imperial")) {
            Kg.setText("lb");
            TKg.setText("lb");
            cm.setText("ft,in");
        }  if (text.equals("metric")) {
            Kg.setText("Kg");
            TKg.setText("Kg");
            cm.setText("Cm");
        }

        Toast.makeText(adapterView.getContext(), "You switched to "+text+" System", Toast.LENGTH_SHORT).show();


    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    public void calculateBMI(String Weight, String Height) {
        validate();
        if (weight.length() != 0 && height.length() != 0) {

            Weight = weight.getText().toString();
            Height = height.getText().toString();

            float weightValue = Float.parseFloat(Weight);
            float heightValue = Float.parseFloat(Height) / 100;

            float bmi = weightValue / (heightValue * heightValue);

            if (bmi < 16) {
                Bmiresults = " You are servely Under weight";

            } else if (bmi < 18.5) {
                Bmiresults = " You are Under weight ";

            } else if (bmi >= 19.5 && bmi <= 28.5) {
                Bmiresults = " This is Normal weight ";

            } else if (bmi >= 30 && bmi <= 49) {

                Bmiresults = " You are OverWeight";

            } else if (bmi >= 50) {
                Bmiresults = " You are Obese";
            }

            calculations = "Results:" + bmi + " " + Bmiresults;

            results.setText(calculations);
        }else {
            Toast.makeText(PersonalInfo.this, "Please fill in all fields ", Toast.LENGTH_SHORT).show();
        }
    }


    private boolean validate(){
       boolean   valid = true;
        final String  weight1 = weight.getText().toString();
        final  String height1 = height.getText().toString();
        final String  TargetSteps1 = TargetSteps.getText().toString();
        final  String  TargetWeight1 = TargetWeight.getText().toString();




        if (weight1.isEmpty() ||  weight1.length() < 1 || weight1.length() > 3) {
            weight.setError("enter a valid weight");
            valid = false;

        } else {
            weight.setError(null);
        }

        if (height1.isEmpty() || height1.length() < 1|| height1.length() > 3) {
            height.setError("Enter a valid height ");
            valid = false;
        } else {
            height.setError(null);
        }
        if (TargetSteps1.isEmpty() || TargetSteps1.length() < 1|| TargetSteps1.length() > 5) {
            TargetSteps.setError("Enter a valid height ");
            valid = false;
        } else {
            TargetSteps.setError(null);
        }
        if (TargetWeight1.isEmpty() || TargetWeight1.length() < 1|| TargetWeight1.length() > 3) {
            TargetWeight.setError("Enter a valid height ");
            valid = false;
        } else {

            TargetWeight.setError(null);
        }

        SharedPreferences prefs = getSharedPreferences(getPackageName(), MODE_PRIVATE);
        prefs.edit().putInt("Value",Integer.parseInt(TargetSteps1)).apply();


        return  valid;
    }









}