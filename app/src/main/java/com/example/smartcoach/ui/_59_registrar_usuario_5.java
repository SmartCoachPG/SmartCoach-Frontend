package com.example.smartcoach.ui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;
import com.example.smartcoach.R;
import android.view.View;

import java.util.ArrayList;

import model.Exercise.Rutina;
import model.User.UsuarioCliente;
import model.User.Valor;

public class _59_registrar_usuario_5 extends AppCompatActivity {

    TextView tituloLimitacionesFisicas, descripcionLimitacionesFisicas, condicionesMedicas;
    ImageButton barraBusquedaB1, barraBusquedaB2, btnBusqueda;
    EditText textoBarraBusqueda;
    Button btnListo;
    ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout._59_registrar_usuario_5);
        getSupportActionBar().hide();

        /*
        UsuarioCliente usuarioCliente = (UsuarioCliente) getIntent().getSerializableExtra("usuarioCliente");
        Log.d("InfoRecibida", "UsuarioCliente: " + usuarioCliente.toString()); // Asegúrate de que UsuarioCliente tenga un método toString() adecuado.

        int musculoObjetivo = getIntent().getIntExtra("musculoObjetivo", 0); // 0 es un valor predeterminado en caso de que no se encuentre el extra.
        Log.d("InfoRecibida", "MusculoObjetivo: " + musculoObjetivo);

        ArrayList<Rutina> listaRutinas = (ArrayList<Rutina>) getIntent().getSerializableExtra("listaRutinas");
        Log.d("InfoRecibida", "ListaRutinas: " + listaRutinas.toString()); // Asegúrate de que Rutina tenga un método toString() adecuado.

        ArrayList<Valor> listaValores = (ArrayList<Valor>) getIntent().getSerializableExtra("listaValoresComposicionCorporal");
        Log.d("InfoRecibida", "ListaValores: " + listaValores.toString()); // Asegúrate de que Valor tenga un método toString() adecuado.
        */

        tituloLimitacionesFisicas = findViewById(R.id.tituloLimitacionesFisicas_59);
        descripcionLimitacionesFisicas = findViewById(R.id.descripcionLimitacionesFisicas_59);
        barraBusquedaB1 = findViewById(R.id.barra_busqueda_b_59);
        barraBusquedaB2 = findViewById(R.id.rectanguloLargo);
        textoBarraBusqueda = findViewById(R.id.texto_barra_busqueda_59);
        btnBusqueda = findViewById(R.id.btnBusqueda);
        condicionesMedicas = findViewById(R.id.condicionesMedicas);
        btnListo = findViewById(R.id.btnListo_59);
        scrollView = findViewById(R.id.scrollView_59);

        btnBusqueda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Debug", "btnBusqueda clickeado");
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(_59_registrar_usuario_5.this);

                View dialogView = getLayoutInflater().inflate(R.layout._59_2_registrar_usuario_5, null);
                dialogBuilder.setView(dialogView);

                AlertDialog dialog = dialogBuilder.create();

                Button btnCerrar = dialogView.findViewById(R.id.btnAceptarSLF);
                btnCerrar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });

        btnListo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Debug", "btnListo clickeado");
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(_59_registrar_usuario_5.this);

                View dialogView = getLayoutInflater().inflate(R.layout._60_mensaje_advertencia_limitaciones_fisicas, null);
                dialogBuilder.setView(dialogView);

                AlertDialog dialog = dialogBuilder.create();

                Button btnContinuar = dialogView.findViewById(R.id.btnContinuarLF);
                btnContinuar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                       // Intent intent = new Intent(_59_registrar_usuario_5.this, _63_mi_actividad.class);
                        //startActivity(intent);
                    }
                });
                dialog.show();
            }
        });


    }
}
