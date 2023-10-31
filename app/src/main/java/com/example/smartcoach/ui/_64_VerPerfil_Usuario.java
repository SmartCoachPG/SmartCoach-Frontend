package com.example.smartcoach.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import com.example.smartcoach.R;
import java.text.SimpleDateFormat;
import api.SharedPreferencesUtil;
import api.User.ObjetivoRutinaApiService;
import api.User.UsuarioClienteApiService;
import api.retro;
import model.User.ObjetivoRutina;
import model.User.UsuarioCliente;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class _64_VerPerfil_Usuario extends BaseActivityCliente {

    ImageButton flechaRegresar,imagePP,botonModificar;
    TextView nombre_user,objetivoUser,textoIngresoNombre_user,textoIngresoEmail_user,textoIngresoGenero_user,textoFechaNacimiento_user,textoIngresoObjetivo_user;
    Long userId;
    String token;
    UsuarioClienteApiService usuarioClienteApiService;
    ObjetivoRutinaApiService objetivoRutinaApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout._64_ver_perfil_usuario);
        userId = SharedPreferencesUtil.getUserId(_64_VerPerfil_Usuario.this);
        token = SharedPreferencesUtil.getToken(_64_VerPerfil_Usuario.this);
        iniciarPeticiones();

        flechaRegresar = findViewById(R.id.flechaRegresar_64);
        imagePP = findViewById(R.id.imagePP_64);
        botonModificar = findViewById(R.id.boton_modificar_user_64);
        nombre_user = findViewById(R.id.nombre_user_64);
        objetivoUser = findViewById(R.id.objetivoUser_64);
        textoIngresoNombre_user = findViewById(R.id.textoIngresoNombre_user_64);
        textoIngresoEmail_user = findViewById(R.id.textoIngresoEmail_user_64);
        textoIngresoGenero_user = findViewById(R.id.textoIngresoGenero_user_64);
        textoFechaNacimiento_user = findViewById(R.id.textoFechaNacimiento_user_64);
        textoIngresoObjetivo_user = findViewById(R.id.textoIngresoObjetivo_user_64);
        cargarInfo();

        flechaRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(_64_VerPerfil_Usuario.this, _63_Principal_Usuario.class));
            }
        });

        botonModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(_64_VerPerfil_Usuario.this, _65_ModificarPerfil_Usuario.class));
            }
        });

    }

    private void iniciarPeticiones()
    {

        OkHttpClient okHttpClient = retro.getUnsafeOkHttpClientWithToken(token);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://10.0.2.2:8043/api/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        usuarioClienteApiService = retrofit.create(UsuarioClienteApiService.class);
        objetivoRutinaApiService = retrofit.create(ObjetivoRutinaApiService.class);

    }

    private void cargarInfo()
    {
        Call<UsuarioCliente> call = usuarioClienteApiService.getUsuarioById(userId);
        call.enqueue(new Callback<UsuarioCliente>() {
            @Override
            public void onResponse(Call<UsuarioCliente> call, Response<UsuarioCliente> response) {
                if (response.isSuccessful()) {
                    UsuarioCliente usuario = response.body();
                    nombre_user.setText(usuario.getNombre());
                    textoIngresoNombre_user.setText(usuario.getNombre());
                    textoIngresoEmail_user.setText(usuario.getEmail());

                    if(usuario.getGenero().equals("F"))
                    {
                        textoIngresoGenero_user.setText("Femenino");
                    }
                    else
                    {
                        textoIngresoGenero_user.setText("Masculino");
                    }

                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                    String dateString = sdf.format(usuario.getFechaDeNacimiento());
                    textoFechaNacimiento_user.setText(dateString);

                    if (usuario.getFotoPerfil() != null && !usuario.getFotoPerfil().isEmpty()) {
                        // Poner la imagen y que quede bien cortada
                        String imagenBase64 = usuario.getFotoPerfil(); // Cadena base64 recuperada del servidor
                        byte[] decodedString = android.util.Base64.decode(imagenBase64, android.util.Base64.DEFAULT);
                        Bitmap originalBitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                        int targetSize = (int) (100 * getResources().getDisplayMetrics().density);
                        Bitmap scaledBitmap = Bitmap.createScaledBitmap(originalBitmap, targetSize, targetSize, true);
                        Bitmap circularBitmap = Bitmap.createBitmap(targetSize, targetSize, Bitmap.Config.ARGB_8888);
                        Canvas canvas = new Canvas(circularBitmap);
                        Paint paint = new Paint();
                        paint.setAntiAlias(true);
                        paint.setShader(new BitmapShader(scaledBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
                        float radius = targetSize / 2f;
                        canvas.drawCircle(radius, radius, radius, paint);
                        imagePP.setImageBitmap(circularBitmap);
                    }

                    cargarInfoObjetivoRutina((long)usuario.getObjetivoRutinaid());

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

    public void cargarInfoObjetivoRutina(Long id)
    {
        Call<ObjetivoRutina> call = objetivoRutinaApiService.getById(id);
        call.enqueue(new Callback<ObjetivoRutina>() {
            @Override
            public void onResponse(Call<ObjetivoRutina> call, Response<ObjetivoRutina> response) {
                if (response.isSuccessful()) {
                    ObjetivoRutina objetivoRutina1 = response.body();
                    objetivoUser.setText(objetivoRutina1.getNombre());
                    textoIngresoObjetivo_user.setText(objetivoRutina1.getNombre());

                } else {
                    // Maneja errores del servidor, por ejemplo, un error 404 o 500.
                    Log.e("Error", "Error en la respuesta: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ObjetivoRutina> call, Throwable t) {
                // Maneja errores de red o de conversión de datos
                Log.e("Error", "Fallo en la petición: " + t.getMessage());
            }
        });
    }
}
