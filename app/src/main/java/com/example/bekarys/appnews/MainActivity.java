package com.example.bekarys.appnews;

import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.example.bekarys.appnews.Fragments.CategoryFragment;
import com.example.bekarys.appnews.Fragments.NewsFragment;
import com.example.bekarys.appnews.NewsAPIModels.ApiClient;
import com.example.bekarys.appnews.NewsAPIModels.ApiInterface;
import com.example.bekarys.appnews.NewsAPIModels.NewsAPI;
import com.example.bekarys.appnews.NewsModels.News;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

//        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
//        Call<List<News>> call = apiService.getNewsAPIList();
//        call.enqueue(new Callback<List<News>>() {
//            @Override
//            public void onResponse(Call<List<News>> call, Response<List<News>> response) {
//                Log.e("Response", response.body().toString());
//                List<News> posts = response.body();
//                for (News post : posts) {
//                    Log.e("GET", post.getTitle());
//                    Log.e("GET", post.getBody());
//                    Log.e("GET", post.getDate());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<News>> call, Throwable t) {
//
//            }
//        });

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new NewsFragment(), "News");
        adapter.addFragment(new CategoryFragment(), "Category");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
