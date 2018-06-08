package com.fa7.popularmovies;


import android.content.Intent;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.fa7.controle.ControlMovies;
import com.fa7.controle.RecycleViewAdapter;

import com.fa7.modelo.Movie;

import java.util.List;

public class ViewListMovies extends AppCompatActivity {

    List<Movie> movies;
    RecycleViewAdapter recycleViewAdapter;
    ListView listView;
    BottomNavigationView btnNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_list_movies);

        movies = ControlMovies.ListMovies();
        listView = (ListView) findViewById(R.id.listView);

        ArrayAdapter<Movie> adapter = new ArrayAdapter<Movie>(this,
                android.R.layout.simple_list_item_1, movies);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getApplicationContext(), DetailsMovie.class);
                intent.putExtra("idMovie", Integer.toString(movies.get(position).getId()));
                startActivity(intent);

            }
        });

        btnNavigation = findViewById(R.id.btnNavigation);

        btnNavigation.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Intent intent;
                        switch (item.getItemId()) {
                            case R.id.mnFavorites:
                                intent = new Intent(getApplicationContext(), Favorites.class);
                                startActivity(intent);
                                break;
                            case R.id.mnLogin:
                                intent = new Intent(getApplicationContext(), CriaLogin.class);
                                startActivity(intent);
                                break;
                        }
                        return false;
                    }
                }
        );

    }
}
