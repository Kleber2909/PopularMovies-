package com.fa7.modelo;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Movie {

    @PrimaryKey
    private int id;
    @ColumnInfo(name = "title")
    private String title;
    @ColumnInfo(name = "poster_path")
    private String poster_path;
    @ColumnInfo(name = "overview")
    private String overview;

    public Movie(int id, String title, String poster_path, String overview) {
        this.id = id;
        this.title = title;
        this.poster_path = poster_path;
        this.overview = overview;
    }

    public Movie(){

    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public String getOverview() {
        return overview;
    }

    public void setId(int id) { this.id = id; }

    public void setTitle(String title) { this.title = title; }

    public void setPoster_path(String poster_path) { this.poster_path = poster_path; }

    public void setOverview(String overview) { this.overview = overview; }

    @Override
    public String toString() {
        return title;
    }
}
