package mif50.com.onlinequizapp;

import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import mif50.com.onlinequizapp.common.Common;

public class Playing extends AppCompatActivity implements View.OnClickListener {

    final static long INTERVAL=1000; // 1 sec start time to show question
    final static long TIMEOUT=7000;  // 7 sec end time to show question
    CountDownTimer mCountDown; // obj of CounterDownTimer
    int progressValue = 0; // start progress value from 0
    int index=0,score=0,thisQuestion=0,totalQuestion,correctAnswer;
    // init view in playing_activity.xml
    ProgressBar progressBar;
    ImageView questionImage;
    Button btnA,btnB,btnC,btnD;
    TextView txtScore,txtQuestionNum,txtQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playing);
        // find view in playing_activity.xml
        txtScore=findViewById(R.id.txt_score);
        txtQuestionNum=findViewById(R.id.txt_total_question);
        txtQuestion=findViewById(R.id.question_text);
        questionImage=findViewById(R.id.question_image);
        progressBar=findViewById(R.id.progress_bar);
        btnA=findViewById(R.id.answer_a);
        btnB=findViewById(R.id.answer_b);
        btnC=findViewById(R.id.answer_c);
        btnD=findViewById(R.id.answer_d);
        // action btn
        btnA.setOnClickListener(this);
        btnB.setOnClickListener(this);
        btnC.setOnClickListener(this);
        btnD.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        mCountDown.cancel();
        if (index<totalQuestion){ // still have Question in List
            Button clickedButton = (Button)v; // casting view to button (btnA,btnB,btnC,btnD)
            if (clickedButton.getText().equals(Common.questionList.get(index).getCorrectAnswer())){ // text in btn = answer in json obj
                // choose correct Answer
                 score+=10; // increase score + 10
                 correctAnswer++; // increase correct Answer to plus one
                 showQuestion(++index); // show next Question
            }

        }else{
            //choose wrong Answer
            startActivity(Done.newIntent(this,score,totalQuestion,correctAnswer)); // move to DoneActivity and send data
            finish();
        }
        txtScore.setText(String.format("%d",score)); // show txt in txtScore in xml

    }
    /*this method show Question in and chooser answer in btn */
    private void showQuestion(int index) {
        if (index < totalQuestion){
            //still have Question IN list
            thisQuestion++; // increase Question plus one
            txtQuestionNum.setText(String.format("%d / %d",thisQuestion,totalQuestion)); // show thisQuestion , totalQuestion in txtQuestionNum in xml
            progressBar.setProgress(0); // set progressBar to o
            progressValue=0; // set value of progressValue to 0

            if (Common.questionList.get(index).getIsImageQuestion().equals("true")){
                // if we have Image
                Picasso.with(getBaseContext()).load(Common.questionList.get(index).getQuestionText()).into(questionImage); // set Image (url in obj in json) in ImageView Obj
                questionImage.setVisibility(View.VISIBLE); // make ImageView Obj appear
                txtQuestion.setVisibility(View.INVISIBLE); // make TextView obj hidden
            }else{
                // if there is no image
                txtQuestion.setText(Common.questionList.get(index).getQuestionText()); // set text in json obj in textView in xml
                questionImage.setVisibility(View.INVISIBLE); // make ImageView Obj hidden
                txtQuestion.setVisibility(View.VISIBLE); // make TextView obj appear
            }
            // show chooser answer in btn
            btnA.setText(Common.questionList.get(index).getAnswerA());
            btnB.setText(Common.questionList.get(index).getAnswerB());
            btnC.setText(Common.questionList.get(index).getAnswerC());
            btnD.setText(Common.questionList.get(index).getAnswerD());
            mCountDown.start(); //start timer
        }else{
            // if it is final Question
            startActivity(Done.newIntent(this,score,totalQuestion,correctAnswer)); // move to DoneActivity and send data (totalQuestion , correctAnswer)
            finish();
        }

    }

    // press ctrl+o


    @Override
    protected void onResume() {
        super.onResume();
        totalQuestion=Common.questionList.size(); // count in question in list
        mCountDown=new CountDownTimer(TIMEOUT,INTERVAL) { // start time in progess for interval to timeOut 1 to 7 sec
            @Override
            public void onTick(long millisUntilFinished) {
                progressBar.setProgress(progressValue); // set value to progress
                progressValue++; // increase progressValue plus one
            }

            @Override
            public void onFinish() {
                mCountDown.cancel(); // finish CountDownTimer
                showQuestion(++index); // show next Question
            }
        };
        showQuestion(index); // show Question
    }

    /*this method used to move ti this activity Playing*/
    public static Intent newIntent(Context context){
        Intent intent = new Intent(context,Playing.class);
        return intent;
    }

}
