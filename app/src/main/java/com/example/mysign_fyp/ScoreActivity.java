package com.example.mysign_fyp;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.concurrent.TimeUnit;

public class ScoreActivity extends AppCompatActivity {

    private TextView scoreTV, timeTV, totalQTV, correctQTV, wrongQTV, unattemptedQTV;
    private TextView scoreMessage;
    private Button leaderB, reAttemptB, viewAnsB;

    private ImageView scoreImageView;

    private long timeTaken;
    private Dialog progressDialog;
    private TextView dialogText;
    private int finalScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Result");
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        progressDialog = new Dialog(ScoreActivity.this);
        progressDialog.setContentView(R.layout.dialog_layout);
        progressDialog.setCancelable(false);
        progressDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        dialogText = progressDialog.findViewById(R.id.dialog_text);
        dialogText.setText("Loading...");

        progressDialog.show();

        init();

        loadData();

        viewAnsB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        reAttemptB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reAttempt();
            }
        });

        saveResult();
    }

    private void init(){
        scoreTV = findViewById(R.id.score);
        timeTV = findViewById(R.id.time);
        totalQTV = findViewById(R.id.totalQ);
        correctQTV = findViewById(R.id.correctQ);
        wrongQTV = findViewById(R.id.wrongQ);
        unattemptedQTV = findViewById(R.id.un_attemptedQ);
        leaderB = findViewById(R.id.leaderB);
        reAttemptB = findViewById(R.id.reattemptB);
        viewAnsB = findViewById(R.id.view_answerB);
        scoreImageView = findViewById(R.id.scoreImageView);
        scoreMessage = findViewById(R.id.scoreMessage);

    }

    private void loadData()
    {
        int correctQ = 0, wrongQ = 0, unattemptQ = 0;

        for( int i=0; i < DbQuery.g_questionList.size(); i++)
        {
            if (DbQuery.g_questionList.get(i).getSelectedAns() == -1)
            {
                unattemptQ ++;
            }
            else
            {
                if (DbQuery.g_questionList.get(i).getSelectedAns() == DbQuery.g_questionList.get(i).getCorrectAns())
                {
                    correctQ ++;
                }
                else
                {
                    wrongQ ++;
                }
            }
        }

        correctQTV.setText(String.valueOf(correctQ));
        wrongQTV.setText(String.valueOf(wrongQ));
        unattemptedQTV.setText(String.valueOf(unattemptQ));

        totalQTV.setText(String.valueOf(DbQuery.g_questionList.size()));

        finalScore = (correctQ*100)/DbQuery.g_questionList.size();
        scoreTV.setText(String.valueOf(finalScore) + "%");

        if (finalScore >= 80 && finalScore <= 100)
        {
            scoreMessage.setText("CONGRATULATION!");
            scoreImageView.setImageResource(R.drawable.congratulation);
        }
        else if (finalScore >= 50 && finalScore <= 79)
        {
            scoreMessage.setText("GOOD JOB!");
            scoreImageView.setImageResource(R.drawable.thumbs_up);
        }
        else if (finalScore >= 0 && finalScore <= 49)
        {
            scoreMessage.setText("KEEP TRYING! YOU CAN IMPROVE.");
            scoreImageView.setImageResource(R.drawable.lose);
        }

        timeTaken = getIntent().getLongExtra("TIME_TAKEN", 0);

        String time = String.format("%02d:%02d min",
                TimeUnit.MILLISECONDS.toMinutes(timeTaken),
                TimeUnit.MILLISECONDS.toSeconds(timeTaken) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(timeTaken))
        );

        timeTV.setText(time);
    }

    private void reAttempt()
    {
        for (int i=0; i < DbQuery.g_questionList.size(); i++)
        {
            DbQuery.g_questionList.get(i).setSelectedAns(-1);
            DbQuery.g_questionList.get(i).setStatus(DbQuery.NOT_VISITED);
        }

        Intent intent = new Intent(ScoreActivity.this, StartTestActivity.class);
        startActivity(intent);
        finish();
    }

    private void saveResult()
    {
        DbQuery.saveResult(finalScore, new MyCompleteListener() {
            @Override
            public void onSuccess() {
                progressDialog.dismiss();
            }

            @Override
            public void onFailure() {
                Toast.makeText(ScoreActivity.this, "Something went wrong ! Please try again later!" , Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == android.R.id.home){
            ScoreActivity.this.finish();
        }

        return super.onOptionsItemSelected(item);
    }
}