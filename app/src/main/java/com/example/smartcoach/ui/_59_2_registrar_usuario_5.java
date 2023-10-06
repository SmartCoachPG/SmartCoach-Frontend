package com.example.smartcoach.ui;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartcoach.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import api.DateSerializer;
import api.User.RestriccionMedicaApiService;
import api.retro;
import model.User.RestriccionMedica;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class _59_2_registrar_usuario_5 extends AppCompatActivity {

    RestriccionMedicaApiService restriccionMedicaApiService;
    List<RestriccionMedica> listaRestriccionMedica = new ArrayList<>();

    Button btnAceptar;
    ImageButton btnCerrar;

    private MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout._59_2_registrar_usuario_5);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        iniciarPeticiones();
        llenarListaRestricciones();

        btnAceptar = findViewById(R.id.btnAceptarSLF_59_2);
        btnCerrar = findViewById(R.id.btn_cerrar_59_2);

        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Debug", "btnAceptar click");
                List<RestriccionMedica> seleccionados = adapter.getItemsSeleccionados();
                Intent intent = new Intent();
                intent.putExtra("seleccionados", new ArrayList<>(seleccionados));
                setResult(RESULT_OK,intent);
                finish();
            }
        });

        btnCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Debug", "btnCerrar click");

                finish();
            }
        });
    }


    private void iniciarPeticiones()
    {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Date.class, new DateSerializer())
                .create();

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = retro.getUnsafeOkHttpClient().newBuilder()
                .addInterceptor(logging)  // Agrega el interceptor al cliente OkHttp
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://10.0.2.2:8043/api/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        restriccionMedicaApiService = retrofit.create(RestriccionMedicaApiService.class);

    }

    private void llenarListaRestricciones()
    {
        Call<List<RestriccionMedica>> call = restriccionMedicaApiService.getAll();
        call.enqueue(new Callback<List<RestriccionMedica>>() {
            @Override
            public void onResponse(Call<List<RestriccionMedica>> call, Response<List<RestriccionMedica>> response) {
                if (response.isSuccessful()) {
                    listaRestriccionMedica = response.body();
                    Log.d("Restriccion medica", "Lista: "+listaRestriccionMedica);
                    mostrarRestricciones();

                } else {
                    Log.e("Error", "Error en la respuesta: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<RestriccionMedica>> call, Throwable t) {
                Log.e("Error", "Fallo en la petición: " + t.getMessage());
            }
        });
    }

    private void mostrarRestricciones()
    {
        RecyclerView recyclerView = findViewById(R.id.recyclerView_59_2);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyAdapter(listaRestriccionMedica);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}
