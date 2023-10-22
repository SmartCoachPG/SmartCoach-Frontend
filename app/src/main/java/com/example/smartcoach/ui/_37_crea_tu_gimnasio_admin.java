package com.example.smartcoach.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.smartcoach.R;

import api.Admi.EquipoApiService;
import api.Admi.GimnasioApiService;
import api.Admi.GimnasioItemApiService;
import api.Admi.MapaApiService;
import api.Admi.UsuarioAdministradorApiService;
import api.SharedPreferencesUtil;
import api.retro;
import model.Admi.Gimnasio;
import model.Admi.Mapa;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class _37_crea_tu_gimnasio_admin extends BaseActivityAdmi{
    ImageView rectanguloTitulo_37;
    ImageButton btnRegresar_37;
    TextView tituloCrear_37, descripcionCrear_37, textSubaImagen_37;
    EditText setTextnombreGimnasio_37, setTextDireccionGimnasio_37, setTextBarrioGimnasio_37, setTextAnchoGimnasio_37, setTextAltoGimnasio_37, setTextPisosGimnasio_37;
    ImageButton imagenGimnasio_37, imageSubirImagen_37;
    Button btnCrearGimnasio_37;

    Long userId;
    String token;
    GimnasioApiService gimnasioApiService;
    MapaApiService mapaApiService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout._37_crear_tu_gimnasio_admin);
        getSupportActionBar().hide();
        userId = SharedPreferencesUtil.getUserId(_37_crea_tu_gimnasio_admin.this);
        token = SharedPreferencesUtil.getToken(_37_crea_tu_gimnasio_admin.this);

        iniciarPeticiones();

        rectanguloTitulo_37 = findViewById(R.id.rectanguloTitulo_37);
        btnRegresar_37 = findViewById(R.id.btnRegresar_37);
        tituloCrear_37 = findViewById(R.id.tituloCrear_37);
        descripcionCrear_37 = findViewById(R.id.descripcionCrear_37);
        setTextnombreGimnasio_37 = findViewById(R.id.setTextnombreGimnasio_37);
        imagenGimnasio_37 = findViewById(R.id.imagenGimnasio_37);
        textSubaImagen_37 = findViewById(R.id.textSubaImagen_37);
        imageSubirImagen_37 = findViewById(R.id.imageSubirImagen_37);
        setTextDireccionGimnasio_37 = findViewById(R.id.setTextDireccionGimnasio_37);
        setTextBarrioGimnasio_37 = findViewById(R.id.setTextBarrioGimnasio_37);
        setTextAnchoGimnasio_37 = findViewById(R.id.setTextAnchoGimnasio_37);
        setTextAltoGimnasio_37 = findViewById(R.id.setTextAltoGimnasio_37);
        setTextPisosGimnasio_37 = findViewById(R.id.setTextPisosGimnasio_37);
        btnCrearGimnasio_37 = findViewById(R.id.btnCrearGimnasio_37);

        btnRegresar_37.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btnRegresar_37.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crearMapa();
            }
        });

    }

    private void iniciarPeticiones()
    {

        OkHttpClient okHttpClient = retro.getUnsafeOkHttpClientWithToken(token);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://10.0.2.2:8043/api/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        gimnasioApiService = retrofit.create(GimnasioApiService.class);
        mapaApiService = retrofit.create(MapaApiService.class);
    }

    private void crearMapa()
    {
        Gimnasio nuevo = new Gimnasio();
        nuevo.setNombre(setTextnombreGimnasio_37.getText().toString());
        nuevo.setDireccion(setTextDireccionGimnasio_37.getText().toString());
        nuevo.setDireccion(setTextDireccionGimnasio_37.getText().toString());
        nuevo.setBarrio(setTextBarrioGimnasio_37.getText().toString());
        nuevo.setPisos(1);

        Mapa mapa = new Mapa();
        mapa.setAlto(setTextAltoGimnasio_37.getId());
        mapa.setAncho(setTextAnchoGimnasio_37.getId());
    }
}
