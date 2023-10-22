package com.example.smartcoach.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.TextView;
import com.example.smartcoach.R;
public class _100_iniciar_rutina extends AppCompatActivity {

    TextView setTextNombreEjercicio_100, numero_serie_inicial_100, guión_100, numero_serie_final_100, numeroTotalseries, nombreSeries, numeroTotalRepeticiones, nombreRepeticiones, pesoMaquina, nombrePeso, nombreLbs, tiempoDescanso, nombreSgs, nombreDescanso, nombrePausa, numeroSeries, nombreContSeries;
    ImageButton imageButton, btnSiguiente_100, btnAtras_100, btnPlay_100, btnMute_100, btnAdelantar_100;
    Chronometer tiempoIniciarPausa;
    boolean isChronometerRunning = false;
    boolean isChronometerStopped = false;
    long pausedTime = 0;

    RecyclerView recyclerViewEjercicios_100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout._100_iniciar_rutina);
        getSupportActionBar().hide();


        setTextNombreEjercicio_100 = findViewById(R.id.setTextNombreEjercicio_100);
        numero_serie_inicial_100 = findViewById(R.id.numero_serie_inicial_100);
        guión_100 = findViewById(R.id.guión_100);
        numero_serie_final_100 = findViewById(R.id.numero_serie_final_100);
        imageButton = findViewById(R.id.imageButton);
        numeroTotalseries = findViewById(R.id.numeroTotalseries);
        nombreSeries = findViewById(R.id.nombreSeries);
        numeroTotalRepeticiones = findViewById(R.id.numeroTotalRepeticiones);
        nombreRepeticiones = findViewById(R.id.nombreRepeticiones);
        pesoMaquina = findViewById(R.id.pesoMaquina);
        nombrePeso = findViewById(R.id.nombrePeso);
        nombreLbs = findViewById(R.id.nombreLbs);
        tiempoDescanso = findViewById(R.id.tiempoDescanso);
        nombreSgs = findViewById(R.id.nombreSgs);
        nombreDescanso = findViewById(R.id.nombreDescanso);
        btnSiguiente_100 = findViewById(R.id.btnSiguiente_100);
        nombrePausa = findViewById(R.id.nombrePausa);
        numeroSeries = findViewById(R.id.numeroSeries);
        nombreContSeries = findViewById(R.id.nombreContSeries);
        btnAtras_100 = findViewById(R.id.btnAtras_100);
        btnPlay_100 = findViewById(R.id.btnPlay_100);
        btnMute_100 = findViewById(R.id.btnMute_100);
        btnAdelantar_100 = findViewById(R.id.btnAdelantar_100);
        tiempoIniciarPausa = findViewById(R.id.tiempoIniciarPausa);
        tiempoIniciarPausa.setBase(SystemClock.elapsedRealtime());
        recyclerViewEjercicios_100 = findViewById(R.id.recyclerViewEjercicios_100);

        //Le da play o pausa
        btnPlay_100.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isChronometerRunning && !isChronometerStopped) {
                    // Detener el cronómetro solo si está en ejecución y no se ha detenido antes
                    tiempoIniciarPausa.stop();
                    pausedTime = SystemClock.elapsedRealtime() - tiempoIniciarPausa.getBase();
                    isChronometerStopped = true;
                } else {
                    // Iniciar el cronómetro y restaurar el tiempo pausado
                    tiempoIniciarPausa.setBase(SystemClock.elapsedRealtime() - pausedTime);
                    tiempoIniciarPausa.start();
                    isChronometerRunning = true;
                    isChronometerStopped = false;
                }
            }
        });

        //Reinicia
        btnAdelantar_100.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tiempoIniciarPausa.setBase(SystemClock.elapsedRealtime());
                tiempoIniciarPausa.stop();
                isChronometerRunning = false;
                pausedTime = 0;
            }
        });
    }
}

