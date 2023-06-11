package com.example.mysign_fyp;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mysign_fyp.Adapter.TestAdapter;

public class TestActivity extends AppCompatActivity {

    private RecyclerView testView;
    private Toolbar toolbar;
    private TestAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        getSupportActionBar().setTitle(DbQuery.g_catList.get(DbQuery.g_selected_cat_index).getName());
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        testView = findViewById(R.id.testRecycleView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        testView.setLayoutManager(layoutManager);

        DbQuery.loadTestData(new MyCompleteListener() {
            @Override
            public void onSuccess() {

                DbQuery.loadMyScore(new MyCompleteListener() {
                    @Override
                    public void onSuccess() {
                        adapter = new TestAdapter(DbQuery.g_testList);
                        testView.setAdapter(adapter);
                    }

                    @Override
                    public void onFailure() {
                        Toast.makeText(TestActivity.this, "Something went wrong ! Please try again later.", Toast.LENGTH_SHORT).show();
                    }
                });


            }

            @Override
            public void onFailure() {
                Toast.makeText(TestActivity.this, "Something went wrong ! Please try again later.", Toast.LENGTH_SHORT).show();
            }
        });
    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == android.R.id.home){
            TestActivity.this.finish();
        }

        return super.onOptionsItemSelected(item);
    }
}