package com.example.smartcoach.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartcoach.R;

public class _7_VerPerfil_Admi extends AppCompatActivity {

    TextView texoIngresoNombre, textoIngresoEmail,textoIngresoCedula, textoIngresoPuesto,nombreAdmi, puestoAdmi;

    ImageButton flechaRegresar, imagePP, botonModificar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout._7_ver_perfil_admi);

        texoIngresoNombre = findViewById(R.id.textoIngresoNombre);
        textoIngresoEmail = findViewById(R.id.textoIngresoEmail);
        textoIngresoCedula = findViewById(R.id.textoIngresoCedula);
        textoIngresoPuesto = findViewById(R.id.textoIngresoPuesto);
        nombreAdmi = findViewById(R.id.nombreAdmi);
        puestoAdmi = findViewById(R.id.puestoAdmi);
        flechaRegresar = findViewById(R.id.flechaRegresar);
        imagePP = findViewById(R.id.imagePP);
        botonModificar = findViewById(R.id.botonModificar);

        flechaRegresar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(_7_VerPerfil_Admi.this, _6_Principal_Admi.class));
                    }
                });

        botonModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(_7_VerPerfil_Admi.this, _8_ModificarPerfil_Admi.class));
            }
        });

    }

}
