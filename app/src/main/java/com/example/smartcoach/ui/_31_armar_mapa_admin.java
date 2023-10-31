package com.example.smartcoach.ui;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.DragEvent;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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

public class _31_armar_mapa_admin extends AppCompatActivity implements OnDefinirButtonClickListener {

    GridLayout gridLayout;
    AppCompatButton equipoB, elementosB;
    ImageView basura;
    UsuarioAdministradorApiService usuarioAdministradorApiService;
    GimnasioApiService gimnasioApiService;
    MapaApiService mapaApiService;
    UbicacionxItemApiService ubicacionxItemApiService;
    GimnasioItemApiService gimnasioItemApiService;
    TipoEquipoApiService tipoEquipoApiService;
    EquipoApiService equipoApiService;

    Long userId;
    String token;
    int gimnasioId = 0;
    Map<Integer, Mapa> mapas = new HashMap<>();
    List<GimnasioItem> listaItems = new ArrayList<>();
    Map<Integer, Integer> tipoEquipoItem = new HashMap<>();
    Map<Integer, String> iconos = new HashMap<>();
    Map<Integer, String> iconosName = new HashMap<>();
    Map<Integer, String> iconosNa = new HashMap<>();
    Map<Integer, List<UbicacionxItem>> ubicaciones = new HashMap<>();
    Map<UbicacionxItem, Integer> añadidos = new HashMap<>();
    int piso = 1;
    Boolean equipo = true;
    Map<Integer, UbicacionxItem> nuevaPosicion = new HashMap<>();
    int tipo=0;

    Equipo equipoBuscado = new Equipo();
    String nombreTipo = "";
    List<String> listaMusculos = new ArrayList<>();

