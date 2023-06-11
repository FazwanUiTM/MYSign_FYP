package com.example.mysign_fyp.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.mysign_fyp.Model.CategoryModel;
import com.example.mysign_fyp.DbQuery;
import com.example.mysign_fyp.R;
import com.example.mysign_fyp.TestActivity;

import java.util.List;

public class CategoryAdapter extends BaseAdapter {

    private List<CategoryModel> cat_list;

    public CategoryAdapter(List<CategoryModel> cat_lists) {
        this.cat_list = cat_lists;
    }

    @Override
    public int getCount() {
        return cat_list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {

        View myView;

        if(view == null)
        {
            myView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cat_item_layout,viewGroup, false);
        }
        else
        {
            myView = view;
        }

        myView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DbQuery.g_selected_cat_index = i;

                Intent intent = new Intent(myView.getContext(), TestActivity.class);
                intent.putExtra("CAT_INDEX", i);
                myView.getContext().startActivity(intent);
            }
        });

        TextView catName = myView.findViewById(R.id.catName);
        TextView noOfTest = myView.findViewById(R.id.noOfTests);

        catName.setText(cat_list.get(i).getName());
        noOfTest.setText(String.valueOf(cat_list.get(i).getNoOfTests()) + " Test");


        return myView;
    }
}
