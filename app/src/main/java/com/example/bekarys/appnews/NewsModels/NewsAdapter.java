package com.example.bekarys.appnews.NewsModels;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bekarys.appnews.CategoryModels.CategoriesAdapter;
import com.example.bekarys.appnews.CategoryModels.Category;
import com.example.bekarys.appnews.NewsByIdActivity;
import com.example.bekarys.appnews.R;

import java.util.List;

/**
 * Created by bekarys on 05.10.17.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.MyNewsViewHolder> {

    private Context mContext;
    private List<News> newsList;

    public NewsAdapter(Context _mContext, List<News> _newsList){
        this.mContext = _mContext;
        this.newsList = _newsList;
    }

    @Override
    public NewsAdapter.MyNewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_card, parent, false);
        return new MyNewsViewHolder(itemView, mContext);
    }

    @Override
    public void onBindViewHolder(NewsAdapter.MyNewsViewHolder holder, int position) {
        holder.setPosition(position);
        holder.title.setText(newsList.get(position).getTitle());
        holder.body.setText(newsList.get(position).getDate());
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }


    // Insert a new item to the RecyclerView on a predefined position
    public void insert(int position, News news) {
        newsList.add(position, news);
        notifyItemInserted(position);
    }

    // Remove a RecyclerView item containing a specified Data object
    public void remove(int position) {
        newsList.remove(position);
        notifyItemRemoved(position);
    }


    //View Holder class
    public class MyNewsViewHolder extends RecyclerView.ViewHolder {
        public TextView title, body;
        int position;

        public MyNewsViewHolder(View view, final Context event) {
            super(view);
            title = (TextView) view.findViewById(R.id.titleNews);
            body = (TextView) view.findViewById(R.id.bodyNews);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(event, NewsByIdActivity.class);
                    intent.putExtra("news-item", (Parcelable) newsList.get(position));
                    event.startActivity(intent);
                }
            });
        }

        public void setPosition(int position) {
            this.position = position;
        }
    }
}
