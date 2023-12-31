package com.example.smartcoach.ui;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.example.smartcoach.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import api.DateSerializer;
import api.Exercise.EjercicioProgresoxEjercicioApiService;
import api.Exercise.ImagenEjercicioApiService;
import api.Exercise.RutinaApiService;
import api.Exercise.RutinaEjercicioApiService;
import api.TimeDeserializer;
import api.TimeSerializer;
import api.User.UsuarioApiService;
import api.User.UsuarioClienteApiService;
import api.retro;
import model.User.UsuarioCliente;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class _40_Registrar_Usuario_2 extends AppCompatActivity {

    EditText nombre, email, editTextFechaNacimiento, contraseña, validContra;
    Spinner spinnerGenero;
    Button btnSiguiente;
    Calendar calendar;
    UsuarioCliente usuarioCliente = new UsuarioCliente();
    private AlertDialog alertDialog;
    Boolean existe;
    UsuarioApiService usuarioApiService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout._40_registrar_usuario_2);
        iniciarPeticiones();

        nombre = findViewById(R.id.nombre_40);
        email = findViewById(R.id.email_40);
        contraseña = findViewById(R.id.contraseña_40);
        validContra = findViewById(R.id.validacionContraseña_40);
        spinnerGenero = findViewById(R.id.spinnerGenero_40);
        btnSiguiente = findViewById(R.id.btnSiguiente_40);
        editTextFechaNacimiento = findViewById(R.id.editTextFechaNacimiento_40);
        calendar = Calendar.getInstance();

        validContra.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                validarContraseñas();
            }
        });

        //Spinner
        String[] opcionesGenero = new String[]{"Genero", "Femenino", "Masculino"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, opcionesGenero);
        adapter.setDropDownViewResource(R.layout.spinner_item);
        spinnerGenero.setAdapter(adapter);

        //Calendario
        editTextFechaNacimiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });
        btnSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String revisarEmail = email.getText().toString();
                validarEmail(revisarEmail,new _40_Registrar_Usuario_2.InfoCallback()
                {
                    @Override
                    public void onCompletion() {
                        if (validarCampos() && validarContraseñas()) {

                            String generoSeleccionado = spinnerGenero.getSelectedItem().toString();
                            String fechaNacimiento = editTextFechaNacimiento.getText().toString();
                            crearUsuario();
                            Intent intent = new Intent(_40_Registrar_Usuario_2.this, _42_Registrar_Usuario_3.class);
                            intent.putExtra("usuarioCliente", usuarioCliente);
                            startActivity(intent);
                        } else {
                            mostrarErrorAlertDialog();
                        }
                    }
                });

            }
        });
    }
    private void mostrarErrorAlertDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        View alertDialogView = getLayoutInflater().inflate(R.layout._5_mensaje_error_datos_invalidos, null);
        alertDialogBuilder.setView(alertDialogView);

        Button btnSeguir = alertDialogView.findViewById(R.id.btnSeguirCamposVacios);
        btnSeguir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

        alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private boolean validarContraseñas() {
        String entradaContraseña = contraseña.getText().toString();
        String entradaValidContra = validContra.getText().toString();

        // Validar longitud de contraseña
        if (entradaContraseña.length() < 8 || entradaContraseña.length() > 15) {
            contraseña.setError("La contraseña debe tener entre 8 y 15 caracteres");
            return false;
        }

        // Validar si contiene al menos una mayúscula
        if (!entradaContraseña.matches(".*[A-Z].*")) {
            contraseña.setError("La contraseña debe contener al menos una letra mayúscula");
            return false;
        }

        // Validar si contiene al menos una minúscula
        if (!entradaContraseña.matches(".*[a-z].*")) {
            contraseña.setError("La contraseña debe contener al menos una letra minúscula");
            return false;
        }

        // Validar si contiene al menos un número
        if (!entradaContraseña.matches(".*\\d.*")) {
            contraseña.setError("La contraseña debe contener al menos un número");
            return false;
        }

        // Validar si contiene al menos un símbolo
        if (!entradaContraseña.matches(".*[@$!%*?&].*")) {
            contraseña.setError("La contraseña debe contener al menos un símbolo (@$!%*?&)");
            return false;
        }

        // Validar si las contraseñas coinciden
        if (!entradaContraseña.equals(entradaValidContra)) {
            validContra.setError("Las contraseñas no coinciden");
            return false;
        }

        // Si todas las validaciones pasan, limpiar los errores y retornar true
        contraseña.setError(null);
        validContra.setError(null);
        return true;
    }

    public boolean validarCampos(){

        boolean retorno = true;

        String entradaNombre = nombre.getText().toString().trim();
        String entradaCorreo = email.getText().toString().trim();
        String entradaContraseña = contraseña.getText().toString();
        String entradaValidContra = validContra.getText().toString();
        String generoSeleccionado = spinnerGenero.getSelectedItem().toString();
        String fechaNacimiento = editTextFechaNacimiento.getText().toString();

        if(entradaNombre.isEmpty()) {
            nombre.setError("Este campo no puede quedar vacio");
            retorno = false;
        }
        if(entradaCorreo.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(entradaCorreo).matches()){
            email.setError("Correo electrónico invalido");
            retorno = false;
        }
        if(existe)
        {
            email.setError("Correo invalido, ya esta en uso");
            retorno = false;
        }
        if (entradaContraseña.isEmpty()) {
            contraseña.setError("Este campo no puede quedar vacío");
            retorno = false;
        }
        if (entradaValidContra.isEmpty()) {
            validContra.setError("Este campo no puede quedar vacío");
            retorno = false;
        } else if (!entradaContraseña.equals(entradaValidContra)) {
            validContra.setError("Las contraseñas no coinciden");
            retorno = false;
        }
        // Validar el género seleccionado en el Spinner
        if (generoSeleccionado.equals("Genero")) {
            ((TextView) spinnerGenero.getSelectedView()).setError("Por favor, seleccione un género");
            return false;
        }
        if (fechaNacimiento.isEmpty()) {
            editTextFechaNacimiento.setError("Este campo no puede quedar vacío");
            return false;
        } else {
            editTextFechaNacimiento.setError(null);
        }
        return retorno;
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
                R.style.DatePickerDialogTheme,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Calendar selectedDate = new GregorianCalendar(year, month, dayOfMonth);

                        // Calendario para verificar si la persona tiene al menos 18 años
                        Calendar minAgeCalendar = new GregorianCalendar();
                        minAgeCalendar.add(Calendar.YEAR, -18);

                        // Verificar si la fecha seleccionada es válida (mayor de 18 años)
                        if (selectedDate.compareTo(minDateCalendar) >= 0 && selectedDate.compareTo(minAgeCalendar) <= 0) {
                            // Settear la fecha seleccionada y mostrarla en el EditText
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
                            String fechaNacimiento = sdf.format(selectedDate.getTime());
                            editTextFechaNacimiento.setText(fechaNacimiento);

                            // Aquí agregamos la fecha al objeto usuarioCliente
                            usuarioCliente.setFechaDeNacimiento(selectedDate.getTime());
                        } else {
                            Toast.makeText(_40_Registrar_Usuario_2.this, "Selecciona una fecha válida (mayor de 18 años)", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, currentYear, currentMonth, currentDayOfMonth);

        datePickerDialog.getDatePicker().setMinDate(minDateCalendar.getTimeInMillis());
        datePickerDialog.getDatePicker().setMaxDate(currentCalendar.getTimeInMillis());
        datePickerDialog.show();
    }

    private void crearUsuario()
    {
        usuarioCliente.setNombre(nombre.getText().toString());
        usuarioCliente.setEmail(email.getText().toString());
        usuarioCliente.setContrasenna(contraseña.getText().toString());

        if(spinnerGenero.getSelectedItemPosition()==1)
        {
            usuarioCliente.setGenero("F");
        }
        else {
            usuarioCliente.setGenero("M");
        }

    }

    interface InfoCallback {
        void onCompletion();
    }

    private void iniciarPeticiones() {

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Date.class, new DateSerializer())
                .registerTypeAdapter(Time.class, new TimeSerializer())
                .registerTypeAdapter(Time.class, new TimeDeserializer())
                .create();

        OkHttpClient okHttpClient = retro.getUnsafeOkHttpClient()
                .newBuilder()
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://10.0.2.2:8043/api/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        usuarioApiService = retrofit.create(UsuarioApiService.class);
    }

    private void validarEmail(String email,_40_Registrar_Usuario_2.InfoCallback callback)
    {
        Call<Boolean> call = usuarioApiService.checkEmail(email);
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.isSuccessful()) {
                    existe = response.body();
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
}

