package com.example.smartcoach.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.smartcoach.R;

import org.w3c.dom.Text;

public class _8_ModificarPerfil_Admi extends AppCompatActivity {

    ImageButton flechaRegresar,imagePP;
    TextView nombreAdmi,puestoAdmi,textoIngresoNombre,textoIngresoEmail,textoIngresoCedula,textoIngresoPuesto;
    AppCompatButton botonGuardardCambios;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout._8_modificar_perfil_admi);

        flechaRegresar = findViewById(R.id.flechaRegresar);
        imagePP = findViewById(R.id.imagePP);
        nombreAdmi = findViewById(R.id.nombreAdmin);
        puestoAdmi = findViewById(R.id.puestoAdmi);
        textoIngresoCedula= findViewById(R.id.textoIngresoCedula);
        textoIngresoNombre = findViewById(R.id.textoIngresoNombre);
        textoIngresoEmail = findViewById(R.id.textoIngresoEmail);
        textoIngresoPuesto = findViewById(R.id.textoIngresoPuesto);
        botonGuardardCambios = findViewById(R.id.botonGuardarCambios);

        flechaRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(_8_ModificarPerfil_Admi.this, _7_VerPerfil_Admi.class));
            }
        });

    }
}
