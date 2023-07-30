package com.example.smartcoach;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class RegistrarseUserUnoActivity extends AppCompatActivity {
    Button btncomenzar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_usuario);

        btncomenzar = findViewById(R.id.btnComenzarUser);

        btncomenzar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegistrarseUserUnoActivity.this, RegistrarseUserDosActivity.class);
                 startActivity(intent);
            }
        });
    }
}
