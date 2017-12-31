package mif50.com.onlinequizapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Collections;

import mif50.com.onlinequizapp.common.Common;
import mif50.com.onlinequizapp.model.Question;

public class Start extends AppCompatActivity implements View.OnClickListener {
    // init view in start_activity.xml
    Button btnPlay;
    // init firebase
    FirebaseDatabase database;
    DatabaseReference questions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        // find Firebae json obj Question
        database = FirebaseDatabase.getInstance();
        questions=database.getReference("Questions");

        loadQuestion(Common.categoryId); // load Question by categoryId 01 or 02 ......
        // find View in strat_activity.xml
        btnPlay=findViewById(R.id.btn_playing);
        // action btn
        btnPlay.setOnClickListener(this);
    }
    /*this methed load Question of Category by category_id and add in Question List on Common class*/
    private void loadQuestion(String categoryId) {
        // first clear list if have old Question
        if (Common.questionList.size()>0){
            Common.questionList.clear(); // clear list
        }
        questions.orderByChild("CategoryId").equalTo(categoryId) // get obj of questions by CategoryId (01 ....) == categoryId in Common.class
                .addValueEventListener(new ValueEventListener() { // action in json obj Questions
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) { // when data change get or set data in json obj Questions
                        for (DataSnapshot postSnapShot: dataSnapshot.getChildren()){ // get Question from json obj
                            Question que=postSnapShot.getValue(Question.class); // get json obj Questions  as Question Class in model
                            Common.questionList.add(que); // add Question in list
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
        Collections.shuffle(Common.questionList); // Random list
    }
    /*this method used to move to this Activity Start*/
    public static Intent newIntent(Context context){
        Intent startGame= new Intent(context,Start.class); // move to Start Activity
        return startGame; // end this Activity
    }

    @Override
    public void onClick(View v) {
        int id =v.getId();
        if (id==R.id.btn_playing){ // if action come from btn_playing
            startActivity(Playing.newIntent(this)); // move to Playing Activity
            finish();
        }
    }
}
