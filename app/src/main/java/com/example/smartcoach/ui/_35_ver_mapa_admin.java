package com.example.smartcoach.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridLayout;
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
import api.Admi.ItemApiService;
import api.Admi.MapaApiService;
import api.Admi.TipoEquipoApiService;
import api.Admi.UbicacionxItemApiService;
import api.Admi.UsuarioAdministradorApiService;
import api.SharedPreferencesUtil;
import api.retro;
import model.Admi.GimnasioItem;
import model.Admi.Mapa;
import model.Admi.TipoEquipo;
import model.Admi.UsuarioAdministrador;
import model.User.UsuarioCliente;
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

    Long userId = SharedPreferencesUtil.getUserId(_35_ver_mapa_admin.this);
    String token = SharedPreferencesUtil.getToken(_35_ver_mapa_admin.this);

    int gimnasioId;
    Map<Integer, Mapa> mapas = new HashMap<>();
    List<GimnasioItem> listaItems = new ArrayList<>();
    Map<Integer,Integer> tipoEquipoItem = new HashMap<>();
    Map<Integer,String> iconos= new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout._35_ver_mapa_admin);

        gridLayout = findViewById(R.id.gridLayout_35);
        iniciarPeticiones();
        cargarInfo();
        cargarCuadrados();
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
    }

    interface InfoCallback {
        void onCompletion();
    }
    private void cargarInfo()
    {
        tenerGym(new InfoCallback() {
            @Override
            public void onCompletion() {
                Log.d("FIN", "rutinas: ");
                llenarMapas(new InfoCallback() {
                    @Override
                    public void onCompletion() {
                        Log.d("FIN", "ejercicios: ");
                        llenarGimnasioItem(new InfoCallback() {
                            @Override
                            public void onCompletion() {
                                Log.d("FIN", "prgoresos");
                                llenarTipoEquipo( new InfoCallback() {
                                @Override
                                public void onCompletion() {
                                    Log.d("FIN", "prgoresos");
                                    llenarUbicacionxItem(new InfoCallback() {
                                        @Override
                                        public void onCompletion() {
                                            Log.d("FIN", "prgoresos");
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
                    Log.d("Usuario", "Nombre: " + usuario.getNombre());
                    gimnasioId = usuario.getGimnasioId();
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

    }

    private void llenarTipoEquipo(_35_ver_mapa_admin.InfoCallback callback)
    {

    }

    private void llenarUbicacionxItem(_35_ver_mapa_admin.InfoCallback callback)
    {

    }

    private void cargarMapa()
    {

    }

    private void cargarCuadrados()
    {

        int ancho = 10;
        int alto = 10;

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

        cargarImagenes();

    }

    private void cargarImagenes()
    {
        ImageView imageView = new ImageView(this);

        GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams(
                GridLayout.spec(0), GridLayout.spec(0));  // 0,0 es para la primera fila, primera columna
        layoutParams.width =  (int) (32 * getResources().getDisplayMetrics().density + 0.5f);
        layoutParams.height =  (int) (32 * getResources().getDisplayMetrics().density + 0.5f);

        imageView.setLayoutParams(layoutParams);
        imageView.setImageResource(R.drawable.icon_mancuerna_ne_fondo_transp);  // Reemplaza "tu_imagen" con el nombre de tu recurso de imagen

        gridLayout.addView(imageView);

    }
}
