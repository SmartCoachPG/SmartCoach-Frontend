package com.example.smartcoach.ui;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.smartcoach.R;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import api.Admi.EquipoApiService;
import api.Admi.GimnasioApiService;
import api.Admi.GimnasioItemApiService;
import api.Admi.MapaApiService;
import api.Admi.TipoEquipoApiService;
import api.Admi.UbicacionxItemApiService;
import api.Admi.UsuarioAdministradorApiService;
import api.SharedPreferencesUtil;
import api.retro;
import model.Admi.Equipo;
import model.Admi.GimnasioItem;
import model.Admi.Mapa;
import model.Admi.TipoEquipo;
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
    int tipo=0;
    Equipo equipoBuscado = new Equipo();
    String nombreTipo = "";
    List<String> listaMusculos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout._35_ver_mapa_admin);
        System.gc();

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
                // es un equipo
                if(gi.getItemid()>=11)
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
                // es un item
                else {
                    tipoEquipoItem.put(gi.getItemid(),gi.getItemid()+4);
                }
            }
        }
        else {
        }
        callback.onCompletion();
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
        Log.d("Cargando", "entre cargar mapa: ");
        cargarCuadrados(mapas.get(piso).getAncho(), mapas.get(piso).getAlto(),()->{
            Log.d("Cargando", "terminer cargar cuadrados ");
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

                int row = i;
                int column = j;

                ImageView cuadrado = new ImageView(this);
                GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams(GridLayout.spec(i), GridLayout.spec(j));
                layoutParams.width = tamañoCasillaPixels;
                layoutParams.height = tamañoCasillaPixels;
                cuadrado.setLayoutParams(layoutParams);
                cuadrado.setBackgroundResource(R.drawable.fondo_mapa);

                GestureDetector gestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener()
                {
                    @Override
                    public boolean onSingleTapConfirmed(MotionEvent e) {
                        tipo =1;
                        return true;
                    }
                    @Override
                    public void onLongPress(MotionEvent e) {
                        tipo=2;
                    }
                    @Override
                    public boolean onDown(MotionEvent e)
                    {

                        return  true;
                    }
                });

                cuadrado.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v,MotionEvent event)
                    {
                        boolean gestureHandled = gestureDetector.onTouchEvent(event);
                        if(tipo==1)
                        {
                            tipo=0;
                            UbicacionxItem item2 = new UbicacionxItem();
                            item2.setCoordenadaX(column);
                            item2.setCoordenadaY(row);
                            UbicacionxItem item = buscarUbicacionxItem(column,row);
                            if(item.getItemid()>10){
                                    mostrarEquipo(item);
                            }

                        }

                        return true;
                    }
                });

                gridLayout.addView(cuadrado);
            }
        }

        infoCallback.onCompletion();
    }

    private UbicacionxItem buscarUbicacionxItem(int coordenadax,int coordenaday)
    {
        UbicacionxItem encontrado = new UbicacionxItem();
        for (Map.Entry<Integer, List<UbicacionxItem>> entry : ubicaciones.entrySet()) {

            // Obtener la lista de UbicacionxItem de la entrada actual
            List<UbicacionxItem> listaUbicaciones = entry.getValue();

            // Iterar a través de cada UbicacionxItem en la lista
            for (UbicacionxItem ubicacion : listaUbicaciones) {

                // Comparar las coordenadas
                if (ubicacion.getCoordenadaX() == coordenadax && ubicacion.getCoordenadaY() == coordenaday) {
                    // Si las coordenadas coinciden, retornar la ubicación encontrada
                    encontrado = ubicacion;
                    break;
                }
            }
        }

        return encontrado;
    }

    private void mostrarEquipo(UbicacionxItem ubicacionxItem)
    {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout._34_ver_informacion_equipo);
        dialog.getWindow().setBackgroundDrawable(null);

        ImageButton botonX = dialog.findViewById(R.id.btnX_34);
        ImageView imagen;
        TextView nombreEquipo,referenciaE,descripE, tipoMaquina;
        nombreEquipo = dialog.findViewById(R.id.nombreEquipo);
        referenciaE = dialog.findViewById(R.id.setTextReferencia_34);
        descripE = dialog.findViewById(R.id.setTextDescripcion_34);
        imagen = dialog.findViewById(R.id.setImageEquipo_34);
        tipoMaquina = dialog.findViewById(R.id.itemTipoMaquina_34);
        RecyclerView recycler = dialog.findViewById(R.id.recyclerViewItemMusculoInvolucrado_34);

        botonX.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        getEquipo(Long.valueOf(ubicacionxItem.getItemid()),()->
        {
            nombreEquipo.setText(equipoBuscado.getNombre());
            referenciaE.setText(equipoBuscado.getReferencia());
            descripE.setText(equipoBuscado.getDescripcion());
            if(equipoBuscado.getImagen()!=null)
            {
                byte[] imageBytes = Base64.decode(equipoBuscado.getImagen(), Base64.DEFAULT);
                Bitmap decodedBitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                imagen.setImageBitmap(decodedBitmap);
            }
            getTipoEquipoNombre(Long.valueOf(ubicacionxItem.getItemid()),()->
            {
                tipoMaquina.setText(nombreTipo);

                getMusculosEquipo(Long.valueOf(ubicacionxItem.getItemid()),()->
                {
                    LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
                    recycler.setLayoutManager(layoutManager);
                    _100_adaptadorI_musculos adapter = new _100_adaptadorI_musculos(listaMusculos);
                    recycler.setAdapter(adapter);
                });
            });

        });

        dialog.show();
    }

    private void getEquipo(Long itemid,_31_armar_mapa_admin.InfoCallback callback)
    {
        Call<Equipo> call = equipoApiService.getById(itemid);
        call.enqueue(new Callback<Equipo>() {
            @Override
            public void onResponse(Call<Equipo> call, Response<Equipo> response) {
                if (response.isSuccessful()) {
                    equipoBuscado = response.body();
                } else {
                    // Maneja errores del servidor, por ejemplo, un error 404 o 500.
                    Log.e("Error", "Error en la respuesta: " + response.code());
                }
                callback.onCompletion();
            }

            @Override
            public void onFailure(Call<Equipo> call, Throwable t) {
                // Maneja errores de red o de conversión de datos
                Log.e("Error", "Fallo en la petición: " + t.getMessage());
            }
        });

    }

    private void getTipoEquipoNombre(Long itemid,_31_armar_mapa_admin.InfoCallback callback)
    {
        Call<TipoEquipo> call = equipoApiService.getTipoNameByEquipoId(itemid);
        call.enqueue(new Callback<TipoEquipo>() {
            @Override
            public void onResponse(Call<TipoEquipo> call, Response<TipoEquipo> response) {
                if (response.isSuccessful()) {
                    nombreTipo = response.body().getNombre();
                } else {
                    // Maneja errores del servidor, por ejemplo, un error 404 o 500.
                    Log.e("Error", "Error en la respuesta: " + response.code());
                }
                callback.onCompletion();
            }

            @Override
            public void onFailure(Call<TipoEquipo> call, Throwable t) {
                // Maneja errores de red o de conversión de datos
                Log.e("Error", "Fallo en la petición: " + t.getMessage());
            }
        });
    }

    private void getMusculosEquipo(Long itemid,_31_armar_mapa_admin.InfoCallback callback)
    {
        Call<List<String>> call = equipoApiService.getMusculosByEquipoId(itemid);
        call.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if (response.isSuccessful()) {
                    if(!response.body().isEmpty())
                        listaMusculos = response.body();
                } else {
                    // Maneja errores del servidor, por ejemplo, un error 404 o 500.
                    Log.e("Error", "Error en la respuesta: " + response.code());
                }
                callback.onCompletion();
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                // Maneja errores de red o de conversión de datos
                Log.e("Error", "Fallo en la petición: " + t.getMessage());
            }
        });
    }
    private void cargarImagenes(_35_ver_mapa_admin.InfoCallback callback)
    {
        Log.d("Cargando", "entre cargar imagenes: ");
        for(GimnasioItem gi : listaItems)
        {
            Log.d("Cargando", "entre cargar lista items: "+gi);
            List<UbicacionxItem> ubi = ubicaciones.get(gi.getItemid());
            if(ubi!=null)
            {
                for(UbicacionxItem uxi : ubi)
                {
                    Log.d("Cargando", "entre ubicanco el item: "+uxi);

                    if(uxi.getMapaid()==mapas.get(piso).getId())
                    {
                        int row = uxi.getCoordenadaY();
                        int column = uxi.getCoordenadaX();

                        for (int i = 0; i < gridLayout.getChildCount(); i++) {
                            View view = gridLayout.getChildAt(i);
                            GridLayout.LayoutParams layoutParams = (GridLayout.LayoutParams) view.getLayoutParams();

                            if (GridLayout.spec(row).equals(layoutParams.rowSpec) &&
                                    GridLayout.spec(column).equals(layoutParams.columnSpec) &&
                                    view instanceof ImageView) {

                                ImageView imageView = (ImageView) view;
                                imageView.setPadding(2,2,2,2);
                                int tipo = tipoEquipoItem.get(uxi.getItemid());
                                int newDrawableId = getResources().getIdentifier(iconos.get(tipo), "drawable", getPackageName());
                                Glide.with(this).load(newDrawableId).into(imageView);
                                break;
                            }
                        }
                    }
                }
            }
        }

        Log.d("Cargar", "termine ");
        callback.onCompletion();
    }
}
