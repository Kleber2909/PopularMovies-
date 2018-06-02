package com.fa7.popularmovies;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class CriaLogin extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    EditText edLogin, edPassword;
    Button btnLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cria_login);

        firebaseAuth = FirebaseAuth.getInstance();
        edLogin = (EditText) findViewById(R.id.edEmail);
        edPassword = (EditText) findViewById(R.id.edPassword);
        btnLogin = (Button) findViewById(R.id.btCriarConta);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("obClik", "Teste");
                createLogin();
            }
        });

    }

    private void createLogin() {
        String email = edLogin.getText().toString().trim();
        String password = edPassword.getText().toString().trim();
        if (TextUtils.isEmpty(email)) {
            Log.i("createLogin", "email null");
            Toast.makeText(this, R.string.errorEmail, Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Log.i("createLogin", "password null");
            Toast.makeText(this, R.string.errorPasswordIncorreto, Toast.LENGTH_SHORT).show();
            return;
        }
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.i("createLogin", "Login criado com sucesso");
                            Toast.makeText(CriaLogin.this, R.string.sucessRegistre, Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), ListFilmes.class);
                            intent.putExtra("usuario", edLogin.getText().toString());
                            startActivity(intent);
                        } else {
                            Toast.makeText(CriaLogin.this,  task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}
