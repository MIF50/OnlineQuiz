package mif50.com.onlinequizapp.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import mif50.com.onlinequizapp.R;
import mif50.com.onlinequizapp.interfa.ItemClickListener;

/**
 * this class find View that put in Recycler View of Ranking  and action item of this View
 */

public class RankingViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    // inti view in layout_ranking.xml
    public TextView txt_cat,txt_cat_score;
    // obj interface that used to action itemView
    ItemClickListener itemClickListener;

    // find view in
    public RankingViewHolder (View itemView){
        super(itemView);
        // find view in layout_ranking.xml
        txt_cat=itemView.findViewById(R.id.txt_user);
        txt_cat_score=itemView.findViewById(R.id.txt_score_user);
        // action itemView when click on
        itemView.setOnClickListener(this);
    }
    // setter
    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {
        // we used obj of interface to implement action later in FirebaseRecyclerAdapter
        itemClickListener.onClick(v,getAdapterPosition(),false);

    }
}
