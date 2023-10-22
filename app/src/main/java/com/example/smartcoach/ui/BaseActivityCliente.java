package com.example.smartcoach.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartcoach.R;

import api.Admi.EquipoApiService;
import api.Admi.GimnasioItemApiService;
import api.Admi.UsuarioAdministradorApiService;
import api.SharedPreferencesUtil;
import api.User.UsuarioClienteApiService;
import api.retro;
import model.Admi.UsuarioAdministrador;
import model.User.UsuarioCliente;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public abstract class BaseActivityCliente extends AppCompatActivity {

    Long userId;
    String token;
    UsuarioClienteApiService usuarioClienteApiService;
    int gimnasioId =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        iniciarPeticiones();
    }


    private void iniciarPeticiones()
    {
        userId = SharedPreferencesUtil.getUserId(this);
        token = SharedPreferencesUtil.getToken(this);
        OkHttpClient okHttpClient = retro.getUnsafeOkHttpClientWithToken(token);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://10.0.2.2:8043/api/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        usuarioClienteApiService = retrofit.create(UsuarioClienteApiService.class);
    }


    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        setupMenu();
    }

    private void setupMenu() {
        ImageButton btnMapaCliente = findViewById(R.id.boton_mapa_menuU);
        ImageButton btnRutinaCliente = findViewById(R.id.boton_casa_menuU);
        ImageButton btnEquipoCliente = findViewById(R.id.boton_equipo_menuU);
        ImageButton btnPerfilCliente = findViewById(R.id.boton_perfil_menuU);

        btnPerfilCliente.setOnClickListener(v -> {
            startActivity(new Intent(this, _63_Principal_Usuario.class));

        });

        btnRutinaCliente.setOnClickListener(v -> {
            String rutina = SharedPreferencesUtil.getRutina(BaseActivityCliente.this);
            Log.d("RUTINAAAA", "tiene valor: " + rutina);
            if (rutina.equals("0")) {
                startActivity(new Intent(this, _95_crear_rutina_usuario.class));
            } else {
                startActivity(new Intent(this, _98_ver_rutina_ejercicio_usuario.class));
            }
        });

        btnMapaCliente.setOnClickListener(v -> {
            idGimnasio(new InfoCallback() {
                @Override
                public void onCompletion() {
                    Log.d("id gimnasio", "tiene valor: " + gimnasioId);
                    abrirGimnasio();
                }
            });

        });

    }

    private void abrirGimnasio()
    {
        if (gimnasioId==0) {
            startActivity(new Intent(this, _127_ver_gimnasios_para_suscribirse.class));
        }
        else {
            startActivity(new Intent(this, _120_ver_informacion_gimnasio.class));
        }
    }
    interface InfoCallback {
        void onCompletion();
    }


    private void idGimnasio(BaseActivityCliente.InfoCallback callback)
    {
        Call<UsuarioCliente> call = usuarioClienteApiService.getUsuarioById(userId);
        call.enqueue(new Callback<UsuarioCliente>() {
            @Override
            public void onResponse(Call<UsuarioCliente> call, Response<UsuarioCliente> response) {
                if (response.isSuccessful()) {
                    if(response.body().getGimnasioid()!=null)
                    {
                        if(response.body().getGimnasioid()==null)
                        {
                            gimnasioId=0;
                        }
                        else
                        {
                            gimnasioId =  response.body().getGimnasioid();

                        }

                    }
                    callback.onCompletion();
                } else {
                }
            }

            @Override
            public void onFailure(Call<UsuarioCliente> call, Throwable t) {
            }
        });
    }
}
