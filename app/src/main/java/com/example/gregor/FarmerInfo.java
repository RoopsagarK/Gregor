package com.example.gregor;

import static android.content.RestrictionsManager.RESULT_ERROR;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.icu.text.IDNA;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import org.w3c.dom.Text;

import java.util.Arrays;
import java.util.List;

import javax.net.ssl.SSLEngineResult;

import kotlin.jvm.internal.Ref;

public class FarmerInfo extends AppCompatActivity {
    TextView textView2;
    EditText editTextAddress;
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_info);
        textView2 = findViewById(R.id.textView2);
        editTextAddress = findViewById(R.id.editTextAddress);
        submit = findViewById(R.id.submit);
        String UserName = getIntent().getStringExtra("user_name");
        textView2.setText("Hi "+UserName+", fill the fallowing details");



        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FarmerInfo.this,FarmerMain.class);

                startActivity(intent);
            }
        });



    }

}
