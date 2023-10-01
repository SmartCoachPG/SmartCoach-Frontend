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
public class _27_configurar_mapa_admin extends AppCompatActivity {

    TextView descripcion_27;
    Button btnVer_27, btnCrearMapa_27, btnModificarInfoMapa_27, btnModificarEquiposElementos_27, btnEliminarMapa_27;
   // ImageButton btnMapa, btnEquipo, btnPerfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout._27_configurar_mapa_admin);

        descripcion_27 = findViewById(R.id.descripcion_27);
        btnVer_27 = findViewById(R.id.btnVer_27);
        btnCrearMapa_27 = findViewById(R.id.btnCrearMapa_27);
        btnModificarInfoMapa_27 = findViewById(R.id.btnModificarInfoMapa_27);
        btnModificarEquiposElementos_27 = findViewById(R.id.btnModificarEquiposElementos_27);
        btnEliminarMapa_27 = findViewById(R.id.btnEliminarMapa_27);
/*
        View menuAdmin_View = LayoutInflater.from(this).inflate(R.layout._otros_menu_admi, null);

        btnMapa = menuAdmin_View.findViewById(R.id.btnMapaAdmin);
        btnEquipo = menuAdmin_View.findViewById(R.id.btnMancuernaAdmin);
        btnPerfil = menuAdmin_View.findViewById(R.id.btnPerfilAdmin);

        btnMapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Botón Mapa", "Se hizo clic en el botón de mapa");
                Toast.makeText(_27_configurar_mapa_admin.this, "Hiciste clic en el botón de mapa", Toast.LENGTH_SHORT).show();
            }
        });

 */
    }
}
