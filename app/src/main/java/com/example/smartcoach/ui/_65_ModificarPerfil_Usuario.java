package com.example.smartcoach.ui;

import android.Manifest;
import android.app.DatePickerDialog;
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
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.smartcoach.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonPrimitive;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import api.DateSerializer;
import api.SharedPreferencesUtil;
import api.User.ObjetivoRutinaApiService;
import api.User.UsuarioClienteApiService;
import api.retro;
import model.Admi.UsuarioAdministrador;
import model.User.ObjetivoRutina;
import model.User.UsuarioCliente;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class _65_ModificarPerfil_Usuario extends BaseActivityCliente {

    ImageButton flechaRegresar, imagePP;
    TextView nombreUser, objetivo;
    EditText textoIngresoNombre, textoIngresoEmail, textoFechaNacimiento, textoIngresoObjetivo;
    Spinner spinnerGenero,spinnerObjetivo;
    AppCompatButton botonGuardarCambios;

    Long userId;
    String token;

    UsuarioCliente usuarioCliente;

    UsuarioClienteApiService usuarioClienteApiService;
    ObjetivoRutinaApiService objetivoRutinaApiService;

    private static final int REQUEST_CODE = 100; // Para la solicitud de permiso
    private static final int PICK_IMAGE_REQUEST = 101; // Para identificar la solicitud de selección de imagen


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout._65_modificar_perfil_usuario);
        userId = SharedPreferencesUtil.getUserId(_65_ModificarPerfil_Usuario.this);
        token = SharedPreferencesUtil.getToken(_65_ModificarPerfil_Usuario.this);
        iniciarPeticiones();

        flechaRegresar = findViewById(R.id.flechaRegresar_65);
        imagePP = findViewById(R.id.imagePP_65);
        nombreUser = findViewById(R.id.nombre_user_65);
        objetivo = findViewById(R.id.Objetivo_user_65);
        textoIngresoNombre = findViewById(R.id.texto_Ingreso_Nombre_user_65);
        textoIngresoEmail = findViewById(R.id.textoIngresoEmail_user_65);
        textoFechaNacimiento = findViewById(R.id.textoFechaNacimiento_user_65);
        spinnerObjetivo = findViewById(R.id.spinnerObjetivo_65);
        botonGuardarCambios = findViewById(R.id.botonGuardarCambios_65);
        spinnerGenero = findViewById(R.id.spinnerGenero_65);
        cargarInfo();
        flechaRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(_65_ModificarPerfil_Usuario.this, _64_VerPerfil_Usuario.class));
            }
        });

        //Spinner
        String[] opcionesGenero = new String[]{"Seleccione", "Femenino", "Masculino"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_item, opcionesGenero);
        adapter.setDropDownViewResource(R.layout.spinner_item);
        spinnerGenero.setAdapter(adapter);


        //Calendario
        textoFechaNacimiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        botonGuardarCambios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modificarInfo();
            }
        });

        imagePP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cambiarImagen();
            }
        });

    }

    private void showDatePickerDialog() {
        // Calendario con la fecha actual
        Calendar currentCalendar = Calendar.getInstance();
        int currentYear = currentCalendar.get(Calendar.YEAR);
        int currentMonth = currentCalendar.get(Calendar.MONTH);
        int currentDayOfMonth = currentCalendar.get(Calendar.DAY_OF_MONTH);

        // Calendario para la fecha mínima (1 de enero de 1905)
        Calendar minDateCalendar = new GregorianCalendar(1905, Calendar.JANUARY, 1);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                R.style.DatePickerDialogTheme, // Usa el nuevo tema personalizado
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Calendar selectedDateUTC = new GregorianCalendar(TimeZone.getTimeZone("UTC"));
                        selectedDateUTC.set(year, month, dayOfMonth, 0, 0, 0);
                        selectedDateUTC.set(Calendar.MILLISECOND, 0);

                        // Calendario para verificar si la persona tiene al menos 18 años
                        Calendar minAgeCalendar = new GregorianCalendar();
                        minAgeCalendar.add(Calendar.YEAR, -18);

                        // Verificar si la fecha seleccionada es válida (mayor de 18 años)
                        if (!selectedDateUTC.before(minDateCalendar) && selectedDateUTC.before(minAgeCalendar)) {
                            // Settear la fecha seleccionada y mostrarla en el EditText
                            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                            String fechaNacimiento = sdf.format(selectedDateUTC.getTime());
                            textoFechaNacimiento.setText(fechaNacimiento);
                            Log.d("DateSerializer", "Formatted date: " + fechaNacimiento);

                            // Aquí es donde estableces la fecha de nacimiento en usuarioCliente
                            usuarioCliente.setFechaDeNacimiento(selectedDateUTC.getTime());

                        } else {
                            Toast.makeText(_65_ModificarPerfil_Usuario.this, "Selecciona una fecha válida (mayor de 18 años)", Toast.LENGTH_SHORT).show();
                        }
                    }

                }, currentYear, currentMonth, currentDayOfMonth);

        datePickerDialog.getDatePicker().setMinDate(minDateCalendar.getTimeInMillis());
        datePickerDialog.getDatePicker().setMaxDate(currentCalendar.getTimeInMillis());
        datePickerDialog.show();
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
                    usuarioCliente = response.body();
                    // Haz algo con el objeto Usuario, por ejemplo:
                    Log.d("Usuario", "Nombre: " + usuarioCliente.getNombre());

                    nombreUser.setText(usuarioCliente.getNombre());
                    textoIngresoNombre.setText(usuarioCliente.getNombre());
                    textoIngresoEmail.setText(usuarioCliente.getEmail());

                    if(usuarioCliente.getGenero().equals("F"))
                    {
                        spinnerGenero.setSelection(1);
                    }
                    else
                    {
                        spinnerGenero.setSelection(2);
                    }

                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                    String dateString = sdf.format(usuarioCliente.getFechaDeNacimiento());
                    textoFechaNacimiento.setText(dateString);

                    if (usuarioCliente.getFotoPerfil() != null && !usuarioCliente.getFotoPerfil().isEmpty()) {
                        // Poner la imagen y que quede bien cortada
                        String imagenBase64 = usuarioCliente.getFotoPerfil(); // Cadena base64 recuperada del servidor
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

                    cargarInfoObjetivoRutina((long)usuarioCliente.getObjetivoRutinaid());
                    cargarObjetivos((long)usuarioCliente.getObjetivoRutinaid());

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

    private void cargarInfoObjetivoRutina(Long id)
    {
        Call<ObjetivoRutina> call = objetivoRutinaApiService.getById(id);
        call.enqueue(new Callback<ObjetivoRutina>() {
            @Override
            public void onResponse(Call<ObjetivoRutina> call, Response<ObjetivoRutina> response) {
                if (response.isSuccessful()) {
                    ObjetivoRutina objetivoRutina1 = response.body();
                    // Haz algo con el objeto Usuario, por ejemplo:
                    Log.d("Usuario", "Nombre: " + objetivoRutina1.getNombre());

                    objetivo.setText(objetivoRutina1.getNombre());
                    // aqui tambien tiene que ser un spinner
                    //textoIngresoObjetivo_user.setText(objetivoRutina1.getNombre());

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

    private void cargarObjetivos(Long id)
    {
        Call<List<ObjetivoRutina>> call = objetivoRutinaApiService.getAll();
        call.enqueue(new Callback<List<ObjetivoRutina>>() {
            @Override
            public void onResponse(Call<List<ObjetivoRutina>> call, Response<List<ObjetivoRutina>> response) {
                if (response.isSuccessful()) {
                    int posi=0;
                    List<ObjetivoRutina> objetivoRutina = response.body();
                    String[] opcionesObjetivos = new String[objetivoRutina.size()];
                    for (int i = 0; i < objetivoRutina.size(); i++) {
                        if(objetivoRutina.get(i).getId()==id)
                        {
                            posi=i;
                        }
                        opcionesObjetivos[i] = objetivoRutina.get(i).getNombre();
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(_65_ModificarPerfil_Usuario.this, R.layout.spinner_item, opcionesObjetivos);
                    adapter.setDropDownViewResource(R.layout.spinner_item);
                    spinnerObjetivo.setAdapter(adapter);
                    spinnerObjetivo.setSelection(posi);

                } else {
                    // Maneja errores del servidor, por ejemplo, un error 404 o 500.
                    Log.e("Error", "Error en la respuesta: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<ObjetivoRutina>> call, Throwable t) {
                // Maneja errores de red o de conversión de datos
                Log.e("Error", "Fallo en la petición: " + t.getMessage());
            }
        });



    }

    private void modificarInfo()
    {
        usuarioCliente.setNombre(textoIngresoNombre.getText().toString());
        usuarioCliente.setEmail(textoIngresoEmail.getText().toString());

        // genero
        if(spinnerGenero.getSelectedItemPosition()==1)
        {
            usuarioCliente.setGenero("F");
        }else {
            usuarioCliente.setGenero("M");
        }

        usuarioCliente.setObjetivoRutinaid(spinnerObjetivo.getSelectedItemPosition()+1);

        Call<UsuarioCliente> call = usuarioClienteApiService.updateUsuarioCliente(userId, usuarioCliente);

        call.enqueue(new Callback<UsuarioCliente>() {
            @Override
            public void onResponse(Call<UsuarioCliente> call, Response<UsuarioCliente> response) {
                if (response.isSuccessful()) {
                    UsuarioCliente usuarioCliente1 = response.body();
                    Log.d("UsuarioActualizado", "Nombre: " + usuarioCliente1.getNombre());
                    Toast.makeText(_65_ModificarPerfil_Usuario.this, "Cambios guardados correctamente", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(_65_ModificarPerfil_Usuario.this, _64_VerPerfil_Usuario.class));

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
                usuarioCliente.setFotoPerfil(imageString);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
