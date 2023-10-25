
package com.example.smartcoach.ui;

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;

import com.example.smartcoach.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import api.DateSerializer;
import api.Exercise.GrupoMuscularApiService;
import api.User.ObjetivoRutinaApiService;
import api.retro;
import model.Exercise.GrupoMuscular;
import model.Exercise.Musculo;
import model.Exercise.Rutina;
import model.User.ObjetivoRutina;
import model.User.UsuarioCliente;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class _42_Registrar_Usuario_3 extends AppCompatActivity {

    TimePickerDialog _timePickerDialog;
    EditText _editTextTime, _editTextTime2;
    Spinner spinnerLogro, spinnerMusculo;
    ImageButton imageLunes, imageMartes, imageMiercoles, imageJueves, imageViernes, imageSabado, imageDomingo, imageLeve, imageModerada, imagenEnergica, btnInfoLeve, btnInfoModerada, btnInfoEnergica;
    Button btnContinuar;
    private int selectedDay = -1;

    UsuarioCliente usuarioCliente;

    private boolean editingStartTime = true;
    private final HashMap<Integer, String> startHoursByDay = new HashMap<>();
    private final HashMap<Integer, String> endHoursByDay = new HashMap<>();
    private final Map<ImageButton, Integer> originalImages = new HashMap<>();
    private final Map<ImageButton, Integer> selectedImages = new HashMap<>();
    private AlertDialog alertDialog;

    ObjetivoRutinaApiService objetivoRutinaApiService;
    GrupoMuscularApiService grupoMuscularApiService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout._42_registrar_usuario_3);
        iniciarPeticiones();

        _editTextTime = findViewById(R.id.editTextHoraInicio_42);
        _editTextTime2 = findViewById(R.id.editTextHoraFinal_42);
        spinnerLogro = findViewById(R.id.spinnerLogro_42);
        spinnerMusculo = findViewById(R.id.spinnerMasaMuscular_42);
        imageLunes = findViewById(R.id.imageLunes_42);
        imageMartes = findViewById(R.id.imageMartes_42);
        imageMiercoles = findViewById(R.id.imageMiercoles_42);
        imageJueves = findViewById(R.id.imageJueves_42);
        imageViernes = findViewById(R.id.imageViernes_42);
        imageSabado = findViewById(R.id.imageSabado_42);
        imageDomingo = findViewById(R.id.imageDomingo_42);
        imageLeve = findViewById(R.id.imagenLeve_42);
        imageModerada = findViewById(R.id.imageModerado_42);
        imagenEnergica = findViewById(R.id.imageEnergico_42);
        btnContinuar = findViewById(R.id.btnContinuar_42);
        btnInfoLeve = findViewById(R.id.imageInfoLeve_42);
        btnInfoModerada = findViewById(R.id.imageInfoModerada_42);
        btnInfoEnergica = findViewById(R.id.imageInfoEnergica_42);
        // Inicializar las imagenes originales
        originalImages.put((ImageButton) findViewById(R.id.imageLunes_42), R.drawable.icon_lunes_b);
        originalImages.put((ImageButton) findViewById(R.id.imageMartes_42), R.drawable.icon_martes_b);
        originalImages.put((ImageButton) findViewById(R.id.imageMiercoles_42), R.drawable.icon_miercoles_b);
        originalImages.put((ImageButton) findViewById(R.id.imageJueves_42), R.drawable.icon_jueves_b);
        originalImages.put((ImageButton) findViewById(R.id.imageViernes_42), R.drawable.icon_viernes_b);
        originalImages.put((ImageButton) findViewById(R.id.imageSabado_42), R.drawable.icon_sabado_b);
        originalImages.put((ImageButton) findViewById(R.id.imageDomingo_42), R.drawable.icon_domingo_b);
        // Inicializar las imágenes seleccionadas
        selectedImages.put((ImageButton) findViewById(R.id.imageLunes_42), R.drawable.icon_lunes_ne);
        selectedImages.put((ImageButton) findViewById(R.id.imageMartes_42), R.drawable.icon_martes_ne);
        selectedImages.put((ImageButton) findViewById(R.id.imageMiercoles_42), R.drawable.icon_miercoles_ne);
        selectedImages.put((ImageButton) findViewById(R.id.imageJueves_42), R.drawable.icon_jueves_ne);
        selectedImages.put((ImageButton) findViewById(R.id.imageViernes_42), R.drawable.icon_viernes_ne);
        selectedImages.put((ImageButton) findViewById(R.id.imageSabado_42), R.drawable.icon_sabado_ne);
        selectedImages.put((ImageButton) findViewById(R.id.imageDomingo_42), R.drawable.icon_domingo_ne);

        usuarioCliente = (UsuarioCliente) getIntent().getSerializableExtra("usuarioCliente");

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
                usuarioCliente.setNivelActividadFisicaid(1);
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
                usuarioCliente.setNivelActividadFisicaid(2);
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
                usuarioCliente.setNivelActividadFisicaid(3);
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

        cargarSpinner();

        spinnerLogro.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                 spinnerMusculo.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });

        btnContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validarCampos()) {
                    pasarInfo();
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
        ImageButton imageLunes = findViewById(R.id.imageLunes_42);
        ImageButton imageMartes = findViewById(R.id.imageMartes_42);
        ImageButton imageMiercoles = findViewById(R.id.imageMiercoles_42);
        ImageButton imageJueves = findViewById(R.id.imageJueves_42);
        ImageButton imageViernes = findViewById(R.id.imageViernes_42);
        ImageButton imageSabado = findViewById(R.id.imageSabado_42);
        ImageButton imageDomingo = findViewById(R.id.imageDomingo_42);


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

        objetivoRutinaApiService = retrofit.create(ObjetivoRutinaApiService.class);
        grupoMuscularApiService = retrofit.create(GrupoMuscularApiService.class);

    }
    private void cargarSpinner(){

        cargarObjetivos();
        cargarMusculos();
    }

    private void cargarObjetivos()
    {
        Call<List<ObjetivoRutina>> call = objetivoRutinaApiService.getAllCreate();
        call.enqueue(new Callback<List<ObjetivoRutina>>() {
            @Override
            public void onResponse(Call<List<ObjetivoRutina>> call, Response<List<ObjetivoRutina>> response) {
                if (response.isSuccessful()) {
                    int posi=0;
                    List<ObjetivoRutina> objetivoRutina = response.body();
                    String[] opcionesObjetivos = new String[objetivoRutina.size()];
                    for (int i = 0; i < objetivoRutina.size(); i++) {
                        opcionesObjetivos[i] = objetivoRutina.get(i).getNombre();
                    }


                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(_42_Registrar_Usuario_3.this, R.layout.spinner_item, opcionesObjetivos){
                        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                            View view = super.getView(position, convertView, parent);

                            TextView text = view.findViewById(android.R.id.text1);
                            text.setTextColor(getResources().getColor(android.R.color.white));
                            text.setTextAppearance(R.style.SpinnerTextWhite);

                            return view;
                        }
                        @Override
                        public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

                            View view = super.getDropDownView(position, convertView, parent);

                            TextView text = view.findViewById(android.R.id.text1);
                            text.setTextColor(getResources().getColor(android.R.color.white));
                            text.setTextAppearance(R.style.SpinnerTextWhite);
                            view.setBackgroundColor(getResources().getColor(R.color.orange));
                            return view;
                        }
                    };

                    spinnerLogro.setAdapter(adapter);

                } else {
                    // Maneja errores del servidor, por ejemplo, un error 404 o 500.
                    Log.e("Error", "Error en la respuesta: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<ObjetivoRutina>> call, Throwable t) {
                // Maneja errores de red o de conversión de datos
                Log.e("Error", "Fallo en la petición: " + t.getMessage());
            }
        });

    }

    private void cargarMusculos()
    {
        Call<List<GrupoMuscular>> call = grupoMuscularApiService.getAll();
        call.enqueue(new Callback<List<GrupoMuscular>>() {
            @Override
            public void onResponse(Call<List<GrupoMuscular>> call, Response<List<GrupoMuscular>> response) {
                if (response.isSuccessful()) {
                    int posi=0;
                    List<GrupoMuscular> musculos = response.body();
                    String[] opcionesMusculos = new String[musculos.size()];
                    for (int i = 0; i < musculos.size(); i++) {
                        opcionesMusculos[i] = musculos.get(i).getNombre();
                    }
                    // Crear un ArrayAdapter personalizado para el segundo Spinner
                    ArrayAdapter<String> musculoAdapter = new ArrayAdapter<String>(_42_Registrar_Usuario_3.this, R.layout.spinner_item, opcionesMusculos) {
                        @NonNull
                        @Override
                        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                            View view = super.getView(position, convertView, parent);

                            TextView text = view.findViewById(android.R.id.text1);
                            text.setTextColor(getResources().getColor(android.R.color.white));
                            text.setTextAppearance(R.style.SpinnerTextWhite);

                            return view;
                        }

                        @Override
                        public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

                            View view = super.getDropDownView(position, convertView, parent);

                            TextView text = view.findViewById(android.R.id.text1);
                            text.setTextColor(getResources().getColor(android.R.color.white));
                            text.setTextAppearance(R.style.SpinnerTextWhite);
                            view.setBackgroundColor(getResources().getColor(R.color.orange));
                            return view;
                        }
                    };
                    spinnerMusculo.setAdapter(musculoAdapter);
                } else {
                    // Maneja errores del servidor, por ejemplo, un error 404 o 500.
                    Log.e("Error", "Error en la respuesta: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<GrupoMuscular>> call, Throwable t) {
                // Maneja errores de red o de conversión de datos
                Log.e("Error", "Fallo en la petición: " + t.getMessage());
            }
        });
    }

    private void pasarInfo()
    {
        usuarioCliente.setObjetivoRutinaid(spinnerLogro.getSelectedItemPosition()+1);
        List<Rutina> listaRutinas = new ArrayList<>();
        Map<Integer, Pair<String, String>> userSelectedDaysAndHours = collectUserInput();

        for (Map.Entry<Integer, Pair<String, String>> entry : userSelectedDaysAndHours.entrySet()) {
            Integer dayInt = entry.getKey();
            String dayString = convertDayIntToString(dayInt); // Función para convertir el día en String
            Pair<String, String> hours = entry.getValue();

            Rutina rutina = new Rutina();
            rutina.setDia(dayString);

            String formattedStartHour = hours.first + (hours.first.length() == 5 ? ":00" : "");
            String formattedEndHour = hours.second + (hours.second.length() == 5 ? ":00" : "");

            rutina.setHoraI(Time.valueOf(formattedStartHour));
            rutina.setHoraF(Time.valueOf(formattedEndHour));


            listaRutinas.add(rutina);
        }

        Log.d("Debug", "usuarioCliente: " + usuarioCliente.toString());

        // Visualizar el contenido de musculoObjetivo
        int musculoObjetivo = spinnerMusculo.getSelectedItemPosition() + 1;
        Log.d("Debug", "musculoObjetivo: " + musculoObjetivo);

        // Visualizar el contenido de listaRutinas
        for (Rutina rutina : listaRutinas) {
            Log.d("Debug", "Rutina: " + rutina.toString());
        }

        Intent intent = new Intent(_42_Registrar_Usuario_3.this, _45_Registrar_Usuario_4.class);
        intent.putExtra("usuarioCliente",usuarioCliente);
        intent.putExtra("musculoObjetivo",spinnerMusculo.getSelectedItemPosition()+1);
        intent.putExtra("listaRutinas", new ArrayList<>(listaRutinas));
        startActivity(intent);

    }

    private Map<Integer, Pair<String, String>> collectUserInput() {
        Map<Integer, Pair<String, String>> userSelectedDaysAndHours = new HashMap<>();

        for (Map.Entry<Integer, String> entry : startHoursByDay.entrySet()) {
            Integer day = entry.getKey();
            String startTime = entry.getValue();
            String endTime = endHoursByDay.get(day);

            if (startTime != null && endTime != null) {
                userSelectedDaysAndHours.put(day, new Pair<>(startTime, endTime));
            }
        }

        return userSelectedDaysAndHours;
    }


    private String convertDayIntToString(int dayInt) {
        switch (dayInt) {
            case Calendar.MONDAY:
                return "Lunes";
            case Calendar.TUESDAY:
                return "Martes";
            case Calendar.WEDNESDAY:
                return "Miércoles";
            case Calendar.THURSDAY:
                return "Jueves";
            case Calendar.FRIDAY:
                return "Viernes";
            case Calendar.SATURDAY:
                return "Sábado";
            case Calendar.SUNDAY:
                return "Domingo";
            default:
                return "Desconocido";
        }

    }}