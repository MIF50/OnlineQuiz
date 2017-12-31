package mif50.com.onlinequizapp.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import mif50.com.onlinequizapp.R;
import mif50.com.onlinequizapp.interfa.ItemClickListener;

/**
 * this class find View that put in Recycler View of Category and action item of this View
 */

public class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    // init View in category_layout.xml
    public TextView name;
    public ImageView image;
    // obj interface that used to action itemView
    ItemClickListener itemClickListener;

    // constructor of View holder that find view in category_layout.xml
    public CategoryViewHolder(View itemView) {
        super(itemView);
        // find view in category_layout.xml
        name=itemView.findViewById(R.id.name_cat);
        image=itemView.findViewById(R.id.image_cat);
        // action itemView when on click on here
        itemView.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        // we used obj of interface to implement action later in FirebaseRecyclerAdapter
        itemClickListener.onClick(v,getAdapterPosition(),false);
    }
    // setter
    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
}
