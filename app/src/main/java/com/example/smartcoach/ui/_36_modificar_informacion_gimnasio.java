package com.example.smartcoach.ui;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.smartcoach.R;

public class _36_modificar_informacion_gimnasio extends BaseActivityAdmi {
    ImageView rectanguloTitulo_36;
    TextView titulo_36, descripcionModificarGym_36;
    ImageButton btnRegresar_36, imagenGimnasio_36;
    EditText setTextnombreGimnasio_36, setTextDireccionGimnasio_36, setTextBarrioGimnasio_36, setTextAnchoGimnasio_36, setTextAltoGimnasio_36, setTextPisosGimnasio_36;
    Button btnCrearMapa_36;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout._36_modificar_informacion_gimnasio);
        getSupportActionBar().hide();



        rectanguloTitulo_36 = findViewById(R.id.rectanguloTitulo_36);
        titulo_36 = findViewById(R.id.titulo_36);
        btnRegresar_36 = findViewById(R.id.btnRegresar_36);
        descripcionModificarGym_36 = findViewById(R.id.descripcionModificarGym_36);
        setTextnombreGimnasio_36 = findViewById(R.id.setTextnombreGimnasio_36);
        imagenGimnasio_36 = findViewById(R.id.imagenGimnasio_36);
        setTextDireccionGimnasio_36 = findViewById(R.id.setTextDireccionGimnasio_36);
        setTextBarrioGimnasio_36 = findViewById(R.id.setTextBarrioGimnasio_36);
        setTextAnchoGimnasio_36 = findViewById(R.id.setTextAnchoGimnasio_36);
        setTextAltoGimnasio_36 = findViewById(R.id.setTextAltoGimnasio_36);
        setTextPisosGimnasio_36 = findViewById(R.id.setTextPisosGimnasio_36);
        btnCrearMapa_36 = findViewById(R.id.btnCrearMapa_36);

        btnRegresar_36.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }
}
