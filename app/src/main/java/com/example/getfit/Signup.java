package com.example.getfit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.app.ProgressDialog;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.BindView;

public class Signup extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private static final String TAG = "SignupActivity";

    @BindView(com.example.getfit.R.id.input_name) EditText _nameText;
    @BindView(com.example.getfit.R.id.input_age) EditText _ageText;
    @BindView(com.example.getfit.R.id.input_email) EditText _emailText;
    @BindView(com.example.getfit.R.id.input_password) EditText _passwordText;
    @BindView(com.example.getfit.R.id.btn_update) Button _signupButton;
    @BindView(com.example.getfit.R.id.link_login) TextView _loginLink;
    @BindView(R.id.spinner) Spinner _spinner;
    private FirebaseAuth mAuth;
    private String gender ="";
    private DatabaseReference mCustomerDatabase;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.getfit.R.layout.activity_signup);
        // in this code we get the values from the string xml file Gender array  to the spinner
        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Gender, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        String currentUserID = null;

        mCustomerDatabase = FirebaseDatabase.getInstance().getReference().child("Users").push();

        ButterKnife.bind(this);

        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate();
                saveUserInformation();
                signup();

            }
        });

        _loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the login activity
                finish();

            }
        });
    }

    public void signup() {
        Log.d(TAG, "Signup");

        if (!validate()) {
            onSignupFailed();
            return;
        }

        _signupButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(Signup.this,
                com.example.getfit.R.style.Theme_AppCompat_DayNight_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();

        Intent intent = new Intent(Signup.this, First.class);
        startActivity(intent);
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // depending on success
                        Toast.makeText(getBaseContext(), "Account Created", Toast.LENGTH_LONG).show();
                        onSignupSuccess();

                        progressDialog.dismiss();

                    }
                }, 3000);

    }


    public void onSignupSuccess() {
        _signupButton.setEnabled(true);
        setResult(RESULT_OK, null);
        finish();
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "login failed", Toast.LENGTH_LONG).show();

        _signupButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String name = _nameText.getText().toString();
        String age = _ageText.getText().toString();
        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            _nameText.setError("Name should  be at least 3 characters");
            valid = false;
        } else {
            _nameText.setError(null);
        }


        if (age.isEmpty() || age.length() < 1 || age.length() > 3 ) {
            _ageText.setError("Please enter a valid Age ");
            valid = false;
        } else {
            _ageText.setError(null);
        }


        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("Please enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError(" Must have between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;


    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String text = adapterView.getItemAtPosition(i).toString();
        if (text.equals("Select Gender--")) {
            _spinner.setMotionEventSplittingEnabled(true);
            Toast.makeText(adapterView.getContext(), "select a gender", Toast.LENGTH_SHORT).show();
        }
        if (text.equals("Male")){
            gender = "Male";
        }
           if (text.equals("Female")){
            gender = "Female";
        }

    }


    private void saveUserInformation() {
        final String mail = _emailText.getText().toString();
        final String pass = _passwordText.getText().toString();
        mAuth.createUserWithEmailAndPassword(mail, pass).addOnCompleteListener(Signup.this, new com.google.android.gms.tasks.OnCompleteListener<com.google.firebase.auth.AuthResult>() {

            @Override
            public void onComplete(@androidx.annotation.NonNull com.google.android.gms.tasks.Task<com.google.firebase.auth.AuthResult> task) {
                if (!task.isSuccessful()) {


                   DatabaseReference current_user_db= FirebaseDatabase.getInstance().getReference().child("Users").child("User").push();


                    String Name = _nameText.getText().toString();
                    String age = _ageText.getText().toString();
                    String Email = _emailText.getText().toString();
                    String Password = _passwordText.getText().toString();
                    String Gender = gender;

                    Map userInfo = new HashMap();
                    userInfo.put("name", Name);
                    userInfo.put("age", age);
                    userInfo.put("gender", Gender);
                    userInfo.put("email", Email);
                    userInfo.put("password", Password);
                    mCustomerDatabase.updateChildren(userInfo);
                    current_user_db.setValue(userInfo);

                    SharedPreferences prefs = getSharedPreferences(getPackageName(), MODE_PRIVATE);
                    prefs.edit().putInt("Value",Integer.parseInt(Name)).apply();

                    SharedPreferences prefs1 = getSharedPreferences(getPackageName(), MODE_PRIVATE);
                    prefs.edit().putInt("age",Integer.parseInt(age)).apply();

                    SharedPreferences prefs2 = getSharedPreferences(getPackageName(), MODE_PRIVATE);
                    prefs.edit().putInt("gender",Integer.parseInt(Gender)).apply();

                    SharedPreferences prefs3 = getSharedPreferences(getPackageName(), MODE_PRIVATE);
                    prefs.edit().putInt("email",Integer.parseInt(Email)).apply();

                    SharedPreferences prefs4 = getSharedPreferences(getPackageName(), MODE_PRIVATE);
                    prefs.edit().putInt("password",Integer.parseInt(Password)).apply();










                } else {
                    android.widget.Toast.makeText(Signup.this, "Unable to save to the Database", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        _ageText.setError("Please select a Gender");
    }
    private FirebaseAuth.AuthStateListener firebaseAuthListener;

}