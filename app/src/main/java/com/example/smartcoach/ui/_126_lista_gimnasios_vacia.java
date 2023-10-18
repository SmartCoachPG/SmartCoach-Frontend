package com.example.smartcoach.ui;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Button;

import com.example.smartcoach.R;

public class _126_lista_gimnasios_vacia extends BaseActivityCliente{
    TextView tituloSuscríbete_126, descripcion_126;
    ImageView rectBusqueda_126;
    EditText editTextGym_126;
    ImageButton btnBusquedaGym_126;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout._126_lista_gimnasios_vacia);

        tituloSuscríbete_126 = findViewById(R.id.tituloSuscríbete_126);
        rectBusqueda_126 = findViewById(R.id.rectBusqueda_126);
        editTextGym_126 = findViewById(R.id.editTextGym_126);
        btnBusquedaGym_126 = findViewById(R.id.btnBusquedaGym_126);
        descripcion_126 = findViewById(R.id.descripcion_126);

    }

}
