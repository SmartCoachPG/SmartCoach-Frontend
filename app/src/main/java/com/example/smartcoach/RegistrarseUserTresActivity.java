package com.example.smartcoach;

import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class RegistrarseUserTresActivity extends AppCompatActivity {

    TimePickerDialog _timePickerDialog;
    EditText _editTextTime, _editTextTime2;
    Spinner spinnerLogro, spinnerMasaMuscular;
    ImageButton imageLunes, imageMartes, imageMiercoles, imageJueves, imageViernes, imageSabado, imageDomingo;
    Button btnContinuar;

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

// Asignar los clics a los EditText para abrir el TimePickerDialog
        _editTextTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open_TimePickerDialog(true); // Indicador para _editTextTime
            }
        });

        _editTextTime2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open_TimePickerDialog(false); // Indicador para _editTextTime2
            }
        });

        //Spinner Logro
        String[] opcionesLogro = new String[]{"Seleccione", "Adelgazar", "no sé xd"};
        ArrayAdapter<String> adapterLogro = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, opcionesLogro);
        adapterLogro.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLogro.setAdapter(adapterLogro);

        //Spinner Masa muscular
        String[] opcionesMasaMuscular = new String[]{"Seleccione", "no sé xd", "no sé xd"};
        ArrayAdapter<String> adapterMM = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, opcionesMasaMuscular);
        adapterMM.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMasaMuscular.setAdapter(adapterMM);
    }

    public void onButtonSelectTimeClick(View view){
        open_TimePickerDialog(true); // Indicador para _editTextTime
    }

    public void onButtonSelectTimeClick2(View view){
        open_TimePickerDialog(false); // Indicador para _editTextTime2
    }
    private void open_TimePickerDialog(final boolean isEditingTime1) {
        int hourOfDay = 23;
        int minute = 55;
        boolean is24HourView = true;

        final EditText editTextToUpdate = isEditingTime1 ? _editTextTime : _editTextTime2;

        _timePickerDialog = new TimePickerDialog(this, android.R.style.Theme_Holo_Light_DarkActionBar, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int intHourofDay, int intMinute) {
                editTextToUpdate.setText(intHourofDay + ":" + intMinute);
               // Toast.makeText(RegistrarseUserTresActivity.this, "i=" + intHourofDay + " i1=" + intMinute, Toast.LENGTH_SHORT).show();
            }
        }, hourOfDay, minute, is24HourView);

        _timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        _timePickerDialog.setTitle("Selecciona hora");
        _timePickerDialog.show();
    }

}
