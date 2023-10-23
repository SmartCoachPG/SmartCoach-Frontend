package com.example.smartcoach.ui;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartcoach.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import api.DateSerializer;
import api.Exercise.RutinaApiService;
import api.SharedPreferencesUtil;
import api.TimeDeserializer;
import api.TimeSerializer;
import api.User.UsuarioClienteApiService;
import api.retro;
import model.Exercise.Rutina;
import model.User.UsuarioCliente;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class _95_crear_rutina_usuario extends BaseActivityCliente {

    ImageView ovaloSup;
    TextView tituloPt2, bienvenida, setTextNombreUser, setTextDuracionRutina;
    Button btnCrearRutina;
    private final Map<ImageButton, Integer> originalImages = new HashMap<>();
    private final Map<ImageButton, Integer> selectedImages = new HashMap<>();
    private int selectedDay = -1;

    Long userId;
    String token;
    ImageButton imageLunes, imageMartes, imageMiercoles, imageJueves, imageViernes, imageSabado, imageDomingo;

    UsuarioClienteApiService usuarioClienteApiService;
    RutinaApiService rutinaApiService;
    List<Rutina> listaRutinas= new ArrayList<>();
    Map<String,Time> duracionRD = new HashMap<>();

    Boolean valida = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout._95_crear_rutina_usuario);
        getSupportActionBar().hide();

        userId = SharedPreferencesUtil.getUserId(_95_crear_rutina_usuario.this);
        token = SharedPreferencesUtil.getToken(_95_crear_rutina_usuario.this);
        iniciarPeticiones();

        tituloPt2 = findViewById(R.id.tituloPt2);
        bienvenida = findViewById(R.id.bienvenida);
        setTextNombreUser = findViewById(R.id.setTextNombreUser);
        btnCrearRutina = findViewById(R.id.btnCrearRutina);
        setTextDuracionRutina = findViewById(R.id.setTextDuracionRutina_95);

        // Inflar el diseño del archivo XML secundario
        View diasSemanaView = LayoutInflater.from(this).inflate(R.layout._otros_dias_semana_ne, null);

        // Encuentra los ImageButtons en el diseño secundario
        imageLunes = diasSemanaView.findViewById(R.id.imageLunes);
        imageMartes = diasSemanaView.findViewById(R.id.imageMartes);
        imageMiercoles = diasSemanaView.findViewById(R.id.imageMiercoles);
        imageJueves = diasSemanaView.findViewById(R.id.imageJueves);
        imageViernes = diasSemanaView.findViewById(R.id.imageViernes);
        imageSabado = diasSemanaView.findViewById(R.id.imageSabado);
        imageDomingo = diasSemanaView.findViewById(R.id.imageDomingo);

        // Agrega aquí tu lógica para interactuar con los elementos de ambos diseños
        originalImages.put((ImageButton) findViewById(R.id.imageLunes), R.drawable.icon_lunes_ne);
        originalImages.put((ImageButton) findViewById(R.id.imageMartes), R.drawable.icon_martes_ne);
        originalImages.put((ImageButton) findViewById(R.id.imageMiercoles), R.drawable.icon_miercoles_ne);
        originalImages.put((ImageButton) findViewById(R.id.imageJueves), R.drawable.icon_jueves_ne);
        originalImages.put((ImageButton) findViewById(R.id.imageViernes), R.drawable.icon_viernes_ne);
        originalImages.put((ImageButton) findViewById(R.id.imageSabado), R.drawable.icon_sabado_ne);
        originalImages.put((ImageButton) findViewById(R.id.imageDomingo), R.drawable.icon_domingo_ne);

        selectedImages.put((ImageButton) findViewById(R.id.imageLunes), R.drawable.icon_lunes_na);
        selectedImages.put((ImageButton) findViewById(R.id.imageMartes), R.drawable.icon_martes_na);
        selectedImages.put((ImageButton) findViewById(R.id.imageMiercoles), R.drawable.icon_miercoles_na);
        selectedImages.put((ImageButton) findViewById(R.id.imageJueves), R.drawable.icon_jueves_na);
        selectedImages.put((ImageButton) findViewById(R.id.imageViernes), R.drawable.icon_viernes_na);
        selectedImages.put((ImageButton) findViewById(R.id.imageSabado), R.drawable.icon_sabado_na);
        selectedImages.put((ImageButton) findViewById(R.id.imageDomingo), R.drawable.icon_domingo_na);
        configureDayClickListeners();
        cargarInfo();
        cargarDuracion();

        btnCrearRutina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iniciarProcesos();

            }
        });
    }

    private void configureDayClickListeners() {
        ImageButton imageLunes = findViewById(R.id.imageLunes);
        ImageButton imageMartes = findViewById(R.id.imageMartes);
        ImageButton imageMiercoles = findViewById(R.id.imageMiercoles);
        ImageButton imageJueves = findViewById(R.id.imageJueves);
        ImageButton imageViernes = findViewById(R.id.imageViernes);
        ImageButton imageSabado = findViewById(R.id.imageSabado);
        ImageButton imageDomingo = findViewById(R.id.imageDomingo);

        imageLunes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedDay = Calendar.MONDAY;
                Time time = duracionRD.get("Lunes");
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(time);
                int hours = calendar.get(Calendar.HOUR_OF_DAY);
                if(time!=null)setTextDuracionRutina.setText(String.valueOf(hours));
                else setTextDuracionRutina.setText("0");
                restoreOriginalImages();
                updateSelectedImage(imageLunes);
            }
        });
        imageMartes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedDay = Calendar.TUESDAY;
                Time time = duracionRD.get("Martes");
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(time);
                int hours = calendar.get(Calendar.HOUR_OF_DAY);
                if(time!=null)setTextDuracionRutina.setText(String.valueOf(hours));
                else setTextDuracionRutina.setText("0");
                restoreOriginalImages();
                updateSelectedImage(imageMartes);
            }
        });
        imageMiercoles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedDay = Calendar.WEDNESDAY;
                Time time = duracionRD.get("Miércoles");
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(time);
                int hours = calendar.get(Calendar.HOUR_OF_DAY);
                if(time!=null)setTextDuracionRutina.setText(String.valueOf(hours));
                else setTextDuracionRutina.setText("0");
                restoreOriginalImages();
                updateSelectedImage(imageMiercoles);
            }
        });
        imageJueves.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedDay = Calendar.THURSDAY;
                Time time = duracionRD.get("Jueves");
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(time);
                int hours = calendar.get(Calendar.HOUR_OF_DAY);
                if(time!=null)setTextDuracionRutina.setText(String.valueOf(hours));
                else setTextDuracionRutina.setText("0");
                restoreOriginalImages();
                updateSelectedImage(imageJueves);
            }
        });
        imageViernes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedDay = Calendar.FRIDAY;
                Time time = duracionRD.get("Viernes");
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(time);
                int hours = calendar.get(Calendar.HOUR_OF_DAY);
                if(time!=null)setTextDuracionRutina.setText(String.valueOf(hours));
                else setTextDuracionRutina.setText("0");
                restoreOriginalImages();
                updateSelectedImage(imageViernes);
            }
        });
        imageSabado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedDay = Calendar.SATURDAY;
                Time time = duracionRD.get("Sábado");
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(time);
                int hours = calendar.get(Calendar.HOUR_OF_DAY);
                if(time!=null)setTextDuracionRutina.setText(String.valueOf(hours));
                else setTextDuracionRutina.setText("0");
                restoreOriginalImages();
                updateSelectedImage(imageSabado);
            }
        });
        imageDomingo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restoreOriginalImages();
                updateSelectedImage(imageDomingo);
                selectedDay = Calendar.SUNDAY;
                Time time = duracionRD.get("Domingo");
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(time);
                int hours = calendar.get(Calendar.HOUR_OF_DAY);
                if(time!=null)setTextDuracionRutina.setText(String.valueOf(hours));
                else setTextDuracionRutina.setText("0");
            }
        });
    }

    private void restoreOriginalImages() {
        for (Map.Entry<ImageButton, Integer> entry : originalImages.entrySet()) {
            ImageButton button = entry.getKey();
            int drawableId = entry.getValue();
            button.setImageResource(drawableId);
        }
    }

    private void updateSelectedImage(ImageButton button) {
        if (button != null && selectedImages.containsKey(button)) {
            int drawableId = selectedImages.get(button);
            button.setImageResource(drawableId);
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

        OkHttpClient okHttpClient = retro.getUnsafeOkHttpClientWithToken(token)
                .newBuilder()
                .addInterceptor(logging)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://10.0.2.2:8043/api/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        usuarioClienteApiService = retrofit.create(UsuarioClienteApiService.class);
        rutinaApiService = retrofit.create(RutinaApiService.class);

    }

    interface InfoCallback {
        void onCompletion();
    }

    private void iniciarProcesos()
    {
        crearRutina(new InfoCallback() {
            @Override
            public void onCompletion() {
                    validarRutina(new InfoCallback()
                    {
                        @Override
                        public void onCompletion()
                        {

                            if(valida)
                            {
                                Intent intent = new Intent(_95_crear_rutina_usuario.this, _98_ver_rutina_ejercicio_usuario.class);
                                SharedPreferencesUtil.saveRutina(_95_crear_rutina_usuario.this,"1");
                                Log.d("RUTINA CREADAAA", "resultado: "+SharedPreferencesUtil.getRutina(_95_crear_rutina_usuario.this));
                                Toast.makeText(_95_crear_rutina_usuario.this, "Rutina creada", Toast.LENGTH_SHORT).show();
                                startActivity(intent);
                            }
                            else {
                                showDialog();
                            }
                        }
                    });
            }
        });
    }

    private void showDialog() {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout._97_mensaje_error_rutina_limitaciones_fisicas);
        dialog.getWindow().setBackgroundDrawable(null);

        Button btnContinuar = dialog.findViewById(R.id.btnSeguirCamposVacios);
        btnContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               dialog.dismiss();
            }
        });

        dialog.show();
    }
    private void cargarInfo()
    {
        Call<UsuarioCliente> call = usuarioClienteApiService.getUsuarioById(userId);
        call.enqueue(new Callback<UsuarioCliente>() {
            @Override
            public void onResponse(Call<UsuarioCliente> call, Response<UsuarioCliente> response) {
                if (response.isSuccessful()) {
                    UsuarioCliente usuario = response.body();
                    Log.d("Usuario", "Nombre: " + usuario.getNombre());
                    setTextNombreUser.setText(usuario.getNombre());
                    setTextDuracionRutina.setText("0");
                } else {
                    // Maneja errores del servidor, por ejemplo, un error 404 o 500.
                    Log.e("Error", "Error en la respuesta: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<UsuarioCliente> call, Throwable t) {
                // Maneja errores de red o de conversión de datos
                Log.e("Error", "Fallo en la petición: " + t.getMessage());
            }
        });
    }

    private void crearRutina(InfoCallback callback)
    {
        Call<Void> call = usuarioClienteApiService.crearRutina(userId);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                } else {
                    // Maneja errores del servidor, por ejemplo, un error 404 o 500.
                    Log.e("Error", "Error en la respuesta: " + response.code());
                }
                callback.onCompletion();

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // Maneja errores de red o de conversión de datos
                Log.e("Error", "Fallo en la petición: " + t.getMessage());
            }
        });
    }

    private void validarRutina(InfoCallback callback)
    {
        Call<Boolean> call = usuarioClienteApiService.validarRutina(userId);
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.isSuccessful()) {
                    valida = response.body();
                    Log.d("RUTINA USUARIO VALIDA?", "respuesta: "+response.body());
                } else {
                    // Maneja errores del servidor, por ejemplo, un error 404 o 500.
                    Log.e("Error", "Error en la respuesta: " + response.code());
                }
                callback.onCompletion();

            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                // Maneja errores de red o de conversión de datos
                Log.e("Error", "Fallo en la petición: " + t.getMessage());
            }
        });
    }

    private void cargarDuracion()
    {
        Call<List<Rutina>> call = rutinaApiService.getByUsuarioClienteId(userId.intValue());
        call.enqueue(new Callback<List<Rutina>>() {
            @Override
            public void onResponse(Call<List<Rutina>> call, Response<List<Rutina>>response) {
                if (response.isSuccessful()) {
                    listaRutinas = response.body();
                    Log.d("Rutinas", "estas son: "+listaRutinas);
                    for(Rutina rut : listaRutinas)
                    {
                        duracionRD.put(rut.getDia(),rut.getDuracion());
                    }
                    Log.d("ORGANIZAR RD", "lista:"+duracionRD);
                } else {
                    // Maneja errores del servidor, por ejemplo, un error 404 o 500.
                    Log.e("Error", "Error en la respuesta: " + response.code());
                }
            }
            @Override
            public void onFailure(Call<List<Rutina>> call, Throwable t) {
                // Maneja errores de red o de conversión de datos
                Log.e("Error", "Fallo en la petición: " + t.getMessage());
            }
        });

    }

}

