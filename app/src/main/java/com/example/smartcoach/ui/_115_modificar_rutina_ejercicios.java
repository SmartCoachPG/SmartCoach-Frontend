package com.example.smartcoach.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.smartcoach.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import api.DateSerializer;
import api.Exercise.EjercicioProgresoxEjercicioApiService;
import api.Exercise.ImagenEjercicioApiService;
import api.Exercise.RutinaApiService;
import api.Exercise.RutinaEjercicioApiService;
import api.SharedPreferencesUtil;
import api.TimeDeserializer;
import api.TimeSerializer;
import api.User.UsuarioClienteApiService;
import api.retro;
import model.Exercise.CajaRutina;
import model.Exercise.Ejercicio;
import model.Exercise.ImagenEjercicio;
import model.Exercise.Rutina;
import model.User.ProgresoxEjercicio;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class _115_modificar_rutina_ejercicios extends BaseActivityCliente {

    TextView tituloPt2;
    Button btnGuardarCambios;
    String dia;
    private final Map<ImageButton, Integer> originalImages = new HashMap<>();
    private final Map<ImageButton, Integer> selectedImages = new HashMap<>();
    private int selectedDay = -1;
    ImageButton imageLunes, imageMartes, imageMiercoles, imageJueves, imageViernes, imageSabado, imageDomingo;
    Long userId;
    String token;
    UsuarioClienteApiService usuarioClienteApiService;
    RutinaApiService rutinaApiService;
    RutinaEjercicioApiService rutinaEjercicioApiService;
    EjercicioProgresoxEjercicioApiService ejercicioProgresoxEjercicioApiService;
    ImagenEjercicioApiService imagenEjercicioApiService;

    // dia , Rutina
    Map<String, Rutina> rutinas = new HashMap<>();
    // idRutina , lista ejercicios
    Map<Integer, List<Ejercicio>> ejercicios = new HashMap();
    // idEjercicio , progresoxEjercicio
    Map<Integer, ProgresoxEjercicio> progresos = new HashMap<>();

    ImagenEjercicio imagenEjercicio = new ImagenEjercicio();

    List<CajaRutina> cajaRutinas = new ArrayList<>();
    List<CajaRutina> opciones = new ArrayList<>();
    CajaRutinaAdapterM adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout._115_modificar_rutina_ejercicios);
        getSupportActionBar().hide();

        userId = SharedPreferencesUtil.getUserId(_115_modificar_rutina_ejercicios.this);
        token = SharedPreferencesUtil.getToken(_115_modificar_rutina_ejercicios.this);
        iniciarPeticiones();

        tituloPt2 = findViewById(R.id.titulo_115);
        btnGuardarCambios = findViewById(R.id.btnGuardarCambios_115);
        View diasSemanaView = LayoutInflater.from(this).inflate(R.layout._otros_dias_semana_ne, null);

        // Encuentra los ImageButtons en el diseño secundario
        imageLunes = diasSemanaView.findViewById(R.id.imageLunes);
        imageMartes = diasSemanaView.findViewById(R.id.imageMartes);
        imageMiercoles = diasSemanaView.findViewById(R.id.imageMiercoles);
        imageJueves = diasSemanaView.findViewById(R.id.imageJueves);
        imageViernes = diasSemanaView.findViewById(R.id.imageViernes);
        imageSabado = diasSemanaView.findViewById(R.id.imageSabado);
        imageDomingo = diasSemanaView.findViewById(R.id.imageDomingo);

        //Lógica para interactuar con los elementos de ambos diseños
        originalImages.put((ImageButton) findViewById(R.id.imageLunes), R.drawable.icon_lunes_ne);
        originalImages.put((ImageButton) findViewById(R.id.imageMartes), R.drawable.icon_martes_ne);
        originalImages.put((ImageButton) findViewById(R.id.imageMiercoles), R.drawable.icon_miercoles_ne);
        originalImages.put((ImageButton) findViewById(R.id.imageJueves), R.drawable.icon_jueves_ne);
        originalImages.put((ImageButton) findViewById(R.id.imageViernes), R.drawable.icon_viernes_ne);
        originalImages.put((ImageButton) findViewById(R.id.imageSabado), R.drawable.icon_sabado_ne);
        originalImages.put((ImageButton) findViewById(R.id.imageDomingo), R.drawable.icon_domingo_ne);

        selectedImages.put((ImageButton) findViewById(R.id.imageLunes), R.drawable.icon_lunes_na);
        selectedImages.put((ImageButton) findViewById(R.id.imageMartes), R.drawable.icon_martes_na);
        selectedImages.put((ImageButton) findViewById(R.id.imageMiercoles), R.drawable.icon_miercoles_na);
        selectedImages.put((ImageButton) findViewById(R.id.imageJueves), R.drawable.icon_jueves_na);
        selectedImages.put((ImageButton) findViewById(R.id.imageViernes), R.drawable.icon_viernes_na);
        selectedImages.put((ImageButton) findViewById(R.id.imageSabado), R.drawable.icon_sabado_na);
        selectedImages.put((ImageButton) findViewById(R.id.imageDomingo), R.drawable.icon_domingo_na);
        llenarRutinas(new LlenarRutinasCallback() {
            @Override
            public void onCompletion() {
                cajaRutinas.clear();
                llenarEjercicios(new LlenarRutinasCallback() {
                    @Override
                    public void onCompletion() {
                        selectCurrentDay();
                        llenarProgresos(new LlenarRutinasCallback() {
                            @Override
                            public void onCompletion() {
                                configureDayClickListeners();
                                btnGuardarCambios.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        filtrarListas();
                                    }
                                });
                            }
                        });
                    }
                });

            }
        });
    }

    private ImageButton getCurrentDayButton() {
        Calendar calendar = Calendar.getInstance();
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

        switch (dayOfWeek) {
            case Calendar.MONDAY:
                return findViewById(R.id.imageLunes);
            case Calendar.TUESDAY:
                return findViewById(R.id.imageMartes);
            case Calendar.WEDNESDAY:
                return findViewById(R.id.imageMiercoles);
            case Calendar.THURSDAY:
                return findViewById(R.id.imageJueves);
            case Calendar.FRIDAY:
                return findViewById(R.id.imageViernes);
            case Calendar.SATURDAY:
                return findViewById(R.id.imageSabado);
            case Calendar.SUNDAY:
                return findViewById(R.id.imageDomingo);
        }
        return null;
    }

    private void selectCurrentDay() {
        ImageButton currentDayButton = getCurrentDayButton();
        if (currentDayButton != null) {
            currentDayButton.performClick();
        }
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
                restoreOriginalImages();
                updateSelectedImage(imageLunes);
                dia="Lunes";
                mostrar();

                }

        });
        imageMartes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedDay = Calendar.TUESDAY;
                restoreOriginalImages();
                updateSelectedImage(imageMartes);
                dia="Martes";
                mostrar();
            }
        });
        imageMiercoles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedDay = Calendar.WEDNESDAY;
                restoreOriginalImages();
                updateSelectedImage(imageMiercoles);
                dia="Miércoles";
                mostrar();
            }
        });
        imageJueves.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedDay = Calendar.THURSDAY;
                restoreOriginalImages();
                updateSelectedImage(imageJueves);
                dia="Jueves";
                mostrar();
            }
        });
        imageViernes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedDay = Calendar.FRIDAY;
                restoreOriginalImages();
                updateSelectedImage(imageViernes);
                dia="Viernes";
                mostrar();
            }
        });
        imageSabado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedDay = Calendar.SATURDAY;
                restoreOriginalImages();
                updateSelectedImage(imageSabado);
                dia="Sábado";
                mostrar();
            }
        });
        imageDomingo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restoreOriginalImages();
                updateSelectedImage(imageDomingo);
                selectedDay = Calendar.SUNDAY;
                dia="Domingo";
                mostrar();
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

    private void iniciarPeticiones() {

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Date.class, new DateSerializer())
                .registerTypeAdapter(Time.class, new TimeSerializer())
                .registerTypeAdapter(Time.class, new TimeDeserializer())
                .create();

        OkHttpClient okHttpClient = retro.getUnsafeOkHttpClientWithToken(token)
                .newBuilder()
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://10.0.2.2:8043/api/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        usuarioClienteApiService = retrofit.create(UsuarioClienteApiService.class);
        rutinaApiService = retrofit.create(RutinaApiService.class);
        rutinaEjercicioApiService = retrofit.create(RutinaEjercicioApiService.class);
        ejercicioProgresoxEjercicioApiService = retrofit.create(EjercicioProgresoxEjercicioApiService.class);
        imagenEjercicioApiService = retrofit.create(ImagenEjercicioApiService.class);
    }

    interface LlenarRutinasCallback {
        void onCompletion();
    }

    private void llenarRutinas(LlenarRutinasCallback callback) {
        Call<List<Rutina>> call = rutinaApiService.getByUsuarioClienteId(userId.intValue());
        call.enqueue(new Callback<List<Rutina>>() {
            @Override
            public void onResponse(Call<List<Rutina>> call, Response<List<Rutina>> response) {
                if (response.isSuccessful()) {
                    for (Rutina rut : response.body()) {
                        rutinas.put(rut.getDia(), rut);
                    }

                } else {
                    Log.e("Error", "Error en la respuesta: " + response.code());
                }
                callback.onCompletion();
            }
            @Override
            public void onFailure(Call<List<Rutina>> call, Throwable t) {
                Log.e("Error", "Fallo en la petición: " + t.getMessage());
            }
        });
    }

    private void llenarEjercicios(LlenarRutinasCallback callback)
    {
        Collection<Rutina> listR = rutinas.values();
        for(Rutina rut: listR)
        {
            Call<List<Ejercicio>> call = rutinaEjercicioApiService.getEjerciciosByRutinaId(rut.getId());
            call.enqueue(new Callback<List<Ejercicio>>() {
                @Override
                public void onResponse(Call<List<Ejercicio>> call, Response<List<Ejercicio>> response) {
                    if (response.isSuccessful()) {
                        List<Ejercicio> listaEjericios = response.body();
                        ejercicios.put(rut.getId(),listaEjericios);
                    } else {
                        // Maneja errores del servidor, por ejemplo, un error 404 o 500.
                        Log.e("Error", "Error en la respuesta: " + response.code());
                    }
                    callback.onCompletion();
                }
                @Override
                public void onFailure(Call<List<Ejercicio>> call, Throwable t) {
                    // Maneja errores de red o de conversión de datos
                    Log.e("Error", "Fallo en la petición: " + t.getMessage());

                }
            });

        }


    }

    private void llenarProgresos(LlenarRutinasCallback callback)
    {
        Collection<List<Ejercicio>> listOfEjercicioLists = ejercicios.values();
        Set<Ejercicio> allEjerciciosSet = new HashSet<>();

        for (List<Ejercicio> ejercicioList : listOfEjercicioLists) {
            allEjerciciosSet.addAll(ejercicioList);
        }
        for(Ejercicio ej: allEjerciciosSet)
        {
            Call<ProgresoxEjercicio> call = ejercicioProgresoxEjercicioApiService.getLatestProgresoxEjercicio(ej.getId().intValue(),userId.intValue());
            call.enqueue(new Callback<ProgresoxEjercicio>() {
                @Override
                public void onResponse(Call<ProgresoxEjercicio> call, Response<ProgresoxEjercicio> response) {
                    if (response.isSuccessful()) {
                        ProgresoxEjercicio progre = response.body();
                        progresos.put(ej.getId().intValue(),progre);
                    } else {
                        // Maneja errores del servidor, por ejemplo, un error 404 o 500.
                        Log.e("Error", "Error en la respuesta: " + response.code());
                    }
                    callback.onCompletion();
                }
                @Override
                public void onFailure(Call<ProgresoxEjercicio> call, Throwable t) {
                    // Maneja errores de red o de conversión de datos
                    Log.e("Error", "Fallo en la petición: " + t.getMessage());

                }
            });
        }

    }

    private void mostrar() {
        RecyclerView recyclerView = findViewById(R.id.recyclerViewEjercicios_115);
        LinearLayout emptyView = findViewById(R.id.empty_view_115);

        if (rutinas.get(dia)==null) {
            recyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        } else {
            Rutina rut = rutinas.get(dia);
            List<Ejercicio> ej = ejercicios.get(rut.getId());
            cajaRutinas.clear();
            opciones.clear();
            recyclerView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);

            for (Ejercicio ejercicio : ej) {
                getImagen(ejercicio.getId(), new LlenarRutinasCallback() {
                    CajaRutina temp = new CajaRutina();

                    @Override
                    public void onCompletion() {
                        temp.setEjercicio(ejercicio);
                        temp.setProgresoxEjercicio(progresos.get(ejercicio.getId().intValue()));
                        temp.setImagenEjercicio(imagenEjercicio);
                        cajaRutinas.add(temp);
                        cargarOpciones(rut.getId(),()->
                        {
                            filtrarOpciones(()->
                            {
                                adapter = new CajaRutinaAdapterM(cajaRutinas,opciones,_115_modificar_rutina_ejercicios.this,btnGuardarCambios,rut.getId());
                                recyclerView.setLayoutManager(new LinearLayoutManager(_115_modificar_rutina_ejercicios.this));
                                recyclerView.setAdapter(adapter);
                                adapter.notifyDataSetChanged();// Notifica al adapter que los datos han cambiado
                            });

                        });
                    }
                });
            }
        }
    }

    private void filtrarOpciones(LlenarRutinasCallback callback)
    {
        Rutina rut = rutinas.get(dia);
        List<Ejercicio> ejer = ejercicios.get(rut.getId());

        for(Ejercicio ejercicio:ejer)
        {
            int ejercicioId = ejercicio.getId().intValue();
            Iterator<CajaRutina> iterator = opciones.iterator();
            while(iterator.hasNext())
            {
                CajaRutina caja = iterator.next();
                if(caja.getEjercicio().getId().intValue()==ejercicioId)
                {
                    iterator.remove();
                }
            }
        }
        callback.onCompletion();
    }

    private void getImagen(Long idEjercicio,LlenarRutinasCallback callback)
    {
        Call<List<ImagenEjercicio>> call = imagenEjercicioApiService.findByEjercicioid(idEjercicio.intValue());
        call.enqueue(new Callback<List<ImagenEjercicio>>() {
            @Override
            public void onResponse(Call<List<ImagenEjercicio>> call, Response<List<ImagenEjercicio>> response) {
                if (response.isSuccessful()) {
                    List<ImagenEjercicio> lista = response.body();
                    imagenEjercicio = lista.get(0);
                } else {
                    // Maneja errores del servidor, por ejemplo, un error 404 o 500.
                    Log.e("Error", "Error en la respuesta: " + response.code());
                }
                callback.onCompletion();
            }
            @Override
            public void onFailure(Call<List<ImagenEjercicio>> call, Throwable t) {
                // Maneja errores de red o de conversión de datos
                Log.e("Error", "Fallo en la petición: " + t.getMessage());

            }
        });

    }
    private void filtrarListas()
    {
        Intent intent = new Intent(_115_modificar_rutina_ejercicios.this, _100_iniciar_rutina.class);
        ArrayList<CajaRutina> arrayListCajaRutinas;
        if (cajaRutinas instanceof ArrayList) {
            arrayListCajaRutinas = (ArrayList<CajaRutina>) cajaRutinas;
        } else {
            arrayListCajaRutinas = new ArrayList<>(cajaRutinas);
        }

        intent.putParcelableArrayListExtra("ListaCajaRutina", arrayListCajaRutinas);
        startActivity(intent);
    }

    private void cargarOpciones(int idRutina, LlenarRutinasCallback callback)
    {
        callback.onCompletion();

        Call<List<CajaRutina>> call = rutinaApiService.getEjerciciosByRutina(userId.intValue(),idRutina);
        call.enqueue(new Callback<List<CajaRutina>>() {
            @Override
            public void onResponse(Call<List<CajaRutina>> call, Response<List<CajaRutina>> response) {
                if (response.isSuccessful()) {
                     opciones = response.body();
                } else {
                    // Maneja errores del servidor, por ejemplo, un error 404 o 500.
                    Log.e("Error", "Error en la respuesta: " + response.code());
                }
                callback.onCompletion();
            }
            @Override
            public void onFailure(Call<List<CajaRutina>> call, Throwable t) {
                // Maneja errores de red o de conversión de datos
                Log.e("Error", "Fallo en la petición: " + t.getMessage());

            }
        });
    }
}
