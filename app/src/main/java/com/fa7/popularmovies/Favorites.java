package com.fa7.popularmovies;


import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.fa7.controle.ControlUser;
import com.fa7.modelo.Movie;
import com.fa7.persistence.AppDatabase;
import com.fa7.persistence.FireBasePersistence;
import com.fa7.persistence.MainDatabase;

import java.util.List;

public class Favorites extends AppCompatActivity {

    List<Movie> movies;
    Movie movie;
    ListView lvFavorites;
    Cursor cursor;
    AppDatabase appDatabase;
    BottomNavigationView btnNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        appDatabase = MainDatabase.getInstance(getApplicationContext());
        lvFavorites = (ListView) findViewById(R.id.lvFavorites);

        new dbAsyncTask().execute();

        lvFavorites.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                movie = movies.get(position);

                Intent intent = new Intent(getApplicationContext(), DetailsMovie.class);
                intent.putExtra("idMovie", Integer.toString(movie.getId()));
                startActivity(intent);
                onBackPressed();

            }});

        lvFavorites.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {

            @Override
            public void onCreateContextMenu(ContextMenu contextMenu, View view,
                                            ContextMenu.ContextMenuInfo contextMenuInfo) {
                contextMenu.add(Menu.NONE, 1, Menu.NONE, "Detalhes");
                contextMenu.add(Menu.NONE, 2, Menu.NONE, "Excluir");

                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) contextMenuInfo;
                movie = movies.get(info.position);
            }


        });

        btnNavigation = findViewById(R.id.btnNavigation);

        btnNavigation.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Intent intent;
                        switch (item.getItemId()) {
                            case R.id.mnListMovies:
                                onBackPressed();
                                break;
                            case R.id.mnLogin:
                                intent = new Intent(getApplicationContext(), Login.class);
                                startActivity(intent);
                                onBackPressed();
                                break;
                        }
                        return false;
                    }
                }
        );
    }

    @Override
    public boolean onContextItemSelected(MenuItem item)
    {
        switch (item.getItemId()) {
            case 1 :
                {
                    Intent intent = new Intent(getApplicationContext(), DetailsMovie.class);
                    intent.putExtra("idMovie", Integer.toString(movie.getId()));
                    startActivity(intent);
                    super.onBackPressed();
                }
                break;
            case 2 :
                {
                    if(movie != null)
                    {
                        try {
                            new FireBasePersistence(new ControlUser(this)
                                    .getUser(), getApplicationContext())
                                    .DataOnFirebase(movie, false);

                            Runnable insert = new Runnable() {
                                @Override
                                public void run() {
                                    appDatabase.userDAO().delete(movie);
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Intent intent = new Intent(getApplicationContext(), Favorites.class);
                                            startActivity(intent);
                                            onBackPressed();
                                        }
                                    });
                                }
                            };

                            AsyncTask.execute(insert);
                        }
                        catch (Exception e) {
                            Log.e("Erro", e.getMessage());
                        }
                    }
                }
                break;
            default: System.out.println("Default");
        }


        return true;
    }

    private class dbAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            movies = appDatabase.userDAO().getAll();

            final ArrayAdapter<Movie> adapter = new ArrayAdapter<Movie>(getApplicationContext(), android.R.layout.simple_list_item_1, movies);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    lvFavorites.setAdapter(adapter);
                }
            });
            return null;
        }
    }

}
