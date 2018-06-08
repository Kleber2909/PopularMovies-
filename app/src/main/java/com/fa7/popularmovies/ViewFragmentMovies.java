package com.fa7.popularmovies;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.fa7.controle.ControlMovies;
import com.fa7.controle.RecycleViewAdapter;
import com.fa7.controle.ViewHolderMovies;
import com.fa7.modelo.Movie;

import java.util.List;

public class ViewFragmentMovies extends AppCompatActivity{
    RecyclerView recyclerView;
    List<Movie> movies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_fragment_movies);


    }
}
