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
import androidx.appcompat.app.AppCompatActivity;

import com.example.smartcoach.R;

public class _16_ver_equipo_admin_todos extends AppCompatActivity {
    TextView tituloBienvenida, setTextAdminName, descripcion;
    View view2;
    EditText editTextBuscaEquipo;
    ImageButton btnBuscar;
    Button btnGuardarCambios, btnEncuentraEquipo, btnTodos, btnMios;
    ImageView rectanguloFondoEquipo;
    ScrollView scrollView_16;
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
        btnGuardarCambios = findViewById(R.id.btnGuardarCambios_16);
        btnEncuentraEquipo = findViewById(R.id.btnEncuentraEquipo_16);
        rectanguloFondoEquipo = findViewById(R.id.rectanguloFondoEquipo_16);
        btnTodos = findViewById(R.id.btnTodos_16);
        btnMios = findViewById(R.id.btnMios_16);
        scrollView_16 = findViewById(R.id.scrollView_16);
        equipo1 = findViewById(R.id.equipo_1_16);
        equipo2 = findViewById(R.id.equipo_2_16);
        equipo3 = findViewById(R.id.equipo_3_16);
        otrosMenu = findViewById(R.id._otros_menu_admi);

        btnMios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(_16_ver_equipo_admin_todos.this, _17_ver_equipo_admin_creados.class);
                startActivity(intent);
            }
        });

    }
}
