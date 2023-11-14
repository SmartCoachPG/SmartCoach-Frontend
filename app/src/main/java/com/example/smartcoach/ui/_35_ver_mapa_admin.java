package com.example.smartcoach.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.smartcoach.R;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import api.Admi.EquipoApiService;
import api.Admi.GimnasioApiService;
import api.Admi.GimnasioItemApiService;
import api.Admi.MapaApiService;
import api.Admi.TipoEquipoApiService;
import api.Admi.UbicacionxItemApiService;
import api.Admi.UsuarioAdministradorApiService;
import api.SharedPreferencesUtil;
import api.retro;
import model.Admi.GimnasioItem;
import model.Admi.Mapa;
import model.Admi.UbicacionxItem;
import model.Admi.UsuarioAdministrador;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class _35_ver_mapa_admin extends AppCompatActivity {

    GridLayout gridLayout;
    UsuarioAdministradorApiService usuarioAdministradorApiService;
    GimnasioApiService gimnasioApiService;
    MapaApiService mapaApiService;
    UbicacionxItemApiService ubicacionxItemApiService;
    GimnasioItemApiService gimnasioItemApiService;
    TipoEquipoApiService tipoEquipoApiService;
    EquipoApiService equipoApiService;

    Long userId = SharedPreferencesUtil.getUserId(_35_ver_mapa_admin.this);
    String token = SharedPreferencesUtil.getToken(_35_ver_mapa_admin.this);

    int gimnasioId=0;
    Map<Integer, Mapa> mapas = new HashMap<>();
    List<GimnasioItem> listaItems = new ArrayList<>();
    Map<Integer,Integer> tipoEquipoItem = new HashMap<>();
    Map<Integer,String> iconos= new HashMap<>();
    Map<Integer,List<UbicacionxItem>> ubicaciones= new HashMap<>();
    int piso = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout._35_ver_mapa_admin);

        gridLayout = findViewById(R.id.gridLayout_35);
        iniciarPeticiones();
        cargarIconos();
        cargarInfo();

        ImageButton btnRegresar = findViewById(R.id.flechaRegresar);
        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(_35_ver_mapa_admin.this, _27_configurar_mapa_admin.class);
                startActivity(intent);
            }
        });


    }

    private void cargarIconos()
    {
        //Pesos
        iconos.put(1,"icon_mancuerna_fondo_ne");
        //Maquinas de peso
        iconos.put(2,"icon_maquina_de_peso");
        //Maquinas de cardio
        iconos.put(3,"icon_corazon_ne");
        //complementos
        iconos.put(4,"icon_complementos_ne");
        // item. caminos
        iconos.put(5,"cuadrado_gr");
        // item. puerta
        iconos.put(6,"rectangulo_alargado_redondeado_n");
        // item. salud
        iconos.put(7,"icon_enfermera_ne");
        // item. baño
        iconos.put(8,"icon_papel_ne");
        // item. entrada
        iconos.put(9,"icon_puerta_ne");
        // item. cafeteria
        iconos.put(10,"icon_basura_ne_tansp");
        // item. lockers
        iconos.put(11,"icon_locker_ne");
        // item. oficina
        iconos.put(12,"icon_silla_ne");
        // item. muro
        iconos.put(13,"icon_linea_gr");
        // item. escaleras
        iconos.put(14,"icon_escaleras_ne");
    }

    private void iniciarPeticiones()
    {
        OkHttpClient okHttpClient = retro.getUnsafeOkHttpClientWithToken(token);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://10.0.2.2:8043/api/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        usuarioAdministradorApiService = retrofit.create(UsuarioAdministradorApiService.class);
        gimnasioApiService = retrofit.create(GimnasioApiService.class);
        mapaApiService = retrofit.create(MapaApiService.class);
        ubicacionxItemApiService = retrofit.create(UbicacionxItemApiService.class);
        gimnasioItemApiService = retrofit.create(GimnasioItemApiService.class);
        tipoEquipoApiService = retrofit.create(TipoEquipoApiService.class);
        equipoApiService = retrofit.create(EquipoApiService.class);
    }

    interface InfoCallback {
        void onCompletion();
    }
    private void cargarInfo()
    {
        tenerGym(new InfoCallback() {
            @Override
            public void onCompletion() {
                llenarMapas(new InfoCallback() {
                    @Override
                    public void onCompletion() {
                        llenarGimnasioItem(new InfoCallback() {
                            @Override
                            public void onCompletion() {
                                llenarTipoEquipo( new InfoCallback() {
                                @Override
                                public void onCompletion() {
                                    procesarItems(0,new InfoCallback() {
                                        @Override
                                        public void onCompletion() {
                                            cargarMapa();
                                        }
                                    });

                                }
                            });
                            }
                        });
                    }
                });

            }
        });
    }

    private void tenerGym(_35_ver_mapa_admin.InfoCallback callback)
    {
        Call<UsuarioAdministrador> call = usuarioAdministradorApiService.getUsuarioById(userId);
        call.enqueue(new Callback<UsuarioAdministrador>() {
            @Override
            public void onResponse(Call<UsuarioAdministrador> call, Response<UsuarioAdministrador> response) {
                if (response.isSuccessful()) {
                    UsuarioAdministrador usuario = response.body();
                    if(usuario.getGimnasioId()!=null)
                    {
                        gimnasioId = usuario.getGimnasioId();
                    }
                    callback.onCompletion();
                } else {
                    // Maneja errores del servidor, por ejemplo, un error 404 o 500.
                    Log.e("Error", "Error en la respuesta: " + response.code());
                }

            }

            @Override
            public void onFailure(Call<UsuarioAdministrador> call, Throwable t) {
                // Maneja errores de red o de conversión de datos
                Log.e("Error", "Fallo en la petición: " + t.getMessage());
            }
        });
    }

    private void llenarMapas(_35_ver_mapa_admin.InfoCallback callback)
    {
        Call<List<Mapa>> call = mapaApiService.getMapasByGimnasioId(gimnasioId);
        call.enqueue(new Callback<List<Mapa>>() {
            @Override
            public void onResponse(Call<List<Mapa>> call, Response<List<Mapa>> response) {
                if (response.isSuccessful()) {
                    List<Mapa> listaMapas = response.body();
                    for(Mapa map: listaMapas )
                    {
                        mapas.put(map.getNivel(),map);
                    }
                } else {
                    // Maneja errores del servidor, por ejemplo, un error 404 o 500.
                    Log.e("Error", "Error en la respuesta: " + response.code());
                }
                callback.onCompletion();
            }

            @Override
            public void onFailure(Call<List<Mapa>> call, Throwable t) {
                // Maneja errores de red o de conversión de datos
                Log.e("Error", "Fallo en la petición: " + t.getMessage());
            }
        });
    }

    private void llenarGimnasioItem(_35_ver_mapa_admin.InfoCallback callback)
    {
        Call<List<GimnasioItem>> call = gimnasioItemApiService.getGimnasioItemsByGimnasioid(gimnasioId);
        call.enqueue(new Callback<List<GimnasioItem>>() {
            @Override
            public void onResponse(Call<List<GimnasioItem>> call, Response<List<GimnasioItem>> response) {
                if (response.isSuccessful()) {
                    Log.e("GIMNASIO", "cargando item: " + response.body().toString());
                    if(!response.body().isEmpty())
                    {
                        listaItems = response.body();
                    }

                } else {
                    // Maneja errores del servidor, por ejemplo, un error 404 o 500.
                    Log.e("Error", "Error en la respuesta: " + response.code());
                }
                callback.onCompletion();
            }

            @Override
            public void onFailure(Call<List<GimnasioItem>> call, Throwable t) {
                // Maneja errores de red o de conversión de datos
                Log.e("Error", "Fallo en la petición: " + t.getMessage());
            }
        });
    }

    private void llenarTipoEquipo(_35_ver_mapa_admin.InfoCallback callback)
    {
        if(!listaItems.isEmpty())
        {
            for(GimnasioItem gi : listaItems)
            {
                if(gi.getItemid()>11)
                {
                    Call<Integer> call = equipoApiService.findTipoEquipoIdByItemId(Long.valueOf(gi.getItemid()));
                    call.enqueue(new Callback<Integer>() {
                        @Override
                        public void onResponse(Call<Integer> call, Response<Integer> response) {
                            if (response.isSuccessful()) {
                                tipoEquipoItem.put(gi.getItemid(),response.body());
                            } else {
                                // Maneja errores del servidor, por ejemplo, un error 404 o 500.
                                Log.e("Error", "Error en la respuesta: " + response.code());
                            }
                            callback.onCompletion();
                        }

                        @Override
                        public void onFailure(Call<Integer> call, Throwable t) {
                            // Maneja errores de red o de conversión de datos
                            Log.e("Error", "Fallo en la petición: " + t.getMessage());
                        }
                    });
                }
                else {
                    tipoEquipoItem.put(gi.getItemid(),gi.getItemid()+4);
                }
            }
        }
        else {
            callback.onCompletion();
        }

    }

    private void procesarItems(int index, InfoCallback callback) {
        if (index >= listaItems.size()|| listaItems.isEmpty()) {
            callback.onCompletion();
            return;
        }

        GimnasioItem gi = listaItems.get(index);
        llenarUbicacionxItem(gi, new InfoCallback() {
            @Override
            public void onCompletion() {
                procesarItems(index + 1, callback);
            }
        });
    }
    private void llenarUbicacionxItem(GimnasioItem gi,_35_ver_mapa_admin.InfoCallback callback) {

            Call<List<UbicacionxItem>> call = ubicacionxItemApiService.getUbicacionxItemsByItemId(gi.getItemid(),gimnasioId);
            call.enqueue(new Callback<List<UbicacionxItem>> () {
                @Override
                public void onResponse(Call<List<UbicacionxItem>>  call, Response<List<UbicacionxItem>>  response) {
                    if (response.isSuccessful()) {
                        if(!response.body().isEmpty())
                        {
                            List<UbicacionxItem> lista = response.body();
                            ubicaciones.put(gi.getItemid(),lista);
                        }

                    } else {
                        // Maneja errores del servidor, por ejemplo, un error 404 o 500.
                        Log.e("Error", "Error en la respuesta: " + response.code());
                    }
                    callback.onCompletion();
                }

                @Override
                public void onFailure(Call<List<UbicacionxItem>>  call, Throwable t) {
                    // Maneja errores de red o de conversión de datos
                    Log.e("Error", "Fallo en la petición: " + t.getMessage());
                }
            });
    }

    private void cargarMapa()
    {
        cargarCuadrados(mapas.get(piso).getAncho(), mapas.get(piso).getAlto(),()->{
            cargarImagenes(()->{});
        });
    }

    private void cargarCuadrados(int ancho,int alto,_35_ver_mapa_admin.InfoCallback infoCallback)
    {
        int tamañoCasilla = 32;
        final float scale = getResources().getDisplayMetrics().density;
        int tamañoCasillaPixels = (int) (tamañoCasilla * scale + 0.5f);

        gridLayout.setColumnCount(ancho);
        gridLayout.setRowCount(alto);

        for (int i = 0; i < alto; i++) {
            for (int j = 0; j < ancho; j++) {
                View cuadrado = new View(this);

                GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams(
                        GridLayout.spec(i), GridLayout.spec(j));
                layoutParams.width = tamañoCasillaPixels;
                layoutParams.height = tamañoCasillaPixels;

                cuadrado.setLayoutParams(layoutParams);
                cuadrado.setBackgroundResource(R.drawable.fondo_mapa);

                gridLayout.addView(cuadrado);
            }
        }

        infoCallback.onCompletion();
    }

    private void cargarImagenes(_35_ver_mapa_admin.InfoCallback callback)
    {
        for(GimnasioItem gi : listaItems)
        {
            List<UbicacionxItem> ubi = ubicaciones.get(gi.getItemid());
            if(!ubi.isEmpty()&&ubi!=null)
            {
                for(UbicacionxItem uxi : ubi)
                {
                    if(uxi.getMapaid()==mapas.get(piso).getId())
                    {
                        ImageView imageView = new ImageView(this);
                        GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams(GridLayout.spec(uxi.getCoordenadaY()), GridLayout.spec(uxi.getCoordenadaX()));  // 0,0 es para la primera fila, primera columna
                        layoutParams.width =  (int) (30 * getResources().getDisplayMetrics().density + 0.5f);
                        layoutParams.height =  (int) (30 * getResources().getDisplayMetrics().density + 0.5f);
                        int margin = (int) (1 * getResources().getDisplayMetrics().density + 0.5f);
                        layoutParams.setMargins(margin,margin,margin,margin);
                        imageView.setLayoutParams(layoutParams);
                        int tipo = tipoEquipoItem.get(uxi.getItemid());
                        int resID = getResources().getIdentifier(iconos.get(tipo), "drawable", getPackageName());
                        imageView.setImageResource(resID);
                        gridLayout.addView(imageView);
                    }
                }
            }

        }
        callback.onCompletion();
    }
}
