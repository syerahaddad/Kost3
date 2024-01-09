package com.example.kost;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PemesananActivity extends AppCompatActivity {
    private EditText etEmail, etTanggal, etTotalTagihan;
    private Spinner spRoomType;
    private Button btnCreate, btnDelete;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pemesanan);

        // untujk inisialisasi komponen UI
        etEmail = findViewById(R.id.et_email);
        etTanggal = findViewById(R.id.etTanggal);
        etTotalTagihan = findViewById(R.id.etTotalTagihan);
        spRoomType = findViewById(R.id.spType);
        btnCreate = findViewById(R.id.btnCreate);
        btnDelete = findViewById(R.id.btnDelete);

        // untuk inisialisasi tombol back
        Button backButton = findViewById(R.id.btnBack);

        // untuk mendapatkan referensi ke tombol "Edit"
        Button btnEdit = findViewById(R.id.btnEdit);

        // untuk menambahkan listener untuk tombol back
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // untuk handle klik tombol Back
                goToMenuActivity();
            }
        });



        // untuk inisialisasi Firebase Database
        mDatabase = FirebaseDatabase.getInstance().getReference("order");

        // untuk mengatur Spinner dengan opsi room type
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.room_types,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spRoomType.setAdapter(adapter);

        // untuk mengatur aksi ketika item spinner dipilih
        spRoomType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // untuk handle aksi ketika item spinner dipilih
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            };;
        });

        // untuk mengatur aksi klik tombol create
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createOrder();
            }
        });

        // untuk mengatur aksi klik tombol delete
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearFields();
            }
        });
    }

    // fungsi untuk membuat order dan menyimpan ke database
    private void createOrder() {
        String email = etEmail.getText().toString().trim();
        String roomType = spRoomType.getSelectedItem().toString();
        String tanggal = etTanggal.getText().toString().trim();
        String totalTagihan = etTotalTagihan.getText().toString().trim();


        // untuk buat objek Order
        Order order = new Order(email, roomType, tanggal, totalTagihan);

        // untuk simpan order ke database
        String key = mDatabase.push().getKey();
        mDatabase.child(key).setValue(order).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    showToast("Order berhasil dibuat!");
                    // Kirim data ke DetailActivity
                    Intent intent = new Intent(PemesananActivity.this, DetailActivity.class);
                    intent.putExtra("EMAIL", email);
                    intent.putExtra("ROOM_TYPE", roomType);
                    intent.putExtra("TANGGAL", tanggal);
                    intent.putExtra("TOTAL_TAGIHAN", totalTagihan);
                    startActivity(intent);
                    clearFields();
                } else {
                    showToast("Gagal membuat order. Coba lagi.");
                }
            }
        });
    }

    // untuk fungsi untuk pindah ke MenuActivity
    private void goToMenuActivity() {
        // Create an Intent to start MenuActivity
        Intent intent = new Intent(PemesananActivity.this, MenuActivity.class);

        // memulai MenuActivity
        startActivity(intent);

        // menyelesaikan aktivitas saat ini (PemesananActivity)
        finish();
    }

    // fungsi untuk menghapus isi input
    private void clearFields() {
        etEmail.setText("");
        spRoomType.setSelection(0);
        etTanggal.setText("");
        etTotalTagihan.setText("");
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
