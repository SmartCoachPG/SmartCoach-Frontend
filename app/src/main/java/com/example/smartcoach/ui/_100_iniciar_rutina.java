package com.example.smartcoach.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import com.example.smartcoach.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import api.Admi.EquipoApiService;
import api.DateSerializer;
import api.Exercise.EjercicioApiService;
import api.Exercise.ImagenEjercicioApiService;
import api.Exercise.MusculoApiService;
import api.SharedPreferencesUtil;
import api.TimeDeserializer;
import api.TimeSerializer;
import api.retro;
import model.Exercise.CajaRutina;
import model.Exercise.Ejercicio;
import model.Exercise.ImagenEjercicio;
import model.User.ProgresoxEjercicio;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class _100_iniciar_rutina extends AppCompatActivity {

    TextView setTextNombreEjercicio_100, numero_serie_inicial_100, guión_100, numero_serie_final_100, numeroTotalseries, nombreSeries, numeroTotalRepeticiones, nombreRepeticiones, pesoMaquina, nombrePeso, nombreLbs, tiempoDescanso, nombreSgs, nombreDescanso, nombrePausa, numeroSeries, nombreContSeries;
    ImageButton imageButton, btnPlay_100, btnMute_100, btnAdelantar_100;
    Chronometer tiempoIniciarPausa;
    boolean isChronometerRunning = false;
    long pausedTime = 0;

    List<Ejercicio> ejerciciosList = new ArrayList<>();
    Map<Integer, ProgresoxEjercicio> progresoEjercicio = new HashMap<>();
    List<String> equiposEjercicio = new ArrayList<>();
    ImagenEjercicio imagenEjercicio = new ImagenEjercicio();
    List<String> musculosEjercicio = new ArrayList<>();
    Ejercicio ejercicio = new Ejercicio();
    int ejercicioActual=0;
    ImagenEjercicioApiService imagenEjercicioApiService;
    EquipoApiService equipoApiService;
    MusculoApiService musculoApiService;
    EjercicioApiService ejercicioApiService;

    Long userId;
    String token;

    int contadorSeries=0;

    List<CajaRutina> cajaRutinas= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout._100_iniciar_rutina);
        getSupportActionBar().hide();

        userId = SharedPreferencesUtil.getUserId(_100_iniciar_rutina.this);
        token = SharedPreferencesUtil.getToken(_100_iniciar_rutina.this);
        iniciarPeticiones();

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
        nombrePausa = findViewById(R.id.nombrePausa);
        numeroSeries = findViewById(R.id.numeroSeries);
        nombreContSeries = findViewById(R.id.nombreContSeries);
        btnPlay_100 = findViewById(R.id.btnPlay_100);
        btnMute_100 = findViewById(R.id.btnMute_100);
        btnAdelantar_100 = findViewById(R.id.btnAdelantar_100);
        tiempoIniciarPausa = findViewById(R.id.tiempoIniciarPausa);
        tiempoIniciarPausa.setBase(SystemClock.elapsedRealtime());


        ArrayList<CajaRutina> cajaRutinas = getIntent().getParcelableArrayListExtra("ListaCajaRutina");
        int cont = 0;
        for(CajaRutina cajaRutina:cajaRutinas)
        {
            ejerciciosList.add(cajaRutina.getEjercicio());
            progresoEjercicio.put(cont,cajaRutina.getProgresoxEjercicio());
            cont++;
        }
        numero_serie_final_100.setText(String.valueOf(ejerciciosList.size()));

        cargarInfo();
        btnPlay_100.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                {
                    configurarTimer();
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
                ejercicioActual++;
                if(!rutinaTerminada())
                {
                    cargarInfo();
                }
                else {
                    Intent intent = new Intent(_100_iniciar_rutina.this, _98_ver_rutina_ejercicio_usuario.class);
                    Toast.makeText(_100_iniciar_rutina.this, "Felicidades terminaste tu rutina", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                }

            }
        });
    }

    private Boolean rutinaTerminada()
    {
        return ejercicioActual==ejerciciosList.size();
    }
    private void iniciarPeticiones() {

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Date.class, new DateSerializer())
                .registerTypeAdapter(Time.class, new TimeSerializer())
                .registerTypeAdapter(Time.class, new TimeDeserializer())
                .create();

        OkHttpClient okHttpClient = retro.getUnsafeOkHttpClientWithToken(token)
                .newBuilder()
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://10.0.2.2:8043/api/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        imagenEjercicioApiService = retrofit.create(ImagenEjercicioApiService.class);
        equipoApiService = retrofit.create(EquipoApiService.class);
        musculoApiService = retrofit.create(MusculoApiService.class);
        ejercicioApiService = retrofit.create(EjercicioApiService.class);
    }
    private void cargarInfo()
    {
        Ejercicio actual = ejerciciosList.get(ejercicioActual);
        contadorSeries= 0;
        numeroSeries.setText(String.valueOf(contadorSeries));
        setTextNombreEjercicio_100.setText(actual.getNombre());
        numero_serie_inicial_100.setText(String.valueOf(ejercicioActual+1));
        ProgresoxEjercicio progresoActual = progresoEjercicio.get(ejercicioActual);
        numeroTotalseries.setText(String.valueOf(progresoActual.getSerie()));
        numeroTotalRepeticiones.setText(String.valueOf(progresoActual.getRepeticiones()));
        pesoMaquina.setText(String.valueOf(progresoActual.getPeso()));
        String tiempo = progresoEjercicio.get(ejercicioActual).getDescansoEntreSeries().toString();

        String[] partes = tiempo.split(":");

        int horas = Integer.parseInt(partes[0]);
        int minutos = Integer.parseInt(partes[1]);
        int segundos = Integer.parseInt(partes[2]);
        minutos += horas * 60;
        String tiempoFormateado = String.format("%02d:%02d", minutos, segundos);
        tiempoDescanso.setText(tiempoFormateado);
        tiempoIniciarPausa.setText(tiempoFormateado);
        cargarImagen(() -> {
            cargarEquipo(() -> {
                cargarMusculos(()-> {
                    cargarEjercicio(()->
                    {
                        RecyclerView recyclerView = findViewById(R.id.recyclerViewEjercicios_100);
                        recyclerView.setAdapter(new RutinaAdapter(imagenEjercicio, equiposEjercicio,musculosEjercicio,ejercicio));
                    });

                });

            });
        });


    }

    public void cargarImagen(InfoCallBack callback)
    {
        int idEjercicio = ejerciciosList.get(ejercicioActual).getId().intValue();
        Call<List<ImagenEjercicio>> call = imagenEjercicioApiService.findByEjercicioid(idEjercicio);
        call.enqueue(new Callback<List<ImagenEjercicio>>() {
            @Override
            public void onResponse(Call<List<ImagenEjercicio>> call, Response<List<ImagenEjercicio>> response) {
                if (response.isSuccessful()) {
                    if(!response.body().isEmpty())
                    {
                        imagenEjercicio = response.body().get(0);
                    }

                    callback.onCompletion();

                } else {
                    // Maneja errores del servidor, por ejemplo, un error 404 o 500.
                    Log.e("Error", "Error en la respuesta: " + response.code());
                }

            }

            @Override
            public void onFailure(Call<List<ImagenEjercicio>> call, Throwable t) {
                // Maneja errores de red o de conversión de datos
                Log.e("Error", "Fallo en la petición: " + t.getMessage());
            }
        });
    }

    public void cargarEquipo(InfoCallBack callback)
    {
        Call<List<String>> call = equipoApiService.findEquipoByEjercicioId(ejerciciosList.get(ejercicioActual).getId());
        call.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if (response.isSuccessful()) {
                    if(!response.body().isEmpty())
                    {
                        equiposEjercicio = response.body();
                    }

                    callback.onCompletion();

                } else {
                    // Maneja errores del servidor, por ejemplo, un error 404 o 500.
                    Log.e("Error", "Error en la respuesta: " + response.code());
                }

            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                // Maneja errores de red o de conversión de datos
                Log.e("Error", "Fallo en la petición: " + t.getMessage());
            }
        });
    }

    public void cargarMusculos(InfoCallBack callback)
    {
        Call<List<String>> call = musculoApiService.findMusculosByEjercicioId(ejerciciosList.get(ejercicioActual).getId());
        call.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if (response.isSuccessful()) {
                    if(!response.body().isEmpty())
                    {
                        musculosEjercicio = response.body();
                    }

                    callback.onCompletion();

                } else {
                    // Maneja errores del servidor, por ejemplo, un error 404 o 500.
                    Log.e("Error", "Error en la respuesta: " + response.code());
                }

            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                // Maneja errores de red o de conversión de datos
                Log.e("Error", "Fallo en la petición: " + t.getMessage());
            }
        });
    }

    public void cargarEjercicio(InfoCallBack callback)
    {
        Call<Ejercicio> call = ejercicioApiService.findById(ejerciciosList.get(ejercicioActual).getId());
        call.enqueue(new Callback<Ejercicio>() {
            @Override
            public void onResponse(Call<Ejercicio> call, Response<Ejercicio> response) {
                if (response.isSuccessful()) {
                    ejercicio = response.body();
                    callback.onCompletion();

                } else {
                    // Maneja errores del servidor, por ejemplo, un error 404 o 500.
                    Log.e("Error", "Error en la respuesta: " + response.code());
                }

            }

            @Override
            public void onFailure(Call<Ejercicio> call, Throwable t) {
                // Maneja errores de red o de conversión de datos
                Log.e("Error", "Fallo en la petición: " + t.getMessage());
            }
        });
    }

    interface InfoCallBack {
        void onCompletion();
    }

    private void configurarTimer()
    {
        Chronometer tiempoIniciarPausa = findViewById(R.id.tiempoIniciarPausa);
        String tiempo = progresoEjercicio.get(ejercicioActual).getDescansoEntreSeries().toString();
        String[] partes = tiempo.split(":");
        int horas = Integer.parseInt(partes[0]);
        int minutos = Integer.parseInt(partes[1]);
        int segundos = Integer.parseInt(partes[2]);
        minutos += horas * 60;
        long tiempoTotalMilis = (horas * 3600 + minutos * 60 + segundos) * 1000;

        btnPlay_100.setOnClickListener(new View.OnClickListener() {
            CountDownTimer countDownTimer;
            @Override
            public void onClick(View v) {
                if (countDownTimer != null) {
                    countDownTimer.cancel();
                    countDownTimer = null;
                } else {
                    countDownTimer = new CountDownTimer(tiempoTotalMilis, 1000) {

                        @Override
                        public void onTick(long millisUntilFinished) {
                            long totalSeconds = millisUntilFinished / 1000;
                            long minutesLeft = totalSeconds / 60;
                            long secondsLeft = totalSeconds % 60;
                            tiempoIniciarPausa.setText(String.format("%02d:%02d", minutesLeft, secondsLeft));
                        }

                        @Override
                        public void onFinish() {
                            tiempoIniciarPausa.setText("00:00");
                            contadorSeries++;
                            numeroSeries.setText(String.valueOf(contadorSeries));
                            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                            Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
                            r.play();
                            if(contadorSeries==progresoEjercicio.get(ejercicioActual).getSerie())
                            {
                                ejercicioActual++;
                                cargarInfo();
                            }
                        }
                    }.start();
                }
            }
        });

    }
}

