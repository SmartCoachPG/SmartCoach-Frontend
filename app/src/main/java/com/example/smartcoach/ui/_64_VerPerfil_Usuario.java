package com.example.smartcoach.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartcoach.R;


public class _64_VerPerfil_Usuario extends AppCompatActivity {

    TextView textoIngresoNombre,textoIngresoEmail,textoIngresoGenero, textoFechaNacimiento,textoIngresoObjetivo;
    ImageButton flechaRegresar,botonModificar;

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
        botonModificar = findViewById(R.id.botonModificar);

        flechaRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(_64_VerPerfil_Usuario.this, _63_Principal_Usuario.class));
            }
        });

        botonModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(_64_VerPerfil_Usuario.this, _65_ModificarPerfil_Usuario.class));
            }
        });




    }

}
