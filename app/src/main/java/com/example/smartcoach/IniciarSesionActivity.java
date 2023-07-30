package com.example.smartcoach;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class IniciarSesionActivity extends AppCompatActivity {

    EditText email, contraseña;
    Button unete, iniciarSesion, registrate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_sesion);

        email = findViewById(R.id.email);
        contraseña = findViewById(R.id.contraseña);
        iniciarSesion = findViewById(R.id.btnIniciarSesion);
        unete = findViewById(R.id.btnUnete);
        registrate = findViewById(R.id.btnRegistrate);

        //Agregar lógica para iniciar sesión!
        unete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IniciarSesionActivity.this, RegistrarseAdminUnoActivity.class);
                startActivity(intent);
            }
        });
        registrate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IniciarSesionActivity.this, RegistrarseUserUnoActivity.class);
                startActivity(intent);
            }
        });
    }
}
