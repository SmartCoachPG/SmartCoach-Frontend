package com.example.smartcoach.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.smartcoach.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.bind.DateTypeAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import api.Admi.UsuarioAdministradorApiService;
import api.DateSerializer;
import api.SharedPreferencesUtil;
import api.retro;
import model.Admi.UsuarioAdministrador;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class _8_ModificarPerfil_Admi extends AppCompatActivity {

    ImageButton flechaRegresar,imagePP;
    TextView nombreAdmi,puestoAdmi,textoIngresoCedula;
    AppCompatButton botonGuardarCambios;

    EditText textoIngresoNombre,textoIngresoEmail,textoIngresoPuesto;

    Long userId = SharedPreferencesUtil.getUserId(_8_ModificarPerfil_Admi.this);
    String token = SharedPreferencesUtil.getToken(_8_ModificarPerfil_Admi.this);

    UsuarioAdministradorApiService usuarioAdministradorApiService;
    UsuarioAdministrador usuarioAdministrador;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout._8_modificar_perfil_admi);
        iniciarPeticiones();

        flechaRegresar = findViewById(R.id.flechaRegresar);
        imagePP = findViewById(R.id.imagePP);
        nombreAdmi = findViewById(R.id.nombre_admin_8);
        puestoAdmi = findViewById(R.id.puesto_admin_8);
        textoIngresoCedula= findViewById(R.id.textoIngresoCedula_admin_8);
        textoIngresoNombre = findViewById(R.id.texto_Ingreso_Nombre_admin_8);
        textoIngresoEmail = findViewById(R.id.textoIngresoEmail_admin_8);
        textoIngresoPuesto = findViewById(R.id.textoIngresoPuesto_admin_8);
        botonGuardarCambios = findViewById(R.id.botonGuardarCambios_admin_8);

        cargarInfo();

        flechaRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(_8_ModificarPerfil_Admi.this, _7_VerPerfil_Admi.class));
            }
        });

        botonGuardarCambios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modificarInfo();
            }
        });

        textoIngresoCedula.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(_8_ModificarPerfil_Admi.this, "No puedes cambiar tu cedula", Toast.LENGTH_SHORT).show();
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


        UsuarioAdministrador testUser = new UsuarioAdministrador();
        testUser.setFechaDeRenovacion(new Date()); // Establece una fecha de prueba
        String json = gson.toJson(testUser);
        Log.d("TEST_JSON", json);


        usuarioAdministradorApiService = retrofit.create(UsuarioAdministradorApiService.class);

    }

    private void cargarInfo()
    {
        Call<UsuarioAdministrador> call = usuarioAdministradorApiService.getUsuarioById(userId);

        call.enqueue(new Callback<UsuarioAdministrador>() {
            @Override
            public void onResponse(Call<UsuarioAdministrador> call, Response<UsuarioAdministrador> response) {
                if (response.isSuccessful()) {
                    usuarioAdministrador = response.body();
                    // Haz algo con el objeto Usuario, por ejemplo:
                    Log.d("Usuario", "Nombre: " + usuarioAdministrador.getNombre());

                    nombreAdmi.setText(usuarioAdministrador.getNombre());
                    puestoAdmi.setText(usuarioAdministrador.getPuesto());
                    textoIngresoNombre.setText(usuarioAdministrador.getNombre());
                    textoIngresoEmail.setText(usuarioAdministrador.getEmail());
                    textoIngresoCedula.setText(Long.toString(usuarioAdministrador.getCedula()));
                    textoIngresoPuesto.setText(usuarioAdministrador.getPuesto());


                } else {
                    // Maneja errores del servidor, por ejemplo, un error 404 o 500.
                    Log.e("Error", "Error en la respuesta: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<UsuarioAdministrador> call, Throwable t) {
                // Maneja errores de red o de conversión de datos
                Log.e("Error", "Fallo en la petición: " + t.getMessage());
            }
        });
    }

    private void modificarInfo()
    {
        usuarioAdministrador.setNombre(textoIngresoNombre.getText().toString());
        usuarioAdministrador.setEmail(textoIngresoEmail.getText().toString());
        usuarioAdministrador.setCedula(Long.parseLong(textoIngresoCedula.getText().toString()));
        usuarioAdministrador.setPuesto(textoIngresoPuesto.getText().toString());


        Log.d("UsuarioActualizado", "Fecha de renovacion: " + usuarioAdministrador.getFechaDeRenovacion());

        Call<UsuarioAdministrador> call = usuarioAdministradorApiService.updateUsuarioAdministrador(userId, usuarioAdministrador);

        call.enqueue(new Callback<UsuarioAdministrador>() {
            @Override
            public void onResponse(Call<UsuarioAdministrador> call, Response<UsuarioAdministrador> response) {
                if (response.isSuccessful()) {
                    UsuarioAdministrador usuarioAdministrador1 = response.body();
                    Log.d("UsuarioActualizado", "Nombre: " + usuarioAdministrador1.getNombre());
                    Toast.makeText(_8_ModificarPerfil_Admi.this, "Cambios guardados correctamente", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(_8_ModificarPerfil_Admi.this, _7_VerPerfil_Admi.class));

                } else {
                    Log.e("Error", "Error en la respuesta: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<UsuarioAdministrador> call, Throwable t) {
                Log.e("Error", "Fallo en la petición: " + t.getMessage());
            }
        });

    }
}
