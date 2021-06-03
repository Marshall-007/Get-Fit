package com.example.getfit;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnItemSelected;

public class ProfileInfo extends AppCompatActivity {
    private EditText mNameField;
    private EditText mAgeField;
    private EditText mEmailFiled;
    private EditText mGenderField;

    private Button mBack, mConfirm;
    private String gender ="";

    @BindView(R.id.spinner) Spinner _spinner;

    private FirebaseAuth mAuth;
    private DatabaseReference mCustomerDatabase;

    private String userID;
    private String mName;
    private String age ;
    private String Email;
    private String Gender;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_info);

        // in this code we get the values from the string xml file Gender array  to the spinner
        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Gender, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);




         mNameField = (EditText) findViewById(R.id.input_name);
         mAgeField = (EditText) findViewById(R.id.input_age);
         mEmailFiled = (EditText) findViewById(R.id.input_email);




        mConfirm = (Button) findViewById(R.id.btn_update);


        mAuth = FirebaseAuth.getInstance();

        mCustomerDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child("ProfileInfo").push();

        getUserInfo();


        mConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate();
                if (!validate()){
                    Toast.makeText(ProfileInfo.this, "Please make sure all fields are filled in", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), ProfileInfo.class);
                    startActivity(intent);
           }else {
                    saveUserInformation();

           }


            }
        });

    }

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
// Getting user info from firebase database
    private void getUserInfo(){
        mCustomerDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists() && dataSnapshot.getChildrenCount()>0){
                    Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                    if(map.get("name")!=null){
                        mName = map.get("name").toString();
                        mNameField.setText(mName);
                    }
                    if(map.get("age")!=null){
                        age = map.get("age").toString();
                        mAgeField.setText(age);
                    }
                    if(map.get("email")!=null){
                        Email = map.get("email").toString();
                        mAgeField.setText(Email);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }





    private boolean validate() {

   boolean valid = false;
        String name = mNameField.getText().toString();
        String age = mAgeField.getText().toString();
        String email = mEmailFiled.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            mNameField.setError("Name should  be at least 3 characters");
        } else {
            mNameField.setError(null);
            valid=true;
        }
        if (age.isEmpty() || age.length() <1) {
            mAgeField.setError("Enter a valid age");
        } else {
            mNameField.setError(null);
            valid=true;
        }
        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mEmailFiled.setError("Please enter a valid email address");
        } else {
            mEmailFiled.setError(null);
            valid=true;
        }
return valid;
    }





    private void saveUserInformation() {


        mName = mNameField.getText().toString();
        age  = mAgeField.getText().toString();
        Email = mEmailFiled.getText().toString();


        Map userInfo = new HashMap();
        userInfo.put("name", mName);
        userInfo.put("age", age);
        userInfo.put("gender", gender);
        userInfo.put("email", Email);
        android.widget.Toast.makeText(ProfileInfo.this, "User Info Updated", Toast.LENGTH_LONG).show();
        mCustomerDatabase.updateChildren(userInfo);
        Intent intent = new Intent(getApplicationContext(), First.class);
        startActivity(intent);

    }
}











