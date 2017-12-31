package mif50.com.onlinequizapp.interfa;

import android.view.View;

/**
 * this interface used to action item in ViewHolder
 */

public interface ItemClickListener {

    void onClick(View view,int position,boolean isLongClick);
}
