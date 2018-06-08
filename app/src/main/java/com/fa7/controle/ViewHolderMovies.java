package com.fa7.controle;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.fa7.popularmovies.R;

public class ViewHolderMovies extends RecyclerView.ViewHolder implements View.OnClickListener {

    final TextView title;
    private ItemClickListener clickListener;

    public ViewHolderMovies(View itemView) {
        super(itemView);
        title = (TextView) itemView.findViewById(R.id.txtTitle);
    }

    @Override
    public void onClick(View view) {
        if (clickListener != null) clickListener.onClick(view, getAdapterPosition());
    }
}
