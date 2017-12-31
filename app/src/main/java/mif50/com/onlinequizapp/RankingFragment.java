package mif50.com.onlinequizapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import mif50.com.onlinequizapp.common.Common;
import mif50.com.onlinequizapp.interfa.ItemClickListener;
import mif50.com.onlinequizapp.interfa.RankingCallback;
import mif50.com.onlinequizapp.model.QuestionScore;
import mif50.com.onlinequizapp.model.Ranking;
import mif50.com.onlinequizapp.viewholder.RankingViewHolder;


public class RankingFragment extends Fragment {
    View myFragment; // obj hold ranking_fragment.xml
    int sumScore=0; // hold sum of score of user
    //init view in ranking_fragment.xml
    RecyclerView ranking_list;
    LinearLayoutManager layoutManager;
    // adapter of FirebaseRecycler that get data from Firebase object
    FirebaseRecyclerAdapter<Ranking,RankingViewHolder> adapter;
    // inti firebase
    FirebaseDatabase database;
    DatabaseReference questionScore,rankingT;

    /*this method that create object of RankingFragment*/
    public static RankingFragment newInstance(){
        RankingFragment rankingFragment=new RankingFragment();
        return rankingFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // get object of firebase json QuestionScore , Ranking
        database=FirebaseDatabase.getInstance();
        questionScore=database.getReference("QuestionScore");
        rankingT=database.getReference("Ranking");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // inflate layout in obj of view
        myFragment=inflater.inflate(R.layout.fragment_ranking,container,false);
        // update score (username,ranking)
        updateScore(Common.currentUser.getUsername(),new RankingCallback<Ranking>(){
            @Override
            public void callBack(Ranking ranking) {
                // upload to ranking table
                rankingT.child(ranking.getUserName()).setValue(ranking);
                //After Upload we will Start Ranking Table And show result
               // showRanking();
            }
        });
        // find view in ranking_fragment.xml
        ranking_list=myFragment.findViewById(R.id.ranking_list);
        layoutManager=new LinearLayoutManager(getActivity()); // manage RecyclerView as linear
        ranking_list.setHasFixedSize(true);
        // because orderByChild method of Firebase will sort list with ascending
        // so we need to inverse RecyclerView data by layoutManager
        layoutManager.setReverseLayout(true); // inverse Recycler data
        layoutManager.setStackFromEnd(true); // make it as stack
        ranking_list.setLayoutManager(layoutManager); //set layoutManager to manage RecyclerView
        // set Adapter
        adapter=new FirebaseRecyclerAdapter<Ranking, RankingViewHolder>(
                Ranking.class,
                R.layout.layout_ranking,
                RankingViewHolder.class,
                rankingT.orderByChild("score")) {
            @Override
            protected void populateViewHolder(RankingViewHolder viewHolder, final Ranking model, int position) {
                // set data in View holder
                viewHolder.txt_cat.setText(model.getUserName());
                viewHolder.txt_cat_score.setText(String.valueOf(model.getScore()));
                // action item of view holder
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        startActivity(ScoreDetails.newIntent(getActivity(),model.getUserName()));//move toScoreDetails Activity and send username
                    }
                });
            }
        };
        adapter.notifyDataSetChanged(); // refresh data when changed
        ranking_list.setAdapter(adapter); // set up adapter
        return myFragment;
    }
    /**/
    private void showRanking() {
        rankingT.orderByChild("score")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot data:dataSnapshot.getChildren()){
                            Ranking local = data.getValue(Ranking.class);
                            Log.d("user",local.getUserName());
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }
    /* this method used to update score of user and sum their score*/
    private void updateScore(final String username, final RankingCallback<Ranking> rankingCallback) {
        questionScore.orderByChild("user").equalTo(username) // search in json QuestionScore to user = username
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) { // on change in data to read and write
                        for (DataSnapshot data:dataSnapshot.getChildren()){ // get data as DataSnapShot
                            QuestionScore que =data.getValue(QuestionScore.class); // get data as obj of QuestionScore
                            sumScore+=Integer.parseInt(que.getScore()); // get score and add to sum
                        }
                        // after sumary of score we need process sum variable here
                        // because firebase is sync db , so if process outSide sum
                        // value will be set to  0
                        Ranking ranking = new Ranking(username,sumScore); // add username and score to Ranking model
                        rankingCallback.callBack(ranking); // to process data out side
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }
}
