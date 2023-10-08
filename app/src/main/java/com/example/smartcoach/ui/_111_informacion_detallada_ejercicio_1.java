package com.example.smartcoach.ui;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartcoach.R;

import java.util.List;

public class _111_informacion_detallada_ejercicio_1 extends AppCompatActivity {

    ImageView rectanguloFondoImageView;
    TextView tituloEquipoTextView;
    RecyclerView recyclerViewEquipo, recyclerViewMusculoInvolucrado;
    TextView tituloMusculosInvolucradosTextView;

    private boolean isEquipo;
    private EquipoAdapter equipoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout._111_informacion_detallada_ejercicio_1);


        isEquipo = getIntent().getBooleanExtra("esEquipo", true);

        rectanguloFondoImageView = findViewById(R.id.rectanguloFondo_111);
        tituloEquipoTextView = findViewById(R.id.tituloEquipo_111);
        recyclerViewEquipo = findViewById(R.id.recyclerViewEquipo);
        recyclerViewMusculoInvolucrado =findViewById(R.id.recyclerViewMusculoInvolucrado);
        tituloMusculosInvolucradosTextView = findViewById(R.id.tituloMusculosInvolucrados_111);


        if (isEquipo) {
         //   equipoAdapter = new EquipoAdapter(this, obtenerListaEquipos());
            recyclerViewEquipo.setAdapter(equipoAdapter); // RecyclerView de equipo
        } else {
         //   equipoAdapter = new EquipoAdapter(this, obtenerListaMusculos(), true);
            recyclerViewMusculoInvolucrado.setAdapter(equipoAdapter); //RecyclerView de músculos involucrados
        }
    }

    /* back
    private List<String> obtenerListaEquipos() {
        // Lógica para obtener la lista de equipos
    }

    private List<String> obtenerListaMusculos() {
        // Lógica para obtener la lista de músculos
    }

     */
}
