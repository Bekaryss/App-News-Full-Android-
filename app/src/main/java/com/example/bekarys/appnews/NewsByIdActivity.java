package com.example.bekarys.appnews;

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.bekarys.appnews.NewsModels.News;
import com.example.bekarys.appnews.NewsModels.NewsAdapter;

public class NewsByIdActivity extends AppCompatActivity {

    private News curNews;
    private View v;
    private NewsAdapter adapter;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    Intent i;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_by_id);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        v = getWindow().getDecorView().getRootView();
        i = getIntent();
        curNews = (News) i.getParcelableExtra("news-item");

        TextView newsTitle = (TextView)findViewById(R.id.title);
        TextView newsBody = (TextView)findViewById(R.id.body);
        TextView newsDate = (TextView)findViewById(R.id.date);
        newsTitle.setText(curNews.getTitle());
        newsBody.setText(curNews.getBody());
        newsDate.setText(curNews.getDate());

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle("News");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item);
    }


}
