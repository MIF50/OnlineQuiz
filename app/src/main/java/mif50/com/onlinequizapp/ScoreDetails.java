package mif50.com.onlinequizapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import mif50.com.onlinequizapp.model.QuestionScore;
import mif50.com.onlinequizapp.viewholder.ScoreDetailsViewHolder;

public class ScoreDetails extends AppCompatActivity {

    private final static String USER="USER"; // variable key used to save data in intent and get data from intent
    String viewUser=""; // variable that hold user
    // init firebase
    FirebaseDatabase database;
    DatabaseReference questionScore;
    // init view in score detail_activity.xml
    RecyclerView score_list;
    LinearLayoutManager layoutManager;
    // adapter of Recycler Firebase that put data in Recycler View
    FirebaseRecyclerAdapter<QuestionScore,ScoreDetailsViewHolder> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_details);
        // find firebase josn obj QuestionScore
        database=FirebaseDatabase.getInstance();
        questionScore=database.getReference("QuestionScore");
        // find view in score detail_activity.xml
        score_list=findViewById(R.id.category_list);
        layoutManager=new LinearLayoutManager(this);
        score_list.setHasFixedSize(true);
        score_list.setLayoutManager(layoutManager);
        // get data that send to this Activity
        if (getIntent()!=null){
            viewUser=getIntent().getStringExtra(USER);
            if (!viewUser.isEmpty()){
                loadScoreDetails(viewUser); // show score by username
            }
        }

    }
    /*this method that get data from obj json firebase and put in view holder to show it in recycler view*/
    private void loadScoreDetails(String viewUser) {
        // get data and set in view holder
        adapter=new FirebaseRecyclerAdapter<QuestionScore, ScoreDetailsViewHolder>(
                QuestionScore.class,
                R.layout.layout_score,
                ScoreDetailsViewHolder.class,
                questionScore.orderByChild("user").equalTo(viewUser)
        ) {
            @Override
            protected void populateViewHolder(ScoreDetailsViewHolder viewHolder, QuestionScore model, int position) {
                // get data and set in view holder
                viewHolder.txt_cat.setText(model.getCategoryName());
                viewHolder.txt_score_cat.setText(model.getScore());
            }
        };
        adapter.notifyDataSetChanged(); // refresh data when changed
        score_list.setAdapter(adapter); // set up adapter
    }

    /* this method that move to this activity Score Details */
    public static Intent newIntent(Context context,String user){
        Intent intent=new Intent(context,ScoreDetails.class);
        intent.putExtra(USER,user);
        return intent;
    }
}
