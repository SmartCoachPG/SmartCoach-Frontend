package com.example.smartcoach.ui;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.ImageButton;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.smartcoach.R;

public class _44_InformacionNivelActividadEnergico extends AppCompatActivity{
    private TextView tituloNivelEnergico;
    private TextView descripcionModerado;
    private ImageButton btnSalirEnergico;
    private ImageView imageEnergico;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout._44_informacion_nivel_actividad_energico);

        // Asignación de los elementos de la vista a las variables
        tituloNivelEnergico = findViewById(R.id.tituloNivel_energico);
        descripcionModerado = findViewById(R.id.descripciónModerado);
        btnSalirEnergico = findViewById(R.id.btnSalirEnergico);
        imageEnergico = findViewById(R.id.imageEnergico);

        btnSalirEnergico.setOnClickListener(v -> {

            finish();
        });
    }
}
