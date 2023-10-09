package com.example.smartcoach.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartcoach.R;

public abstract class BaseActivityAdmi extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        setupMenu();
    }

    private void setupMenu() {
        ImageButton btnMapaAdmin = findViewById(R.id.btnMapaAdmin_menu);
        ImageButton btnMancuernaAdmin = findViewById(R.id.btnMancuernaAdmin_menu);
        ImageButton btnPerfilAdmin = findViewById(R.id.btnPerfilAdmin_menu);

        btnMapaAdmin.setOnClickListener(v -> {
            startActivity(new Intent(this, _27_configurar_mapa_admin.class));

        });

        btnMancuernaAdmin.setOnClickListener(v -> {
            // Iniciar actividad o mostrar layout de mancuerna
        });

        btnPerfilAdmin.setOnClickListener(v -> {
            startActivity(new Intent(this, _6_Principal_Admi.class));
        });
    }
}
