package com.example.smartcoach.ui;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import com.example.smartcoach.R;

public class _115_modificar_rutina_ejercicios extends AppCompatActivity {

    TextView tituloModificar_115, tituloRutinaDia, setTextDuracionRutina, horas;
    ScrollView scrollView3;
    Button btnGuardarCambios_115;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout._115_modificar_rutina_ejercicios);
        getSupportActionBar().hide();

        tituloModificar_115 = findViewById(R.id.tituloModificar_115);
        tituloRutinaDia = findViewById(R.id.tituloRutinaDia);
        setTextDuracionRutina = findViewById(R.id.setTextDuracionRutina);
        horas = findViewById(R.id.horas);
        scrollView3 = findViewById(R.id.scrollView3);
        btnGuardarCambios_115 = findViewById(R.id.btnGuardarCambios_115);
    }
}
