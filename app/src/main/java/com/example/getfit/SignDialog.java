package com.example.getfit;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import butterknife.BindView;

public class SignDialog extends AppCompatDialogFragment{

    private EditText Username;
    private EditText Email;
    private EditText Password;
    private SignupDialogListener listener;




    private static final String TAG = "SignupActivity";

    @BindView(com.example.getfit.R.id.input_name) EditText _nameText;
    @BindView(com.example.getfit.R.id.input_email) EditText _emailText;
    @BindView(com.example.getfit.R.id.input_password) EditText _passwordText;
    @BindView(com.example.getfit.R.id.btn_update) Button _signupButton;
    @BindView(com.example.getfit.R.id.link_login) TextView _loginLink;


    public void signup() {
        Log.d(TAG, "Signup");



        String name = _nameText.getText().toString();
        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();


    }



    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog, null);
        builder.setView(view)
                .setTitle("Sign up")
                .setNegativeButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intentB = new Intent(getContext(), MainActivity.class);
                        startActivity(intentB);

                    }
                })
                .setPositiveButton("Sign up", new DialogInterface.OnClickListener() {
                    @NonNull
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String username = Username.getText().toString();
                        String email = Email.getText().toString();

                        if (username.isEmpty() || username.length() < 3) {
                            _nameText.setError("at least 3 characters");
                        } else {
                            _nameText.setError(null);
                        }

                        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                            _emailText.setError("enter a valid email address");

                        } else {
                            _emailText.setError(null);
                        }

                        if (Password.equals("") || Password.length() < 4 || Password.length() > 10) {
                            _passwordText.setError("between 4 and 10 alphanumeric characters");

                        } else {
                            _passwordText.setError(null);
                        }
                        listener.applyText(username,email);

                        Toast.makeText(getContext(),"Your account has been created.",Toast.LENGTH_LONG).show();
                    }
                });
        Username = view.findViewById(R.id.input_name);
        Email = view.findViewById(R.id.input_email);

        return builder.create();


    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (SignupDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " Must implement Dialog Listener ");
        }

    }


    public interface  SignupDialogListener {
        void  applyText(String Username, String Email);


    }


      }
