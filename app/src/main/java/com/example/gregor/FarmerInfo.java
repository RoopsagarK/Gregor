package com.example.gregor;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

public class FarmerInfo extends AppCompatActivity {
    TextView textView2;
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_info);
        textView2 = findViewById(R.id.textView2);
        String UserName = getIntent().getStringExtra("user_name");
        textView2.setText("Hi " + UserName + ", please fill the fallowing details..!");

    }
}