package com.example.smartcoach.ui;
import static android.view.View.GONE;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import com.example.smartcoach.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import api.Admi.GimnasioApiService;
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

public class _36_modificar_informacion_gimnasio extends BaseActivityAdmi {
    ImageView rectanguloTitulo_36;
    TextView titulo_36, descripcionModificarGym_36,setTextPisosGimnasio_36;
    ImageButton btnRegresar_36, imagenGimnasio_36;
    EditText setTextnombreGimnasio_36, setTextDireccionGimnasio_36, setTextBarrioGimnasio_36, setTextAnchoGimnasio_36, setTextAltoGimnasio_36 ;
    Button btnCrearMapa_36;

    Long userId;
    String token;
    GimnasioApiService gimnasioApiService;
    MapaApiService mapaApiService;
    UsuarioAdministradorApiService usuarioAdministradorApiService;
    Gimnasio gym = new Gimnasio();
    UsuarioAdministrador admi = new UsuarioAdministrador();
    Mapa mapa = new Mapa();

    private static final int REQUEST_CODE = 100; // Para la solicitud de permiso
    private static final int PICK_IMAGE_REQUEST = 101; // Para identificar la solicitud de selección de imagen


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout._36_modificar_informacion_gimnasio);
        getSupportActionBar().hide();
        userId = SharedPreferencesUtil.getUserId(_36_modificar_informacion_gimnasio.this);
        token = SharedPreferencesUtil.getToken(_36_modificar_informacion_gimnasio.this);

        iniciarPeticiones();
        cargarInfo();

        rectanguloTitulo_36 = findViewById(R.id.rectanguloTitulo_36);
        titulo_36 = findViewById(R.id.titulo_36);
        btnRegresar_36 = findViewById(R.id.btnRegresar_36);
        descripcionModificarGym_36 = findViewById(R.id.descripcionModificarGym_36);
        setTextnombreGimnasio_36 = findViewById(R.id.setTextnombreGimnasio_36);
        imagenGimnasio_36 = findViewById(R.id.imagenGimnasio_36);
        setTextDireccionGimnasio_36 = findViewById(R.id.setTextDireccionGimnasio_36);
        setTextBarrioGimnasio_36 = findViewById(R.id.setTextBarrioGimnasio_36);
        setTextAnchoGimnasio_36 = findViewById(R.id.setTextAnchoGimnasio_36);
        setTextAltoGimnasio_36 = findViewById(R.id.setTextAltoGimnasio_36);
        setTextPisosGimnasio_36 = findViewById(R.id.setTextPisosGimnasio_36);
        btnCrearMapa_36 = findViewById(R.id.btnCrearMapa_36);

        btnRegresar_36.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btnCrearMapa_36.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guardarInfo();
            }
        });

        imagenGimnasio_36.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cambiarImagen();
            }
        });
        setTextPisosGimnasio_36.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(_36_modificar_informacion_gimnasio.this, "No puedes modificar los pisos de tu gimnasio", Toast.LENGTH_SHORT).show();

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

    private void cargarInfo()
    {
        cargarAdmi(new InfoCallback() {
            @Override
            public void onCompletion() {
                Log.d("MODI GYM", "cargo admi: "+admi);
                cargarGym(new InfoCallback() {
                    @Override
                    public void onCompletion() {
                        Log.d("MODI GYM", "cargo gym: "+gym);

                        cargarMapa(new InfoCallback() {

                            @Override
                            public void onCompletion() {
                                Log.d("MODI GYM", "cargo mapa: "+mapa);
                                mostrarInfo();
                            }
                        });
                    }
                });
            }
        });
    }

    private void cargarAdmi(_36_modificar_informacion_gimnasio.InfoCallback callback)
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

    private void cargarGym(_36_modificar_informacion_gimnasio.InfoCallback callback)
    {
        Call<Gimnasio> call = gimnasioApiService.findById(admi.getGimnasioId().longValue());
        call.enqueue(new Callback<Gimnasio>() {
            @Override
            public void onResponse(Call<Gimnasio> call, Response<Gimnasio> response) {
                if (response.isSuccessful()) {
                    gym = response.body();
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

    private void cargarMapa(_36_modificar_informacion_gimnasio.InfoCallback callback)
    {
        Call<List<Mapa>> call = mapaApiService.getMapasByGimnasioId(gym.getId().intValue());
        call.enqueue(new Callback<List<Mapa>>() {
            @Override
            public void onResponse(Call<List<Mapa>> call, Response<List<Mapa>> response) {
                if (response.isSuccessful()) {
                    List<Mapa> result = response.body();
                    if(!result.isEmpty())
                    mapa = result.get(0);
                } else {
                    // Maneja errores del servidor, por ejemplo, un error 404 o 500.
                    Log.e("Error", "Error en la respuesta: " + response.code());
                }
                callback.onCompletion();
            }

            @Override
            public void onFailure(Call<List<Mapa>> call, Throwable t) {
                // Maneja errores de red o de conversión de datos
                Log.e("Error", "Fallo en la petición: " + t.getMessage());
            }
        });
    }

    private void mostrarInfo()
    {
        setTextnombreGimnasio_36.setText(gym.getNombre());
        setTextDireccionGimnasio_36.setText(gym.getDireccion());
        setTextBarrioGimnasio_36.setText(gym.getBarrio());
        setTextAnchoGimnasio_36.setText(mapa.getAncho().toString());
        setTextAltoGimnasio_36.setText(mapa.getAlto().toString());
        setTextPisosGimnasio_36.setText(gym.getPisos().toString());
        String imageString = gym.getImagenGimnasio();
        byte[] imageBytes = Base64.decode(imageString, Base64.DEFAULT);
        Bitmap decodedBitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        imagenGimnasio_36.setImageBitmap(decodedBitmap);
    }

    private void guardarInfo()
    {
        guardarGym(new InfoCallback() {
            @Override
            public void onCompletion() {
                guardarMapa(new InfoCallback() {
                    @Override
                    public void onCompletion() {
                        Toast.makeText(_36_modificar_informacion_gimnasio.this, "Cambios Guardados", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(_36_modificar_informacion_gimnasio.this, _27_configurar_mapa_admin.class);
                        startActivity(intent);
                    }
                });
            }
        });

    }

    private void guardarGym(_36_modificar_informacion_gimnasio.InfoCallback callback)
    {
        gym.setNombre(setTextnombreGimnasio_36.getText().toString());
        gym.setDireccion(setTextDireccionGimnasio_36.getText().toString());
        gym.setBarrio(setTextBarrioGimnasio_36.getText().toString());

        Call<Gimnasio> call = gimnasioApiService.save(gym);
        call.enqueue(new Callback<Gimnasio>() {
            @Override
            public void onResponse(Call<Gimnasio> call, Response<Gimnasio> response) {
                if (response.isSuccessful()) {

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

    private void guardarMapa(_36_modificar_informacion_gimnasio.InfoCallback callback)
    {
        String alto = setTextAltoGimnasio_36.getText().toString();
        String ancho = setTextAnchoGimnasio_36.getText().toString();

        mapa.setAlto(Integer.parseInt(alto));
        mapa.setAncho(Integer.parseInt(ancho));

        Call<Mapa> call = mapaApiService.save(mapa);
        call.enqueue(new Callback<Mapa>() {
            @Override
            public void onResponse(Call<Mapa> call, Response<Mapa> response) {
                if (response.isSuccessful()) {
                    Log.d("MODI", "mapa nuevo: "+response.body());
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

                imagenGimnasio_36.setImageBitmap(scaledBitmap);

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
