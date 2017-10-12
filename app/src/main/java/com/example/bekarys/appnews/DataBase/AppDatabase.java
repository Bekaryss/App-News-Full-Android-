package com.example.bekarys.appnews.DataBase;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.bekarys.appnews.NewsModels.News;

/**
 * Created by bekarys on 05.10.17.
 */

@Database(entities = {News.class}, version = 4)
public abstract class AppDatabase extends RoomDatabase {
    public abstract NewsDao newsDao();
}