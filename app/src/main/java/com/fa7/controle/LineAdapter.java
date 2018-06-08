package com.fa7.controle;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.fa7.modelo.Movie;
import com.fa7.popularmovies.R;
import com.fa7.popularmovies.ViewHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class LineAdapter extends RecyclerView.Adapter<ViewHolder> {

    private final List<Movie> mMovie;

    public LineAdapter(ArrayList mMovie) {
        this.mMovie = mMovie;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_view_holder, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.txtTitle.setText(mMovie.get(position).getTitle());
        holder.txtOverview.setText(mMovie.get(position).getTitle());

        //holder.moreButton.setOnClickListener(view -> updateItem(position));
        //holder.deleteButton.setOnClickListener(view -> removerItem(position));
    }

    @Override
    public int getItemCount() {
        return mMovie != null ? mMovie.size() : 0;
    }

    public void insertMovie(Movie movie) {
        mMovie.add(movie);
        notifyItemInserted(getItemCount());
    }
}
