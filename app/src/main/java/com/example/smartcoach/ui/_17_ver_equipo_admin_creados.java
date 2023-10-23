package com.example.smartcoach.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.smartcoach.R;

public class _17_ver_equipo_admin_creados extends AppCompatActivity {
    TextView tituloBienvenida, setTextAdminName, descripcion, nombreEquipo, nombreReferenciaEquipo, descEquipo;
    View view2;
    EditText editTextBuscaEquipo;
    ImageButton btnBuscar, btnModificarEquipo, btnEliminar;
    Button btnCrearEquipo, btnEncuentraEquipo, btnTodos, btnMios;
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
        btnCrearEquipo = findViewById(R.id.btnCrearEquipo_17);
        btnEncuentraEquipo = findViewById(R.id.btnEncuentraEquipo_17);
        btnTodos = findViewById(R.id.btnTodos_17);
        btnMios = findViewById(R.id.btnMios_17);
        rectanguloFondoEquipo = findViewById(R.id.rectanguloFondoEquipo_17);
        scrollView = findViewById(R.id.scrollView_17);
        equipo1 = findViewById(R.id.equipo_1_17);
        equipo2 = findViewById(R.id.equipo_2_17);
        equipo3 = findViewById(R.id.equipo_3_17);
        otrosMenu = findViewById(R.id._otros_menu_admi);

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

        btnCrearEquipo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(_17_ver_equipo_admin_creados.this, _19_crear_equipo_admin.class);
                startActivity(intent);
            }
        });

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarDialogoConfirmacionEliminacion();
            }
        });

    }
    private void mostrarDialogoConfirmacionEliminacion() {
        // Infla la vista personalizada para el cuadro de diálogo
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout._16_eliminar_equipo, null);

        // Crea el cuadro de diálogo
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);

        // Configura eventos y lógica para los botones en la vista personalizada
        AppCompatButton botonConfirmar = dialogView.findViewById(R.id.botonConfirmar_16_eliminar);
        AppCompatButton botonCancelar = dialogView.findViewById(R.id.botonCancelar_16_eliminar);

        botonConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lógica para eliminar el equipo
                // Aquí debes implementar la lógica para eliminar el equipo
                alertDialog.dismiss();
            }
        });

        botonCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        // Crea el cuadro de diálogo
        alertDialog = builder.create();
        alertDialog.show();
    }
}