package com.example.smartcoach.ui;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.smartcoach.R;

public class _114_ver_rutina_ejercicio_dia_sin_ejercicio extends AppCompatActivity {

    TextView tituloPt2_114, setTextNombreUser_114, bienvenida_114, tituloRutinaDia_114, setTextDuracionRutina_114, horas_114, descripcionDiaSinEjercicio;
    Button btnIniciarRutina_114;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout._114_ver_rutina_ejercicio_dia_sin_ejercicio);
        getSupportActionBar().hide();

        tituloPt2_114 = findViewById(R.id.tituloPt2_114);
        setTextNombreUser_114 = findViewById(R.id.setTextNombreUser_114);
        bienvenida_114 = findViewById(R.id.bienvenida_114);
        tituloRutinaDia_114 = findViewById(R.id.tituloRutinaDia_114);
        setTextDuracionRutina_114 = findViewById(R.id.setTextDuracionRutina_114);
        horas_114 = findViewById(R.id.horas_144);
        btnIniciarRutina_114 = findViewById(R.id.btnIniciarRutina_114);
        descripcionDiaSinEjercicio = findViewById(R.id.descripcionDiaSinEjercicio);
    }
}
