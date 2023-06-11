package com.example.mysign_fyp.Auth;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mysign_fyp.DbQuery;
import com.example.mysign_fyp.MyCompleteListener;
import com.example.mysign_fyp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText editTextUsername, editTextEmail, editTextPassword, editTextConfirmPassword;
    private Button signup;

    private FirebaseAuth mAuth;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        signup = (Button) findViewById(R.id.buttonSignup);
        signup.setOnClickListener(this);

        editTextUsername = (EditText) findViewById(R.id.editTextTextUsername);
        editTextEmail = (EditText) findViewById(R.id.editTextTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextTextPassword);
        editTextConfirmPassword = (EditText) findViewById(R.id.editTextTextConfirmPassword);

        mAuth = FirebaseAuth.getInstance();
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.buttonSignup) {
            SignupUser();
        }
    }

    private void SignupUser() {
        String username = editTextUsername.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String confirmPassword = editTextConfirmPassword.getText().toString().trim();

        if(username.isEmpty()){
            editTextUsername.setError("Username is Required!");
            editTextUsername.requestFocus();
            return;
        }

        if(email.isEmpty()){
            editTextEmail.setError("Email is Required!");
            editTextEmail.requestFocus();
            return;
        }

        if(password.isEmpty()){
            editTextPassword.setError("Password is Required!");
            editTextPassword.requestFocus();
            return;
        }

        if(password.length() < 6){
            editTextPassword.setError("Must has 6 characters");
            editTextPassword.requestFocus();
            return;
        }

        if(confirmPassword.isEmpty()){
            editTextConfirmPassword.setError("Password Needs to be Confirmed!!");
            editTextConfirmPassword.requestFocus();
            return;
        }

        if(!confirmPassword.equals(password)){
            editTextConfirmPassword.setError("Password Does Not Match!");
            editTextConfirmPassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(SignUpActivity.this, "SignUp Successful.",
                                    Toast.LENGTH_SHORT).show();

                            DbQuery.createUserData(email, username, new MyCompleteListener() {
                                @Override
                                public void onSuccess() {
                                    progressBar.setVisibility(View.GONE);
                                    Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                    SignUpActivity.this.finish();
                                }

                                @Override
                                public void onFailure() {
                                    progressBar.setVisibility(View.GONE);
                                    Toast.makeText(SignUpActivity.this, "Authentication Failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            });


                        }
                        else{
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(SignUpActivity.this, "Authentication Failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}