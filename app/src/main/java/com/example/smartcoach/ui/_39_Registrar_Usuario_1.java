package com.example.smartcoach.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.example.smartcoach.R;

public class _39_Registrar_Usuario_1 extends AppCompatActivity {
    Button btncomenzar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout._39_registrar_usuario_1);

        btncomenzar = findViewById(R.id.btnComenzarUser);

        btncomenzar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(_39_Registrar_Usuario_1.this, _40_Registrar_Usuario_2.class);
                 startActivity(intent);
            }
        });
    }
}
