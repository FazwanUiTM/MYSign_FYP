package com.example.mysign_fyp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.firebase.auth.FirebaseAuth;

public class SettingActivity extends AppCompatActivity {

    private ConstraintLayout bookmarkB, leaderB, profileB, logoutB;
    private TextView profile_img_text, name, score, rank;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        initViews();

        String userName = DbQuery.myProfile.getName();
        profile_img_text.setText(userName.toUpperCase().substring(0,1));

        name.setText(userName);

        score.setText(String.valueOf(DbQuery.myPerformance.getScore()));



        logoutB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(SettingActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                finish();
                startActivity(intent);

            }
        });

        bookmarkB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        profileB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        leaderB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingActivity.this, LeaderboardActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initViews()
    {
        logoutB = findViewById(R.id.logoutB);
        profile_img_text = findViewById(R.id.profile_img_text);
        name = findViewById(R.id.name);
        score = findViewById(R.id.total_Score);
        rank = findViewById(R.id.rank);
        leaderB = findViewById(R.id.leaderB);
        bookmarkB = findViewById(R.id.bookmarkB);
        profileB = findViewById(R.id.profileB);
    }

}