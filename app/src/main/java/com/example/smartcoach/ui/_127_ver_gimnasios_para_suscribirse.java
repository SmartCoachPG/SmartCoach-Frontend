package com.example.smartcoach.ui;

import android.os.Bundle;
        import android.widget.TextView;

        import android.widget.EditText;
        import android.widget.ImageButton;
        import android.widget.ScrollView;
        import androidx.appcompat.app.AppCompatActivity;

import com.example.smartcoach.R;

public class _127_ver_gimnasios_para_suscribirse extends AppCompatActivity {
    TextView tituloSuscríbete_127;
    EditText editTextGym_127;
    ImageButton btnBusquedaGym_127;
    ScrollView scrollViewGymSuscribirse_127;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout._127_ver_gimnasios_para_suscribirse);
        getSupportActionBar().hide();

        tituloSuscríbete_127 = findViewById(R.id.tituloSuscríbete_127);
        editTextGym_127 = findViewById(R.id.editTextGym_127);
        btnBusquedaGym_127 = findViewById(R.id.btnBusquedaGym_127);
        scrollViewGymSuscribirse_127 = findViewById(R.id.scrollViewGymSuscribirse);

    }
}