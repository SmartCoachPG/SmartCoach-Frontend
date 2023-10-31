package com.example.smartcoach.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;
import com.example.smartcoach.R;

public class _19_crear_equipo_admin extends BaseActivityAdmi {
    ImageButton atras;
    TextView tituloModificar, descripcionModificar, textSubaImagen, textoMusculos;
    EditText setTextnombreEquipo, setTextReferenciaEquipo, descripcionEquipo;
    ImageButton imagenEquipo, imageSubirImagen, imageAgregarMusculo;
    ScrollView scrollView;
    Button btnGuardarCambios;
    View menuAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout._19_crear_equipo_admin);
        getSupportActionBar().hide();

        atras = findViewById(R.id.atras_19);
        tituloModificar = findViewById(R.id.tituloModificar_19);
        descripcionModificar = findViewById(R.id.descripcionModificar_19);
        textSubaImagen = findViewById(R.id.textSubaImagen_19);
        textoMusculos = findViewById(R.id.textoMusculos_19);
        setTextnombreEquipo = findViewById(R.id.setTextnombreEquipo_19);
        setTextReferenciaEquipo = findViewById(R.id.setTextReferenciaEquipo_19);
        descripcionEquipo = findViewById(R.id.descripcionEquipo_19);
        imagenEquipo = findViewById(R.id.imagenEquipo_19);
        imageSubirImagen = findViewById(R.id.imageSubirImagen_19);
        imageAgregarMusculo = findViewById(R.id.imageAgregarMusculo_19);
        scrollView = findViewById(R.id.scrollView_19);
        btnGuardarCambios = findViewById(R.id.btnGuardarCambios_19);
        menuAdmin = findViewById(R.id.menuAdmin_19);

        atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}


