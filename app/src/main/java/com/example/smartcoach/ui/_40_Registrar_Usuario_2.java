package com.example.smartcoach.ui;

import android.app.DatePickerDialog;
import android.content.Intent;
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

import com.example.smartcoach.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

public class _40_Registrar_Usuario_2 extends AppCompatActivity {

    EditText nombre, email, editTextFechaNacimiento, contraseña, validContra;
    Spinner spinnerGenero;
    Button btnSiguiente;
    Calendar calendar;
    private AlertDialog alertDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout._40_registrar_usuario_2);

        nombre = findViewById(R.id.nombre);
        email = findViewById(R.id.email);
        contraseña = findViewById(R.id.contraseña);
        validContra = findViewById(R.id.validacionContraseña);
        spinnerGenero = findViewById(R.id.spinnerGenero);
        btnSiguiente = findViewById(R.id.btnSiguiente);
        editTextFechaNacimiento = findViewById(R.id.editTextFechaNacimiento);
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
                    Toast.makeText(_40_Registrar_Usuario_2.this, "Sus datos se agregaron correctamente", Toast.LENGTH_SHORT).show();

                    String generoSeleccionado = spinnerGenero.getSelectedItem().toString();
                    String fechaNacimiento = editTextFechaNacimiento.getText().toString();

                    // Solo accede a la siguiente pantalla si se cumplen las validaciones
                    Intent intent = new Intent(_40_Registrar_Usuario_2.this, _42_Registrar_Usuario_3.class);
                    startActivity(intent);
                } else {
                    mostrarErrorAlertDialog();
                }
            }
        });
    }
    private void mostrarErrorAlertDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        View alertDialogView = getLayoutInflater().inflate(R.layout._41_mensaje_error_datos_invalidos, null);
        alertDialogBuilder.setView(alertDialogView);

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
        if (generoSeleccionado.equals("Seleccione")) {
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
                R.style.DatePickerDialogTheme, // Usa el nuevo tema personalizado
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
                            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                            String fechaNacimiento = sdf.format(selectedDate.getTime());
                            editTextFechaNacimiento.setText(fechaNacimiento);
                        } else {
                            Toast.makeText(_40_Registrar_Usuario_2.this, "Selecciona una fecha válida (mayor de 18 años)", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, currentYear, currentMonth, currentDayOfMonth);

        datePickerDialog.getDatePicker().setMinDate(minDateCalendar.getTimeInMillis());
        datePickerDialog.getDatePicker().setMaxDate(currentCalendar.getTimeInMillis());
        datePickerDialog.show();
    }
}

