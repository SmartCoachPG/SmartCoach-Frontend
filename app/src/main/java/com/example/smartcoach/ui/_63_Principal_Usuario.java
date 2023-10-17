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
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import api.DateSerializer;
import api.SharedPreferencesUtil;


import androidx.appcompat.app.AppCompatActivity;

import com.example.smartcoach.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import java.util.Date;

import api.User.ObjetivoRutinaApiService;
import api.User.UsuarioClienteApiService;
import api.retro;
import model.User.ObjetivoRutina;
import model.User.UsuarioCliente;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class _63_Principal_Usuario extends BaseActivityCliente {

    ImageButton imagePP, infoUser,limitacionesFisicas,horarioRutinas,registroProgreso,objetivos,composicionCorporal,eliminarCuenta,cerrarSesion;
    TextView objetivoRutina,nombreUser,infoUserTexto,limitacionesFisicasTexto,horarioRutinasTexto,registroProgresoTexto,composicionCorporalTexo,eliminarCuentaTexto,cerrarSesionTexto, objetivosTexto;
    Long userId;
    String token;

    UsuarioClienteApiService usuarioClienteApiService;
    ObjetivoRutinaApiService objetivoRutinaApiService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout._63_principal_usuario);
        userId = SharedPreferencesUtil.getUserId(_63_Principal_Usuario.this);
        token = SharedPreferencesUtil.getToken(_63_Principal_Usuario.this);
        iniciarPeticiones();

        imagePP = findViewById(R.id.imagePP_63);
        infoUser= findViewById(R.id.info_user_63);
        limitacionesFisicas = findViewById(R.id.limitaciones_fisicas_63);
        horarioRutinas = findViewById(R.id.horario_rutinas_63);
        registroProgreso = findViewById(R.id.registro_progreso_63);
        objetivos = findViewById(R.id.objetivos_63);
        composicionCorporal = findViewById(R.id.composicion_corporal_63);
        eliminarCuenta = findViewById(R.id.eliminar_cuenta_63);
        cerrarSesion = findViewById(R.id.cerrar_sesion_63);
        nombreUser = findViewById(R.id.nombreUser_63);
        objetivoRutina = findViewById(R.id.objetivoUser_63);
        infoUserTexto= findViewById(R.id.info_user_texto_63);
        limitacionesFisicasTexto = findViewById(R.id.limitaciones_fisicas_texto_63);
        horarioRutinasTexto = findViewById(R.id.horario_rutinas_texto_63);
        registroProgresoTexto = findViewById(R.id.registro_progreso_texto_63);
        objetivosTexto= findViewById(R.id.objetivos_texto_63);
        composicionCorporalTexo = findViewById(R.id.composicion_corporal_texto_63);
        eliminarCuentaTexto = findViewById(R.id.eliminar_cuenta_texto_63);
        cerrarSesionTexto = findViewById(R.id.cerrar_sesion_texto_63);
        cargarInfo();

        infoUser.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(_63_Principal_Usuario.this, _64_VerPerfil_Usuario.class));
                    }
                });

        infoUserTexto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(_63_Principal_Usuario.this, _64_VerPerfil_Usuario.class));
            }
        });

        eliminarCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });

        eliminarCuentaTexto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });

        cerrarSesionTexto.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cerrarSesion();
                    }
                });

        cerrarSesion.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cerrarSesion();
                    }
                });


    }

    private void showDialog() {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout._84_confirmacion_eliminacion_cuenta); // Usa el nombre de tu archivo XML
        dialog.getWindow().setBackgroundDrawable(null);

        Button botonConfirmar = dialog.findViewById(R.id.botonConfirmar);
        botonConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Acciones a realizar cuando se presiona "Confirmar"
                eliminarCuenta();
            }
        });

        Button botonCancelar = dialog.findViewById(R.id.botonCancelar);
        botonCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Acciones a realizar cuando se presiona "Cancelar"
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void eliminarCuenta()
    {
        Call<Void> call = usuarioClienteApiService.deleteUsuarioCliente(userId);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(_63_Principal_Usuario.this, "Cuenta eliminada", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(_63_Principal_Usuario.this, _1_PantallaInicio.class));
                } else {
                    Toast.makeText(_63_Principal_Usuario.this, "Error al eliminar la cuenta", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(_63_Principal_Usuario.this, "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void iniciarPeticiones() {


        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = retro.getUnsafeOkHttpClientWithToken(token)
                .newBuilder()
                .addInterceptor(logging)
                .build();

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
                    // Haz algo con el objeto Usuario, por ejemplo:
                    Log.d("Usuario", "Nombre: " + usuario.getNombre());

                    nombreUser.setText(usuario.getNombre());
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
                    // Haz algo con el objeto Usuario, por ejemplo:
                    Log.d("Usuario", "Nombre: " + objetivoRutina1.getNombre());

                    objetivoRutina.setText(objetivoRutina1.getNombre());


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

    private void cerrarSesion()
    {
        SharedPreferencesUtil.deleteToken(_63_Principal_Usuario.this);
        SharedPreferencesUtil.deleteUserId(_63_Principal_Usuario.this);
        SharedPreferencesUtil.deleteRutina(_63_Principal_Usuario.this);
        Toast.makeText(_63_Principal_Usuario.this, "Cesion Cerrada", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(_63_Principal_Usuario.this, _2_IniciarSesionRegistrarse.class);
        startActivity(intent);
    }

}
