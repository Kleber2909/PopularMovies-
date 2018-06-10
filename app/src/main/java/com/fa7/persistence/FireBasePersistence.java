package com.fa7.persistence;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.fa7.controle.ControlMovies;
import com.fa7.modelo.Movie;
import com.fa7.modelo.User;
import com.fa7.popularmovies.Favorites;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FireBasePersistence extends AppCompatActivity {

    static User user;
    Context context;
    final List<Movie> movies = new ArrayList<Movie>();

    public FireBasePersistence(User user, Context context){
        this.user = user;
        this.context = context;
    }


    private void UpdateSqLite(){
       if(movies.size() > 0){
           for(final Movie movie : movies) {
               new dbAsyncTask(movie).execute();
           }
       }
    }

    private class dbAsyncTask extends AsyncTask<Void, Void, Void> {
        Movie movie;
        public dbAsyncTask(Movie movie){
            this.movie = movie;
        }

        @Override
        protected Void doInBackground(Void... params) {
            AppDatabase appDatabase = MainDatabase.getInstance(context);

            if(appDatabase.userDAO().getMovie(movie.getId()) == null)
            {
                appDatabase = MainDatabase.getInstance(context);
                appDatabase.userDAO().insertAll(movie);
            }
            return null;
        }
    }

    public void SincFirebase() {
        try {
            FirebaseAuth.getInstance()
                    .signInWithEmailAndPassword(user.getLogin(), user.getPassword())
                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            final String uID = authResult.getUser().getUid();
                            final FirebaseDatabase firebaseDatabase =
                                    FirebaseDatabase.getInstance();

                            firebaseDatabase
                                    .getReference()
                                    .child("favorites")
                                    .child(uID)
                                    .addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            try {
                                                for (DataSnapshot child : dataSnapshot.getChildren()) {
                                                    Movie m = child.getValue(Movie.class);
                                                    movies.add(m);
                                                }
                                                UpdateSqLite();
                                            } catch (Exception e) {
                                                Log.e("Rtt", e.getMessage());
                                            }
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }
                                    });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.e("SnapshotFirebase", e.getMessage());
                }
            });
        } catch (Exception e) {
            Log.e("SnapshotFirebase", e.getMessage());
        }
    }

    public static void DataOnFirebase(final Movie movie, final Boolean add) {
        try {
            FirebaseAuth.getInstance()
                    .signInWithEmailAndPassword(user.getLogin(), user.getPassword())
                    .addOnSuccessListener(
                            new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {

                                    final String uID = FirebaseAuth.getInstance()
                                            .getCurrentUser().getUid();

                                    final FirebaseDatabase firebaseDatabase =
                                            FirebaseDatabase.getInstance();
                                    DatabaseReference databaseReference = firebaseDatabase.getReference();

                                    if(add)
                                        databaseReference
                                                .child("favorites")
                                                .child(uID)
                                                .child(String.valueOf(movie.getId()))
                                                .setValue(movie);
                                    else
                                        databaseReference
                                                .child("favorites")
                                                .child(uID)
                                                .child(String.valueOf(movie.getId()))
                                                .removeValue();

                                }
                            });
        }
        catch (Exception e){
            Log.e("DataOnFirebase", e.getMessage());
        }
    }
}
