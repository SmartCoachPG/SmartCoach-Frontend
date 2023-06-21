package com.example.smartcoach;

import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class IniciarSesionActivity extends AppCompatActivity {

    EditText email, contraseña;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_sesion);

        email = findViewById(R.id.email);
        contraseña = findViewById(R.id.contraseña);

    }
}
