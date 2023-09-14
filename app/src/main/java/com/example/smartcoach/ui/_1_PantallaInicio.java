package com.example.smartcoach.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import com.example.smartcoach.R;

public class _1_PantallaInicio extends AppCompatActivity {

    Button comenzar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout._1_pantalla_inicio);

        comenzar = findViewById(R.id.btnComenzar);

        comenzar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(_1_PantallaInicio.this, _2_IniciarSesionRegistrarse.class));
            }
        });
    }
}

