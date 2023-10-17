package com.example.smartcoach.ui;

import android.os.Bundle;
        import android.widget.TextView;

        import android.widget.EditText;
        import android.widget.ImageButton;
        import android.widget.ScrollView;
        import androidx.appcompat.app.AppCompatActivity;

import com.example.smartcoach.R;

public class _127_ver_gimnasios_para_suscribirse extends AppCompatActivity {
    TextView tituloSuscríbete;
    EditText editTextGym;
    ImageButton btnBusquedaGym;
    ScrollView scrollViewGymSuscribirse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout._127_ver_gimnasios_para_suscribirse);
        getSupportActionBar().hide();

        tituloSuscríbete = findViewById(R.id.tituloSuscríbete_127);
        editTextGym = findViewById(R.id.editTextGym_127);
        btnBusquedaGym = findViewById(R.id.btnBusquedaGym_127);
        scrollViewGymSuscribirse = findViewById(R.id.scrollViewGymSuscribirse);

    }
}
