package com.example.smartcoach.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

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
import model.User.Usuario;
import model.User.UsuarioCliente;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class _120_ver_informacion_gimnasio extends BaseActivityCliente{

    ImageView rectanguloTitulo_120, setImageGym_120;
    TextView titulo_120, descripcion_120, tituloGym_120, textBarrio, barrioGym_120, textDireccion_120, direccionGym_120;
    Button btnVerMapa_120, btnDesuscribirse_120;

    UsuarioCliente usuarioCliente;
    Gimnasio gym;

    Long userId;
    String token;


    GimnasioApiService gimnasioApiService;
    UsuarioClienteApiService usuarioClienteApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout._120_ver_informacion_gimnasio);
        getSupportActionBar().hide();

        userId = SharedPreferencesUtil.getUserId(_120_ver_informacion_gimnasio.this);
        token = SharedPreferencesUtil.getToken(_120_ver_informacion_gimnasio.this);

        iniciarPeticiones();
        rectanguloTitulo_120 = findViewById(R.id.rectanguloTitulo_120);

        titulo_120 = findViewById(R.id.titulo_120);
        descripcion_120 = findViewById(R.id.descripcion_120);
        setImageGym_120 = findViewById(R.id.setImageGym_120);
        textBarrio = findViewById(R.id.textBarrio);
        textDireccion_120 = findViewById(R.id.textDireccion_120);
        btnVerMapa_120 = findViewById(R.id.btnVerMapa_120);
        btnDesuscribirse_120 = findViewById(R.id.btnDesuscribirse_120);

        cargarInfo();

        btnDesuscribirse_120.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                desuscribirse();
            }
        });

        btnVerMapa_120.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(_120_ver_informacion_gimnasio.this, _121_ver_mapa_cliente.class);
                intent.putExtra("idGimnasio",gym.getId().intValue());
                startActivity(intent);
            }
        });

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
    private void cargarInfo()
    {
        Log.d("GYM INFO", "cargarInfo: ");
        infoUsuario(new InfoCallback() {
            @Override
            public void onCompletion() {
                Log.d("GYM INFO", "info usuario check: "+usuarioCliente);

                infoGimnasio(new InfoCallback() {
                    @Override
                    public void onCompletion() {
                        Log.d("GYM INFO", "info gym check: "+gym);
                        mostrarInfo();
                    }
                });
            }
        });
    }

    private void infoUsuario(_120_ver_informacion_gimnasio.InfoCallback callback)
    {
        Call<UsuarioCliente> call = usuarioClienteApiService.getUsuarioById(userId);
        call.enqueue(new Callback<UsuarioCliente>() {
            @Override
            public void onResponse(Call <UsuarioCliente> call, Response<UsuarioCliente> response) {
                if (response.isSuccessful()) {
                usuarioCliente = response.body();
                callback.onCompletion();
                }
            }
            @Override
            public void onFailure(Call <UsuarioCliente> call, Throwable t) {
                Toast.makeText(_120_ver_informacion_gimnasio.this, "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void infoGimnasio(_120_ver_informacion_gimnasio.InfoCallback callback){
        Call<Gimnasio> call = gimnasioApiService.findById(usuarioCliente.getGimnasioid().longValue());
        call.enqueue(new Callback<Gimnasio>() {
            @Override
            public void onResponse(Call <Gimnasio> call, Response<Gimnasio> response) {
                if (response.isSuccessful()) {
                    gym = response.body();
                    callback.onCompletion();
                }
            }
            @Override
            public void onFailure(Call <Gimnasio> call, Throwable t) {
                Toast.makeText(_120_ver_informacion_gimnasio.this, "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void mostrarInfo()
    {
        tituloGym_120 = findViewById(R.id.tituloGym_120);
        barrioGym_120 = findViewById(R.id.barrioGym_120);
        direccionGym_120 = findViewById(R.id.direccionGym_120);
        tituloGym_120.setText(gym.getNombre());
        barrioGym_120.setText(gym.getBarrio());
        direccionGym_120.setText(gym.getDireccion());

    }

    private void desuscribirse()
    {
        usuarioCliente.setGimnasioid(null);
        Call<UsuarioCliente> call = usuarioClienteApiService.updateUsuarioCliente(userId,usuarioCliente);
        Log.d("DESUS", "desuscribirse: nuevo usuario "+usuarioCliente);
        call.enqueue(new Callback<UsuarioCliente>() {
            @Override
            public void onResponse(Call <UsuarioCliente> call, Response<UsuarioCliente> response) {
                if (response.isSuccessful()) {
                    usuarioCliente = response.body();
                    Log.d("DESUS", "listo: nuevo usuario "+usuarioCliente);
                    Toast.makeText(_120_ver_informacion_gimnasio.this, "Gimnasio Desuscrito", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(_120_ver_informacion_gimnasio.this, _127_ver_gimnasios_para_suscribirse.class);
                    startActivity(intent);
                }
            }
            @Override
            public void onFailure(Call <UsuarioCliente> call, Throwable t) {
                Toast.makeText(_120_ver_informacion_gimnasio.this, "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
