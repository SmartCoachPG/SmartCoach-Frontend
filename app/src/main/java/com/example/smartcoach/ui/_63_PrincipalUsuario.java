package com.example.smartcoach.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartcoach.R;

public class _63_PrincipalUsuario extends AppCompatActivity {

    ImageButton imagePP, infoUser,limitacionesFisicas,horarioRutinas,registroProgreso,objetivos,composicionCorporal,eliminarCuenta,cerrarSesion;
    TextView nombreUser,infoUserTexto,limitacionesFisicasTexto,horarioRutinasTexto,registroProgresoTexto,composicionCorporalTexo,eliminarCuentaTexto,cerrarSesionTexto, objetivosTexto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout._63_principal_usuario);

        imagePP = findViewById(R.id.imagePP);
        nombreUser = findViewById(R.id.nombreUser);
        infoUser= findViewById(R.id.info_user);
        infoUserTexto= findViewById(R.id.info_user_texto);
        limitacionesFisicas = findViewById(R.id.limitaciones_fisicas);
        limitacionesFisicasTexto = findViewById(R.id.limitaciones_fisicas_texto);
        horarioRutinas = findViewById(R.id.horario_rutinas);
        horarioRutinasTexto = findViewById(R.id.horario_rutinas_texto);
        registroProgreso = findViewById(R.id.registro_progreso);
        registroProgresoTexto = findViewById(R.id.registro_progreso_texto);
        objetivos = findViewById(R.id.objetivos);
        objetivosTexto= findViewById(R.id.objetivos_texto);
        composicionCorporal = findViewById(R.id.composicion_corporal);
        composicionCorporalTexo = findViewById(R.id.composicion_corporal_texto);
        eliminarCuenta = findViewById(R.id.eliminar_cuenta);
        eliminarCuentaTexto = findViewById(R.id.eliminar_cuenta_texto);
        cerrarSesion = findViewById(R.id.cerrar_sesion);
        cerrarSesionTexto = findViewById(R.id.cerrar_sesion_texto);


        infoUser.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(_63_PrincipalUsuario.this, _64_VerPerfil_Usuario.class));
                    }
                });

        infoUserTexto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(_63_PrincipalUsuario.this, _64_VerPerfil_Usuario.class));
            }
        });



    }
}
