package com.example.smartcoach.ui;

import static android.view.View.GONE;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.smartcoach.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import api.Admi.EquipoApiService;
import api.Admi.GimnasioApiService;
import api.Admi.GimnasioItemApiService;
import api.Admi.MapaApiService;
import api.Admi.UsuarioAdministradorApiService;
import api.DateSerializer;
import api.SharedPreferencesUtil;
import api.retro;
import model.Admi.Gimnasio;
import model.Admi.Mapa;
import model.Admi.UsuarioAdministrador;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class _37_crea_tu_gimnasio_admin extends BaseActivityAdmi{
    ImageView rectanguloTitulo_37;
    ImageButton btnRegresar_37;
    TextView tituloCrear_37, descripcionCrear_37, textSubaImagen_37;
    EditText setTextnombreGimnasio_37, setTextDireccionGimnasio_37, setTextBarrioGimnasio_37, setTextAnchoGimnasio_37, setTextAltoGimnasio_37, setTextPisosGimnasio_37;
    ImageButton imagenGimnasio_37, imageSubirImagen_37;
    Button btnCrearGimnasio_37;

    Long userId;
    String token;
    GimnasioApiService gimnasioApiService;
    MapaApiService mapaApiService;
    UsuarioAdministradorApiService usuarioAdministradorApiService;
    Gimnasio gym = new Gimnasio();
    UsuarioAdministrador admi = new UsuarioAdministrador();

    private static final int REQUEST_CODE = 100; // Para la solicitud de permiso
    private static final int PICK_IMAGE_REQUEST = 101; // Para identificar la solicitud de selección de imagen

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout._37_crear_tu_gimnasio_admin);
        getSupportActionBar().hide();
        userId = SharedPreferencesUtil.getUserId(_37_crea_tu_gimnasio_admin.this);
        token = SharedPreferencesUtil.getToken(_37_crea_tu_gimnasio_admin.this);

        iniciarPeticiones();

        rectanguloTitulo_37 = findViewById(R.id.rectanguloTitulo_37);
        btnRegresar_37 = findViewById(R.id.btnRegresar_37);
        tituloCrear_37 = findViewById(R.id.tituloCrear_37);
        descripcionCrear_37 = findViewById(R.id.descripcionCrear_37);
        setTextnombreGimnasio_37 = findViewById(R.id.setTextnombreGimnasio_37);
        imagenGimnasio_37 = findViewById(R.id.imagenGimnasio_37);
        textSubaImagen_37 = findViewById(R.id.textSubaImagen_37);
        imageSubirImagen_37 = findViewById(R.id.imageSubirImagen_37);
        setTextDireccionGimnasio_37 = findViewById(R.id.setTextDireccionGimnasio_37);
        setTextBarrioGimnasio_37 = findViewById(R.id.setTextBarrioGimnasio_37);
        setTextAnchoGimnasio_37 = findViewById(R.id.setTextAnchoGimnasio_37);
        setTextAltoGimnasio_37 = findViewById(R.id.setTextAltoGimnasio_37);
        btnCrearGimnasio_37 = findViewById(R.id.btnCrearGimnasio_37);

        btnRegresar_37.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btnCrearGimnasio_37.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crear();
            }
        });

        imagenGimnasio_37.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cambiarImagen();
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
        mapaApiService = retrofit.create(MapaApiService.class);
        usuarioAdministradorApiService = retrofit.create(UsuarioAdministradorApiService.class);
    }

    interface InfoCallback {
        void onCompletion();
    }
    private void crear()
    {
        Log.d("GYM CREANDO", "entro a crear: ");

        crearGimnasio(new InfoCallback() {
            @Override
            public void onCompletion() {

                crearMapa(new InfoCallback() {
                    @Override
                    public void onCompletion() {
                        cargarAdmi(new InfoCallback() {
                            @Override
                            public void onCompletion() {
                                guardarAdmi(new InfoCallback() {
                                    @Override
                                    public void onCompletion() {
                                        Toast.makeText(_37_crea_tu_gimnasio_admin.this, "Mapa Creado, ahora creemos tu mapa", Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(_37_crea_tu_gimnasio_admin.this, _27_configurar_mapa_admin.class);
                                        startActivity(intent);
                                    }
                                });
                            }
                        });
                    }
                });
            }
        });
    }

    private void crearGimnasio(_37_crea_tu_gimnasio_admin.InfoCallback callback)
    {
        Log.d("GYM CREANDO", "entro a crear gimnasio: "+gym);

        gym.setNombre(setTextnombreGimnasio_37.getText().toString());
        gym.setDireccion(setTextDireccionGimnasio_37.getText().toString());
        gym.setBarrio(setTextBarrioGimnasio_37.getText().toString());
        gym.setPisos(1);

        Call<Gimnasio> call = gimnasioApiService.save(gym);
        call.enqueue(new Callback<Gimnasio>() {
            @Override
            public void onResponse(Call<Gimnasio> call, Response<Gimnasio> response) {
                if (response.isSuccessful()) {
                    gym = response.body();
                    Log.d("GYM CREANDO", "creado: "+gym);

                } else {
                    // Maneja errores del servidor, por ejemplo, un error 404 o 500.
                    Log.e("Error", "Error en la respuesta: " + response.code());
                }
                callback.onCompletion();
            }

            @Override
            public void onFailure(Call<Gimnasio> call, Throwable t) {
                // Maneja errores de red o de conversión de datos
                Log.e("Error", "Fallo en la petición: " + t.getMessage());
            }
        });
    }

    private void crearMapa(_37_crea_tu_gimnasio_admin.InfoCallback callback)
    {
        Log.d("GYM CREANDO", "entro a crear mapa: "+gym);
        Mapa mapa = new Mapa();
        mapa.setAlto(Integer.parseInt(setTextAltoGimnasio_37.getText().toString()));
        mapa.setAncho(Integer.parseInt(setTextAnchoGimnasio_37.getText().toString()));
        mapa.setGimnasioId(gym.getId().intValue());
        mapa.setNivel(1);
        mapa.setVersion(1);

        Call<Mapa> call = mapaApiService.save(mapa);
        call.enqueue(new Callback<Mapa>() {
            @Override
            public void onResponse(Call<Mapa> call, Response<Mapa> response) {
                if (response.isSuccessful()) {

                } else {
                    // Maneja errores del servidor, por ejemplo, un error 404 o 500.
                    Log.e("Error", "Error en la respuesta: " + response.code());
                }
                callback.onCompletion();
            }

            @Override
            public void onFailure(Call<Mapa> call, Throwable t) {
                // Maneja errores de red o de conversión de datos
                Log.e("Error", "Fallo en la petición: " + t.getMessage());
            }
        });
    }

    public void cambiarImagen()
    {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE);
        } else {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, PICK_IMAGE_REQUEST);
        }
    }

    public void guardarAdmi(_37_crea_tu_gimnasio_admin.InfoCallback callback)
    {
        admi.setGimnasioId(gym.getId().intValue());
        Call<UsuarioAdministrador> call = usuarioAdministradorApiService.updateUsuarioAdministrador(userId,admi);
        call.enqueue(new Callback<UsuarioAdministrador>() {
            @Override
            public void onResponse(Call<UsuarioAdministrador> call, Response<UsuarioAdministrador> response) {
                if (response.isSuccessful()) {
                    admi = response.body();
                } else {
                    // Maneja errores del servidor, por ejemplo, un error 404 o 500.
                    Log.e("Error", "Error en la respuesta: " + response.code());
                }
                callback.onCompletion();
            }

            @Override
            public void onFailure(Call<UsuarioAdministrador> call, Throwable t) {
                // Maneja errores de red o de conversión de datos
                Log.e("Error", "Fallo en la petición: " + t.getMessage());
            }
        });
    }

    public void cargarAdmi(_37_crea_tu_gimnasio_admin.InfoCallback callback)
    {
        Call<UsuarioAdministrador> call = usuarioAdministradorApiService.getUsuarioById(userId);
        call.enqueue(new Callback<UsuarioAdministrador>() {
            @Override
            public void onResponse(Call<UsuarioAdministrador> call, Response<UsuarioAdministrador> response) {
                if (response.isSuccessful()) {
                    admi = response.body();
                } else {
                    // Maneja errores del servidor, por ejemplo, un error 404 o 500.
                    Log.e("Error", "Error en la respuesta: " + response.code());
                }
                callback.onCompletion();
            }

            @Override
            public void onFailure(Call<UsuarioAdministrador> call, Throwable t) {
                // Maneja errores de red o de conversión de datos
                Log.e("Error", "Fallo en la petición: " + t.getMessage());
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);

                int targetWidth = 348;
                int targetHeight = 170;
                Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, targetWidth, targetHeight, true);

                imagenGimnasio_37.setImageBitmap(scaledBitmap);
                textSubaImagen_37.setVisibility(GONE);
                imageSubirImagen_37.setVisibility(GONE);

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] imageBytes = baos.toByteArray();
                String imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);
                gym.setImagenGimnasio(imageString);


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
