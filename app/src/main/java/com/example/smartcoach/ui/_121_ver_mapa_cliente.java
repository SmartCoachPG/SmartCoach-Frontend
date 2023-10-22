package com.example.smartcoach.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartcoach.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Date;

import api.Admi.GimnasioApiService;
import api.DateSerializer;
import api.SharedPreferencesUtil;
import api.User.UsuarioClienteApiService;
import api.retro;
import model.Admi.Gimnasio;
import model.User.UsuarioCliente;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class _121_ver_mapa_cliente extends AppCompatActivity {



    Long userId;
    String token;

    GimnasioApiService gimnasioApiService;
    UsuarioClienteApiService usuarioClienteApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout._121_ver_mapa_cliente);
        getSupportActionBar().hide();

        userId = SharedPreferencesUtil.getUserId(_121_ver_mapa_cliente.this);
        token = SharedPreferencesUtil.getToken(_121_ver_mapa_cliente.this);

        iniciarPeticiones();


    }

    private void iniciarPeticiones()
    {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Date.class, new DateSerializer())
                .create();
        OkHttpClient okHttpClient = retro.getUnsafeOkHttpClientWithToken(token);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://10.0.2.2:8043/api/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        gimnasioApiService = retrofit.create(GimnasioApiService.class);
        usuarioClienteApiService = retrofit.create(UsuarioClienteApiService.class);
    }

    interface InfoCallback {
        void onCompletion();
    }


}
