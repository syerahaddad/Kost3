package com.example.kost;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private Button btSignUp, btNext;
    private EditText etEmail;
    private EditText etPassword;
    private FirebaseAuth fAuth;
    private FirebaseAuth.AuthStateListener fStateListener;
    private static final String TAG = RegisterActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        fAuth = FirebaseAuth.getInstance();

        fStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {

                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());

                } else {

                    Log.d(TAG, "onAuthStateChanged:signed_out");

                }
            }
        };
        btSignUp = (Button) findViewById(R.id.bt_signup);
        btNext = (Button) findViewById(R.id.bt_next);
        etEmail = (EditText) findViewById(R.id.et_email);
        etPassword = (EditText) findViewById(R.id.et_password);
        btSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                signUp(etEmail.getText().toString(),
                        etPassword.getText().toString());
            }
        });
        // untuk menambahkkan kode untuk tombol ""Next"
        btNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // mulai  TipeActivity ketika klik tombol Next
                startActivity(new Intent(LoginActivity.this, TipeActivity.class));
            }
        });

    }

    private void signUp(final String email, String password) {
        fAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUserWithEmail:onComplete:" +
                                task.isSuccessful());
                        /**
                         * jika sign in gagal, maka tampilkan pesan ke user. lalu jika sign
                         in sukses maka auth state listener akan dipanggil dan logic untuk
                         menghandle signed in user bisa dihandle di listener.
                         */

                        if (!task.isSuccessful()) {
                            task.getException().printStackTrace();

                            Snackbar.make(findViewById(R.id.bt_signup), "Akun Sudah Terdaftar", Snackbar.LENGTH_LONG).show();
                        } else {
                            Snackbar.make(findViewById(R.id.bt_signup), "Proses Pendaftaran Berhasil\n" +
                                    "Email " + email, Snackbar.LENGTH_LONG).show();
                        }
                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
        fAuth.addAuthStateListener(fStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (fStateListener != null) {
            fAuth.removeAuthStateListener(fStateListener);
        }
    }
    private void redirectToMenuActivity() {
        startActivity(new Intent(this, MenuActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
    }
}