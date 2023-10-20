package com.example.smartcoach.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.smartcoach.R;


public class _37_crea_tu_gimnasio_admin extends BaseActivityAdmi{
    ImageView rectanguloTitulo_37;
    ImageButton btnRegresar_37;
    TextView tituloCrear_37, descripcionCrear_37, textSubaImagen_37;
    EditText setTextnombreGimnasio_37, setTextDireccionGimnasio_37, setTextBarrioGimnasio_37, setTextAnchoGimnasio_37, setTextAltoGimnasio_37, setTextPisosGimnasio_37;
    ImageButton imagenGimnasio_37, imageSubirImagen_37;
    Button btnCrearGimnasio_37;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout._37_crear_tu_gimnasio_admin);

        rectanguloTitulo_37 = findViewById(R.id.rectanguloTitulo_37);
        btnRegresar_37 = findViewById(R.id.btnRegresar_37);
        tituloCrear_37 = findViewById(R.id.tituloCrear_37);
        descripcionCrear_37 = findViewById(R.id.descripcionCrear_37);
        setTextnombreGimnasio_37 = findViewById(R.id.setTextnombreGimnasio_37);
        imagenGimnasio_37 = findViewById(R.id.imagenGimnasio_37);
        textSubaImagen_37 = findViewById(R.id.textSubaImagen_37);
        imageSubirImagen_37 = findViewById(R.id.imageSubirImagen_37);
        setTextDireccionGimnasio_37 = findViewById(R.id.setTextDireccionGimnasio_37);
        setTextBarrioGimnasio_37 = findViewById(R.id.setTextBarrioGimnasio_37);
        setTextAnchoGimnasio_37 = findViewById(R.id.setTextAnchoGimnasio_37);
        setTextAltoGimnasio_37 = findViewById(R.id.setTextAltoGimnasio_37);
        setTextPisosGimnasio_37 = findViewById(R.id.setTextPisosGimnasio_37);
        btnCrearGimnasio_37 = findViewById(R.id.btnCrearGimnasio_37);

        btnRegresar_37.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }
}
