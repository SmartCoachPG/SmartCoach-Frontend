package com.example.smartcoach.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartcoach.R;

public class _63_PrincipalUsuario extends AppCompatActivity {

    View rectanguloSup, barraInf;
    ImageButton flechaRegresar, cerrarSesionAdmin, info_admin, verificacion, eliminarCuenta, imagePP, btnMapaBI, btnMancuernaBI, btnPerfilBI, limitacionesFisicas, horariosRutinas, registroProgreso, objetivos, composicionCorporal;
    Button btnCerrarSesion, btnEliminarCuenta, btnVerificacion, btnInfoPersonal, btnLimitacionesFisicas, btnHorariosRutinas, btnRegistroProgreso, btnObjetivos, btnComposicionCorporal;
    TextView nombreUser, objetivoUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout._63_principal_usuario);

        rectanguloSup = findViewById(R.id.rectangulo_naranja_superior);
        barraInf = findViewById(R.id.barraInferior);
        flechaRegresar = findViewById(R.id.flechaRegresar);
        objetivoUser = findViewById(R.id.objetivoUser);
        limitacionesFisicas = findViewById(R.id.limitacionesFisicas);
        cerrarSesionAdmin = findViewById(R.id.cerrarSesionAdmin);
        horariosRutinas = findViewById(R.id.horariosRutinas);
        registroProgreso = findViewById(R.id.registroProgreso);
        objetivos = findViewById(R.id.objetivos);
        info_admin = findViewById(R.id.info_admin);
        verificacion = findViewById(R.id.limitacionesFisicas);
        eliminarCuenta = findViewById(R.id.eliminarCuenta);
        composicionCorporal = findViewById(R.id.composicionCorporal);
        imagePP = findViewById(R.id.imagePP);
        btnRegistroProgreso = findViewById(R.id.btnRegistroProgreso);
        btnLimitacionesFisicas = findViewById(R.id.btnLimitacionesFisicas);
        btnHorariosRutinas = findViewById(R.id.btnHorariosRutinas);
        btnObjetivos = findViewById(R.id.btnObjetivos);
        btnMapaBI = findViewById(R.id.btnMapaBI);
        btnComposicionCorporal = findViewById(R.id.btnComposicionCorporal);
        btnMancuernaBI = findViewById(R.id.btnMancuernaBI);
        btnPerfilBI = findViewById(R.id.btnPerfilBI);
        btnCerrarSesion = findViewById(R.id.btnCerrarSesion);
        btnEliminarCuenta = findViewById(R.id.btnEliminarCuenta);
        btnVerificacion = findViewById(R.id.btnLimitacionesFisicas);
        btnInfoPersonal = findViewById(R.id.btnInfoPersonal);
        nombreUser = findViewById(R.id.nombreUser);
    }
}
