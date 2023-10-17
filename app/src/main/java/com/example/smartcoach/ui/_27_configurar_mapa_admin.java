package com.example.smartcoach.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartcoach.R;
public class _27_configurar_mapa_admin extends BaseActivityAdmi {

    TextView descripcion_27;
    Button btnVer_27, btnCrearMapa_27, btnModificarInfoMapa_27, btnModificarEquiposElementos_27, btnEliminarMapa_27;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout._27_configurar_mapa_admin);
        getSupportActionBar().hide();

        descripcion_27 = findViewById(R.id.descripcion_27);
        btnVer_27 = findViewById(R.id.btnVer_27);
        btnCrearMapa_27 = findViewById(R.id.btnCrearMapa_27);
        btnModificarInfoMapa_27 = findViewById(R.id.btnModificarInfoMapa_27);
        btnModificarEquiposElementos_27 = findViewById(R.id.btnModificarEquiposElementos_27);
        btnEliminarMapa_27 = findViewById(R.id.btnEliminarMapa_27);

        btnVer_27.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(_27_configurar_mapa_admin.this, _35_ver_mapa_admin.class);
                startActivity(intent);
            }
        });
    }
}
