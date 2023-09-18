package com.example.smartcoach.ui;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartcoach.R;

public class _63_Principal_Usuario extends AppCompatActivity {

    ImageButton imagePP, infoUser,limitacionesFisicas,horarioRutinas,registroProgreso,objetivos,composicionCorporal,eliminarCuenta,cerrarSesion;
    TextView nombreUser,infoUserTexto,limitacionesFisicasTexto,horarioRutinasTexto,registroProgresoTexto,composicionCorporalTexo,eliminarCuentaTexto,cerrarSesionTexto, objetivosTexto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout._63_principal_usuario);

        imagePP = findViewById(R.id.imagePP);
        nombreUser = findViewById(R.id.nombreUser_63);
        infoUser= findViewById(R.id.info_user_63);
        infoUserTexto= findViewById(R.id.info_user_texto_63);
        limitacionesFisicas = findViewById(R.id.limitaciones_fisicas_63);
        limitacionesFisicasTexto = findViewById(R.id.limitaciones_fisicas_texto_63);
        horarioRutinas = findViewById(R.id.horario_rutinas_63);
        horarioRutinasTexto = findViewById(R.id.horario_rutinas_texto_63);
        registroProgreso = findViewById(R.id.registro_progreso_63);
        registroProgresoTexto = findViewById(R.id.registro_progreso_texto_63);
        objetivos = findViewById(R.id.objetivos_63);
        objetivosTexto= findViewById(R.id.objetivos_texto_63);
        composicionCorporal = findViewById(R.id.composicion_corporal_63);
        composicionCorporalTexo = findViewById(R.id.composicion_corporal_texto_63);
        eliminarCuenta = findViewById(R.id.eliminar_cuenta);
        eliminarCuentaTexto = findViewById(R.id.eliminar_cuenta_texto_63);
        cerrarSesion = findViewById(R.id.cerrar_sesion_63);
        cerrarSesionTexto = findViewById(R.id.cerrar_sesion_texto_63);


        infoUser.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(_63_Principal_Usuario.this, _64_VerPerfil_Usuario.class));
                    }
                });

        infoUserTexto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(_63_Principal_Usuario.this, _64_VerPerfil_Usuario.class));
            }
        });

        eliminarCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });

        eliminarCuentaTexto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });


    }

    private void showDialog() {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout._84_confirmacion_eliminacion_cuenta); // Usa el nombre de tu archivo XML
        dialog.getWindow().setBackgroundDrawable(null);

        Button botonConfirmar = dialog.findViewById(R.id.botonConfirmar);
        botonConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Acciones a realizar cuando se presiona "Confirmar"
                dialog.dismiss();
            }
        });

        Button botonCancelar = dialog.findViewById(R.id.botonCancelar);
        botonCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Acciones a realizar cuando se presiona "Cancelar"
                dialog.dismiss();
            }
        });

        dialog.show();
    }




}
