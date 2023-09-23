package com.example.smartcoach.ui;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.smartcoach.R;

public class _4_Registrarse_Admi_4 extends AppCompatActivity {

    EditText nombre, email, contraseña, validContra, cedula, nombreGym, direccionGym, barrioGym, puestoGym;
    Button btnsiguiente;

    private AlertDialog alertDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout._4_registrarse_admi_4);

        nombre = findViewById(R.id.nombre);
        email = findViewById(R.id.email);
        contraseña = findViewById(R.id.contraseña);
        validContra = findViewById(R.id.validacionContraseña);
        cedula = findViewById(R.id.cedula);
        nombreGym = findViewById(R.id.nombreGym);
        direccionGym = findViewById(R.id.direccionGym);
        barrioGym = findViewById(R.id.barrioGym);
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
                    Toast.makeText(_4_Registrarse_Admi_4.this, "Sus datos se agregaron correctamente", Toast.LENGTH_SHORT).show();
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
        String entradaNombreGym = nombreGym.getText().toString().trim();
        String entradaDireccionGym = direccionGym.getText().toString().trim();
        String entradaBarrioGym = barrioGym.getText().toString().trim();
        String entradaPuestoGym = puestoGym.getText().toString().trim();

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
        if (entradaNombreGym.isEmpty()) {
            nombreGym.setError("Este campo no puede quedar vacío");
            retorno = false;
        }
        if (entradaDireccionGym.isEmpty()) {
            direccionGym.setError("Este campo no puede quedar vacío");
            retorno = false;
        }
        if (entradaBarrioGym.isEmpty()) {
            barrioGym.setError("Este campo no puede quedar vacío");
            retorno = false;
        }
        if (entradaPuestoGym.isEmpty()) {
            puestoGym.setError("Este campo no puede quedar vacío");
            retorno = false;
        }
        return retorno;
    }
}
