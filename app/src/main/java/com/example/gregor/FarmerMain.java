package com.example.gregor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class FarmerMain extends AppCompatActivity {
ImageView imageView2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_main);
        imageView2 = findViewById(R.id.imageView2);
        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FarmerMain.this,Profile.class);
                startActivity(intent);
            }
        });
    }
}