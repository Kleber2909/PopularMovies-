package com.fa7.popularmovies;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fa7.controle.ControlMovies;
import com.fa7.controle.ControlUser;
import com.fa7.modelo.Movie;
import com.fa7.persistence.AppDatabase;
import com.fa7.persistence.FireBasePersistence;
import com.fa7.persistence.MainDatabase;
import com.squareup.picasso.Picasso;

public class DetailsMovie extends AppCompatActivity {

    TextView txtTitle, txtOverview;
    ImageView imvPoster;
    Movie movie, movie1;
    private static final String URL_MOVE_POSTER = "https://image.tmdb.org/t/p/w500/";
    BottomNavigationView btnNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_movie);
        txtTitle = (TextView) findViewById(R.id.txtTitle);
        txtOverview = (TextView) findViewById(R.id.txtOverview);
        imvPoster = (ImageView) findViewById(R.id.imvPoster);

        Intent intent = getIntent();

        movie = ControlMovies.GetMovie(intent.getStringExtra("idMovie"));

        txtTitle.setText(movie.getTitle());
        txtOverview.setText(movie.getOverview());

        Picasso.get().load(URL_MOVE_POSTER + movie.getPoster_path()).into(imvPoster);

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
                            case R.id.mnFavorites:
                                intent = new Intent(getApplicationContext(), Favorites.class);
                                startActivity(intent);
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

    public void AddFavortitos(View view) throws InterruptedException {
        new FireBasePersistence(new ControlUser(this)
                .getUser(), getApplicationContext())
                .DataOnFirebase(movie, true);

        new dbAsyncTask().execute();
        Thread.sleep(1000);
        super.onBackPressed();
    }

    private class dbAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            AppDatabase appDatabase = MainDatabase.getInstance(getApplicationContext());

            if(appDatabase.userDAO().getMovie(movie.getId()) == null)
            {
                appDatabase = MainDatabase.getInstance(getApplicationContext());
                appDatabase.userDAO().insertAll(movie);

            }

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(), "Filme adicionado com sucesso!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(), Favorites.class);
                    startActivity(intent);
                }
            });

            return null;
        }
    }
}
