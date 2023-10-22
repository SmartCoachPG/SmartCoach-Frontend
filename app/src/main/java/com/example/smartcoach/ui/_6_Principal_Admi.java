package com.example.smartcoach.ui;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

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

public class _6_Principal_Admi extends BaseActivityAdmi {

    ImageButton flechaRegresar, eliminarCuenta, imagePP,verificacionAdmin,cerrarSesion,infoAdmi;
    TextView nombreAdmi, puestoAdmi, infoAdmiTexto,verificacionAdmiTexto,eliminarCuentaTexto,cerrarSesionTexto;
    ImageView admiCheck;
        Long userId;
        String token;

    UsuarioAdministradorApiService usuarioAdministradorApiService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout._6_principal_admi);
        userId = SharedPreferencesUtil.getUserId(_6_Principal_Admi.this);
        token = SharedPreferencesUtil.getToken(_6_Principal_Admi.this);
        iniciarPeticiones();

        // Inicializa las vistas
        flechaRegresar = findViewById(R.id.flechaRegresar);
        imagePP = findViewById(R.id.imagePP_admin_6);
        nombreAdmi = findViewById(R.id.nombreAdmin_admin_6);
        puestoAdmi = findViewById(R.id.puesto_admin_6);
        infoAdmi = findViewById(R.id.info_admi);
        verificacionAdmin = findViewById(R.id.verificacion_admin_6);
        eliminarCuenta = findViewById(R.id.eliminar_cuenta_admin_6);
        cerrarSesion = findViewById(R.id.cerrar_sesion_admin_6);
        infoAdmiTexto = findViewById(R.id.info_admi_texto_admin_6);
        verificacionAdmiTexto = findViewById(R.id.verificacion_admi_texto_admin_6);
        eliminarCuentaTexto = findViewById(R.id.eliminar_cuenta_texto_admin_6);
        cerrarSesionTexto = findViewById(R.id.cerrar_sesion_texto_admin_6);
        admiCheck = findViewById(R.id.admiCheck_6);

        cargarInfo();
        infoAdmi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(_6_Principal_Admi.this, _7_VerPerfil_Admi.class));
            }
        });

        infoAdmiTexto.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(_6_Principal_Admi.this, _7_VerPerfil_Admi.class));
                    }
                });

        eliminarCuenta.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showDialog();
                    }
                });

        eliminarCuentaTexto.setOnClickListener(
                new View.OnClickListener() {
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
        Call<Void> call = usuarioAdministradorApiService.deleteUsuarioAdministrador(userId);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(_6_Principal_Admi.this, "Cuenta eliminada", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(_6_Principal_Admi.this, _1_PantallaInicio.class));
                } else {
                    Toast.makeText(_6_Principal_Admi.this, "Error al eliminar la cuenta", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(_6_Principal_Admi.this, "Error de conexión", Toast.LENGTH_SHORT).show();
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


    private void cerrarSesion()
    {
        SharedPreferencesUtil.deleteToken(_6_Principal_Admi.this);
        SharedPreferencesUtil.deleteUserId(_6_Principal_Admi.this);
        Toast.makeText(_6_Principal_Admi.this, "Cesion Cerrada", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(_6_Principal_Admi.this, _2_IniciarSesionRegistrarse.class);
        startActivity(intent);
    }
}