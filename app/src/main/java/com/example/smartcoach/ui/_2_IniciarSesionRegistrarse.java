package com.example.smartcoach.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.example.smartcoach.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.sql.Time;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import api.DateSerializer;
import api.SharedPreferencesUtil;
import api.TimeDeserializer;
import api.TimeSerializer;
import api.User.ProgresoxEjercicioService;
import api.User.UsuarioApiService;
import api.retro;
import model.User.ProgresoxEjercicio;
import model.User.Usuario;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
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
    ProgresoxEjercicioService progresoxEjercicioService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
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
                iniciarSesion();
            }
        });

        unete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(_2_IniciarSesionRegistrarse.this, _3_Registrarse_Admi_1.class);
                startActivity(intent);
            }
        });
        registrate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(_2_IniciarSesionRegistrarse.this, _39_Registrar_Usuario_1.class);
                startActivity(intent);
            }
        });
    }

    private void iniciarPeticiones()
    {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Date.class, new DateSerializer())
                .registerTypeAdapter(Time.class,new TimeSerializer())
                .registerTypeAdapter(Time.class,new TimeDeserializer())
                .create();

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        String token = SharedPreferencesUtil.getToken(_2_IniciarSesionRegistrarse.this);
        OkHttpClient okHttpClient = retro.getUnsafeOkHttpClientWithToken(token)
                .newBuilder()
                .addInterceptor(logging)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://10.0.2.2:8043/api/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        progresoxEjercicioService = retrofit.create(ProgresoxEjercicioService.class);
    }


    private void iniciarSesion()
    {
        String emailStr = email.getText().toString();
        String contraseñaStr = contraseña.getText().toString();
        SharedPreferencesUtil sharedpreferencesutil = new SharedPreferencesUtil();


        credenciales.put("email", emailStr);
        credenciales.put("contrasenna", contraseñaStr);


        Call<Usuario> call = usuarioApiService.iniciarSesion(credenciales);
        call.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if (response.isSuccessful()) {
                    Usuario usuarioResponse = response.body();
                    if (usuarioResponse != null) {
                        Integer tipoUsuario = usuarioResponse.getAdmi();
                        if (tipoUsuario != null) {
                            sharedpreferencesutil.saveToken(_2_IniciarSesionRegistrarse.this,usuarioResponse.getToken());
                            sharedpreferencesutil.saveUserId(_2_IniciarSesionRegistrarse.this,usuarioResponse.getId());

                            if (tipoUsuario == 1) {
                                Intent intent = new Intent(_2_IniciarSesionRegistrarse.this, _6_Principal_Admi.class);
                                startActivity(intent);
                            } else if (tipoUsuario == 0) {
                                validarRutina(usuarioResponse.getId().intValue(), new ValidarRutinaCallback() {
                                    @Override
                                    public void onResult(int resultado) {
                                        // Aquí manejas el resultado, por ejemplo, guardar en SharedPreferences
                                        sharedpreferencesutil.saveRutina(_2_IniciarSesionRegistrarse.this, String.valueOf(resultado));
                                    }
                                });
                                Intent intent = new Intent(_2_IniciarSesionRegistrarse.this, _63_Principal_Usuario.class);
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
                    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(_2_IniciarSesionRegistrarse.this);
                    View dialogView = getLayoutInflater().inflate(R.layout._61_iniciar_sesion_mensaje_de_error_informacion_incorrecta_user_admin, null);
                    dialogBuilder.setView(dialogView);
                    AlertDialog dialog = dialogBuilder.create();
                    Button btnContinuar = dialogView.findViewById(R.id.btnContinuar_61);
                    btnContinuar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });

                    dialog.show();


                }
            }
            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                Log.d("IniciarSesionActivity", "Error en la llamada API", t);
            }

        });

    }

    public interface ValidarRutinaCallback {
        void onResult(int resultado);
    }

    private void validarRutina(int idU, ValidarRutinaCallback callback) {
        iniciarPeticiones();

        Call<List<ProgresoxEjercicio>> call = progresoxEjercicioService.getByUsuarioClienteId(idU);
        call.enqueue(new Callback<List<ProgresoxEjercicio>>() {
            @Override
            public void onResponse(Call<List<ProgresoxEjercicio>> call, Response<List<ProgresoxEjercicio>> response) {
                int resultado = 0;
                if (response.isSuccessful() && response.body() != null && !response.body().isEmpty()) {
                    resultado = 1;
                }
                callback.onResult(resultado);
            }

            @Override
            public void onFailure(Call<List<ProgresoxEjercicio>> call, Throwable t) {
                callback.onResult(0);
            }
        });
    }

}


