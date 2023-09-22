
package com.example.smartcoach.ui;

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;

import com.example.smartcoach.R;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class _42_Registrar_Usuario_3 extends AppCompatActivity {

    TimePickerDialog _timePickerDialog;
    EditText _editTextTime, _editTextTime2;
    Spinner spinnerLogro, spinnerMusculo;
    ImageButton imageLunes, imageMartes, imageMiercoles, imageJueves, imageViernes, imageSabado, imageDomingo, imageLeve, imageModerada, imagenEnergica, btnInfoLeve, btnInfoModerada, btnInfoEnergica;
    Button btnContinuar;
    private int selectedDay = -1;

    private boolean editingStartTime = true;
    private final HashMap<Integer, String> startHoursByDay = new HashMap<>();
    private final HashMap<Integer, String> endHoursByDay = new HashMap<>();
    private final Map<ImageButton, Integer> originalImages = new HashMap<>();
    private final Map<ImageButton, Integer> selectedImages = new HashMap<>();
    private AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout._42_registrar_usuario_3);

        _editTextTime = findViewById(R.id.editTextHoraInicio);
        _editTextTime2 = findViewById(R.id.editTextHoraFinal);
        spinnerLogro = findViewById(R.id.spinnerLogro);
        spinnerMusculo = findViewById(R.id.spinnerMasaMuscular);
        imageLunes = findViewById(R.id.imageLunes);
        imageMartes = findViewById(R.id.imageMartes);
        imageMiercoles = findViewById(R.id.imageMiercoles);
        imageJueves = findViewById(R.id.imageJueves);
        imageViernes = findViewById(R.id.imageViernes);
        imageSabado = findViewById(R.id.imageSabado);
        imageDomingo = findViewById(R.id.imageDomingo);
        imageLeve = findViewById(R.id.imagenLeve);
        imageModerada = findViewById(R.id.imageModerado);
        imagenEnergica = findViewById(R.id.imageEnergico);
        btnContinuar = findViewById(R.id.btnContinuar);
        btnInfoLeve = findViewById(R.id.imageInfoLeve);
        btnInfoModerada = findViewById(R.id.imageInfoModerada);
        btnInfoEnergica = findViewById(R.id.imageInfoEnergica);
        // Inicializar las imagenes originales
        originalImages.put((ImageButton) findViewById(R.id.imageLunes), R.drawable.icon_lunes_b);
        originalImages.put((ImageButton) findViewById(R.id.imageMartes), R.drawable.icon_martes_b);
        originalImages.put((ImageButton) findViewById(R.id.imageMiercoles), R.drawable.icon_miercoles_b);
        originalImages.put((ImageButton) findViewById(R.id.imageJueves), R.drawable.icon_jueves_b);
        originalImages.put((ImageButton) findViewById(R.id.imageViernes), R.drawable.icon_viernes_b);
        originalImages.put((ImageButton) findViewById(R.id.imageSabado), R.drawable.icon_sabado_b);
        originalImages.put((ImageButton) findViewById(R.id.imageDomingo), R.drawable.icon_domingo_b);
        // Inicializar las imágenes seleccionadas
        selectedImages.put((ImageButton) findViewById(R.id.imageLunes), R.drawable.icon_lunes_ne);
        selectedImages.put((ImageButton) findViewById(R.id.imageMartes), R.drawable.icon_martes_ne);
        selectedImages.put((ImageButton) findViewById(R.id.imageMiercoles), R.drawable.icon_miercoles_ne);
        selectedImages.put((ImageButton) findViewById(R.id.imageJueves), R.drawable.icon_jueves_ne);
        selectedImages.put((ImageButton) findViewById(R.id.imageViernes), R.drawable.icon_viernes_ne);
        selectedImages.put((ImageButton) findViewById(R.id.imageSabado), R.drawable.icon_sabado_ne);
        selectedImages.put((ImageButton) findViewById(R.id.imageDomingo), R.drawable.icon_domingo_ne);

        configureDayClickListeners();
        btnInfoLeve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Debug", "btnInfoLeve clickeado");
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(_42_Registrar_Usuario_3.this);
                // Inflar el diseño del diálogo directamente
                View dialogView = getLayoutInflater().inflate(R.layout._43_informacion_nivel_actividad_leve, null);
                dialogBuilder.setView(dialogView);

                AlertDialog dialog = dialogBuilder.create();

                // Obtiene la referencia al botón de cerrar del diálogo
                AppCompatImageButton btnCerrar = dialogView.findViewById(R.id.btnSalirLeve);

                // Agregar OnClickListener para el botón de cerrar
                btnCerrar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Cierra el diálogo
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });
        btnInfoModerada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Debug", "btnInfoModerada clickeado");
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(_42_Registrar_Usuario_3.this);
                // Inflar el diseño del diálogo directamente
                View dialogView = getLayoutInflater().inflate(R.layout._44_informacion_nivel_actividad_moderado, null);
                dialogBuilder.setView(dialogView);

                AlertDialog dialog = dialogBuilder.create();

                // Obtiene la referencia al botón de cerrar del diálogo
                AppCompatImageButton btnCerrar = dialogView.findViewById(R.id.btnSalirModerado);

                // Agregar OnClickListener para el botón de cerrar
                btnCerrar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Cierra el diálogo
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });
        btnInfoEnergica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Debug", "btnInfoModerada clickeado");
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(_42_Registrar_Usuario_3.this);
                // Inflar el diseño del diálogo directamente
                View dialogView = getLayoutInflater().inflate(R.layout._44_informacion_nivel_actividad_energico, null);
                dialogBuilder.setView(dialogView);

                AlertDialog dialog = dialogBuilder.create();

                // Obtiene la referencia al botón de cerrar del diálogo
                AppCompatImageButton btnCerrar = dialogView.findViewById(R.id.btnSalirEnergico);

                // Agregar OnClickListener para el botón de cerrar
                btnCerrar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Cierra el diálogo
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });
        imageLeve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Cambiar la imagen de imageLeve
                imageLeve.setImageResource(R.drawable.icon_completo_leve_na);
                // Restaurar las imágenes originales de imageModerada e imageEnergica
                imageModerada.setImageResource(R.drawable.icon_completo_moderada);
                imagenEnergica.setImageResource(R.drawable.icon_completo_energica);
            }
        });

        imageModerada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Cambiar la imagen de imageModerada
                imageModerada.setImageResource(R.drawable.icon_completo_moderada_na);
                // Restaurar las imágenes originales de imageLeve e imagenEnergica
                imageLeve.setImageResource(R.drawable.icon_completo_leve);
                imagenEnergica.setImageResource(R.drawable.icon_completo_energica);
            }
        });

        imagenEnergica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Cambiar la imagen de imagenEnergica
                imagenEnergica.setImageResource(R.drawable.icon_completo_energica_na);
                // Restaurar las imágenes originales de imageLeve e imageModerada
                imageLeve.setImageResource(R.drawable.icon_completo_leve);
                imageModerada.setImageResource(R.drawable.icon_completo_moderada);
            }
        });

        _editTextTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editingStartTime = true;
                open_TimePickerDialog();
            }
        });
        _editTextTime2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editingStartTime = false;
                open_TimePickerDialog();
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
        spinnerMusculo.setAdapter(adapterMM);

        // Configurar el Listener para el spinnerLogro
        spinnerLogro.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedLogro = spinnerLogro.getSelectedItem().toString();

                if (selectedLogro.equals("Aumentar músculo") || selectedLogro.equals("Aumentar fuerza")) {
                    spinnerMusculo.setVisibility(View.VISIBLE);
                } else {
                    spinnerMusculo.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });

        btnContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validarCampos()) {
                    Toast.makeText(_42_Registrar_Usuario_3.this, "Sus datos se agregaron correctamente", Toast.LENGTH_SHORT).show();

                    // Solo accede a la siguiente pantalla si se cumplen las validaciones
                    Intent intent = new Intent(_42_Registrar_Usuario_3.this, _45_Registrar_Usuario_4.class);
                    startActivity(intent);
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

    private void configureDayClickListeners() {
        ImageButton imageLunes = findViewById(R.id.imageLunes);
        ImageButton imageMartes = findViewById(R.id.imageMartes);
        ImageButton imageMiercoles = findViewById(R.id.imageMiercoles);
        ImageButton imageJueves = findViewById(R.id.imageJueves);
        ImageButton imageViernes = findViewById(R.id.imageViernes);
        ImageButton imageSabado = findViewById(R.id.imageSabado);
        ImageButton imageDomingo = findViewById(R.id.imageDomingo);


        imageLunes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedDay = Calendar.MONDAY;
                updateEditTexts();
                restoreOriginalImages();
                updateSelectedImage(imageLunes);
            }
        });
        imageMartes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedDay = Calendar.TUESDAY;
                updateEditTexts();
                restoreOriginalImages();
                updateSelectedImage(imageMartes);
            }
        });
        imageMiercoles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedDay = Calendar.WEDNESDAY;
                updateEditTexts();
                restoreOriginalImages();
                updateSelectedImage(imageMiercoles);
            }
        });
        imageJueves.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedDay = Calendar.THURSDAY;
                updateEditTexts();
                restoreOriginalImages();
                updateSelectedImage(imageJueves);
            }
        });
        imageViernes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedDay = Calendar.FRIDAY;
                updateEditTexts();
                restoreOriginalImages();
                updateSelectedImage(imageViernes);
            }
        });
        imageSabado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedDay = Calendar.SATURDAY;
                updateEditTexts();
                restoreOriginalImages();
                updateSelectedImage(imageSabado);
            }
        });
        imageDomingo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restoreOriginalImages();
                updateSelectedImage(imageDomingo);
                selectedDay = Calendar.SUNDAY;
                updateEditTexts();
            }
        });
    }

    private void restoreOriginalImages() {
        for (Map.Entry<ImageButton, Integer> entry : originalImages.entrySet()) {
            ImageButton button = entry.getKey();
            int drawableId = entry.getValue();
            button.setImageResource(drawableId);
        }
    }

    private void updateSelectedImage(ImageButton button) {
        if (button != null && selectedImages.containsKey(button)) {
            int drawableId = selectedImages.get(button);
            button.setImageResource(drawableId);
        }
    }

    // Actualizar los campos de texto de hora inicial y final
    @SuppressLint("SetTextI18n")
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

    public boolean validarCampos() {
        boolean retorno = true;
        String objetivoSeleccionado = spinnerLogro.getSelectedItem().toString();

        if (objetivoSeleccionado.equals("Seleccione")) {
            ((TextView) spinnerLogro.getSelectedView()).setError("Por favor, seleccione un logro");
            return false;
        }

        // Validación específica para "Aumentar Masa Muscular"
        if (objetivoSeleccionado.equals("Aumentar músculo")) {
            String musculoSeleccionado = spinnerMusculo.getSelectedItem().toString();
            if (musculoSeleccionado.equals("Seleccione")) {
                ((TextView) spinnerMusculo.getSelectedView()).setError("Por favor, seleccione un género");
                return false;
            }
        }

        String horaInicio = _editTextTime.getText().toString();
        String horaFinal = _editTextTime2.getText().toString();

        // Validar si se ha seleccionado una hora inicial y una hora final
        if (!horaInicio.equals("Hora inicial") && !horaFinal.equals("Hora final")) {
            try {
                int horaInicioHoras = Integer.parseInt(horaInicio.substring(0, 2));
                int horaInicioMinutos = Integer.parseInt(horaInicio.substring(3, 5));
                int horaFinalHoras = Integer.parseInt(horaFinal.substring(0, 2));
                int horaFinalMinutos = Integer.parseInt(horaFinal.substring(3, 5));

                if (horaInicioHoras > horaFinalHoras ||
                        (horaInicioHoras == horaFinalHoras && horaInicioMinutos >= horaFinalMinutos)) {
                    _editTextTime.setError("La hora inicial debe ser menor que la hora final");
                    _editTextTime2.setError("La hora final debe ser mayor que la hora inicial");
                    Toast.makeText(_42_Registrar_Usuario_3.this, "La hora inicial debe ser menor que la hora final", Toast.LENGTH_SHORT).show();
                    retorno = false;
                }
            } catch (NumberFormatException e) {
                // Manejo de excepción si hay problemas al convertir las horas y minutos a enteros
                e.printStackTrace();
            } catch (StringIndexOutOfBoundsException e) {
                // Manejo de excepción si la cadena de horaInicio o horaFinal es demasiado corta
                e.printStackTrace();
            }
        }

        return retorno;
    }
}