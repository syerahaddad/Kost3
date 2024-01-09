package com.example.kost;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;


public class MenuActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        FirebaseAuth mAuth;

        Button btnTipeKamar = findViewById(R.id.btnTipeKamar);
        Button btnOrder = findViewById(R.id.btnOrder);
        Button btnLogout = findViewById(R.id.btnLogout);


        mAuth = FirebaseAuth.getInstance();

        btnTipeKamar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MenuActivity.this, TipeActivity.class));
            }
        });

        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MenuActivity.this, PemesananActivity.class));
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();

                // untuk navigasi kembali ke halaman login atau halaman awal
                Intent intent = new Intent(MenuActivity.this, MainActivity.class);
                startActivity(intent);

                // untuk memindahkan pemanggilan finish( ke dalam callback onSuccess
                mAuth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
                    @Override
                    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                        if (firebaseAuth.getCurrentUser() == null) {
                            // Pengguna sudah logout, sekarang tutup aktivitas saat ini
                            finish();
                        }
                    }
                });
            }
        });
    }
}