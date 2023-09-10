package com.example.smartcoach;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class inicioAdminSeisActivity extends AppCompatActivity {
    View rectanguloSup, barraInf;
    ImageButton flechaRegresar, cerrarSesionAdmin, info_admin, verificacion, eliminarCuenta, imagePP, btnMapaBI, btnMancuernaBI, btnPerfilBI;
    Button btnCerrarSesion, btnEliminarCuenta, btnVerificacion, btnInfoPersonal;
    TextView nombreAdmin, cargoAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seis_pantalla_inicio_admin);

        rectanguloSup = findViewById(R.id.rectangulo_naranja_superior);
        barraInf = findViewById(R.id.barraInferior);
        flechaRegresar = findViewById(R.id.flechaRegresar);
        cerrarSesionAdmin = findViewById(R.id.cerrarSesionAdmin);
        info_admin = findViewById(R.id.info_admin);
        verificacion = findViewById(R.id.limitacionesFisicas);
        eliminarCuenta = findViewById(R.id.eliminarCuenta);
        imagePP = findViewById(R.id.imagePP);
        btnMapaBI = findViewById(R.id.btnMapaBI);
        btnMancuernaBI = findViewById(R.id.btnMancuernaBI);
        btnPerfilBI = findViewById(R.id.btnPerfilBI);
        btnCerrarSesion = findViewById(R.id.btnCerrarSesion);
        btnEliminarCuenta = findViewById(R.id.btnEliminarCuenta);
        btnVerificacion = findViewById(R.id.btnLimitacionesFisicas);
        btnInfoPersonal = findViewById(R.id.btnInfoPersonal);
        nombreAdmin = findViewById(R.id.nombreAdmin);
        cargoAdmin = findViewById(R.id.objetivoUser);
    }
}