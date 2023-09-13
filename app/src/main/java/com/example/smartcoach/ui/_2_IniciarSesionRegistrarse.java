package com.example.smartcoach.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartcoach.R;

import java.util.HashMap;
import java.util.Map;

import api.User.UsuarioApiService;
import api.retro;
import model.User.Usuario;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class _2_IniciarSesionRegistrarse extends AppCompatActivity {

    EditText email, contraseña;
    Button unete, iniciarSesion, registrate;

    Map<String, String> credenciales = new HashMap<>();

    OkHttpClient okHttpClient = retro.getUnsafeOkHttpClient();


    Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://10.0.2.2:8043/api/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

    UsuarioApiService usuarioApiService = retrofit.create(UsuarioApiService.class);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout._2_iniciar_sesion_registrarse);

        email = findViewById(R.id.email);
        contraseña = findViewById(R.id.contraseña);
        iniciarSesion = findViewById(R.id.btnIniciarSesion);
        unete = findViewById(R.id.btnUnete);
        registrate = findViewById(R.id.btnRegistrate);

        iniciarSesion.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                Log.d("CLICK", "identifico que hago click");
                iniciarSesion();
            }
        });

        //Agregar lógica para iniciar sesión!
        unete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(_2_IniciarSesionRegistrarse.this, _63_PrincipalUsuario.class);
                startActivity(intent);
            }
        });
        registrate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(_2_IniciarSesionRegistrarse.this, _6_PrincipalAdmi.class);
                startActivity(intent);
            }
        });
    }

    private void iniciarSesion()
    {
        String emailStr = email.getText().toString();
        String contraseñaStr = contraseña.getText().toString();

        credenciales.put("email", emailStr);
        credenciales.put("contrasenna", contraseñaStr);

        Log.d("IniciarSesionActivity", "Email: " + emailStr);
        Log.d("IniciarSesionActivity", "Contraseña: " + contraseñaStr);


        Call<Usuario> call = usuarioApiService.iniciarSesion(credenciales);
        call.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if (response.isSuccessful()) {
                    Log.d("IniciarSesionActivity", "Respuesta recibida: " + response.toString());

                    Usuario usuarioResponse = response.body();

                    if (usuarioResponse != null) {
                        Log.d("IniciarSesionActivity", "Respuesta recibida: " + usuarioResponse.toString());

                        Integer tipoUsuario = usuarioResponse.getAdmi();

                        if (tipoUsuario != null) {
                            if (tipoUsuario == 1) {
                                Log.d("IniciarSesionActivity", "Administrador inició sesión: " + usuarioResponse.getId());
                                Intent intent = new Intent(_2_IniciarSesionRegistrarse.this, _3_RegistrarseAdmi_1.class);
                                startActivity(intent);
                            } else if (tipoUsuario == 0) {
                                Log.d("IniciarSesionActivity", "Cliente inició sesión: " + usuarioResponse.getId());
                                Intent intent = new Intent(_2_IniciarSesionRegistrarse.this, _40_RegistrarUsuario_2.class);
                                startActivity(intent);
                            } else {
                                Log.d("IniciarSesionActivity", "Tipo de usuario desconocido");
                            }
                        } else {
                            Log.d("IniciarSesionActivity", "Campo 'admi' es null");
                        }
                    } else {
                        Log.d("IniciarSesionActivity", "Respuesta recibida pero el cuerpo es null");
                    }
                } else {
                    Log.d("IniciarSesionActivity", "Inicio de sesión fallido");
                }
            }
            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                Log.d("IniciarSesionActivity", "Error en la llamada API", t);
            }

        });

    }
}


