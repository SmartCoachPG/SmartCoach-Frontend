package com.example.smartcoach.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.example.smartcoach.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import api.Admi.UsuarioAdministradorApiService;
import api.DateSerializer;
import api.SharedPreferencesUtil;
import api.User.UsuarioApiService;
import api.retro;
import model.Admi.UsuarioAdministrador;
import model.User.Usuario;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class _4_Registrarse_Admi_4 extends AppCompatActivity {

    EditText nombre, email, contraseña, validContra, cedula, nombreGym, direccionGym, barrioGym, puestoGym;
    Button btnsiguiente;
    UsuarioAdministradorApiService usuarioAdministradorApiService;
    UsuarioApiService usuarioApiService;
    private AlertDialog alertDialog;
    UsuarioAdministrador newUser = new UsuarioAdministrador();

    Boolean existe;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout._4_registrarse_admi_4);
        iniciarPeticiones();

        nombre = findViewById(R.id.nombre);
        email = findViewById(R.id.email);
        contraseña = findViewById(R.id.contraseña);
        validContra = findViewById(R.id.validacionContraseña);
        cedula = findViewById(R.id.cedula);
        puestoGym = findViewById(R.id.puestoGym);
        btnsiguiente = findViewById(R.id.btnSiguiente);
        // Agregar TextWatcher al campo validContra para validar en tiempo real
        validContra.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                validarContraseñas(); // Llamar al método para validar contraseñas en tiempo real
            }
        });
        btnsiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validarCampos() && validarContraseñas()) {
                    crearUsuarioAdministrador();
                } else {
                    mostrarErrorAlertDialog();
                }
            }
        });
    }
    private void mostrarErrorAlertDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        View alertDialogView = getLayoutInflater().inflate(R.layout._5_mensaje_error_datos_invalidos, null);
        alertDialogBuilder.setView(alertDialogView);

        TextView tituloAlerta = alertDialogView.findViewById(R.id.tituloAlerta);
        tituloAlerta.setText("Error");

        TextView mensajeAlerta = alertDialogView.findViewById(R.id.mensajeAlerta);
        mensajeAlerta.setText("Lo sentimos, los datos ingresados para crear la cuenta no son válidos. Por favor, revisa la información que has proporcionado e inténtalo de nuevo.");

        Button btnSeguir = alertDialogView.findViewById(R.id.btnSeguirCamposVacios);
        btnSeguir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Cerrar el AlertDialog
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
        String entradaCedula = cedula.getText().toString().trim();

        if(entradaNombre.isEmpty()) {
            nombre.setError("Este campo no puede quedar vacio");
            retorno = false;
        }
        if(entradaCorreo.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(entradaCorreo).matches()){
            email.setError("Correo electrónico invalido");
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
        if (entradaCedula.isEmpty()) {
            cedula.setError("Este campo no puede quedar vacío");
            retorno = false;
        } else if (entradaCedula.length() < 8 || entradaCedula.length() > 10) {
            cedula.setError("La cédula debe tener entre 8 y 10 dígitos");
            return false;
        }


        return retorno;
    }

    private void iniciarPeticiones()
    {

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Date.class, new DateSerializer())
                .create();

        OkHttpClient okHttpClient = retro.getUnsafeOkHttpClient();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://10.0.2.2:8043/api/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        usuarioAdministradorApiService = retrofit.create(UsuarioAdministradorApiService.class);
        usuarioApiService = retrofit.create(UsuarioApiService.class);
    }


    public void crearUsuarioAdministrador()
    {
        newUser.setNombre(nombre.getText().toString());
        newUser.setEmail(email.getText().toString());
        newUser.setContrasenna(contraseña.getText().toString());
        newUser.setFotoPerfil(null);
        newUser.setToken(null);
        newUser.setCedula(Long.parseLong(cedula.getText().toString()));
        newUser.setPuesto(puestoGym.getText().toString());
        newUser.setVerificado(0);
        newUser.setAdmi(1);
        Date currentDate = new Date();
        newUser.setFechaDeRenovacion(currentDate);

        newUser.setGimnasioId(null);
        Call<UsuarioAdministrador> call = usuarioAdministradorApiService.createUsuarioAdministrador(newUser);

        call.enqueue(new Callback<UsuarioAdministrador>() {
            @Override
            public void onResponse(Call<UsuarioAdministrador> call, Response<UsuarioAdministrador> response) {
                if (response.isSuccessful()) {
                    UsuarioAdministrador usuarioAdministrador1 = response.body();
                    Toast.makeText(_4_Registrarse_Admi_4.this, "Sus datos se agregaron correctamente", Toast.LENGTH_SHORT).show();
                    iniciarSesion();

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

    public void iniciarSesion()
    {
        String emailStr = newUser.getEmail();
        String contraseñaStr= newUser.getContrasenna();
        SharedPreferencesUtil sharedpreferencesutil = new SharedPreferencesUtil();
        Map<String, String> credenciales = new HashMap<>();
        credenciales.put("email", emailStr);
        credenciales.put("contrasenna", contraseñaStr);

        Call<Usuario> call = usuarioApiService.iniciarSesion(credenciales);
        call.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if (response.isSuccessful()) {
                    Usuario usuarioResponse = response.body();

                    if (usuarioResponse != null) {

                        Integer tipoUsuario = usuarioResponse.getAdmi();

                        if (tipoUsuario != null) {
                                sharedpreferencesutil.saveToken(_4_Registrarse_Admi_4.this,usuarioResponse.getToken());
                                sharedpreferencesutil.saveUserId(_4_Registrarse_Admi_4.this,usuarioResponse.getId());
                                Intent intent = new Intent(_4_Registrarse_Admi_4.this, _6_Principal_Admi.class);
                                startActivity(intent);

                        } else {
                            Log.d("IniciarSesionActivity", "Campo 'admi' es null");
                        }
                    } else {
                        Log.d("IniciarSesionActivity", "Respuesta recibida pero el cuerpo es null");
                    }
                } else {
                    Log.d("IniciarSesionActivity", "Inicio de sesión fallido");
                    Toast toast = Toast.makeText(getApplicationContext(), "Correo o contraseña incorrecta,por favor vuelve a intentar", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            }
            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                Log.d("IniciarSesionActivity", "Error en la llamada API", t);
            }

        });
    }

}
