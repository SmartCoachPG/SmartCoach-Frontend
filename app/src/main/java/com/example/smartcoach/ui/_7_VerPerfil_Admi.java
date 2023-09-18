package com.example.smartcoach.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

        texoIngresoNombre = findViewById(R.id.texto_Ingreso_Nombre_admin_7);
        textoIngresoEmail = findViewById(R.id.textoIngresoEmail_admin_7);
        textoIngresoCedula = findViewById(R.id.textoIngresoCedula_admin_7);
        textoIngresoPuesto = findViewById(R.id.textoIngresoPuesto_admin_7);
        nombreAdmi = findViewById(R.id.nombre_admin_7);
        puestoAdmi = findViewById(R.id.puesto_admin_7);
        flechaRegresar = findViewById(R.id.flechaRegresar);
        imagePP = findViewById(R.id.imagePP_admin_7);
        botonModificar = findViewById(R.id.boton_modificar_admin_7);

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
