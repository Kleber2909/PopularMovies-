package com.fa7.popularmovies;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.fa7.controle.ControlMovies;
import com.fa7.controle.ControlUser;
import com.fa7.modelo.User;
import com.fa7.persistence.FireBasePersistence;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity  {
    private FirebaseAuth firebaseAuth;
    EditText edLogin, edPassword;
    Button btnLogin;
    ImageView imageView;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        imageView = findViewById(R.id.imageView2);

        Glide.with(this)
                .load(R.drawable.movies)
                .asGif()
                .into(imageView);

        firebaseAuth = FirebaseAuth.getInstance();
        edLogin = (EditText) findViewById(R.id.edEmail);
        edPassword = (EditText) findViewById(R.id.edPassword);
        btnLogin = (Button) findViewById(R.id.btLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("obClik", "Teste");
                user = new User(edLogin.getText().toString().trim(), edPassword.getText().toString().trim());
                Login();
            }
        });
    }

    private void Login() {
        if (TextUtils.isEmpty(user.getLogin())) {
            Log.i("createLogin", "email null");
            Toast.makeText(this, R.string.errorEmail, Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(user.getPassword())) {
            Log.i("createLogin", "password null");
            Toast.makeText(this, R.string.errorPasswordIncorreto, Toast.LENGTH_SHORT).show();
            return;
        }
        firebaseAuth.signInWithEmailAndPassword(user.getLogin(), user.getPassword())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.i("Login", "Logado com sucesso");
                            ControlUser.SaveUser(getApplicationContext(), user);
                            ControlMovies.DeleteMovies(getApplicationContext());
                            new FireBasePersistence(new ControlUser(getApplicationContext())
                                    .getUser(), getApplicationContext())
                                    .SincFirebase();
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            Intent intent = new Intent(getApplicationContext(), ViewListMovies.class);
                            intent.putExtra("usuario", user.getEmail());
                            startActivity(intent);
                            onBackPressed();
                        } else {
                            //Toast.makeText(getApplicationContext(),  task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            CriaLogin();
                        }
                    }
                });
    }

    private void CriaLogin() {
        firebaseAuth.createUserWithEmailAndPassword(user.getLogin(), user.getPassword())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.i("createLogin", "Login criado com sucesso");
                            Toast.makeText(getApplicationContext(), R.string.sucessRegistre, Toast.LENGTH_SHORT).show();
                            ControlUser.SaveUser(getApplicationContext(), user);
                            ControlMovies.DeleteMovies(getApplicationContext());
                            new FireBasePersistence(new ControlUser(getApplicationContext())
                                    .getUser(), getApplicationContext())
                                    .SincFirebase();
                            Intent intent = new Intent(getApplicationContext(), ViewListMovies.class);
                            intent.putExtra("usuario", edLogin.getText().toString());
                            startActivity(intent);
                            onBackPressed();
                        } else {
                            Toast.makeText(Login.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            Throwable a = task.getException().getCause();
                            String x = "";
                        }
                    }
                });
    }

}
