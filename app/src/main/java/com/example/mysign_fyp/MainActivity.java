package com.example.mysign_fyp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.mysign_fyp.Auth.LoginActivity;
import com.example.mysign_fyp.Auth.SignUpActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button login;
    private Button signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login = (Button) findViewById(R.id.buttonLogin);
        login.setOnClickListener(this);

        signup = (Button) findViewById(R.id.buttonSignup);
        signup.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.buttonSignup:
                startActivity(new Intent(this, SignUpActivity.class));
                break;
            case R.id.buttonLogin:
                startActivity(new Intent(this, LoginActivity.class));
            }
        }
}