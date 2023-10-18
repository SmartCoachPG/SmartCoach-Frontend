package com.example.smartcoach.ui;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;

import com.example.smartcoach.R;

public class _120_ver_informacion_gimnasio extends BaseActivityCliente{

    ImageView rectanguloTitulo_120, setImageGym_120;
    TextView titulo_120, descripcion_120, tituloGym_120, textBarrio, barrioGym_120, textDireccion_120, direccionGym_120;
    Button btnVerMapa_120, btnDesuscribirse_120;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout._120_ver_informacion_gimnasio);
        getSupportActionBar().hide();

        rectanguloTitulo_120 = findViewById(R.id.rectanguloTitulo_120);
        titulo_120 = findViewById(R.id.titulo_120);
        descripcion_120 = findViewById(R.id.descripcion_120);
        tituloGym_120 = findViewById(R.id.tituloGym_120);
        setImageGym_120 = findViewById(R.id.setImageGym_120);
        textBarrio = findViewById(R.id.textBarrio);
        barrioGym_120 = findViewById(R.id.barrioGym_120);
        textDireccion_120 = findViewById(R.id.textDireccion_120);
        direccionGym_120 = findViewById(R.id.direccionGym_120);
        btnVerMapa_120 = findViewById(R.id.btnVerMapa_120);
        btnDesuscribirse_120 = findViewById(R.id.btnDesuscribirse_120);


    }


}
