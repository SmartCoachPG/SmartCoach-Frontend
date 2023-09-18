package com.example.smartcoach.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.smartcoach.R;

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
        nombreAdmi = findViewById(R.id.nombre_admin_8);
        puestoAdmi = findViewById(R.id.puesto_admin_8);
        textoIngresoCedula= findViewById(R.id.textoIngresoCedula_admin_8);
        textoIngresoNombre = findViewById(R.id.texto_Ingreso_Nombre_admin_8);
        textoIngresoEmail = findViewById(R.id.textoIngresoEmail_admin_8);
        textoIngresoPuesto = findViewById(R.id.textoIngresoPuesto_admin_8);
        botonGuardardCambios = findViewById(R.id.botonGuardarCambios_admin_8);

        flechaRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(_8_ModificarPerfil_Admi.this, _7_VerPerfil_Admi.class));
            }
        });

        botonGuardardCambios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Aquí puedes agregar el código para guardar los cambios en el perfil del administrador.

                // Muestra un Toast para confirmar que los cambios se han guardado.
                Toast.makeText(_8_ModificarPerfil_Admi.this, "Cambios guardados correctamente", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
