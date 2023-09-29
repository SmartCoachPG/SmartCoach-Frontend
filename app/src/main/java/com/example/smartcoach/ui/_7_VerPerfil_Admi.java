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
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartcoach.R;

import api.Admi.UsuarioAdministradorApiService;
import api.SharedPreferencesUtil;
import api.retro;
import model.Admi.UsuarioAdministrador;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class _7_VerPerfil_Admi extends AppCompatActivity {

    TextView textoIngresoNombre, textoIngresoEmail,textoIngresoCedula, textoIngresoPuesto,nombreAdmi, puestoAdmi;

    ImageButton flechaRegresar, imagePP, botonModificar;

    ImageView admiCheck;

    Long userId = SharedPreferencesUtil.getUserId(_7_VerPerfil_Admi.this);
    String token = SharedPreferencesUtil.getToken(_7_VerPerfil_Admi.this);

    UsuarioAdministradorApiService usuarioAdministradorApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout._7_ver_perfil_admi);
        iniciarPeticiones();

        textoIngresoNombre = findViewById(R.id.texto_Ingreso_Nombre_admin_7);
        textoIngresoEmail = findViewById(R.id.textoIngresoEmail_admin_7);
        textoIngresoCedula = findViewById(R.id.textoIngresoCedula_admin_7);
        textoIngresoPuesto = findViewById(R.id.textoIngresoPuesto_admin_7);
        nombreAdmi = findViewById(R.id.nombre_admin_7);
        puestoAdmi = findViewById(R.id.puesto_admin_7);
        flechaRegresar = findViewById(R.id.flechaRegresar);
        imagePP = findViewById(R.id.imagePP_admin_7);
        botonModificar = findViewById(R.id.boton_modificar_admin_7);
        admiCheck = findViewById(R.id.admiCheck_7);

        cargarInfo();

        flechaRegresar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(_7_VerPerfil_Admi.this, _6_Principal_Admi.class));
                    }
                });

        botonModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(_7_VerPerfil_Admi.this, _8_ModificarPerfil_Admi.class));
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

        usuarioAdministradorApiService = retrofit.create(UsuarioAdministradorApiService.class);

    }

    private void cargarInfo()
    {
        Call<UsuarioAdministrador> call = usuarioAdministradorApiService.getUsuarioById(userId);

        call.enqueue(new Callback<UsuarioAdministrador>() {
            @Override
            public void onResponse(Call<UsuarioAdministrador> call, Response<UsuarioAdministrador> response) {
                if (response.isSuccessful()) {
                    UsuarioAdministrador usuario = response.body();
                    // Haz algo con el objeto Usuario, por ejemplo:
                    Log.d("Usuario", "Nombre: " + usuario.getNombre());

                    nombreAdmi.setText(usuario.getNombre());
                    puestoAdmi.setText(usuario.getPuesto());
                    textoIngresoNombre.setText(usuario.getNombre());
                    textoIngresoEmail.setText(usuario.getEmail());
                    textoIngresoCedula.setText(Long.toString(usuario.getCedula()));
                    textoIngresoPuesto.setText(usuario.getPuesto());

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

                    if(usuario.getVerificado()==1)
                    {
                        admiCheck.setVisibility(View.VISIBLE);
                    }
                    else
                    {
                        admiCheck.setVisibility(View.GONE);
                    }

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

}
