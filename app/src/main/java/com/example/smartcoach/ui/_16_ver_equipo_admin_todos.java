package com.example.smartcoach.ui;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartcoach.R;

public class _16_ver_equipo_admin_todos extends BaseActivityCliente {
    TextView tituloBienvenida, setTextAdminName, descripcion, nombreEquipo, nombreReferenciaEquipo, desc_equipo, textbtnAgregar, textbtnEliminar;
    View view2;
    EditText editTextBuscaEquipo;
    ImageButton btnBuscar, btnAgregarEquipo, btnEliminarEquipo;
    Button btnCrearEquipo, btnEncuentraEquipo, btnTodos, btnMios;
    ImageView rectanguloFondoEquipo, imageEquipo;
    RecyclerView recyclerView;
    View equipo1, equipo2, equipo3, otrosMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout._16_ver_equipo_admin_todos);
        getSupportActionBar().hide();

        tituloBienvenida = findViewById(R.id.tituloBienvenida_16);
        setTextAdminName = findViewById(R.id.setTextAdminName);
        descripcion = findViewById(R.id.descripcion_16);
        view2 = findViewById(R.id.view_16);
        editTextBuscaEquipo = findViewById(R.id.editTextBuscaEquipo);
        btnBuscar = findViewById(R.id.btnBuscar);
        btnCrearEquipo = findViewById(R.id.btnCrearEquipo_16);
        btnEncuentraEquipo = findViewById(R.id.btnEncuentraEquipo_16);
        rectanguloFondoEquipo = findViewById(R.id.rectanguloFondoEquipo_16);
        btnTodos = findViewById(R.id.btnTodos_16);
        btnMios = findViewById(R.id.btnMios_16);
        recyclerView = findViewById(R.id.recyclerView_16);
        otrosMenu = findViewById(R.id._otros_menu_admi);

        imageEquipo = findViewById(R.id.imageEquipo);
        nombreEquipo = findViewById(R.id.nombreEquipo);
        nombreReferenciaEquipo = findViewById(R.id.nombreReferenciaEquipo);
        desc_equipo = findViewById(R.id.desc_equipo);
        textbtnAgregar=findViewById(R.id.textbtnAgregar);

        imageEquipo = findViewById(R.id.imageEquipo);
        nombreEquipo = findViewById(R.id.nombreEquipo);
        nombreReferenciaEquipo = findViewById(R.id.nombreReferenciaEquipo);
        desc_equipo = findViewById(R.id.desc_equipo);
        textbtnEliminar=findViewById(R.id.textbtnAgregar);
        btnEliminarEquipo=findViewById(R.id.btnEliminar);

        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.caja_agregar_equipo_todos, null);

        btnAgregarEquipo = dialogView.findViewById(R.id.btnAgregar);


        btnAgregarEquipo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                View dialogView = getLayoutInflater().inflate(R.layout.caja_eliminar_equipo_todos, null);

                Button btnEliminar = dialogView.findViewById(R.id.btnEliminar);

                btnEliminar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });

                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setView(dialogView);

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        btnMios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(_16_ver_equipo_admin_todos.this, _17_ver_equipo_admin_creados.class);
                startActivity(intent);
            }
        });

        btnCrearEquipo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(_16_ver_equipo_admin_todos.this, _19_crear_equipo_admin.class);
                startActivity(intent);
            }
        });

    }
}