    Map<Integer,String> nombreTipos = new HashMap<>();
    List<Equipo> listaEquipos = new ArrayList<>();
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout._31_armar_mapa_admin);

        gridLayout = findViewById(R.id.gridLayout_31);
        userId = SharedPreferencesUtil.getUserId(_31_armar_mapa_admin.this);
        token = SharedPreferencesUtil.getToken(_31_armar_mapa_admin.this);
        iniciarPeticiones();
        cargarIconos();
        cargarInfo();

        ImageButton btnRegresar = findViewById(R.id.flechaRegresar_31);
        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(_31_armar_mapa_admin.this, _27_configurar_mapa_admin.class);
                startActivity(intent);
            }
        });

        equipoB = findViewById(R.id.botonEquipo_31);
        elementosB = findViewById(R.id.botonElementos_31);

        equipoB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                equipo = true;
                int orangeColor = ContextCompat.getColor(view.getContext(), R.color.orange);
                equipoB.setBackgroundColor(orangeColor);
                int grayColor = ContextCompat.getColor(view.getContext(), R.color.grisClaro);
                elementosB.setBackgroundColor(grayColor);
                cargarListas();

            }
        });

        elementosB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                equipo = false;
                int orangeColor = ContextCompat.getColor(view.getContext(), R.color.orange);
                elementosB.setBackgroundColor(orangeColor);
                int grayColor = ContextCompat.getColor(view.getContext(), R.color.grisClaro);
                equipoB.setBackgroundColor(grayColor);
                cargarListas();
            }
        });

        basura = findViewById(R.id.basura_31);
        basura.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                switch (event.getAction()) {
                    case DragEvent.ACTION_DRAG_STARTED:
                        Log.d("BASURA", "Drag started");
                        return true;

                    case DragEvent.ACTION_DRAG_ENTERED:
                        Log.d("BASURA", "Drag entered");
                        return true;

                    case DragEvent.ACTION_DRAG_EXITED:
                        Log.d("BASURA", "Drag exited");
                        return true;

                    case DragEvent.ACTION_DROP:
                        Log.d("BASURA", "Dropped");
                        eliminarItem(()->{
                            Toast.makeText(_31_armar_mapa_admin.this, "Item Eliminado", Toast.LENGTH_SHORT).show();
                        });
                        View draggedView = (View) event.getLocalState();
                        draggedView.setVisibility(View.INVISIBLE);
                        return true;

                    case DragEvent.ACTION_DRAG_ENDED:
                        Log.d("BASURA", "Drag ended");
                        return true;
                }
                return false;
            }
        });

    }

    private void cargarListas() {
        RecyclerView recyclerView = findViewById(R.id.recyclerView_31);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        EquipoAdapter adapter = new EquipoAdapter(this, iconosName, iconos, equipo);
        recyclerView.setAdapter(adapter);
    }

    private void cargarIconos() {
        //Pesos
        iconos.put(1, "icon_mancuerna_fondo_ne");
        iconosName.put(1, "Pesos");
        iconosNa.put(1, "icon_mancuerna_fondo_na");
        //Maquinas de peso
        iconos.put(2, "icon_maquina_de_peso");
        iconosName.put(2, "Maquinas de peso");
        iconosNa.put(2, "icon_maquina_de_peso_na");

        //Maquinas de cardio
        iconos.put(3, "icon_corazon_ne");
        iconosName.put(3, "Maquinas de cardio");
        iconosNa.put(3, "icon_corazon_na");

        //complementos
        iconos.put(4, "icon_complementos_ne");
        iconosName.put(4, "Complementos");
        iconosNa.put(4, "icon_complementos_na");

        // item. caminos
        iconos.put(5, "cuadrado_gr");
        iconosName.put(5, "Caminos");
        // item. puerta
        iconos.put(6, "rectangulo_alargado_redondeado_n");
        iconosName.put(6, "Puerta");
        // item. salud
        iconos.put(7, "icon_enfermera_ne");
        iconosName.put(7, "Salud");
        // item. baño
        iconos.put(8, "icon_papel_ne");
        iconosName.put(8, "Baño");
        // item. entrada
        iconos.put(9, "icon_puerta_ne");
        iconosName.put(9, "Entrada");
        // item. cafeteria
        iconos.put(10, "icon_basura_ne_tansp");
        iconosName.put(10, "Cafetería");
        // item. lockers
        iconos.put(11, "icon_locker_ne");
        iconosName.put(11, "Lockers");
        // item. oficina
        iconos.put(12, "icon_silla_ne");
        iconosName.put(12, "Oficina");
        // item. muro
        iconos.put(13, "icon_linea_gr");
        iconosName.put(13, "Muro");
        // item. escaleras
        iconos.put(14, "icon_escaleras_ne");
        iconosName.put(14, "Escaleras");

        nombreTipos.put(1,"pesos");
        nombreTipos.put(2,"maquinas de peso");
        nombreTipos.put(3,"maquinas de cardio");
        nombreTipos.put(4,"complementos");
        cargarListas();
    }

    private void iniciarPeticiones() {
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

    private void cargarInfo() {
        tenerGym(new InfoCallback() {
            @Override
            public void onCompletion() {
                llenarMapas(new InfoCallback() {
                    @Override
                    public void onCompletion() {
                        llenarGimnasioItem(new InfoCallback() {
                            @Override
                            public void onCompletion() {
                                llenarTipoEquipo(new InfoCallback() {
                                    @Override
                                    public void onCompletion() {
                                        procesarItems(0, new InfoCallback() {
                                            @Override
                                            public void onCompletion() {
                                                cargarMapa();
                                                Log.d("CARGANDO IMAGENES", "termino cargarMapa");

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

    private void tenerGym(_31_armar_mapa_admin.InfoCallback callback) {
        Call<UsuarioAdministrador> call = usuarioAdministradorApiService.getUsuarioById(userId);
        call.enqueue(new Callback<UsuarioAdministrador>() {
            @Override
            public void onResponse(Call<UsuarioAdministrador> call, Response<UsuarioAdministrador> response) {
                if (response.isSuccessful()) {
                    UsuarioAdministrador usuario = response.body();
                    if (usuario.getGimnasioId() != null) {
                        Log.d("Usuario", "Nombre: " + usuario.getNombre());
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

    private void llenarMapas(_31_armar_mapa_admin.InfoCallback callback) {
        Call<List<Mapa>> call = mapaApiService.getMapasByGimnasioId(gimnasioId);
        call.enqueue(new Callback<List<Mapa>>() {
            @Override
            public void onResponse(Call<List<Mapa>> call, Response<List<Mapa>> response) {
                if (response.isSuccessful()) {
                    List<Mapa> listaMapas = response.body();
                    for (Mapa map : listaMapas) {
                        mapas.put(map.getNivel(), map);
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

    private void llenarGimnasioItem(_31_armar_mapa_admin.InfoCallback callback) {
        Call<List<GimnasioItem>> call = gimnasioItemApiService.getGimnasioItemsByGimnasioid(gimnasioId);
        call.enqueue(new Callback<List<GimnasioItem>>() {
            @Override
            public void onResponse(Call<List<GimnasioItem>> call, Response<List<GimnasioItem>> response) {
                if (response.isSuccessful()) {
                    Log.e("GIMNASIO", "cargando item: " + response.body().toString());
                    if (!response.body().isEmpty()) {
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

    private void llenarTipoEquipo(_31_armar_mapa_admin.InfoCallback callback) {
        Log.d("Mostrar", "mirando lista Items: "+listaItems);

        if (!listaItems.isEmpty()) {
            for (GimnasioItem gi : listaItems) {
                if (gi.getItemid() > 10) {
                    Call<Integer> call = equipoApiService.findTipoEquipoIdByItemId(Long.valueOf(gi.getItemid()));
                    call.enqueue(new Callback<Integer>() {
                        @Override
                        public void onResponse(Call<Integer> call, Response<Integer> response) {
                            if (response.isSuccessful()) {
                                Log.d("Mostrar", "mirando item: "+gi.getItemid()+" el tipo es:"+response.body());
                                tipoEquipoItem.put(gi.getItemid(), response.body());
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
                } else {
                    tipoEquipoItem.put(gi.getItemid(), gi.getItemid() + 4);
                }
            }
        } else {
            callback.onCompletion();
        }

    }

    private void procesarItems(int index, InfoCallback callback) {
        if (index >= listaItems.size() || listaItems.isEmpty()) {
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

    private void llenarUbicacionxItem(GimnasioItem gi, _31_armar_mapa_admin.InfoCallback callback) {

        Call<List<UbicacionxItem>> call = ubicacionxItemApiService.getUbicacionxItemsByItemId(gi.getItemid(), gimnasioId);
        call.enqueue(new Callback<List<UbicacionxItem>>() {
            @Override
            public void onResponse(Call<List<UbicacionxItem>> call, Response<List<UbicacionxItem>> response) {
                if (response.isSuccessful()) {
                    if (!response.body().isEmpty()) {
                        List<UbicacionxItem> lista = response.body();
                        ubicaciones.put(gi.getItemid(), lista);
                    }

                } else {
                    // Maneja errores del servidor, por ejemplo, un error 404 o 500.
                    Log.e("Error", "Error en la respuesta: " + response.code());
                }
                callback.onCompletion();
            }

            @Override
            public void onFailure(Call<List<UbicacionxItem>> call, Throwable t) {
                // Maneja errores de red o de conversión de datos
                Log.e("Error", "Fallo en la petición: " + t.getMessage());
            }
        });
    }

    private void cargarMapa() {
        Log.d("FIN", "idGimnasio: " + gimnasioId);
        Log.d("FIN", "mapas: " + mapas);
        Log.d("FIN", "listaItems: " + listaItems);
        Log.d("FIN", "tipo equipo: " + tipoEquipoItem);
        Log.d("FIN", "iconos: " + iconos);
        Log.d("FIN", "ubicaciones: " + ubicaciones);
        cargarCuadrados(mapas.get(piso).getAncho(), mapas.get(piso).getAlto(),()->{
            cargarImagenes(()->{});
        });

    }

    private void cargarCuadrados(int ancho, int alto,_31_armar_mapa_admin.InfoCallback callback) {
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
                            Log.d("Mostrar", "me dieron click");
                            tipo=0;
                            UbicacionxItem item2 = new UbicacionxItem();
                            item2.setCoordenadaX(column);
                            item2.setCoordenadaY(row);
                            if(nuevo(item2).getCoordenadaX()!=0)
                            {   Log.d("Mostrar", "entro ahi");
                                ubicarEquipo(cuadrado,nuevo(item2));
                            }else {
                                UbicacionxItem item = buscarUbicacionxItem(column,row);
                                if(item.getItemid()>10){
                                    mostrarEquipo(item);
                                }
                            }
                        }
                        else if(tipo==2){
                            tipo=0;
                            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
                            v.startDrag(null,shadowBuilder,v,0);
                            UbicacionxItem ubicacionxItem = new UbicacionxItem();
                            ubicacionxItem.setCoordenadaX(column);
                            ubicacionxItem.setCoordenadaY(row);
                            nuevaPosicion.put(0,ubicacionxItem);
                        }

                        return true;
                    }
                });

                gridLayout.addView(cuadrado);
            }
        }

        gridLayout.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                switch (event.getAction()) {
                    case DragEvent.ACTION_DROP:
                        int x = (int) event.getX();
                        int y = (int) event.getY();

                        // Encuentra el ImageView basado en las coordenadas x, y
                        for (int i = 0; i < gridLayout.getChildCount(); i++) {
                            ImageView child = (ImageView) gridLayout.getChildAt(i);
                            View draggedView = (View) event.getLocalState();

                            if (isPointWithinView(x, y, basura)) {
                                draggedView.setVisibility(View.INVISIBLE);
                            } else {
                                if (isPointWithinView(x, y, child)) {

                                    ImageView targetView = child;
                                    Drawable draggedDrawable = ((ImageView) draggedView).getDrawable();
                                    ((ImageView) draggedView).setImageDrawable(targetView.getDrawable());
                                    int tamañoCasilla = 32;
                                    final float scale = getResources().getDisplayMetrics().density;
                                    int tamañoCasillaPixels = (int) (tamañoCasilla * scale + 0.5f);

                                    int droppedRow = y / tamañoCasillaPixels;
                                    int droppedColumn = x / tamañoCasillaPixels;

                                    droppedRow = Math.round((float) droppedRow);
                                    droppedColumn = Math.round((float) droppedColumn);

                                    // Añadir nuevos elementos
                                    if (draggedView.getTag() != null) {
                                        int position = (int) draggedView.getTag();
                                        draggedView.setTag(null);
                                        Log.d("Mostrar", "posicion es: "+position);
                                        if(position<5)
                                        {
                                            UbicacionxItem ubicacionxItem = new UbicacionxItem();
                                            ubicacionxItem.setCoordenadaY(droppedRow);
                                            ubicacionxItem.setCoordenadaX(droppedColumn);
                                            ubicacionxItem.setItemid(position);
                                            int newDrawableId = getResources().getIdentifier(iconosNa.get(position), "drawable", getPackageName());
                                            Drawable newDrawable = ContextCompat.getDrawable(_31_armar_mapa_admin.this, newDrawableId);
                                            targetView.setImageDrawable(newDrawable);
                                            añadidos.put(ubicacionxItem, position);
                                        }
                                        else {
                                            UbicacionxItem ubicacionxItem = new UbicacionxItem();
                                            ubicacionxItem.setCoordenadaY(droppedRow);
                                            ubicacionxItem.setCoordenadaX(droppedColumn);
                                            targetView.setImageDrawable(draggedDrawable);
                                            ubicacionxItem.setItemid(position-4);
                                            nuevaPosicion.put(0, ubicacionxItem);
                                            crearNuevoItem(()->{});
                                        }
                                    }else { //Mover elementos
                                        UbicacionxItem ubicacionxItem = new UbicacionxItem();
                                        ubicacionxItem.setCoordenadaY(droppedRow);
                                        ubicacionxItem.setCoordenadaX(droppedColumn);
                                        ubicacionxItem.setCoordenadaX(ubicacionxItem.getCoordenadaX());
                                        nuevaPosicion.put(1, ubicacionxItem);
                                        targetView.setImageDrawable(draggedDrawable);
                                        guardarNuevaPosi(() -> {});
                                    }

                                    draggedView.setVisibility(View.VISIBLE);

                                    break;
                                }

                            }
                        }
                        break;
                }
                return true;
            }

            private boolean isPointWithinView(int x, int y, View view) {
                int viewX = (int) view.getX();
                int viewY = (int) view.getY();
                int viewWidth = view.getWidth();
                int viewHeight = view.getHeight();

                return (x >= viewX && x <= (viewX + viewWidth)) &&
                        (y >= viewY && y <= (viewY + viewHeight));
            }
        });

        callback.onCompletion();
    }

    private void cargarImagenes( _31_armar_mapa_admin.InfoCallback callback) {
        for (GimnasioItem gi : listaItems) {
            List<UbicacionxItem> ubi = ubicaciones.get(gi.getItemid());
            if (ubi != null &&!ubi.isEmpty() ) {
                for (UbicacionxItem uxi : ubi) {
                    if (uxi.getMapaid() == mapas.get(piso).getId()) {
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
                                Log.d("MOSTRAR", "tipoEquipoItem: "+tipoEquipoItem);
                                Log.d("MOSTRAR", "uxi: "+uxi.getItemid());
                                Log.d("MOSTRAR", "tipo: "+tipoEquipoItem.get(uxi.getItemid()));

                                int tipo = tipoEquipoItem.get(uxi.getItemid());
                                int newDrawableId = getResources().getIdentifier(iconos.get(tipo), "drawable", getPackageName());
                                Drawable newDrawable = ContextCompat.getDrawable(_31_armar_mapa_admin.this, newDrawableId);
                                Log.d("MOSTRAR", "drawable: "+iconos.get(tipo));
                                imageView.setImageDrawable(newDrawable);
                                Log.d("MOSTRAR", "en cuadro: ");
                                break;
                            }
                        }
                    }
                }
            }
        }
        callback.onCompletion();

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
    private void guardarNuevaPosi(_31_armar_mapa_admin.InfoCallback callback) {
        int xBuscar = nuevaPosicion.get(0).getCoordenadaX();
        int yBuscar = nuevaPosicion.get(0).getCoordenadaY();
        UbicacionxItem encontrado = buscarUbicacionxItem(xBuscar,yBuscar);

        Log.d("NUEVA posicion", "elemento movido: " + encontrado);
        encontrado.setCoordenadaY(nuevaPosicion.get(1).getCoordenadaY());
        encontrado.setCoordenadaX(nuevaPosicion.get(1).getCoordenadaX());

        Log.d("Nuevo item ", "elemento inicio: " + nuevaPosicion.get(0));
        Log.d("Nuevo item ", "elemento fin: " + nuevaPosicion.get(1));

        Call<UbicacionxItem> call = ubicacionxItemApiService.updateUbicacionxItem(encontrado);
        call.enqueue(new Callback<UbicacionxItem>() {
            @Override
            public void onResponse(Call<UbicacionxItem> call, Response<UbicacionxItem> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(_31_armar_mapa_admin.this, "Nueva ubicacion guardada", Toast.LENGTH_SHORT).show();
                    nuevaPosicion.remove(0);
                    nuevaPosicion.remove(1);
                } else {
                    // Maneja errores del servidor, por ejemplo, un error 404 o 500.
                    Log.e("Error", "Error en la respuesta: " + response.code());
                }
                callback.onCompletion();
            }

            @Override
            public void onFailure(Call<UbicacionxItem> call, Throwable t) {
                // Maneja errores de red o de conversión de datos
                Log.e("Error", "Fallo en la petición: " + t.getMessage());
            }
        });
    }

    private void crearNuevoItem(_31_armar_mapa_admin.InfoCallback callback)
    {
        UbicacionxItem nuevoItem = nuevaPosicion.get(0);
        nuevoItem.setGimnasioid(gimnasioId);
        nuevoItem.setMapaid(mapas.get(piso).getId().intValue());

        Log.e("Update", "Nuevo item " + nuevoItem.getItemid());
        Call<UbicacionxItem> call = ubicacionxItemApiService.addUbicacionxItem(nuevoItem);

        call.enqueue(new Callback<UbicacionxItem>() {
            @Override
            public void onResponse(Call<UbicacionxItem> call, Response<UbicacionxItem> response) {
                if (response.isSuccessful()) {
                    UbicacionxItem nuevoItem = response.body();

                    List<UbicacionxItem> actualizado = ubicaciones.get(nuevoItem.getItemid());
                    if(actualizado==null || actualizado.isEmpty())
                    {
                        Log.d("Update", "creo nuevo gimnasioItem: "+nuevoItem.getItemid());
                        actualizado = new ArrayList<>();
                        actualizado.add(nuevoItem);
                        ubicaciones.put(nuevoItem.getItemid(),actualizado);
                        createGimnasioItem(nuevoItem,()->{});
                    }
                    else {
                        actualizado.add(nuevoItem);
                        ubicaciones.remove(nuevoItem.getItemid());
                        ubicaciones.put(nuevoItem.getItemid(),actualizado);
                        updateGimnasioItem(nuevoItem,1,()-> {});
                    }

                    nuevaPosicion.remove(0);
                    Toast.makeText(_31_armar_mapa_admin.this, "Nuevo item añadido ", Toast.LENGTH_SHORT).show();

                } else {
                    // Maneja errores del servidor, por ejemplo, un error 404 o 500.
                    Log.e("Error", "Error en la respuesta: " + response.code());
                }
                callback.onCompletion();
            }

            @Override
            public void onFailure(Call<UbicacionxItem> call, Throwable t) {
                // Maneja errores de red o de conversión de datos
                Log.e("Error", "Fallo en la petición: " + t.getMessage());
            }
        });
    }

    private void updateGimnasioItem(UbicacionxItem ubicacionxItem,int cambio, _31_armar_mapa_admin.InfoCallback callback)
    {
        GimnasioItem actualizar = new GimnasioItem();
        Log.d("Update", "listaItems: "+listaItems);
        for(GimnasioItem gimnasioItem:listaItems)
        {
            if(gimnasioItem.getItemid()==ubicacionxItem.getItemid())
            {
                actualizar = gimnasioItem;
                Log.d("Update", "encontro cambio: "+actualizar);
                listaItems.remove(actualizar);
                break;
            }
        }

        Log.d("Update", "modifico lista: "+listaItems);
        int original = actualizar.getCantidad();
        Log.d("Update", "cantidad items antes: "+actualizar.getCantidad());
        int nuevo = original+cambio;
        actualizar.setCantidad(nuevo);
        Log.d("Update", "cantidad items despues: "+actualizar.getCantidad());

        if(actualizar.getCantidad()>0)
        { // actualizar
            Log.d("Update", "actualizo cantidad: "+actualizar.getCantidad());
            listaItems.add(actualizar);
            Call<GimnasioItem> call = gimnasioItemApiService.updateGimnasioItem(actualizar);
            call.enqueue(new Callback<GimnasioItem>() {
                @Override
                public void onResponse(Call<GimnasioItem> call, Response<GimnasioItem> response) {
                    if (response.isSuccessful()) {

                    } else {
                        // Maneja errores del servidor, por ejemplo, un error 404 o 500.
                        Log.e("Error", "Error en la respuesta: " + response.code());
                    }
                    callback.onCompletion();
                }

                @Override
                public void onFailure(Call<GimnasioItem> call, Throwable t) {
                    // Maneja errores de red o de conversión de datos
                    Log.e("Error", "Fallo en la petición: " + t.getMessage());
                }
            });
        }
        else { // Eliminar gimnasio item
            Log.d("Update", "elimino"+ actualizar.getCantidad());
            listaItems.remove(actualizar);
            Log.d("Eliminar", "idGimnasio: "+gimnasioId+" idItem:"+actualizar.getItemid());
            Call<Void> call = gimnasioItemApiService.deleteGimnasioItem(gimnasioId,actualizar.getItemid());
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {

                    } else {
                        // Maneja errores del servidor, por ejemplo, un error 404 o 500.
                        Log.e("Error", "Error en la respuesta, eliminar gimnasioItem: " + response.code());
                    }
                    callback.onCompletion();
                }
                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    // Maneja errores de red o de conversión de datos
                    Log.e("Error", "Fallo en la petición: " + t.getMessage());
                }
            });
        }

    }

    private void createGimnasioItem(UbicacionxItem ubicacionxItem, _31_armar_mapa_admin.InfoCallback callback)
    {
        GimnasioItem actualizar = new GimnasioItem();
        actualizar.setCantidad(1);
        actualizar.setGimnasioid(gimnasioId);
        actualizar.setItemid(ubicacionxItem.getItemid());
        listaItems.add(actualizar);

        Call<GimnasioItem> call = gimnasioItemApiService.addGimnasioItem(actualizar);
        call.enqueue(new Callback<GimnasioItem>() {
            @Override
            public void onResponse(Call<GimnasioItem> call, Response<GimnasioItem> response) {
                if (response.isSuccessful()) {

                    Log.e("Update", "Nuevo gimnasio item" + response.body());

                } else {
                    // Maneja errores del servidor, por ejemplo, un error 404 o 500.
                    Log.e("Error", "En eliminar gimnasioItem " + response.code());
                }
                callback.onCompletion();
            }

            @Override
            public void onFailure(Call<GimnasioItem> call, Throwable t) {
                // Maneja errores de red o de conversión de datos
                Log.e("Error", "En eliminar gimnasioItem" + t.getMessage());
            }
        });
    }

    private void eliminarItem(_31_armar_mapa_admin.InfoCallback callback)
    {
        Log.d("Eliminar", "entro a eliminarItem ");
        UbicacionxItem eliminar = buscarUbicacionxItem(nuevaPosicion.get(0).getCoordenadaX(),nuevaPosicion.get(0).getCoordenadaY());
        Log.d("Eliminar", "voy a eliminar este item: "+eliminar);

        // eliminar ubicacionxItem
         eliminarUbicacionItem(eliminar,()->{
             // update gimnasioItem
             updateGimnasioItem(eliminar,-1,()->{
                 callback.onCompletion();
             });

         });
    }

    private void eliminarUbicacionItem(UbicacionxItem eliminar,_31_armar_mapa_admin.InfoCallback callback)
    {
        Log.d("Eliminar", "eliminarUbicacionItem: "+eliminar);
        Call<Void> call = ubicacionxItemApiService.deleteUbicacionxItem(Long.valueOf(eliminar.getId()));
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    List<UbicacionxItem> actualizar = ubicaciones.get(eliminar.getItemid());
                    ubicaciones.remove(eliminar.getItemid());
                    actualizar.remove(eliminar);
                    ubicaciones.put(eliminar.getItemid(),actualizar);
                    nuevaPosicion.remove(0);
                    Log.d("Eliminar", "todo good eliminado: ");

                } else {
                    // Maneja errores del servidor, por ejemplo, un error 404 o 500.
                    Log.e("Error", "en ubicacionItem: " + response.code());
                }
                callback.onCompletion();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // Maneja errores de red o de conversión de datos
                Log.e("Error", "En eliminar gimnasioItem " + t.getMessage());
            }
        });

    }

    private void mostrarEquipo(UbicacionxItem ubicacionxItem)
    {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout._34_ver_informacion_equipo_ubicado_en_mapa_admin);
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

    private UbicacionxItem nuevo(UbicacionxItem ub1) {
        Log.d("Mostrar", " estoy en nuevo añadidos: "+añadidos);
        Set<UbicacionxItem> ubicaciones = añadidos.keySet();
        Log.d("Mostrar", " estoy en nuevo ubicaciones: "+ubicaciones);
        UbicacionxItem ubicacionxItem = new UbicacionxItem();
        for (UbicacionxItem ub : ubicaciones) {
            if (ub1.getCoordenadaX() == ub.getCoordenadaX()) {
                if (ub1.getCoordenadaY() == ub.getCoordenadaY()) {
                    Log.d("Mostrar", "encontre que es nuevo retorno: "+ub);
                    return ub;
                }
            }
        }
        Log.d("Mostrar", " no encontre nada: "+ubicaciones);

        return ubicacionxItem;
    }

    private void ubicarEquipo(ImageView cuadrado, UbicacionxItem ubicacionxItem) {
        ubicacionxItem.setMapaid(mapas.get(piso).getId().intValue());
        ubicacionxItem.setGimnasioid(gimnasioId);
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout._33_ubicar_equipo_en_mapa_admin);
        dialog.getWindow().setBackgroundDrawable(null);

        ImageButton botonX = dialog.findViewById(R.id.btnX_33);

        botonX.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        RecyclerView recycler = dialog.findViewById(R.id.recyclerViewEquipo_33);
        recycler.setLayoutManager(new LinearLayoutManager(this));

        llenarEquipos(()->
        {
            Log.d("MOSTRAR", "Lista equipos: "+listaEquipos);
            Log.d("MOSTRAR", "Lista nombres: "+nombreTipos);

            MapaEquipoAdapter adapter = new MapaEquipoAdapter(gimnasioId,listaEquipos,nombreTipos,ubicacionxItem,dialog,_31_armar_mapa_admin.this,this::onDefinirButtonClick,cuadrado);
            recycler.setAdapter(adapter);
        });

        dialog.show();

    }

    public interface OnDefinirButtonClickListener {
        void onDefinirButtonClick(boolean isClicked,ImageView cuadrado,UbicacionxItem ubicacionxItem);
    }

    @Override
    public void onDefinirButtonClick(boolean isClicked,ImageView cuadrado, UbicacionxItem ubicacionxItem) {
        dialog.dismiss();
        Log.d("Mostrar", "iconos: "+iconos);
        Log.d("Mostrar", "iconos: "+añadidos);
        Log.d("Mostrar", "iconos: "+ubicacionxItem);

        int newDrawableId = getResources().getIdentifier(iconos.get(añadidos.get(nuevo(ubicacionxItem))), "drawable", getPackageName());
        cuadrado.setImageResource(newDrawableId);
        añadidos.remove(ubicacionxItem);
    }

    private void llenarEquipos(_31_armar_mapa_admin.InfoCallback callback)
    {
        Call<List<Equipo>> call = equipoApiService.getAll();
        call.enqueue(new Callback<List<Equipo>>() {
            @Override
            public void onResponse(Call<List<Equipo>> call, Response<List<Equipo>> response) {
                if (response.isSuccessful()) {
                    if(!response.body().isEmpty())
                        listaEquipos = response.body();
                } else {
                    // Maneja errores del servidor, por ejemplo, un error 404 o 500.
                    Log.e("Error", "Error en la respuesta: " + response.code());
                }
                callback.onCompletion();
            }

            @Override
            public void onFailure(Call<List<Equipo>> call, Throwable t) {
                // Maneja errores de red o de conversión de datos
                Log.e("Error", "Fallo en la petición: " + t.getMessage());
            }
        });
    }
}


