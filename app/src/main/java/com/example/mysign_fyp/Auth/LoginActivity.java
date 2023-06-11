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

import com.example.mysign_fyp.DashboardActivity;
import com.example.mysign_fyp.DbQuery;
import com.example.mysign_fyp.MyCompleteListener;
import com.example.mysign_fyp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText editTextEmail, editTextPassword;
    private Button login;

    private FirebaseAuth mAuth;
    private ProgressBar progressBar;

    public FirebaseFirestore g_firestore = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = (Button) findViewById(R.id.buttonLogin);
        login.setOnClickListener(this);

        editTextEmail = (EditText) findViewById(R.id.editTextTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextTextPassword);

        mAuth = FirebaseAuth.getInstance();
        progressBar = (ProgressBar) findViewById(R.id.progressBar);


    }

    @Override
    public void onStart() {

        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();

        if (user != null){
            Intent intent = new Intent(this, DashboardActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.buttonLogin) {
            loginUser();
        }
    }

    private void loginUser() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

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
            editTextPassword.setError("Must have 6 characters");
            editTextPassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            Toast.makeText(LoginActivity.this, "Login Success.",
                                    Toast.LENGTH_SHORT).show();


                            DbQuery.getUserData(new MyCompleteListener() {
                                @Override
                                public void onSuccess() {

                                    progressBar.setVisibility(View.GONE);

                                    Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                                    startActivity(intent);
                                    LoginActivity.this.finish();
                                }

                                @Override
                                public void onFailure() {
                                    progressBar.setVisibility(View.GONE);
                                    Toast.makeText(LoginActivity.this, "Authentication failed. Please check your confidential !",
                                            Toast.LENGTH_SHORT).show();
                                }
                            });


                        } else {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(LoginActivity.this, "Authentication failed. Please check your confidential !",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }
}