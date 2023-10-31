package com.example.smartcoach.ui;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.example.smartcoach.R;

public class _26_modificar_equipo_admin_3 extends AppCompatActivity {
    ImageButton btnCerrar_26;
    TextView tituloSeleccioneMT_26;
    RecyclerView recyclerView_26;
    Button btnAceptarMT_26;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout._26_modificar_equipo_admin_3);

        btnCerrar_26 = findViewById(R.id.btn_cerrar_26);
        tituloSeleccioneMT_26 = findViewById(R.id.tituloSeleccioneMT);
        recyclerView_26 = findViewById(R.id.recyclerView_26);
        btnAceptarMT_26 = findViewById(R.id.btnAceptarMT_26);

    }
}

