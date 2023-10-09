package com.example.smartcoach.ui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import com.example.smartcoach.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import android.view.View;
import android.widget.Toast;

import java.sql.Time;
import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import api.DateSerializer;
import api.Exercise.RutinaApiService;
import api.SharedPreferencesUtil;
import api.TimeDeserializer;
import api.TimeSerializer;
import api.User.PerfilMedicoApiService;
import api.User.UsuarioApiService;
import api.User.UsuarioClienteApiService;
import api.User.UsuarioClienteRestriccionMedicaApiService;
import api.User.ValorApiService;
import api.retro;
import model.Exercise.Rutina;
import model.User.PerfilMedico;
import model.User.RestriccionMedica;
import model.User.Usuario;
import model.User.UsuarioCliente;
import model.User.UsuarioClienteRestriccionMedica;
import model.User.Valor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class _59_registrar_usuario_5 extends AppCompatActivity {

    TextView tituloLimitacionesFisicas, descripcionLimitacionesFisicas, condicionesMedicas;
    ImageButton barraBusquedaB1, barraBusquedaB2, btnBusqueda;
    EditText textoBarraBusqueda;
    Button btnListo;
    ScrollView scrollView;

    List<RestriccionMedica> listaRestricciones= new ArrayList<>();
    UsuarioCliente usuarioCliente;
    int musculoObjetivo;

    ArrayList<Rutina> listaRutinas;

    ArrayList<Valor> listaValores;
    private static final int REQUEST_CODE = 123;

    UsuarioClienteApiService usuarioClienteApiService;
    RutinaApiService rutinaApiService;
    PerfilMedicoApiService perfilMedicoApiService;
    ValorApiService valorApiService;
    UsuarioClienteRestriccionMedicaApiService usuarioClienteRestriccionMedicaApiService;
    UsuarioApiService usuarioApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout._59_registrar_usuario_5);
        getSupportActionBar().hide();
        iniciarPeticiones();
        usuarioCliente = (UsuarioCliente) getIntent().getSerializableExtra("usuarioCliente");

        musculoObjetivo = getIntent().getIntExtra("musculoObjetivo", 0); // 0 es un valor predeterminado en caso de que no se encuentre el extra.

        listaRutinas = (ArrayList<Rutina>) getIntent().getSerializableExtra("listaRutinas");

        listaValores = (ArrayList<Valor>) getIntent().getSerializableExtra("listaValoresComposicionCorporal");

        tituloLimitacionesFisicas = findViewById(R.id.tituloLimitacionesFisicas_59);
        descripcionLimitacionesFisicas = findViewById(R.id.descripcionLimitacionesFisicas_59);
        barraBusquedaB1 = findViewById(R.id.barra_busqueda_b_59);
        barraBusquedaB2 = findViewById(R.id.rectanguloLargo);
        textoBarraBusqueda = findViewById(R.id.texto_barra_busqueda_59);
        btnBusqueda = findViewById(R.id.btnBusqueda);
        condicionesMedicas = findViewById(R.id.condicionesMedicas);
        btnListo = findViewById(R.id.btnListo_59);
        scrollView = findViewById(R.id.scrollView_59);

        btnBusqueda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Debug", "btnBusqueda clickeado");
                Intent intent = new Intent(_59_registrar_usuario_5.this, _59_2_registrar_usuario_5.class);
                intent.putExtra("textoBusqueda",textoBarraBusqueda.getText().toString());
                Log.d("Debug", "texto busqueda: "+textoBarraBusqueda.getText().toString());
                startActivityForResult(intent, REQUEST_CODE);
            }

        });

        btnListo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Debug", "btnListo clickeado");
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(_59_registrar_usuario_5.this);

                View dialogView = getLayoutInflater().inflate(R.layout._60_mensaje_advertencia_limitaciones_fisicas, null);
                dialogBuilder.setView(dialogView);

                AlertDialog dialog = dialogBuilder.create();

                Button btnContinuar = dialogView.findViewById(R.id.btnContinuarLF);
                btnContinuar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        crearUsuarioCliente(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(_59_registrar_usuario_5.this, "Cuenta Creada", Toast.LENGTH_SHORT).show();
                                iniciarSesion(usuarioCliente);
                            }
                        });
                    }
                });
                dialog.show();
            }
        });

        actualizarLista();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            ArrayList<RestriccionMedica> restriccionesRecibidas = (ArrayList<RestriccionMedica>) data.getSerializableExtra("seleccionados");
            if (restriccionesRecibidas != null) {
                listaRestricciones.addAll(restriccionesRecibidas);
            }
            actualizarLista();
        }
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

        OkHttpClient okHttpClient = retro.getUnsafeOkHttpClient().newBuilder()
                .addInterceptor(logging)  // Agrega el interceptor al cliente OkHttp
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://10.0.2.2:8043/api/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        usuarioClienteApiService = retrofit.create(UsuarioClienteApiService.class);
        rutinaApiService = retrofit.create(RutinaApiService.class);
        perfilMedicoApiService= retrofit.create(PerfilMedicoApiService.class);
        valorApiService = retrofit.create(ValorApiService.class);
        usuarioClienteRestriccionMedicaApiService = retrofit.create(UsuarioClienteRestriccionMedicaApiService.class);
        usuarioApiService = retrofit.create(UsuarioApiService.class);
    }

    private void actualizarLista(){
        LinearLayout linearLayout = findViewById(R.id.linear_layout_inside_scrollview);
        linearLayout.removeAllViews();

        if(listaRestricciones == null) {
            Log.e("Error", "La lista de restricciones médicas seleccionadas es null");
            return;
        } else {
            LayoutInflater inflater = LayoutInflater.from(this);
            for (RestriccionMedica item : listaRestricciones) {
                View itemView = inflater.inflate(R.layout.item_restriccion, linearLayout, false);
                TextView tvRestriccion = itemView.findViewById(R.id.textViewItem);
                tvRestriccion.setText(item.getNombreLimitacion());

                ImageButton btnEliminar = itemView.findViewById(R.id.btnEliminar);
                btnEliminar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listaRestricciones.remove(item);
                        actualizarLista();
                    }
                });

                linearLayout.addView(itemView);

            }
        }
    }

    private void crearUsuarioCliente(final Runnable callback)
    {
       usuarioCliente.setAdmi(0);
       usuarioCliente.setGrupoMuscularid(musculoObjetivo);

       Call<UsuarioCliente> call = usuarioClienteApiService.createUsuarioCliente(usuarioCliente);

       call.enqueue(new Callback<UsuarioCliente>() {
            @Override
            public void onResponse(Call<UsuarioCliente> call, Response<UsuarioCliente> response) {
                if (response.isSuccessful()) {
                    UsuarioCliente usuarioResponse = response.body();
                    Log.d("Usuario creado", "info: "+usuarioResponse.toString());
                    crearRutinas(usuarioResponse.getId(),usuarioResponse.getGrupoMuscularid());
                    crearPefilMedico(usuarioResponse.getId());
                    asignarRestriccionesM(usuarioResponse.getId());
                    callback.run();
                } else {
                    Log.e("Error", "Error en la respuesta: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<UsuarioCliente> call, Throwable t) {
                Log.e("Error", "Fallo en la petición: " + t.getMessage());
            }
       });


    }

    private void crearRutinas(Long idUsuario, int idGrupoMuscular)
    {
        for (Rutina rutina : listaRutinas) {

            rutina.setUsuarioClienteId(idUsuario.intValue());
            rutina.setCantEjercicios(4);
            rutina.setGrupoMuscularId(idGrupoMuscular);
            LocalTime start = Instant.ofEpochMilli(rutina.getHoraI().getTime()).atZone(ZoneId.systemDefault()).toLocalTime();
            LocalTime end = Instant.ofEpochMilli(rutina.getHoraF().getTime()).atZone(ZoneId.systemDefault()).toLocalTime();

            long hoursDifference = end.getHour() - start.getHour();
            long minutesDifference = end.getMinute() - start.getMinute();

            if (minutesDifference < 0) {
                hoursDifference--;
                minutesDifference += 60;
            }

            LocalTime durationLocalTime = LocalTime.of((int) hoursDifference, (int) minutesDifference);
            String formattedTime = String.format("%02d:%02d:%02d", durationLocalTime.getHour(), durationLocalTime.getMinute(), 0);
            Time duration = Time.valueOf(formattedTime);
            rutina.setDuracion(duration);

            System.out.println("Duración: " + duration);

            Call<Rutina> call = rutinaApiService.create(rutina);

                call.enqueue(new Callback<Rutina>() {
                    @Override
                    public void onResponse(Call<Rutina> call, Response<Rutina> response) {
                        if (response.isSuccessful()) {
                            Rutina rutinaResponse = response.body();
                            Log.d("Rutina creada", "info: "+rutinaResponse.toString());
                            String rawResponse = response.raw().body().toString();
                            Log.d("Raw Response", rawResponse);
                        } else {
                            Log.e("Error", "Error en la respuesta: " + response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<Rutina> call, Throwable t) {
                        Log.e("Error", "Fallo en la petición: " + t.getMessage());
                    }
                });

        }

    }

    private void crearPefilMedico(Long idUsuario)
    {
        PerfilMedico newPerfil = new PerfilMedico();
        newPerfil.setUsuarioClienteUsuarioid(idUsuario.intValue());
        Date fecha = new Date();
        newPerfil.setFecha(fecha);

        Call<PerfilMedico> call = perfilMedicoApiService.createPerfilMedico(newPerfil);

        call.enqueue(new Callback<PerfilMedico>() {
            @Override
            public void onResponse(Call<PerfilMedico> call, Response<PerfilMedico> response) {
                if (response.isSuccessful()) {
                    PerfilMedico perfilResponse = response.body();
                    Log.d("´Perfil creado", "info: "+perfilResponse.toString());
                    crearValores(perfilResponse.getId());
                } else {
                    Log.e("Error", "Error en la respuesta: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<PerfilMedico> call, Throwable t) {
                Log.e("Error", "Fallo en la petición: " + t.getMessage());
            }
        });


    }

    private void crearValores(int idPerfilMedico)
    {
        for(Valor valor: listaValores)
        {
            valor.setPerfilMedicoid(idPerfilMedico);

            Call<Valor> call = valorApiService.createValor(valor);

            call.enqueue(new Callback<Valor>() {
                @Override
                public void onResponse(Call<Valor> call, Response<Valor> response) {
                    if (response.isSuccessful()) {
                        Valor valorResponse = response.body();
                        Log.d("´Valor creado", "info: "+valorResponse.toString());
                    } else {
                        Log.e("Error", "Error en la respuesta: " + response.code());
                    }
                }

                @Override
                public void onFailure(Call<Valor> call, Throwable t) {
                    Log.e("Error", "Fallo en la petición: " + t.getMessage());
                }
            });
        }
    }

    private void asignarRestriccionesM(Long idUsuario)
    {
        for(RestriccionMedica restriccionMedica : listaRestricciones)
        {
            UsuarioClienteRestriccionMedica newRestriccion = new UsuarioClienteRestriccionMedica();
            newRestriccion.setRestriccionMedicaid(restriccionMedica.getId());
            newRestriccion.setUsuarioClienteUsuarioid(idUsuario.intValue());

            Call<UsuarioClienteRestriccionMedica> call = usuarioClienteRestriccionMedicaApiService.createRestriccion(newRestriccion);

            call.enqueue(new Callback<UsuarioClienteRestriccionMedica>() {
                @Override
                public void onResponse(Call<UsuarioClienteRestriccionMedica> call, Response<UsuarioClienteRestriccionMedica> response) {
                    if (response.isSuccessful()) {
                        UsuarioClienteRestriccionMedica USCResponse = response.body();
                        Log.d("´Restriccion asignada", "info: "+USCResponse.toString());
                    } else {
                        Log.e("Error", "Error en la respuesta: " + response.code());
                    }
                }

                @Override
                public void onFailure(Call<UsuarioClienteRestriccionMedica> call, Throwable t) {
                    Log.e("Error", "Fallo en la petición: " + t.getMessage());
                }
            });

        }
    }

    private void iniciarSesion(UsuarioCliente usuarioCliente)
    {
        String emailStr = usuarioCliente.getEmail();
        String contraseñaStr= usuarioCliente.getContrasenna();
        SharedPreferencesUtil sharedpreferencesutil = new SharedPreferencesUtil();
        Map<String, String> credenciales = new HashMap<>();
        credenciales.put("email", emailStr);
        credenciales.put("contrasenna", contraseñaStr);

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
                            Log.d("IniciarSesionActivity", "Usuario inició sesión: " + usuarioResponse.getId());
                            sharedpreferencesutil.saveToken(_59_registrar_usuario_5.this,usuarioResponse.getToken());
                            sharedpreferencesutil.saveUserId(_59_registrar_usuario_5.this,usuarioResponse.getId());
                            Intent intent = new Intent(_59_registrar_usuario_5.this, _63_Principal_Usuario.class);
                            startActivity(intent);

                        } else {
                            Log.d("IniciarSesionActivity", "Campo 'admi' es null");
                        }
                    } else {
                        Log.d("IniciarSesionActivity", "Respuesta recibida pero el cuerpo es null");
                    }
                } else {
                    Log.d("IniciarSesionActivity", "Inicio de sesión fallido");
                    Toast toast = Toast.makeText(getApplicationContext(), "Correo o contraseña incorrecta,por favor vuelve a intentar", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            }
            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                Log.d("IniciarSesionActivity", "Error en la llamada API", t);
            }

        });
    }
}

