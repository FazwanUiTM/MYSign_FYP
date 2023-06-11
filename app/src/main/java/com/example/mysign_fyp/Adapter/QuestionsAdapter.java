package com.example.mysign_fyp.Adapter;

import static com.example.mysign_fyp.DbQuery.ANSWERED;
import static com.example.mysign_fyp.DbQuery.REVIEW;
import static com.example.mysign_fyp.DbQuery.UNANSWERED;
import static com.example.mysign_fyp.DbQuery.g_questionList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mysign_fyp.DbQuery;
import com.example.mysign_fyp.Model.QuestionModel;
import com.example.mysign_fyp.R;

import java.util.Collections;
import java.util.List;



public class QuestionsAdapter extends RecyclerView.Adapter<QuestionsAdapter.ViewHolder> {

    private List<QuestionModel> questionsList;

    private Context context;

    public QuestionsAdapter(List<QuestionModel> questionsList, Context context) {
        Collections.shuffle(questionsList); // Shuffle the questionsList
        this.questionsList = questionsList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.question_item_layout, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewholder, int i) {
        viewholder.setData(i);
    }

    @Override
    public int getItemCount() {
        return questionsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView ques;
        private Button optionA, optionB, optionC, optionD, prevSelectedB;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ques = itemView.findViewById(R.id.tv_question);
            optionA = itemView.findViewById(R.id.optionA);
            optionB = itemView.findViewById(R.id.optionB);
            optionC = itemView.findViewById(R.id.optionC);
            optionD = itemView.findViewById(R.id.optionD);

            prevSelectedB =null;
        }

        private void setData(final int pos)
        {
            Glide.with(context).load(questionsList.get(pos).getQuestion()).into(ques);
            //ques.setText(questionsList.get(pos).getQuestion());
            optionA.setText(questionsList.get(pos).getOptionA());
            optionB.setText(questionsList.get(pos).getOptionB());
            optionC.setText(questionsList.get(pos).getOptionC());
            optionD.setText(questionsList.get(pos).getOptionD());

            setOption(optionA ,1, pos);
            setOption(optionB ,2, pos);
            setOption(optionC ,3, pos);
            setOption(optionD ,4, pos);

            optionA.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectOption(optionA, 1, pos);
                }
            });

            optionB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectOption(optionB, 2, pos);
                }
            });

            optionC.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectOption(optionC, 3, pos);
                }
            });

            optionD.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectOption(optionD, 4, pos);
                }
            });
        }

        private void selectOption(Button btn, int option_num, int quesID)
        {
            if (prevSelectedB == null){
                btn.setBackgroundResource(R.drawable.selected_bt);
                DbQuery.g_questionList.get(quesID).setSelectedAns(option_num);

                changeStatus(quesID, ANSWERED);
                prevSelectedB = btn;
            }
            else
            {
                if (prevSelectedB.getId() == btn.getId())
                {
                    btn.setBackgroundResource(R.drawable.unselected_bt);
                    DbQuery.g_questionList.get(quesID).setSelectedAns(-1);

                    changeStatus(quesID, UNANSWERED);
                    prevSelectedB = null;
                }
                else
                {
                    prevSelectedB.setBackgroundResource(R.drawable.unselected_bt);
                    btn.setBackgroundResource(R.drawable.selected_bt);

                    DbQuery.g_questionList.get(quesID).setSelectedAns(option_num);

                    changeStatus(quesID, ANSWERED);
                    prevSelectedB = btn;
                }
            }
        }

        private void changeStatus(int id, int status)
        {
            if(g_questionList.get(id).getStatus() != REVIEW)
            {
                g_questionList.get(id).setStatus(status);
            }
        }

        private void setOption(Button btn, int option_num, int quesID)
        {
            if(DbQuery.g_questionList.get(quesID).getSelectedAns() == option_num)
            {
                btn.setBackgroundResource(R.drawable.selected_bt);
            }
            else
            {
                btn.setBackgroundResource(R.drawable.unselected_bt);
            }
        }
    }
}