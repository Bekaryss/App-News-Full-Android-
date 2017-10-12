package com.example.bekarys.appnews.NewsAPIModels;

import com.google.gson.annotations.SerializedName;

/**
 * Created by bekarys on 09.10.17.
 */

public class NewsAPI {
    @SerializedName("id")
    private Integer id;

    @SerializedName("title")
    private String title;

    @SerializedName("body")
    private String body;

    @SerializedName("date")
    private String date;

    public NewsAPI() { };

    public NewsAPI(String _title, String _body, String _date) {
        this.title = _title;
        this.body = _body;
        this.date = _date;
    }

    public void setId(Integer id){this.id = id;}

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String   getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
