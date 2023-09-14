package com.example.smartcoach.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartcoach.R;

public class _7_ver_perfil_admi extends AppCompatActivity {

    EditText texoIngresoNombre, textoIngresoEmail,textoIngresoCedula, textoIngresoPuesto;
    TextView nombreAdmi, puestoAdmi;
    ImageButton flechaRegresar, imagePP;

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


        flechaRegresar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(_7_ver_perfil_admi.this, _6_PrincipalAdmi.class));
                    }
                });


    }

}
