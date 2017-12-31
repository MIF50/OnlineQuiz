package mif50.com.onlinequizapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import mif50.com.onlinequizapp.common.Common;
import mif50.com.onlinequizapp.interfa.ItemClickListener;
import mif50.com.onlinequizapp.model.Category;
import mif50.com.onlinequizapp.viewholder.CategoryViewHolder;


public class CategoryFragment extends Fragment {
    View myFragment; // TODO:obj that hold category_fragment.xml
    // init view in category_fragment.xml
    RecyclerView list;
    RecyclerView.LayoutManager layoutManager;
    // adapter of RecyclerFirebase that hold data of category (firebase json obj)
    FirebaseRecyclerAdapter<Category,CategoryViewHolder> adapter;
    // init firebase
    FirebaseDatabase database;
    DatabaseReference categories;

    /*this method used to create CategoryFragment */
    public static CategoryFragment newInstance() {
        CategoryFragment categoryFragment = new CategoryFragment();
        return categoryFragment;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // find firebase json obj Category
        database=FirebaseDatabase.getInstance();
        categories=database.getReference("Category");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // inflate layout on obj myFragment
        myFragment=inflater.inflate(R.layout.fragment_category,container,false);
        // find view in category_fragment.xml
        list=myFragment.findViewById(R.id.recycler_list);  // find recycler view from layout (myFragment)
        list.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(container.getContext()); // layout manager is linear layout
        list.setLayoutManager(layoutManager); // made recycler view as linear layout (layout manager that made it)
        loadCategories();// get data from Firebase json object and put it in ViewHolder to show it
        return myFragment;
    }
    /*this method used to get data from firebase obj json Category and show it in Fragment Category */
    private void loadCategories() {
        // adapter that show data (View Holder with data )in Recycler View
        adapter=new FirebaseRecyclerAdapter<Category, CategoryViewHolder>(
                Category.class,
                R.layout.category_layout,
                CategoryViewHolder.class,
                categories) {
            @Override
            protected void populateViewHolder(CategoryViewHolder viewHolder, final Category model, int position) {
                // set data in View Holder
                viewHolder.name.setText(model.getName());
                Picasso.with(getActivity()).load(model.getImage()).into(viewHolder.image);
                // action View Holder
                viewHolder.setItemClickListener(new ItemClickListener() {
                    // method that i create in interface and put it in action of click item in category view holder (to use position )
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        //get category id from adapter and set in Common.CategoryId
                        Common.categoryId=adapter.getRef(position).getKey(); // 01 or 02 or 03 ..... of category
                        Common.categoryName=model.getName(); // set name of category to Common variable categoryName
                        startActivity(Start.newIntent(getActivity()));// move to StartActivity

                    }
                });

            }
        };
        adapter.notifyDataSetChanged(); //refresh data when changed
        list.setAdapter(adapter); // set adapter
    }
}
