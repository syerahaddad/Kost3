
package com.example.kost;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class DetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // untuk mendapatkan data yang dikirim dari PemesananActivity
        String email = getIntent().getStringExtra("EMAIL");
        String roomType = getIntent().getStringExtra("ROOM_TYPE");
        String tanggal = getIntent().getStringExtra("TANGGAL");
        String totalTagihan = getIntent().getStringExtra("TOTAL_TAGIHAN");

        // untuk menampilkan data di TextView atau komponen UI lainnya
        TextView tvEmail = findViewById(R.id.tvEmail);
        TextView tvRoomType = findViewById(R.id.tvRoomType);
        TextView tvTanggal = findViewById(R.id.tvTanggal);
        TextView tvTotalTagihan = findViewById(R.id.tvTotalTagihan);

        tvEmail.setText("Email: " + email);
        tvRoomType.setText("Room Type: " + roomType);
        tvTanggal.setText("Tanggal: " + tanggal);
        tvTotalTagihan.setText("Total Tagihan: " + totalTagihan);

        // untuk mendapatkan referensi ke tombol "Edit"
        Button btnEdit = findViewById(R.id.btnEdit);

        // untuk menambahkan onClickListener ke tombol "Edit"
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // untuk membuat Intent untuk kembali ke PemesananActivity
                Intent editIntent = new Intent(DetailActivity.this, PemesananActivity.class);

                // untuk mengirim data yang sama ke PemesananActivity untuk diedit
                editIntent.putExtra("EMAIL", email);
                editIntent.putExtra("ROOM_TYPE", roomType);
                editIntent.putExtra("TANGGAL", tanggal);
                editIntent.putExtra("TOTAL_TAGIHAN", totalTagihan);

                // untuk menjalankan intent untuk memulai PemesananActivity untuk diedit
                startActivity(editIntent);

                // untuk menutup DetailActivity agar tidak kembali ke sini saat tombol back ditekan
                finish();
            }
        });

        // untuk mendapatkan referensi ke tombol "Finish"
        Button btnFinish = findViewById(R.id.btnFinish);

        // untuk menambahkan onClickListener ke tombol "Finish"
        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // untuk membuat Intent untuk kembali ke MainActivity
                Intent intent = new Intent(DetailActivity.this, MainActivity.class);

                // untuk menjalankan intent untuk memulai MainActivity
                startActivity(intent);

                // untuk menutup DetailActivity agar tidak kembali ke sini saat tombol back ditekan
                finish();
            }
        });


    }
}