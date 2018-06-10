package com.fa7.controle;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import com.fa7.modelo.Movie;
import com.fa7.persistence.AppDatabase;
import com.fa7.persistence.MainDatabase;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

//import java.io.InputStream;
public class ControlMovies {

    private static final String URL_MOVES = "https://api.themoviedb.org/3/discover/movie?api_key=d64533681aa6c5d4718df88118a3412e&language=pt-BRS&sort_by=popularity.desc&include_adult=false&include_video=false&page=1";
    private static final String URL_MOVE = "https://api.themoviedb.org/3/movie/{movie_id}?api_key=d64533681aa6c5d4718df88118a3412e&language=pt-BR";

    static String json;

    public static ArrayList<Movie> ListMovies()
    {
        ArrayList<Movie> listMovies = new ArrayList<Movie>();
        try {

            ConnectionTMDB connectionTMDB = new ConnectionTMDB(URL_MOVES);
            connectionTMDB.execute();
            json = connectionTMDB.get();

            JSONObject josonMovie = new JSONObject(json);
            JSONArray jsonMoviesArray = josonMovie.getJSONArray("results");

            for (int i = 0; i < jsonMoviesArray.length(); i++) {
                JSONObject jsonObject = new JSONObject(jsonMoviesArray.getString(i));

                Movie movie = new Movie(
                        Integer.parseInt(jsonObject.getString("id")),
                        jsonObject.getString("title"),
                        jsonObject.getString("poster_path"),
                        jsonObject.getString("overview")
                );

                listMovies.add(movie);
            }

        } catch (Exception t) {
            Log.e("ListMovies", t.getMessage());
        }
        return listMovies;
    }

    public static Movie GetMovie(String idMovie) {
        Movie movie = null;
        try {

            ConnectionTMDB connectionTMDB = new ConnectionTMDB(URL_MOVE.replace("{movie_id}", idMovie));
            connectionTMDB.execute();
            json = connectionTMDB.get();

            JSONObject josonMovie = new JSONObject(json);
            movie = new Movie(
                    Integer.parseInt(josonMovie.getString("id")),
                    josonMovie.getString("title"),
                    josonMovie.getString("poster_path"),
                    josonMovie.getString("overview")
            );

        } catch (Exception t) {
            Log.e("GetMovie", t.getMessage());
        }
        return movie;
    }

    private static String HttpGetListMovies(String url) {
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(url).build();
            Response response = client.newCall(request).execute();
            return response.body().string();
        }
        catch (Exception e) {
            Log.e("HttpGetListMovies", e.getMessage());
        }
        return "";
    }

    private static class ConnectionTMDB extends AsyncTask<Void, Void, String> {
        String urlMoves;
        public ConnectionTMDB(String urlMoves) {
            this.urlMoves = urlMoves;
        }

        @Override
            protected String doInBackground(Void... params) {

                return HttpGetListMovies(urlMoves);
            }

            @Override
            protected void onPreExecute () {
                // Progresso.
            }

    }

    public static void DeleteMovies(Context context){
        new dbAsyncTask(context).execute();
    }

    private static class dbAsyncTask extends AsyncTask<Void, Void, Void> {
        Context context;
        public dbAsyncTask(Context context){
            this.context = context;
        }

        @Override
        protected Void doInBackground(Void... params) {
            AppDatabase appDatabase = MainDatabase.getInstance(context);
            appDatabase.userDAO().deleteAll();

            return null;
        }
    }
}
