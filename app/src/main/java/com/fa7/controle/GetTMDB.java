package com.fa7.controle;

import android.os.AsyncTask;
import android.util.Log;

import com.fa7.modelo.Movie;

import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GetTMDB extends AsyncTask{

    private final String URL_MOVE = "https://api.themoviedb.org/3/discover/movie?api_key=d64533681aa6c5d4718df88118a3412e&language=pt-BRS&sort_by=popularity.desc&include_adult=false&include_video=false&page=1";
    public ArrayList<Movie> listMovies;


    @Override
    protected Object doInBackground(Object... arg0) {
        //listMovies = HttpGetListMovies();
        LineAdapter lineAdapter = new LineAdapter(listMovies);
        return true;
    }

    /*
    private ArrayList<Movie> HttpGetListMovies() {
        ArrayList<Movie> listMovies = new ArrayList<Movie>();
        ControlMovies controlMovies = new ControlMovies();
        try {

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(URL_MOVE).build();
            Response response = client.newCall(request).execute();
            listMovies = controlMovies.ListMovies(response.body().string());
        }
        catch (Exception e) {
            Log.e("HttpGetListMovies", e.getMessage());
        }
        return listMovies;
    }
    */
}
