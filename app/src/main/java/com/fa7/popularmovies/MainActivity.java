package com.fa7.popularmovies;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.fa7.controle.ControlUser;
import com.fa7.modelo.Movie;
import com.fa7.persistence.FireBasePersistence;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    ControlUser controlUser;
    private FirebaseAuth firebaseAuth;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.imageView4);

        Glide.with(this)
                .load(R.drawable.movies)
                .asGif()
                .into(imageView);

        firebaseAuth = FirebaseAuth.getInstance();
        controlUser = new ControlUser(this);

        if(controlUser.getUser() != null)
            Login();
        else
            CreateLogin();
 }

    private void Login() {

        firebaseAuth.signInWithEmailAndPassword(controlUser.getUser().getLogin(), controlUser.getUser().getPassword())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.i("Login", "Logado com sucesso");

                            new FireBasePersistence(new ControlUser(getApplicationContext())
                                    .getUser(), getApplicationContext())
                                    .SincFirebase();

                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            Intent intent = new Intent(getApplicationContext(), ViewListMovies.class);
                            intent.putExtra("usuario", user.getEmail());
                            startActivity(intent);
                            onBackPressed();
                        } else {
                            Toast.makeText(getApplicationContext(),  task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            CreateLogin();
                        }
                    }
                });
    }

    public  void CreateLogin(){
        Intent intent = new Intent(getApplicationContext(), Login.class);
        startActivity(intent);
        onBackPressed();
    }
}
