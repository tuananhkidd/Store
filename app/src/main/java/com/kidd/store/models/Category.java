package com.kidd.store.models;

import android.content.Context;

import com.kidd.store.SQLiteHelper.DBManager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by TuanAnhKid on 4/1/2018.
 */

public class Category implements Serializable {
    private String id;
    private String title;
    private List<Book> lsBook = new ArrayList<>();


    public Category(String id, String title) {
        this.id = id;
        this.title = title;
    }

    public Category() {
    }

    public void setLsBook(Context context) {
        DBManager dbManager = new DBManager(context);
        lsBook = dbManager.getAllBook();
    }

    public List<Book> getLsBook() {
        return lsBook;
    }

    public void setLsBook(List<Book> lsBook) {
        this.lsBook = lsBook;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
