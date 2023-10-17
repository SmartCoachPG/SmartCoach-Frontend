package com.example.smartcoach.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartcoach.R;

public class _35_ver_mapa_admin extends AppCompatActivity {

    GridLayout gridLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout._35_ver_mapa_admin);

        gridLayout = findViewById(R.id.gridLayout_35);

        cargarCuadrados();
    }

    private void cargarCuadrados()
    {

        int ancho = 10;
        int alto = 10;

        int tamañoCasilla = 32;
        final float scale = getResources().getDisplayMetrics().density;
        int tamañoCasillaPixels = (int) (tamañoCasilla * scale + 0.5f);

        gridLayout.setColumnCount(ancho);
        gridLayout.setRowCount(alto);

        for (int i = 0; i < alto; i++) {
            for (int j = 0; j < ancho; j++) {
                View cuadrado = new View(this);

                GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams(
                        GridLayout.spec(i), GridLayout.spec(j));
                layoutParams.width = tamañoCasillaPixels;
                layoutParams.height = tamañoCasillaPixels;

                cuadrado.setLayoutParams(layoutParams);
                cuadrado.setBackgroundResource(R.drawable.fondo_mapa);

                gridLayout.addView(cuadrado);
            }
        }

        cargarImagenes();

    }

    private void cargarImagenes()
    {
        ImageView imageView = new ImageView(this);

        GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams(
                GridLayout.spec(0), GridLayout.spec(0));  // 0,0 es para la primera fila, primera columna
        layoutParams.width =  (int) (32 * getResources().getDisplayMetrics().density + 0.5f);
        layoutParams.height =  (int) (32 * getResources().getDisplayMetrics().density + 0.5f);

        imageView.setLayoutParams(layoutParams);
        imageView.setImageResource(R.drawable.icon_mancuerna_ne_fondo_transp);  // Reemplaza "tu_imagen" con el nombre de tu recurso de imagen

        gridLayout.addView(imageView);

    }
}
