package com.example.smartcoach.ui;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.smartcoach.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

public class _65_ModificarPerfil_Usuario extends AppCompatActivity {

    ImageButton flechaRegresar, imagePP;

    TextView nombreUser, puestoAdmi;

    EditText  textoIngresoNombre, textoIngresoEmail, textoFechaNacimiento, textoIngresoObjetivo;

    AppCompatButton botonGuardarCambios;

    Spinner spinnerGenero;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout._65_modificar_perfil_usuario);

        flechaRegresar = findViewById(R.id.flechaRegresar);
        imagePP = findViewById(R.id.imagePP);
        spinnerGenero = findViewById(R.id.spinnerGenero);
        nombreUser = findViewById(R.id.nombre_user_65);
        puestoAdmi = findViewById(R.id.puesto_user_65);
        textoIngresoNombre = findViewById(R.id.texto_Ingreso_Nombre_user_65);
        textoIngresoEmail = findViewById(R.id.textoIngresoEmail_user_65);
        textoFechaNacimiento = findViewById(R.id.textoFechaNacimiento_user_65);
        textoIngresoObjetivo = findViewById(R.id.textoIngresoObjetivo_user_65);
        botonGuardarCambios = findViewById(R.id.botonGuardarCambios_65);

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
                            textoFechaNacimiento.setText(fechaNacimiento);
                        } else {
                            Toast.makeText(_65_ModificarPerfil_Usuario.this, "Selecciona una fecha válida (mayor de 18 años)", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, currentYear, currentMonth, currentDayOfMonth);

        datePickerDialog.getDatePicker().setMinDate(minDateCalendar.getTimeInMillis());
        datePickerDialog.getDatePicker().setMaxDate(currentCalendar.getTimeInMillis());
        datePickerDialog.show();
    }
}
