package com.example.gregor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class FarmerInfo extends AppCompatActivity {
    TextView textView2;
    EditText editTextAddress;
    Button SubmitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_info);
        textView2 = findViewById(R.id.textView2);
        editTextAddress = findViewById(R.id.editTextAddress);
        SubmitButton = findViewById(R.id.SubmitButton);
        String UserName = getIntent().getStringExtra("user_name");
        textView2.setText("Hi "+UserName+", fill the fallowing details");

        SubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FarmerInfo.this,FarmerMain.class);
                startActivity(intent);
            }
        });



    }

}
