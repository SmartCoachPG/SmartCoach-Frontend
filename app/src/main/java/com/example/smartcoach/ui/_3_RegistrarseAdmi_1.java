package com.example.smartcoach.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.smartcoach.R;

public class _3_RegistrarseAdmi_1 extends AppCompatActivity {

    Button btncontinuar, btncancelar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout._3_registrarse_admi_1);

        btncontinuar = findViewById(R.id.btnContinuar);
        btncancelar = findViewById(R.id.btnCancelar);

        btncontinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(_3_RegistrarseAdmi_1.this, _4_RegistrarseAdmi_4.class);
                startActivity(intent);
            }
        });

        btncancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarDialogoConfirmacion();
            }
        });
    }

    private void mostrarDialogoConfirmacion() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Cancelar Registro");
        builder.setMessage("¡Hola administrador, bienvenido a SmartCoach!\n" +
                        "\n" +
                        "¿Estás seguro de que no quieres registrarte ahora? Si cambias de opinión, siempre estaremos aquí para ayudarte.\n" +
                        "\n" +
                        "¡Gracias por considerarnos y mucho éxito con tu gimnasio y clientes!\n" +
                        "\n" +
                        "Atentamente,\n" +
                        "El equipo de SmartCoach");
        builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(_3_RegistrarseAdmi_1.this, _1_PantallaInicio.class);
                startActivity(intent);
                finish();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        // Mostrar el cuadro de diálogo
        builder.show();
    }
}