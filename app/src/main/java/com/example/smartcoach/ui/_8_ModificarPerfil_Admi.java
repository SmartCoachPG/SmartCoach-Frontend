package com.example.smartcoach.ui;

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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.example.smartcoach.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import api.Admi.UsuarioAdministradorApiService;
import api.DateSerializer;
import api.SharedPreferencesUtil;
import api.retro;
import model.Admi.UsuarioAdministrador;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class _8_ModificarPerfil_Admi extends BaseActivityAdmi {

    ImageButton flechaRegresar,imagePP;
    TextView nombreAdmi,puestoAdmi,textoIngresoCedula;
    AppCompatButton botonGuardarCambios;
    ImageView admiCheck;
    EditText textoIngresoNombre,textoIngresoEmail,textoIngresoPuesto;
    Long userId = SharedPreferencesUtil.getUserId(_8_ModificarPerfil_Admi.this);
    String token = SharedPreferencesUtil.getToken(_8_ModificarPerfil_Admi.this);
    UsuarioAdministradorApiService usuarioAdministradorApiService;
    UsuarioAdministrador usuarioAdministrador;

    private static final int REQUEST_CODE = 100; // Para la solicitud de permiso
    private static final int PICK_IMAGE_REQUEST = 101; // Para identificar la solicitud de selección de imagen

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
        admiCheck = findViewById(R.id.admiCheck_8);

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

        imagePP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                    nombreAdmi.setText(usuarioAdministrador.getNombre());
                    puestoAdmi.setText(usuarioAdministrador.getPuesto());
                    textoIngresoNombre.setText(usuarioAdministrador.getNombre());
                    textoIngresoEmail.setText(usuarioAdministrador.getEmail());
                    textoIngresoCedula.setText(Long.toString(usuarioAdministrador.getCedula()));
                    textoIngresoPuesto.setText(usuarioAdministrador.getPuesto());

                    if (usuarioAdministrador.getFotoPerfil() != null && !usuarioAdministrador.getFotoPerfil().isEmpty()) {
                        // Poner la imagen y que quede bien cortada
                        String imagenBase64 = usuarioAdministrador.getFotoPerfil(); // Cadena base64 recuperada del servidor
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

                    if(usuarioAdministrador.getVerificado()==1)
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

    private void modificarInfo()
    {
        usuarioAdministrador.setNombre(textoIngresoNombre.getText().toString());
        usuarioAdministrador.setEmail(textoIngresoEmail.getText().toString());
        usuarioAdministrador.setCedula(Long.parseLong(textoIngresoCedula.getText().toString()));
        usuarioAdministrador.setPuesto(textoIngresoPuesto.getText().toString());

        Call<UsuarioAdministrador> call = usuarioAdministradorApiService.updateUsuarioAdministrador(userId, usuarioAdministrador);

        call.enqueue(new Callback<UsuarioAdministrador>() {
            @Override
            public void onResponse(Call<UsuarioAdministrador> call, Response<UsuarioAdministrador> response) {
                if (response.isSuccessful()) {
                    UsuarioAdministrador usuarioAdministrador1 = response.body();
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
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] imageBytes = baos.toByteArray();
                String imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);
                byte[] decodedString = android.util.Base64.decode(imageString, android.util.Base64.DEFAULT);
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
                usuarioAdministrador.setFotoPerfil(imageString);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
