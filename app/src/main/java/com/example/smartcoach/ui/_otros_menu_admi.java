package com.example.smartcoach.ui;

import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartcoach.R;

public class _otros_menu_admi extends AppCompatActivity {

    ImageButton botonMapa,botonEquipo,botonPerfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout._otros_menu_admi);

        botonMapa = findViewById(R.id.boton_mapa);
        botonEquipo = findViewById(R.id.boton_equipo);
        botonPerfil = findViewById(R.id.boton_perfil);

    }

    }

