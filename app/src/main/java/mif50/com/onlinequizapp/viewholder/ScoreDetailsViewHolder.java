package mif50.com.onlinequizapp.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import mif50.com.onlinequizapp.R;

/**
 * this class find View that put in Recycler View of ScoreDetail
 */

public class ScoreDetailsViewHolder extends RecyclerView.ViewHolder {
    // init view in layout_score.xml
    public TextView txt_cat,txt_score_cat;
    public ScoreDetailsViewHolder(View itemView) {
        super(itemView);
        // find view in layout_score.xml
        txt_cat=itemView.findViewById(R.id.txt_cat);
        txt_score_cat=itemView.findViewById(R.id.txt_score_cat);
    }
}
