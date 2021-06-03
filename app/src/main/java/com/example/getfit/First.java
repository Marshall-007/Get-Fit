package com.example.getfit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class First extends AppCompatActivity implements SensorEventListener {
   private ImageView Capture;
   private ImageView Meals;
   private ImageView exersise;
   private ImageView Calculate;
   private TextView textView;

    private TextView text;

   private EditText MaxProgress;


   /////////// Sensor ( for step counter )
   private double MagnitudePrevious = 0;
   private Integer stepCount = 0;
   private SharedPreferences sharedPreferences;
   private SharedPreferences.Editor editor;
   private SensorManager sensorManager;
   /////////////Progress bar

    private TextView txtCalories;
    private ProgressBar progressBar;
    private int pStatus = 0;
    private Handler handler = new Handler();





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        Toolbar  toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        SharedPreferences prefs2 = getSharedPreferences(getPackageName(), MODE_PRIVATE);
        final int Target = prefs2.getInt("Value",100);



        //Image view
        Capture = (ImageView)findViewById(R.id.ImgCamera);
        Meals = (ImageView)findViewById(R.id.ImgMeal);
        Calculate = (ImageView)findViewById(R.id.BMIcalc);
        exersise = (ImageView)findViewById(R.id.exersise);

       final int oldSteps= pStatus;
        //Text View
        textView = (TextView) findViewById(R.id.tv_steps);
        txtCalories = (TextView) findViewById(R.id.txtcalories);
           txtCalories.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   int steper = Integer.parseInt(textView.getText().toString());
                   int total = oldSteps-steper+1;
                   textView.setText(total+"");
               }
           });




        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        SensorEventListener stepDetector = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                if (event != null){
                    float x_acceleration = event.values[0];
                    float y_acceleration = event.values[1];
                    float z_acceleration = event.values[2];

                    double Magnitude = Math.sqrt(x_acceleration*x_acceleration + y_acceleration*y_acceleration + z_acceleration*z_acceleration);
                  final   double MagnitudeDelta = Magnitude - MagnitudePrevious;
                    MagnitudePrevious = Magnitude;

                    if(MagnitudeDelta > 3){
                        stepCount++;
                    }


                 /////Code for progress bar
                    progressBar = (ProgressBar) findViewById(R.id.progressBar);

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            while (MagnitudeDelta > 6) {
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {

                                        progressBar.setMax(Target);
                                        progressBar.setProgress(pStatus);
                                        int calories = (int) (stepCount*0.045);
                                        txtCalories.setText("Calories burned: "+calories);
                                        textView.setText(pStatus+ "/"+Target);
                                    }
                                });
                                try {
                                    Thread.sleep(100);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                pStatus=stepCount;
                            }
                        }
                    }).start();
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
            }
        };

        sensorManager.registerListener(stepDetector, sensor, sensorManager.SENSOR_DELAY_NORMAL);


     exersise.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             Intent intent = new Intent(First.this, Exersises.class);
             startActivity(intent);
         }
     });

        Capture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(First.this, camera.class);
                startActivity(intent);

            }
        });

        Meals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(First.this, MealPlan.class);
                startActivity(intent);

            }
        });

     Calculate.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             Intent intent = new Intent(First.this, IbnCulculator.class);
             startActivity(intent);

         }
     });



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.dots,menu);

        return  true;

    }


















    @Override
    public void setSupportActionBar(@Nullable Toolbar toolbar) {
        super.setSupportActionBar(toolbar);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.logout:
                com.google.firebase.auth.FirebaseAuth.getInstance().signOut();

               final ProgressDialog progressDialog = new ProgressDialog(com.example.getfit.First.this,
                       com.example.getfit.R.style.Theme_AppCompat_DayNight_Dialog);
               progressDialog.setIndeterminate(true);
               progressDialog.setMessage("Logging User out ...");
               progressDialog.show();

                new android.os.Handler().postDelayed(
                       new Runnable() {
                       public void run() {
                             // On complete call  onLogout Success
                                Toast.makeText(getBaseContext(), "You Have been logged Out ", Toast.LENGTH_SHORT);
                           progressDialog.dismiss();
                              //  // onLoginFailed();
                           android.content.Intent intent = new android.content.Intent(First.this , MainActivity.class);
                           startActivity(intent);
                          // Intent intent = new Intent(First.this, MainActivity.class);
                           //startActivity(intent);

                           }
                       }, 3000);
                return true;

            case R.id.profile :
                Intent intent0 = new Intent(First.this, com.example.getfit.PersonalInfo.class);
                startActivity(intent0);
                return true;
            case R.id.Settings :
                Intent intent1 = new Intent(First.this, com.example.getfit.FitnessTracker.class);
                startActivity(intent1);
                return true;
            case R.id.exit:
                Intent intent2 = new Intent(First.this, com.example.getfit.ProfileInfo.class);
                startActivity(intent2);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }





    }


    protected void onPause() {
        super.onPause();
        sharedPreferences = getPreferences(MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.clear();
        editor.putInt("stepCount", stepCount);
        editor.apply();
    }

    protected void onStop() {
        super.onStop();
        sharedPreferences = getPreferences(MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.clear();
        editor.putInt("stepCount", stepCount);
        editor.apply();
    }
    protected void  onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        stepCount = sharedPreferences.getInt("stepCount", 0);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}