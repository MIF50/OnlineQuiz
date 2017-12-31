package mif50.com.onlinequizapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import mif50.com.onlinequizapp.common.Common;
import mif50.com.onlinequizapp.model.QuestionScore;

public class Done extends AppCompatActivity implements View.OnClickListener {
    private final static String SCORE="SCORE"; // key to store score value to Bundle
    private final static String TOTAL="TOTAL"; // key to store total value to Bundle
    private final static String CORRECT="CORRECT"; // key to store correct value to Bundle
    // init view in done_activity.xml
    Button btnTryAgain;
    TextView txtResultScore,getTxtResultQuestion;
    ProgressBar progressBar;
    // init firebase
    FirebaseDatabase database;
    DatabaseReference questionScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_done);
        // find firebase json obj QuestionScore
        database=FirebaseDatabase.getInstance();
        questionScore=database.getReference("QuestionScore");
        // find view in done_activity.xml
        progressBar=findViewById(R.id.progress_bar_2);
        txtResultScore=findViewById(R.id.txt_total_score);
        getTxtResultQuestion=findViewById(R.id.txt_total_questions);
        btnTryAgain=findViewById(R.id.btn_try_again);
        // action btn
        btnTryAgain.setOnClickListener(this);

        // get data from Bundle and put it in view obj that send to this activity
        Bundle extra = getIntent().getExtras(); // get data that send to it in obj Bundle
        if (extra !=null){ // if there is data
            int score=extra.getInt(SCORE); // get value that have Key SCORE in extra obj
            int total=extra.getInt(TOTAL); // get value that have Key TOTAL in extra obj
            int correct=extra.getInt(CORRECT); // get value that have Key CORRECT in extra obj
            txtResultScore.setText(String.format("SCORE : %d",score)); // put score value in txt in xml
            getTxtResultQuestion.setText(String.format("PASSED : %d / %d",correct,total)); // put correct value and total value in txt in xml

            progressBar.setMax(total); // max value
            progressBar.setProgress(correct); // value that reached to it

            // upload point to BD
            questionScore.child(String.format("%s_%s", Common.currentUser.getUsername(),Common.categoryId))
                    .setValue(new QuestionScore(String.format("%s_%s",Common.currentUser.getUsername(),Common.categoryId)
                            ,Common.currentUser.getUsername(),String.valueOf(score),Common.categoryId,Common.categoryName));


        }
    }

    // method that move to this Activity
    /*this method used to move to this activity Done */
    public static Intent newIntent(Context context,int score,int total,int correct){
        Intent intent=new Intent(context,Done.class);
        Bundle dataSend=new Bundle(); // obj to Bundle to Store data in (key,value)
        dataSend.putInt(SCORE,score); // put value of score in bundle obj dataSend
        dataSend.putInt(TOTAL,total); // put value of total in bundle obj dataSend
        dataSend.putInt(CORRECT,correct); // put value of correct in bundle obj dataSend
        intent.putExtras(dataSend); // put bundle obj (dataSend) in obj of Intent (intent) to send data with obj of intent
        return intent;
    }

    @Override
    public void onClick(View v) {
        int id= v.getId(); // get id of view to listener to action
        if (id==R.id.btn_try_again){ // id id == btnTryAgain
            startActivity(Home.newIntent(this)); // move to Home Activity
            finish();
        }
    }
}
