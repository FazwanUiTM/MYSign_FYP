package com.example.mysign_fyp;

import static com.example.mysign_fyp.DbQuery.ANSWERED;
import static com.example.mysign_fyp.DbQuery.NOT_VISITED;
import static com.example.mysign_fyp.DbQuery.REVIEW;
import static com.example.mysign_fyp.DbQuery.UNANSWERED;
import static com.example.mysign_fyp.DbQuery.g_catList;
import static com.example.mysign_fyp.DbQuery.g_questionList;
import static com.example.mysign_fyp.DbQuery.g_selected_cat_index;
import static com.example.mysign_fyp.DbQuery.g_selected_test_index;
import static com.example.mysign_fyp.DbQuery.g_testList;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.example.mysign_fyp.Adapter.QuestionGridAdapter;
import com.example.mysign_fyp.Adapter.QuestionsAdapter;

import java.util.concurrent.TimeUnit;

public class QuestionsActivity extends AppCompatActivity {

    private RecyclerView questionVIew;
    private TextView tvQuesID, timerTV, catNameTV, markedB;
    private Button submitB, markB, clearSelB;
    private ImageButton prevQuesB, nextQuesB;
    private ImageView questListB;
    private int quesID;
    QuestionsAdapter questionsAdapter;
    private DrawerLayout drawer;
    private ImageButton drawerClose;
    private GridView quesListGV;
    private QuestionGridAdapter gridAdapter;
    private CountDownTimer timer;
    private long timeLeft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.questions_list_layout);

        init();

        questionsAdapter = new QuestionsAdapter(g_questionList, this);
        questionVIew.setAdapter(questionsAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        questionVIew.setLayoutManager(layoutManager);

        gridAdapter = new QuestionGridAdapter(this, g_questionList.size());
        quesListGV.setAdapter(gridAdapter);

        setSnapHelper();

        setClickListeners();

        startTimer();
    }

    private void init()
    {
        questionVIew = findViewById(R.id.questions_view);
        tvQuesID = findViewById(R.id.tv_quesID);
        timerTV = findViewById(R.id.tv_timer);
        catNameTV = findViewById(R.id.qa_catName);
        submitB = findViewById(R.id.submitB);
        markB = findViewById(R.id.markB);
        clearSelB = findViewById(R.id.clear_selB);
        prevQuesB = findViewById(R.id.prev_quesB);
        nextQuesB = findViewById(R.id.next_quesB);
        questListB = findViewById(R.id. ques_list_gridB);
        drawer = findViewById(R.id.drawer_layout);
        drawerClose = findViewById(R.id.drawer_bt);
        markedB = findViewById(R.id.mark_bt);
        quesListGV = findViewById(R.id.ques_list_gv);

        quesID = 0;

        tvQuesID.setText("1 /" + String.valueOf(g_questionList.size()));
        catNameTV.setText(g_catList.get(g_selected_cat_index).getName());

        g_questionList.get(0).setStatus(UNANSWERED);
    }

    private void setSnapHelper() {

        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(questionVIew);

        questionVIew.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                View view = snapHelper.findSnapView(recyclerView.getLayoutManager());
                quesID = recyclerView.getLayoutManager().getPosition(view);

                if(g_questionList.get(quesID).getStatus() == NOT_VISITED)
                    g_questionList.get(quesID).setStatus(UNANSWERED);

                if(g_questionList.get(quesID).getStatus() == REVIEW)
                {
                    markedB.setVisibility(View.VISIBLE);
                }
                else {
                    markedB.setVisibility(View.GONE);
                }

                tvQuesID.setText(String.valueOf(quesID + 1) + "/" + String.valueOf(g_questionList.size()));
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    private void setClickListeners()
    {
        prevQuesB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(quesID > 0)
                {
                    questionVIew.smoothScrollToPosition(quesID - 1);
                }
            }
        });

        nextQuesB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(quesID < g_questionList.size() - 1)
                {
                    questionVIew.smoothScrollToPosition(quesID + 1);
                }
            }
        });

        clearSelB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                g_questionList.get(quesID).setSelectedAns(-1);
                g_questionList.get(quesID).setStatus(UNANSWERED);
                markedB.setVisibility(View.GONE);
                questionsAdapter.notifyDataSetChanged();
            }
        });

        questListB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( ! drawer.isDrawerOpen(GravityCompat.END))
                {
                    gridAdapter.notifyDataSetChanged();
                    drawer.openDrawer(GravityCompat.END);
                }
            }
        });

        drawerClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(drawer.isDrawerOpen(GravityCompat.END)){
                    drawer.closeDrawer(GravityCompat.END);
                }
            }
        });

        markB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (markedB.getVisibility() != View.VISIBLE)
                {
                    markedB.setVisibility(View.VISIBLE);

                    g_questionList.get(quesID).setStatus(REVIEW);
                }
                else
                {
                    markedB.setVisibility(View.GONE);

                    if(g_questionList.get(quesID).getSelectedAns() != -1)
                    {
                        g_questionList.get(quesID).setStatus(ANSWERED);
                    }
                    else
                    {
                        g_questionList.get(quesID).setStatus(UNANSWERED);
                    }
                }
            }
        });

        submitB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitTest();
            }
        });
    }

    private void submitTest()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(QuestionsActivity.this);
        builder.setCancelable(true);

        View view = getLayoutInflater().inflate(R.layout.alert_dialog_layout, null);

        Button cancelB = view.findViewById(R.id.cancelB);
        Button confirmB = view.findViewById(R.id.confirmB);

        builder.setView(view);

        AlertDialog alertDialog = builder.create();

        cancelB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        confirmB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                timer.cancel();
                alertDialog.dismiss();

                Intent intent = new Intent(QuestionsActivity.this, ScoreActivity.class);
                long totalTime = g_testList.get(g_selected_test_index).getTime()*60*1000;
                intent.putExtra("TIME_TAKEN", totalTime - timeLeft);
                startActivity(intent);
                QuestionsActivity.this.finish();
            }
        });
        alertDialog.show();
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setMessage("Are you sure you want to exit the test?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // User confirmed to exit the test
                dialog.dismiss();
                finish(); // Close the activity
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // User cancelled the exit
                dialog.dismiss();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


    public void goToQuestion(int position)
    {
        questionVIew.smoothScrollToPosition(position);

        if(drawer.isDrawerOpen(GravityCompat.END))
            drawer.closeDrawer(GravityCompat.END);
    }

    private void startTimer()
    {
        long totalTime = g_testList.get(g_selected_test_index).getTime()*60*1000;

        timer = new CountDownTimer(totalTime + 1000, 1000) {
            @Override
            public void onTick(long remainingTime) {

                timeLeft = remainingTime;

                String time = String.format("%02d:%02d min",
                TimeUnit.MILLISECONDS.toMinutes(remainingTime),
                        TimeUnit.MILLISECONDS.toSeconds(remainingTime) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(remainingTime))
                );

                timerTV.setText(time);
            }

            @Override
            public void onFinish() {
                Intent intent = new Intent(QuestionsActivity.this, ScoreActivity.class);
                long totalTime = g_testList.get(g_selected_test_index).getTime()*60*1000;
                intent.putExtra("TIME_TAKEN", totalTime - timeLeft);
                startActivity(intent);
                QuestionsActivity.this.finish();
            }
        };

        timer.start();
    }

}