package com.example.bekarys.appnews.DataBase;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.bekarys.appnews.NewsModels.News;

import java.util.List;

/**
 * Created by bekarys on 05.10.17.
 */

@Dao
public interface NewsDao {

    @Query("SELECT * FROM news")
    List<News> getAll();

    @Insert
    void insert(News news);

    @Delete
    void delete(News news);

    @Query("SELECT * FROM news")
    List<News> getAllNews();
}
