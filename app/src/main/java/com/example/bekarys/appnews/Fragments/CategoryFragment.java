package com.example.bekarys.appnews.Fragments;


import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.bekarys.appnews.CategoryModels.CategoriesAdapter;
import com.example.bekarys.appnews.CategoryModels.Category;
import com.example.bekarys.appnews.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */

public class CategoryFragment extends Fragment {

    private RecyclerView recyclerView;
    private CategoriesAdapter adapter;
    private List<Category> categoryList;

    public CategoryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_category, container, false);
        // Inflate the layout for this fragment

        recyclerView = view.findViewById(R.id.recycler_view);

        categoryList = new ArrayList<>();
        adapter = new CategoriesAdapter(this.getContext(), categoryList);
        recyclerView.setLayoutManager(new GridLayoutManager(this.getContext(), 2));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        prepareCategories();

        return view;
    }

    /**
     * Adding few categories for testing
     */
    private void prepareCategories() {
        int[] covers = new int[]{
                R.drawable.album1,
                R.drawable.album2,
                R.drawable.album3,
                R.drawable.album4,
                R.drawable.album5,
                R.drawable.album6,
                R.drawable.album7,
                R.drawable.album8,
                R.drawable.album9,
                R.drawable.album10,
                R.drawable.album11};

        Category a = new Category("True Romance", 13, covers[0]);
        categoryList.add(a);

        a = new Category("Xscpae", 8, covers[1]);
        categoryList.add(a);

        a = new Category("Maroon 5", 11, covers[2]);
        categoryList.add(a);

        a = new Category("Born to Die", 12, covers[3]);
        categoryList.add(a);

        a = new Category("Honeymoon", 14, covers[4]);
        categoryList.add(a);

        a = new Category("I Need a Doctor", 1, covers[5]);
        categoryList.add(a);

        a = new Category("Loud", 11, covers[6]);
        categoryList.add(a);

        a = new Category("Legend", 14, covers[7]);
        categoryList.add(a);

        a = new Category("Hello", 11, covers[8]);
        categoryList.add(a);

        a = new Category("Greatest Hits", 17, covers[9]);
        categoryList.add(a);

        adapter.notifyDataSetChanged();
    }
}
