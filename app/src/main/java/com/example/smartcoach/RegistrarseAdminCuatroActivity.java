package com.example.smartcoach;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class RegistrarseAdminCuatroActivity extends AppCompatActivity {

    EditText nombre, email, contraseña, validContra, cedula, nombreGym, direccionGym, barrioGym, puestoGym;
    Button btnsiguiente;

    private AlertDialog alertDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse_admin_cuatro);

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

    }

    public void siguiente(View v) {
        if (validarCampos()) {
            Toast.makeText(this, "Sus datos se agregaron correctamente", Toast.LENGTH_SHORT).show();
        } else {
            // Los datos no se agregaron correctamente
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            View alertDialogView = getLayoutInflater().inflate(R.layout.activity_error_registrarse_admin_cinco, null);
            alertDialogBuilder.setView(alertDialogView);

            TextView tituloAlerta = alertDialogView.findViewById(R.id.tituloAlerta);
            tituloAlerta.setText("Error");

            TextView mensajeAlerta = alertDialogView.findViewById(R.id.mensajeAlerta);
            mensajeAlerta.setText("Lo sentimos, los datos ingresados para crear la cuenta no son válidos. Por favor, revisa la información que has proporcionado e inténtalo de nuevo.");

            Button btnSeguir = alertDialogView.findViewById(R.id.btnSeguir);
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
        } else if (entradaCedula.length() != 10) {
            cedula.setError("La cédula debe tener 10 dígitos");
            retorno = false;
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
