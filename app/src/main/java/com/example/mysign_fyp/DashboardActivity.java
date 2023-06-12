package com.example.mysign_fyp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class DashboardActivity extends AppCompatActivity implements View.OnClickListener{

    private ConstraintLayout dictionary, lessons, realTime, profile, leaderboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        dictionary = findViewById(R.id.dictionary_layout);
        dictionary.setOnClickListener(this);

        lessons = findViewById(R.id.lessons_layout);
        lessons.setOnClickListener(this);

        realTime = findViewById(R.id.realTime_layout);
        realTime.setOnClickListener(this);

        profile = findViewById(R.id.setting_layout);
        profile.setOnClickListener(this);

        leaderboard = findViewById(R.id.leaderboard_layout);
        leaderboard.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.lessons_layout:
                startActivity(new Intent(DashboardActivity.this, LessonActivity.class));
                break;
            case R.id.leaderboard_layout:
                startActivity(new Intent(DashboardActivity.this, LeaderboardActivity.class));
                break;
            case R.id.realTime_layout:
                startActivity(new Intent(DashboardActivity.this, CameraActivity.class));
                break;
            case R.id.setting_layout:
                startActivity(new Intent(this, SettingActivity.class));
                break;
        }
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}