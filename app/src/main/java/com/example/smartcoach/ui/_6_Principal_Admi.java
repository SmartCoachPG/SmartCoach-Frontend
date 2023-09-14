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

public class _6_Principal_Admi extends AppCompatActivity {

    ImageButton flechaRegresar, eliminarCuenta, imagePP,verificacionAdmin,cerrarSesion,infoAdmi;
    TextView nombreAdmi, puestoAdmi, infoAdmiTexto,verificacionAdmiTexto,eliminarCuentaTexto,cerrarSesionTexto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout._6_principal_admi);

        // Inicializa las vistas
        flechaRegresar = findViewById(R.id.flechaRegresar);
        imagePP = findViewById(R.id.imagePP);
        nombreAdmi = findViewById(R.id.nombreAdmin);
        puestoAdmi = findViewById(R.id.puestoAdmi);
        infoAdmi = findViewById(R.id.info_admi);
        verificacionAdmin = findViewById(R.id.verificacion_admi);
        eliminarCuenta = findViewById(R.id.eliminar_cuenta);
        cerrarSesion = findViewById(R.id.cerrar_sesion);
        infoAdmiTexto = findViewById(R.id.info_admi_texto);
        verificacionAdmiTexto = findViewById(R.id.verificacion_admi_texto);
        eliminarCuentaTexto = findViewById(R.id.eliminar_cuenta_texto);
        cerrarSesionTexto = findViewById(R.id.cerrar_sesion_texto);

        infoAdmi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(_6_Principal_Admi.this, _7_VerPerfil_Admi.class));
            }
        });

        infoAdmiTexto.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(_6_Principal_Admi.this, _7_VerPerfil_Admi.class));
                    }
                });

        eliminarCuenta.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showDialog();
                    }
                });

        eliminarCuentaTexto.setOnClickListener(
                new View.OnClickListener() {
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