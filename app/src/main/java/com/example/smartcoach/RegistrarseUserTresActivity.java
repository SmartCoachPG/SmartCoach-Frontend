package com.example.smartcoach;

import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.HashMap;

public class RegistrarseUserTresActivity extends AppCompatActivity {

    TimePickerDialog _timePickerDialog;
    EditText _editTextTime, _editTextTime2;
    Spinner spinnerLogro, spinnerMasaMuscular;
    ImageButton imageLunes, imageMartes, imageMiercoles, imageJueves, imageViernes, imageSabado, imageDomingo;
    Button btnContinuar;
    private int selectedDay = -1;
    private boolean editingStartTime = true;
    private HashMap<Integer, String> startHoursByDay = new HashMap<>();
    private HashMap<Integer, String> endHoursByDay = new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse_user_tres);

        _editTextTime=findViewById(R.id.editTextHoraInicio);
        _editTextTime2 = findViewById(R.id.editTextHoraFinal);
        spinnerLogro = findViewById(R.id.spinnerLogro);
        spinnerMasaMuscular = findViewById(R.id.spinnerMasaMuscular);
        imageLunes = findViewById(R.id.imageLunes);
        imageMartes = findViewById(R.id.imageMartes);
        imageMiercoles = findViewById(R.id.imageMiercoles);
        imageJueves = findViewById(R.id.imageJueves);
        imageViernes = findViewById(R.id.imageViernes);
        imageSabado = findViewById(R.id.imageSabado);
        imageDomingo = findViewById(R.id.imageDomingo);
        btnContinuar = findViewById(R.id.btnContinuar);
        configureDayClickListeners();

// Asignar los clics a los EditText para abrir el TimePickerDialog
        _editTextTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editingStartTime = true;
                open_TimePickerDialog(); // Indicador para _editTextTime
            }
        });

        _editTextTime2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editingStartTime = false;
                open_TimePickerDialog(); // Indicador para _editTextTime2
            }
        });
        //Spinner Logro
        String[] opcionesLogro = new String[]{"Seleccione", "Bajar peso", "Aumentar músculo", "Aumentar fuerza"};
        ArrayAdapter<String> adapterLogro = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, opcionesLogro);
        adapterLogro.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLogro.setAdapter(adapterLogro);

        //Spinner Masa muscular
        String[] opcionesMasaMuscular = new String[]{"Seleccione", "Pectorales", "Glúteos", "Cuádriceps", "Abdominales"};
        ArrayAdapter<String> adapterMM = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, opcionesMasaMuscular);
        adapterMM.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMasaMuscular.setAdapter(adapterMM);
        // Configurar el Listener para el spinnerLogro
        spinnerLogro.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedLogro = spinnerLogro.getSelectedItem().toString();

                if (selectedLogro.equals("Aumentar músculo") || selectedLogro.equals("Aumentar fuerza")) {
                    spinnerMasaMuscular.setVisibility(View.VISIBLE);
                } else {
                    spinnerMasaMuscular.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // No se necesita implementar en este caso
            }
        });

    }
    private void configureDayClickListeners() {
        imageLunes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedDay = Calendar.MONDAY;
                updateEditTexts();
            }
        });

        imageMartes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedDay = Calendar.TUESDAY;
                updateEditTexts();
            }
        });

        imageMiercoles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedDay = Calendar.WEDNESDAY;
                updateEditTexts();
            }
        });

        imageJueves.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedDay = Calendar.THURSDAY;
                updateEditTexts();
            }
        });

        imageViernes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedDay = Calendar.FRIDAY;
                updateEditTexts();
            }
        });

        imageSabado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedDay = Calendar.SATURDAY;
                updateEditTexts();
            }
        });

        imageDomingo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedDay = Calendar.SUNDAY;
                updateEditTexts();
            }
        });
    }


    // Actualizar los campos de texto de hora inicial y final
    private void updateEditTexts() {
        String startTime = startHoursByDay.get(selectedDay);
        String endTime = endHoursByDay.get(selectedDay);

        if (startTime != null) {
            _editTextTime.setText(startTime);
        } else {
            _editTextTime.setText("Hora inicial");
        }

        if (endTime != null) {
            _editTextTime2.setText(endTime);
        } else {
            _editTextTime2.setText("Hora final");
        }
    }
    private void open_TimePickerDialog() {
        int hourOfDay = 23;
        int minute = 55;
        boolean is24HourView = true;

        _timePickerDialog = new TimePickerDialog(this, android.R.style.Theme_Holo_Light_DarkActionBar, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int intHourofDay, int intMinute) {
                String formattedTime = String.format("%02d:%02d", intHourofDay, intMinute);

                if (selectedDay != -1) {
                    if (editingStartTime) {
                        startHoursByDay.put(selectedDay, formattedTime);
                    } else {
                        endHoursByDay.put(selectedDay, formattedTime);
                    }
                    updateEditTexts();
                }
            }
        }, hourOfDay, minute, is24HourView);

        _timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        _timePickerDialog.setTitle("Selecciona hora");
        _timePickerDialog.show();
    }
}
