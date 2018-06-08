package com.fa7.persistence;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.fa7.modelo.Movie;

import java.util.List;

@Dao
public interface MovieDAO {
    @Query("SELECT * FROM movie")
    List<Movie> getAll();

    @Query("SELECT * FROM movie WHERE id IN (:movieIds)")
    List<Movie> loadAllByIds(int[] movieIds);

    @Query("SELECT * FROM movie WHERE id IN (:id)")
    Movie getMovie(int id);

    @Query("SELECT * FROM movie WHERE title LIKE :title LIMIT 1")
    Movie findByName(String title);

    @Insert
    void insertAll(Movie... movie);

    @Delete
    void delete(Movie movie);

}
