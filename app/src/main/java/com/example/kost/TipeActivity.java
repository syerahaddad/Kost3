package com.example.kost;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class TipeActivity extends AppCompatActivity {


    private Button nextButton;
    private Spinner spRoomType;
    private ArrayAdapter<CharSequence> adapter;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tipe);

        // inisialisasi spinner dan ArrayAdapter
        spRoomType = findViewById(R.id.spType);
        adapter = ArrayAdapter.createFromResource(
                this,
                R.array.room_types,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spRoomType.setAdapter(adapter);


        // tombol inisialisasi
        nextButton = findViewById(R.id.bt_next);
        mAuth = FirebaseAuth.getInstance();
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("SELECTED_ROOM_TYPE")) {
            String selectedRoomType = intent.getStringExtra("SELECTED_ROOM_TYPE");


            int position = adapter.getPosition(selectedRoomType);
            spRoomType.setSelection(position);
        }


        // untuk menambahkan listener untuk button
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // untuk menghandle klik button Next
                goToPemesananActivity();
            }
        });
    }

    // ini method untuk pindah ke PemesananActivity
    private void goToPemesananActivity() {
        // dapatkan tipe kamar yang dipilih (dengan asumsi itu adalah string)
        String selectedRoomType = "YourLogicToGetSelectedRoomType"; // Replace this with the actual logic

        // buat intent untuk memulai PemesananActivity
        Intent intent = new Intent(TipeActivity.this, PemesananActivity.class);

        // berikan tipe kamar yang dipilih sebagai tambahan
        intent.putExtra("SELECTED_ROOM_TYPE", selectedRoomType);

        // mulai PemesananActivity
        startActivity(intent);

        // selesaikan aktivitas saat ini(TipeActivity)
        finish();
    }


    // override method onStart untuk memastikan pengguna sudah login sebelum melanjutkan
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser == null) {
            // jika pengguna belum login, maka arahkan ke aktivitas login atau tindakan yang sesuai

            finish();
        }
    }
    public void logoutUser(View view) {
        mAuth.signOut();

        // untuk navigasi kembali ke halaman login atau halaman awal
        Intent intent = new Intent(TipeActivity.this, MainActivity.class);
        startActivity(intent);

        // untuk memindahkan pemanggilan finish() ke dalam callback onSuccess
        mAuth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() == null) {
                    finish();
                }
            }
        });
    }
}