package com.example.bekarys.appnews.Fragments;


import android.arch.persistence.room.Room;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.bekarys.appnews.CategoryModels.CategoriesAdapter;
import com.example.bekarys.appnews.DataBase.AppDatabase;
import com.example.bekarys.appnews.MainActivity;
import com.example.bekarys.appnews.NewsAPIModels.ApiClient;
import com.example.bekarys.appnews.NewsAPIModels.ApiInterface;
import com.example.bekarys.appnews.NewsByIdActivity;
import com.example.bekarys.appnews.NewsModels.News;
import com.example.bekarys.appnews.NewsModels.NewsAdapter;
import com.example.bekarys.appnews.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewsFragment extends Fragment implements View.OnClickListener{

    private RecyclerView recyclerView;
    private NewsAdapter adapter;
    private List<News> newsList;
    private AlertDialog.Builder alertDialog;
    private boolean add = false;

    AppDatabase database;

    View view;

    public NewsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = Room.databaseBuilder(getActivity().getApplicationContext(), AppDatabase.class, "Room.db").build();
    }

    public void initialize(){
        initDialog();
        initSwipe();
        GetNews();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_news, container, false);
        recyclerView = rootView.findViewById(R.id.recycler_viewNews);
        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab_news);
        fab.setOnClickListener(this);
        FloatingActionButton sync = (FloatingActionButton) rootView.findViewById(R.id.syn_news);
        sync.setOnClickListener(this);
        initialize();
        return rootView;
    }

    private void initDialog(){
        alertDialog = new AlertDialog.Builder(getActivity());
        view = getLayoutInflater().inflate(R.layout.news_create, null);
        alertDialog.setView(view);
        alertDialog.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(add) {
                    add = false;
                    EditText titel = (EditText) view.findViewById(R.id.cr_title);
                    EditText body = (EditText) view.findViewById(R.id.cr_body);
                    Calendar c = Calendar.getInstance();
                    SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                    String formattedDate = df.format(c.getTime());
                    News crNews = new News(titel.getText().toString(), body.getText().toString(), formattedDate);
                    adapter.insert(newsList.size(), crNews);
                    AddNews(crNews);
                    adapter.notifyDataSetChanged();
                    dialog.dismiss();
                }
            }
        });
    }

    private void initSwipe(){
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();

                if (direction == ItemTouchHelper.LEFT) {
                    DeleteNews(newsList.get(position));
                    adapter.remove(position);
                    adapter.notifyDataSetChanged();
                }
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    private void removeView(){
        if(view.getParent()!=null) {
            ((ViewGroup) view.getParent()).removeView(view);
        }
    }

    public void FillData() {
//        Calendar c = Calendar.getInstance();
//        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
//        String formattedDate = df.format(c.getTime());
//
//        newsList.add(new News("Batman vs Superman", "Following the destruction of Metropolis, Batman embarks on a personal vendetta against Superman", formattedDate));
//        newsList.add(new News("X-Men: Apocalypse", "X-Men: Apocalypse is an upcoming American superhero film based on the X-Men characters that appear in Marvel Comics", formattedDate));
//        newsList.add(new News("Captain America: Civil War", "A feud between Captain America and Iron Man leaves the Avengers in turmoil.", formattedDate));
//        newsList.add(new News("Kung Fu Panda 3", "After reuniting with his long-lost father, Po  must train a village of pandas", formattedDate));
//        newsList.add(new News("Warcraft", "Fleeing their dying home to colonize another, fearsome orc warriors invade the peaceful realm of Azeroth.", formattedDate));
//        newsList.add(new News("Alice in Wonderland", "Alice in Wonderland: Through the Looking Glass", formattedDate));
//
//        adapter.notifyDataSetChanged();
    }

    public void GetNews(){
        try {

            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<List<News>> call = apiService.getNewsAPIList();
            call.enqueue(new Callback<List<News>>() {
                @Override
                public void onResponse(Call<List<News>> call, Response<List<News>> response) {
                    Log.e("Response", response.body().toString());
                    List<News> newNewsList = response.body();
                    setToRecyclerView(newNewsList);
                    new InsertListAsync().execute(newNewsList);
                }

                @Override
                public void onFailure(Call<List<News>> call, Throwable t) {
                    Log.e("Error", t.getLocalizedMessage());
                }
            });
        } catch (Exception e) {
            System.out.println("Error " + e.getMessage());
            new GetNewsAsync().execute();
        }

    }

    public void AddNews(News _news){
        new InsertAsync().execute(_news);
    }

    public void DeleteNews(News _news){
        new DeleteAsync().execute(_news);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fab_news:
                removeView();
                add = true;
                alertDialog.setTitle("Add News");
                alertDialog.show();
                break;
            case R.id.syn_news:
                removeView();
                SyncWithAPI(newsList);
        }
    }

    public void SyncWithAPI(List<News> _newsList){
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<News>> postCreateCall = apiService.createNewsAPIBlob();

        postCreateCall.enqueue(new Callback<List<News>>() {
            @Override
            public void onResponse(Call<List<News>> call, Response<List<News>> response) {
                List<News> newsListAPI = response.body();
            }

            @Override
            public void onFailure(Call<List<News>> call, Throwable t) {

            }
        });
    }


    private class GetNewsAsync extends AsyncTask<Void, Void, List <News>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected List <News> doInBackground(Void... voids) {
            return database.newsDao().getAll();
        }

        @Override
        protected void onPostExecute(List <News> myList) {
            super.onPostExecute(myList);
            setToRecyclerView(myList);
        }
    }

    void setToRecyclerView(List <News> myList) {
        newsList = myList;
        adapter = new NewsAdapter(this.getContext(), newsList);
        recyclerView.setLayoutManager(new GridLayoutManager(this.getContext(), 1));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    private class InsertAsync extends AsyncTask<News, Void, Void>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(News ... crNews) {
            database.newsDao().insert(crNews[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }

    private class InsertListAsync extends AsyncTask<List<News>, Void, Void>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(List<News> ... crNews) {
            for (News nItem : crNews[0]) {
                database.newsDao().insert(nItem);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }



    private  class DeleteAsync extends AsyncTask<News, Void, Void>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(News ... crNews) {
            database.newsDao().delete(crNews[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }
}
