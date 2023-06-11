package com.example.mysign_fyp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.GridView;

import com.example.mysign_fyp.Adapter.CategoryAdapter;

public class LessonActivity extends AppCompatActivity {

    private GridView CatView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson);

        CatView = findViewById(R.id.catGrid);

        //loadCategories();

        CategoryAdapter adapter = new CategoryAdapter(DbQuery.g_catList);
        CatView.setAdapter(adapter);
    }


}