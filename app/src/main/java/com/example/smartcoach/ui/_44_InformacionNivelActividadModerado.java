package com.example.smartcoach.ui;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.ImageButton;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.smartcoach.R;

public class _44_InformacionNivelActividadModerado extends AppCompatActivity {

    private TextView tituloNivelModerado;
    private TextView descripcionModerado;
    private ImageButton btnSalirModerado;
    private ImageView imageModerado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout._44_informacion_nivel_actividad_moderado);

        // Asignación de los elementos de la vista a las variables
        tituloNivelModerado = findViewById(R.id.tituloNivel_moderado);
        descripcionModerado = findViewById(R.id.descripciónModerado);
        btnSalirModerado = findViewById(R.id.btnSalirModerado);
        imageModerado = findViewById(R.id.imageModerado);

        btnSalirModerado.setOnClickListener(v -> {
            finish();
        });
    }
}

