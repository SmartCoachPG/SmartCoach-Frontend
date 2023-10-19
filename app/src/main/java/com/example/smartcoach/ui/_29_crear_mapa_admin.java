package com.example.smartcoach.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ImageButton;
import android.widget.Button;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.smartcoach.R;

public class _29_crear_mapa_admin extends BaseActivityAdmi{

    ImageView rectanguloTitulo_29;
    ImageButton btnRegresar_29;
    TextView tituloCrear_29, descripcionCrear_29;
    EditText setTextnombreGimnasio_29, setTextDireccionGimnasio_29, setTextBarrioGimnasio_29, setTextAnchoGimnasio_29, setTextAltoGimnasio_29, setTextPisosGimnasio_29;
    ImageButton imagenGimnasio_29, imageSubirImagen_29;
    TextView textSubaImagen_29;
    Button btnCrearMapa_29;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout._29_crear_mapa_admin);
        getSupportActionBar().hide();

        rectanguloTitulo_29 = findViewById(R.id.rectanguloTitulo_29);
        btnRegresar_29 = findViewById(R.id.btnRegresar_29);
        tituloCrear_29 = findViewById(R.id.tituloCrear_29);
        descripcionCrear_29 = findViewById(R.id.descripcionCrear_29);
        setTextnombreGimnasio_29 = findViewById(R.id.setTextnombreGimnasio_29);
        imagenGimnasio_29 = findViewById(R.id.imagenGimnasio_29);
        textSubaImagen_29 = findViewById(R.id.textSubaImagen_29);
        imageSubirImagen_29 = findViewById(R.id.imageSubirImagen_29);
        setTextDireccionGimnasio_29 = findViewById(R.id.setTextDireccionGimnasio_29);
        setTextBarrioGimnasio_29 = findViewById(R.id.setTextBarrioGimnasio_29);
        setTextAnchoGimnasio_29 = findViewById(R.id.setTextAnchoGimnasio_29);
        setTextAltoGimnasio_29 = findViewById(R.id.setTextAltoGimnasio_29);
        setTextPisosGimnasio_29 = findViewById(R.id.setTextPisosGimnasio_29);
        btnCrearMapa_29 = findViewById(R.id.btnCrearMapa_29);

        btnRegresar_29.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

}
