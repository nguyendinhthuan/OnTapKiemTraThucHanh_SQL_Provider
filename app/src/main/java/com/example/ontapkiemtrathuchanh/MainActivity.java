package com.example.ontapkiemtrathuchanh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button btnSach, btnTG, btnTim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSach = (Button)findViewById(R.id.buttonSach);
        btnTG = (Button)findViewById(R.id.buttonTacGia);

        btnTG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, TacGiaActivity.class);
                startActivity(intent);
            }
        });
        btnSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SachActivity.class);
                startActivity(intent);
            }
        });
    }
}
