package com.example.smartcoach.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartcoach.R;


public class _64_VerPerfil_Usuario extends AppCompatActivity {

    EditText textoIngresoNombre,textoIngresoEmail,textoIngresoGenero, textoFechaNacimiento,textoIngresoObjetivo;
    ImageButton flechaRegresar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout._64_ver_perfil_usuario);

        textoIngresoNombre = findViewById(R.id.textoIngresoNombre);
        textoIngresoEmail = findViewById(R.id.textoIngresoEmail);
        textoIngresoGenero = findViewById(R.id.textoIngresoGenero);
        textoFechaNacimiento = findViewById(R.id.textoFechaNacimiento);
        textoIngresoObjetivo = findViewById(R.id.textoIngresoObjetivo);
        flechaRegresar = findViewById(R.id.flechaRegresar);

        flechaRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(_64_VerPerfil_Usuario.this, _63_Principal_Usuario.class));
            }
        });


    }

}
