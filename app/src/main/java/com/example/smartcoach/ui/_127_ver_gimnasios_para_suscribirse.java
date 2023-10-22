package com.example.smartcoach.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

        import android.widget.EditText;
        import android.widget.ImageButton;
        import android.widget.ScrollView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartcoach.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import api.Admi.EquipoApiService;
import api.Admi.GimnasioApiService;
import api.Admi.GimnasioItemApiService;
import api.Admi.UsuarioAdministradorApiService;
import api.DateSerializer;
import api.SharedPreferencesUtil;
import api.TimeDeserializer;
import api.TimeSerializer;
import api.User.UsuarioClienteApiService;
import api.retro;
import model.Admi.Equipo;
import model.Admi.Gimnasio;
import model.Admi.GimnasioItem;
import model.Admi.UsuarioAdministrador;
import model.User.UsuarioCliente;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class _127_ver_gimnasios_para_suscribirse extends BaseActivityCliente implements CajaGimnasioAdapter.OnSuscribirClickListener{
    TextView tituloSuscríbete_127;
    EditText editTextGym_127;
    ImageButton btnBusquedaGym_127;
    Button btnSuscribir;

    RecyclerView recyclerViewGymSuscribirse_127;
    GimnasioApiService gimnasioApiService;
    List<Gimnasio> gimnasios;
    CajaGimnasioAdapter adapter;

    Long userId;
    String token;

    UsuarioClienteApiService usuarioClienteApiService;
    UsuarioCliente temp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout._127_ver_gimnasios_para_suscribirse);
        getSupportActionBar().hide();
        userId = SharedPreferencesUtil.getUserId(_127_ver_gimnasios_para_suscribirse.this);
        token = SharedPreferencesUtil.getToken(_127_ver_gimnasios_para_suscribirse.this);

        iniciarPeticiones();

        tituloSuscríbete_127 = findViewById(R.id.tituloSuscríbete_127);
        editTextGym_127 = findViewById(R.id.editTextGym_127);
        btnBusquedaGym_127 = findViewById(R.id.btnBusquedaGym_127);
        recyclerViewGymSuscribirse_127 = findViewById(R.id.recyclerViewGymSuscribirse_127);

        cargarInfo();


    }

    private void iniciarPeticiones()
    {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Date.class, new DateSerializer())
                .create();
        OkHttpClient okHttpClient = retro.getUnsafeOkHttpClientWithToken(token);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://10.0.2.2:8043/api/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        gimnasioApiService = retrofit.create(GimnasioApiService.class);
        usuarioClienteApiService = retrofit.create(UsuarioClienteApiService.class);
    }

    private void cargarInfo()
    {
        cargarGimnasios(new _127_ver_gimnasios_para_suscribirse.InfoCallback() {
            @Override
            public void onCompletion() {
                mostrarGimnasios();
                actualizarLista();
            }
        });
    }

    private void cargarGimnasios(_127_ver_gimnasios_para_suscribirse.InfoCallback callback)
    {
        Call<List<Gimnasio>> call = gimnasioApiService.getAll();
        call.enqueue(new Callback<List<Gimnasio>>() {
            @Override
            public void onResponse(Call<List<Gimnasio>> call, Response<List<Gimnasio>> response) {
                if (response.isSuccessful()) {
                    gimnasios = response.body();
                    callback.onCompletion();
                } else {
                }
            }

            @Override
            public void onFailure(Call<List<Gimnasio>> call, Throwable t) {
                Toast.makeText(_127_ver_gimnasios_para_suscribirse.this, "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void mostrarGimnasios()
    {
        adapter = new CajaGimnasioAdapter(gimnasios,_127_ver_gimnasios_para_suscribirse.this,this);
        RecyclerView recyclerView = findViewById(R.id.recyclerViewGymSuscribirse_127);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    public void actualizarLista()
    {
        EditText editTextBuscaEquipo = findViewById(R.id.editTextGym_127);
        editTextBuscaEquipo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // Filtra tu lista aquí
                filter(editable.toString());
            }
        });
    }

    public void filter(String text) {
        List<Gimnasio> filteredList = new ArrayList<>();

        if (text.isEmpty()) {
            filteredList.addAll(gimnasios);
        } else {
            for (Gimnasio gimnasio : gimnasios) {
                if (gimnasio.getNombre().toLowerCase().contains(text.toLowerCase())) {
                    filteredList.add(gimnasio);
                }
            }
        }

        adapter.updateList(filteredList);
    }


    interface InfoCallback {
        void onCompletion();
    }

    public void onSuscribirClick(int gimnasioId)
    {
        Log.d("SUSCRIBIR", "Me dieron click: ");

        suscribirGimnasio(new InfoCallback() {
        @Override
        public void onCompletion () {
            suscribirGimnasio2(new InfoCallback() {
                @Override
                public void onCompletion () {
                    Toast.makeText(_127_ver_gimnasios_para_suscribirse.this, "Gimnasio Suscrito", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(_127_ver_gimnasios_para_suscribirse.this, _120_ver_informacion_gimnasio.class));

                }
            });

        }
        });
    }


    private void suscribirGimnasio(_127_ver_gimnasios_para_suscribirse.InfoCallback callback)
    {
        Log.d("SUSCRIBIR", "entreo a suscribir1: ");
        Call <UsuarioCliente> call = usuarioClienteApiService.getUsuarioById(userId);
        call.enqueue(new Callback <UsuarioCliente>() {
            @Override
            public void onResponse(Call <UsuarioCliente> call, Response <UsuarioCliente> response) {
                if (response.isSuccessful()) {
                    temp = response.body();
                    temp.setGimnasioid(SharedPreferencesUtil.getGimnasio(_127_ver_gimnasios_para_suscribirse.this));
                    Log.d("SUSCRIBIR", "entreo a suscribir1, CLIENTE: "+temp);
                    SharedPreferencesUtil.deleteGimnasio(_127_ver_gimnasios_para_suscribirse.this);
                    callback.onCompletion();
                }
            }
            @Override
            public void onFailure(Call <UsuarioCliente> call, Throwable t) {
                Toast.makeText(_127_ver_gimnasios_para_suscribirse.this, "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void suscribirGimnasio2(_127_ver_gimnasios_para_suscribirse.InfoCallback callback)
    {
        Log.d("SUSCRIBIR", "entreo a suscribir2: ");
        Call <UsuarioCliente> call = usuarioClienteApiService.updateUsuarioCliente(Long.valueOf(userId),temp);
        Log.d("SUSCRIBIR", "entreo a suscribir2: cliente "+temp);

        call.enqueue(new Callback <UsuarioCliente>() {
            @Override
            public void onResponse(Call <UsuarioCliente> call, Response <UsuarioCliente> response) {
                if (response.isSuccessful()) {
                    Log.d("SUSCRIBIR", "entreo a update: ");
                    callback.onCompletion();
                }
            }

            @Override
            public void onFailure(Call <UsuarioCliente> call, Throwable t) {
                Toast.makeText(_127_ver_gimnasios_para_suscribirse.this, "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
