package com.example.smartcoach.ui;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
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

public class _31_armar_mapa_admin extends AppCompatActivity {

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

    Long userId = SharedPreferencesUtil.getUserId(_31_armar_mapa_admin.this);
    String token = SharedPreferencesUtil.getToken(_31_armar_mapa_admin.this);

    int gimnasioId=0;
    Map<Integer, Mapa> mapas = new HashMap<>();
    List<GimnasioItem> listaItems = new ArrayList<>();
    Map<Integer,Integer> tipoEquipoItem = new HashMap<>();
    Map<Integer,String> iconos= new HashMap<>();
    Map<Integer,String> iconosName = new HashMap<>();
    Map<Integer,String> iconosNa = new HashMap<>();
    Map<Integer,List<UbicacionxItem>> ubicaciones= new HashMap<>();

    Map<UbicacionxItem,Integer> añadidos = new HashMap<>();
    int piso = 1;
    Boolean equipo=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout._31_armar_mapa_admin);

        gridLayout = findViewById(R.id.gridLayout_31);
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
                equipo=true;
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
                equipo=false;
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

    public boolean isViewOverlapping(View firstView, View secondView) {
        int[] firstPosition = new int[2];
        int[] secondPosition = new int[2];

        firstView.getLocationOnScreen(firstPosition);
        secondView.getLocationOnScreen(secondPosition);

        return firstPosition[0] < secondPosition[0] + secondView.getWidth() &&
                firstPosition[0] + firstView.getWidth() > secondPosition[0] &&
                firstPosition[1] < secondPosition[1] + secondView.getHeight() &&
                firstPosition[1] + firstView.getHeight() > secondPosition[1];
    }

    private void cargarListas()
    {
        RecyclerView recyclerView = findViewById(R.id.recyclerView_31);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        EquipoAdapter adapter = new EquipoAdapter(this, iconosName,iconos,equipo);
        recyclerView.setAdapter(adapter);
    }
    private void cargarIconos()
    {
        //Pesos
        iconos.put(1,"icon_mancuerna_fondo_ne");
        iconosName.put(1,"Pesos");
        iconosNa.put(1,"icon_mancuerna_fondo_na");
        //Maquinas de peso
        iconos.put(2,"icon_maquina_de_peso");
        iconosName.put(2,"Maquinas de peso");
        iconosNa.put(2,"icon_maquina_de_peso_na");

        //Maquinas de cardio
        iconos.put(3,"icon_corazon_ne");
        iconosName.put(3,"Maquinas de cardio");
        iconosNa.put(3,"icon_corazon_na");

        //complementos
        iconos.put(4,"icon_complementos_ne");
        iconosName.put(4,"Complementos");
        iconosNa.put(4,"icon_complementos_na");

        // item. caminos
        iconos.put(5,"cuadrado_gr");
        iconosName.put(5,"Caminos");
        // item. puerta
        iconos.put(6,"rectangulo_alargado_redondeado_n");
        iconosName.put(6,"Puerta");
        // item. salud
        iconos.put(7,"icon_enfermera_ne");
        iconosName.put(7,"Salud");
        // item. baño
        iconos.put(8,"icon_papel_ne");
        iconosName.put(8,"Baño");
        // item. entrada
        iconos.put(9,"icon_puerta_ne");
        iconosName.put(9,"Entrada");
        // item. cafeteria
        iconos.put(10,"icon_basura_ne_tansp");
        iconosName.put(10,"Cafetería");
        // item. lockers
        iconos.put(11,"icon_locker_ne");
        iconosName.put(11,"Lockers");
        // item. oficina
        iconos.put(12,"icon_silla_ne");
        iconosName.put(12,"Oficina");
        // item. muro
        iconos.put(13,"icon_linea_gr");
        iconosName.put(13,"Muro");
        // item. escaleras
        iconos.put(14,"icon_escaleras_ne");
        iconosName.put(14,"Escaleras");

        cargarListas();

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

    private void tenerGym(_31_armar_mapa_admin.InfoCallback callback)
    {
        Call<UsuarioAdministrador> call = usuarioAdministradorApiService.getUsuarioById(userId);
        call.enqueue(new Callback<UsuarioAdministrador>() {
            @Override
            public void onResponse(Call<UsuarioAdministrador> call, Response<UsuarioAdministrador> response) {
                if (response.isSuccessful()) {
                    UsuarioAdministrador usuario = response.body();
                    if(usuario.getGimnasioId()!=null)
                    {
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

    private void llenarMapas(_31_armar_mapa_admin.InfoCallback callback)
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

    private void llenarGimnasioItem(_31_armar_mapa_admin.InfoCallback callback)
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

    private void llenarTipoEquipo(_31_armar_mapa_admin.InfoCallback callback)
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
    private void llenarUbicacionxItem(GimnasioItem gi,_31_armar_mapa_admin.InfoCallback callback) {

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
        Log.d("FIN", "idGimnasio: "+gimnasioId);
        Log.d("FIN", "mapas: "+mapas);
        Log.d("FIN", "listaItems: "+listaItems);
        Log.d("FIN", "tipo equipo: "+tipoEquipoItem);
        Log.d("FIN", "iconos: "+iconos);
        Log.d("FIN", "ubicaciones: "+ubicaciones);
        cargarCuadrados(mapas.get(piso).getAncho(),mapas.get(piso).getAlto());
        cargarImagenes();

    }

    private void cargarCuadrados(int ancho,int alto)
    {
        int tamañoCasilla = 32;
        final float scale = getResources().getDisplayMetrics().density;
        int tamañoCasillaPixels = (int) (tamañoCasilla * scale + 0.5f);

        gridLayout.setColumnCount(ancho);
        gridLayout.setRowCount(alto);

        for (int i = 0; i < alto; i++) {
            for (int j = 0; j < ancho; j++) {

                final int row = i; // Variable final para almacenar el valor de i
                final int column = j;

                ImageView cuadrado = new ImageView(this);
                GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams(
                        GridLayout.spec(i), GridLayout.spec(j));
                layoutParams.width = tamañoCasillaPixels;
                layoutParams.height = tamañoCasillaPixels;
                cuadrado.setLayoutParams(layoutParams);
                cuadrado.setBackgroundResource(R.drawable.fondo_mapa);

                cuadrado.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
                            v.startDrag(null, shadowBuilder, v, 0);
                            UbicacionxItem ubicacionxItem = new UbicacionxItem();
                            ubicacionxItem.setCoordenadaX(row);
                            ubicacionxItem.setCoordenadaY(column);
                            Log.d("MAPAAA", "ubicacionXitem despues: "+ubicacionxItem);
                            if(nuevo(ubicacionxItem).getCoordenadaX()!=0)
                            {
                                Toast.makeText(_31_armar_mapa_admin.this, "Vista clickeada!", Toast.LENGTH_SHORT).show();
                                ubicarEquipo(cuadrado,nuevo(ubicacionxItem));
                            }
                            return true;
                        } else {
                            return false;
                        }
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

                            if(isPointWithinView(x,y,basura))
                            {
                                draggedView.setVisibility(View.INVISIBLE);
                            }
                            else {
                                if (isPointWithinView(x, y, child)) {

                                    ImageView targetView = child;

                                    Drawable draggedDrawable = ((ImageView) draggedView).getDrawable();
                                    ((ImageView) draggedView).setImageDrawable(targetView.getDrawable());

                                    if(draggedView.getTag()!=null)
                                    {
                                        int position = (int) draggedView.getTag();
                                        int newDrawableId=getResources().getIdentifier(iconosNa.get(position), "drawable", getPackageName());
                                        targetView.setImageResource(newDrawableId);
                                        UbicacionxItem ubicacionxItem = new UbicacionxItem();
                                        int cellHeight = gridLayout.getHeight() / gridLayout.getRowCount();
                                        int cellWidth = gridLayout.getWidth() / gridLayout.getColumnCount();

                                        // Calcular la fila y la columna basándose en las coordenadas x y y
                                        int droppedRow = y / cellHeight;
                                        int droppedColumn = x / cellWidth;

                                        ubicacionxItem.setCoordenadaX(droppedRow);
                                        ubicacionxItem.setCoordenadaY(droppedColumn);
                                        añadidos.put(ubicacionxItem,position);
                                    }
                                    else
                                    {
                                        targetView.setImageDrawable(draggedDrawable);
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

    }

    private void cargarImagenes() {
        for (GimnasioItem gi : listaItems) {
            List<UbicacionxItem> ubi = ubicaciones.get(gi.getItemid());
            if (!ubi.isEmpty() && ubi != null) {
                for (UbicacionxItem uxi : ubi) {
                    if (uxi.getMapaid() == mapas.get(piso).getId()) {
                        int row = uxi.getCoordenadaY();
                        int column = uxi.getCoordenadaX();

                        // Encuentra la ImageView en la posición específica
                        for (int i = 0; i < gridLayout.getChildCount(); i++) {
                            View view = gridLayout.getChildAt(i);
                            GridLayout.LayoutParams layoutParams = (GridLayout.LayoutParams) view.getLayoutParams();

                            if (GridLayout.spec(row).equals(layoutParams.rowSpec) &&
                                    GridLayout.spec(column).equals(layoutParams.columnSpec) &&
                                    view instanceof ImageView) {

                                ImageView imageView = (ImageView) view;
                                imageView.setPadding(2,2,2,2);
                                int tipo = tipoEquipoItem.get(uxi.getItemid());
                                int resID = getResources().getIdentifier(iconos.get(tipo), "drawable", getPackageName());
                                imageView.setImageResource(resID);
                                break;
                            }
                        }
                    }
                }
            }
        }
    }


    private UbicacionxItem nuevo(UbicacionxItem ub1)
    {
        Set<UbicacionxItem> ubicaciones = añadidos.keySet();
        UbicacionxItem ubicacionxItem= new UbicacionxItem();
        for(UbicacionxItem ub:ubicaciones)
        {
            if(ub1.getCoordenadaX()== ub.getCoordenadaX())
            {
                if(ub1.getCoordenadaY()==ub.getCoordenadaY())
                {
                    return ub;
                }
            }
        }
        return ubicacionxItem;
    }

 private void ubicarEquipo(ImageView cuadrado,UbicacionxItem ubicacionxItem)
 {
     int newDrawableId=getResources().getIdentifier(iconos.get(añadidos.get(nuevo(ubicacionxItem))), "drawable", getPackageName());
     cuadrado.setImageResource(newDrawableId);
 }
}

