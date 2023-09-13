package com.example.smartcoach.ui;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.ImageButton;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.smartcoach.R;

public class _43_InformacionNivelActividadLeve extends AppCompatActivity {

    private TextView tituloNivelLeve;
    private TextView descripcionLeve;
    private ImageButton btnSalirLeve;
    private ImageView imageLeve;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout._43_informacion_nivel_actividad_leve);

        // Asignación de los elementos de la vista a las variables
        tituloNivelLeve = findViewById(R.id.tituloNivel_leve);
        descripcionLeve = findViewById(R.id.descripciónLeve);
        btnSalirLeve = findViewById(R.id.btnSalirLeve);
        imageLeve = findViewById(R.id.imageLeve);

        btnSalirLeve.setOnClickListener(v -> {
            finish();
        });
    }
}
