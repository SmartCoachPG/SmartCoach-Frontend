package com.example.smartcoach.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartcoach.R;

import api.SharedPreferencesUtil;

public abstract class BaseActivityCliente extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        setupMenu();
    }

    private void setupMenu() {
        ImageButton btnMapaCliente = findViewById(R.id.boton_mapa_menuU);
        ImageButton btnRutinaCliente = findViewById(R.id.boton_casa_menuU);
        ImageButton btnEquipoCliente = findViewById(R.id.boton_equipo_menuU);
        ImageButton btnPerfilCliente = findViewById(R.id.boton_perfil_menuU);

        btnPerfilCliente.setOnClickListener(v -> {
            startActivity(new Intent(this, _63_Principal_Usuario.class));

        });

        btnRutinaCliente.setOnClickListener(v -> {
            String rutina = SharedPreferencesUtil.getRutina(BaseActivityCliente.this);
            Log.d("RUTINAAAA", "tiene valor: "+rutina);
            if (rutina.equals("0")) {
                startActivity(new Intent(this, _95_crear_rutina_usuario.class));
            } else {
                startActivity(new Intent(this, _98_ver_rutina_ejercicio_usuario.class));
            }
        });


    }
}
