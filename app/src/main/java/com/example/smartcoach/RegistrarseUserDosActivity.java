package com.example.smartcoach;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class RegistrarseUserDosActivity extends AppCompatActivity {

    EditText nombre, email, editTextFechaNacimiento, contraseña, validContra;
    Spinner spinnerGenero;
    Button btnSiguiente;
    Calendar calendar;
    private AlertDialog alertDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse_user_dos);

        nombre = findViewById(R.id.nombre);
        email = findViewById(R.id.email);
        contraseña = findViewById(R.id.contraseña);
        validContra = findViewById(R.id.validacionContraseña);
        spinnerGenero = findViewById(R.id.spinnerGenero);
        btnSiguiente = findViewById(R.id.btnSiguiente);
        editTextFechaNacimiento = findViewById(R.id.editTextFechaNacimiento);
        btnSiguiente = findViewById(R.id.btnSiguiente);
        calendar = Calendar.getInstance();

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

        //Spinner
        String[] opcionesGenero = new String[]{"Seleccione", "Femenino", "Masculino"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, opcionesGenero);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
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
                if (validarCampos() && validarContraseñas()) {
                    Toast.makeText(RegistrarseUserDosActivity.this, "Sus datos se agregaron correctamente", Toast.LENGTH_SHORT).show();
                } else {
                    mostrarErrorAlertDialog();
                }
                String generoSeleccionado = spinnerGenero.getSelectedItem().toString();
                String fechaNacimiento = editTextFechaNacimiento.getText().toString();
            }
        });
    }
    private void mostrarErrorAlertDialog() {
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
        // Validar el género seleccionado en el Spinner
        String generoSeleccionado = spinnerGenero.getSelectedItem().toString();
        if (generoSeleccionado.equals("Seleccione")) {
            ((TextView) spinnerGenero.getSelectedView()).setError("Por favor, seleccione un género");
            return false;
        }

        String fechaNacimiento = editTextFechaNacimiento.getText().toString();
        if (fechaNacimiento.isEmpty()) {
            editTextFechaNacimiento.setError("Este campo no puede quedar vacío");
            return false;
        } else {
            editTextFechaNacimiento.setError(null);
        }

        return retorno;
    }

    private void showDatePickerDialog() {
        // Fecha actual para que sea la fecha máxima seleccionable
        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH);
        int currentDayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        // Calcular la fecha mínima seleccionable (18 años)
        Calendar minDateCalendar = Calendar.getInstance();
        minDateCalendar.add(Calendar.YEAR, -18);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                R.style.DatePickerDialogTheme, // Usa el nuevo tema personalizado
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, month);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        // Settear la fecha seleccionada y mostrarla en el EditText
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                        String fechaNacimiento = sdf.format(calendar.getTime());
                        editTextFechaNacimiento.setText(fechaNacimiento);
                    }
                }, currentYear, currentMonth, currentDayOfMonth);

        datePickerDialog.getDatePicker().setMinDate(minDateCalendar.getTimeInMillis());
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePickerDialog.show();
    }
}
