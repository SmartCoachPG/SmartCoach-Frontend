package com.example.smartcoach.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.smartcoach.R;

public class _17_ver_equipo_admin_creados extends AppCompatActivity {
    TextView tituloBienvenida, setTextAdminName, descripcion, nombreEquipo, nombreReferenciaEquipo, descEquipo;
    View view2;
    EditText editTextBuscaEquipo;
    ImageButton btnBuscar, btnModificarEquipo, btnEliminar;
    Button btnGuardarCambios, btnEncuentraEquipo, btnTodos, btnMios;
    ImageView rectanguloFondoEquipo, imageEquipo;
    ScrollView scrollView;
    View equipo1, equipo2, equipo3, otrosMenu;
    private AlertDialog alertDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout._17_ver_equipo_admin_creados);
        getSupportActionBar().hide();

        tituloBienvenida = findViewById(R.id.tituloBienvenida_17);
        setTextAdminName = findViewById(R.id.setTextAdminName);
        descripcion = findViewById(R.id.descripcion_17);
        view2 = findViewById(R.id.view_17);
        editTextBuscaEquipo = findViewById(R.id.editTextBuscaEquipo_17);
        btnBuscar = findViewById(R.id.btnBuscar_17);
        btnGuardarCambios = findViewById(R.id.btnGuardarCambios_17);
        btnEncuentraEquipo = findViewById(R.id.btnEncuentraEquipo_17);
        btnTodos = findViewById(R.id.btnTodos_17);
        btnMios = findViewById(R.id.btnMios_17);
        rectanguloFondoEquipo = findViewById(R.id.rectanguloFondoEquipo_17);
        scrollView = findViewById(R.id.scrollView_17);
        equipo1 = findViewById(R.id.equipo_1_17);
        equipo2 = findViewById(R.id.equipo_2_17);
        equipo3 = findViewById(R.id.equipo_3_17);
        otrosMenu = findViewById(R.id._otros_menu_admi);

        setContentView(R.layout.caja_equipo_descripcion_creados);
        imageEquipo = findViewById(R.id.imageEquipo);
        nombreEquipo = findViewById(R.id.nombreEquipo);
        nombreReferenciaEquipo = findViewById(R.id.nombreReferenciaEquipo);
        descEquipo = findViewById(R.id.desc_equipo);
        btnModificarEquipo = findViewById(R.id.btnModificarEquipo);
        btnEliminar = findViewById(R.id.btnEliminar);

        btnTodos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(_17_ver_equipo_admin_creados.this, _16_ver_equipo_admin_todos.class);
                startActivity(intent);
            }
        });
    }
}
