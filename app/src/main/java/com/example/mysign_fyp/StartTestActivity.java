package com.example.mysign_fyp;

import static com.example.mysign_fyp.DbQuery.g_catList;
import static com.example.mysign_fyp.DbQuery.loadquestions;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class StartTestActivity extends AppCompatActivity {

    private TextView catName, testNo, totalQ, bestScore, time;
    private Button startTestB;
    private ImageView backB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_test);

        init();

        loadquestions(new MyCompleteListener() {
            @Override
            public void onSuccess() {

                setData();
            }

            @Override
            public void onFailure() {
                Toast.makeText(StartTestActivity.this, "Something went wrong ! Please try again later.", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void init()
    {
        catName = findViewById(R.id.st_cat_name);
        testNo = findViewById(R.id.st_test_no);
        totalQ = findViewById(R.id.st_total_ques);
        bestScore = findViewById(R.id.st_best_score);
        time = findViewById(R.id.st_time);
        startTestB = findViewById(R.id.start_testB);
        backB = findViewById(R.id.st_backB);

        backB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartTestActivity.this.finish();
            }
        });

        startTestB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(StartTestActivity.this, QuestionsActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void setData()
    {
        catName.setText(g_catList.get(DbQuery.g_selected_cat_index).getName());
        testNo.setText("Test No. " + String.valueOf(DbQuery.g_selected_test_index + 1));
        totalQ.setText(String.valueOf(DbQuery.g_questionList.size()));
        bestScore.setText(String.valueOf(DbQuery.g_testList.get(DbQuery.g_selected_test_index).getTopScore()));
        time.setText(String.valueOf(DbQuery.g_testList.get(DbQuery.g_selected_test_index).getTime()+"m"));
    }
}