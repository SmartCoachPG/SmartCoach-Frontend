package com.example.smartcoach.ui;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartcoach.R;

import java.util.ArrayList;
import java.util.List;

import api.Admi.EquipoApiService;
import api.Admi.GimnasioItemApiService;
import api.Admi.UsuarioAdministradorApiService;
import api.SharedPreferencesUtil;
import api.retro;
import model.Admi.Equipo;
import model.Admi.GimnasioItem;
import model.Admi.UsuarioAdministrador;
import model.Exercise.CajaRutina;
import model.Exercise.Ejercicio;
import model.Exercise.Rutina;
import model.User.ProgresoxEjercicio;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class _16_ver_equipo_admin_todos extends BaseActivityAdmi {
    TextView tituloBienvenida, setTextAdminName, descripcion, nombreEquipo, nombreReferenciaEquipo, desc_equipo;
    View view2;
    EditText editTextBuscaEquipo;
    ImageButton btnBuscar, btnEliminarEquipo;
    Button btnCrearEquipo, btnEncuentraEquipo, btnTodos, btnMios,btnAgregarEquipo;
    ImageView rectanguloFondoEquipo, imageEquipo;
    RecyclerView recyclerView;
    View otrosMenu;

    RecyclerView recyclerView_16;

    List<Equipo> listaEquipo = new ArrayList<>();
    List<GimnasioItem> listaEquipoGym = new ArrayList<>();
    UsuarioAdministrador usuarioAdministrador = new UsuarioAdministrador();
    CajaEquipoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout._16_ver_equipo_admin_todos);
        getSupportActionBar().hide();
        userId = SharedPreferencesUtil.getUserId(_16_ver_equipo_admin_todos.this);
        token = SharedPreferencesUtil.getToken(_16_ver_equipo_admin_todos.this);
        iniciarPeticiones();

        tituloBienvenida = findViewById(R.id.tituloBienvenida_16);
        setTextAdminName = findViewById(R.id.setTextAdminName);
        descripcion = findViewById(R.id.descripcion_16);
        view2 = findViewById(R.id.view_16);
        btnBuscar = findViewById(R.id.btnBuscar);
        btnCrearEquipo = findViewById(R.id.btnCrearEquipo_16);
        btnEncuentraEquipo = findViewById(R.id.btnEncuentraEquipo_16);
        rectanguloFondoEquipo = findViewById(R.id.rectanguloFondoEquipo_16);
        btnTodos = findViewById(R.id.btnTodos_16);
        btnMios = findViewById(R.id.btnMios_16);
        otrosMenu = findViewById(R.id._otros_menu_admi);
        recyclerView_16 = findViewById(R.id.recyclerView_16);
        imageEquipo = findViewById(R.id.imageEquipo);
        nombreEquipo = findViewById(R.id.nombreEquipo);
        nombreReferenciaEquipo = findViewById(R.id.nombreReferenciaEquipo);
        desc_equipo = findViewById(R.id.desc_equipo);
        imageEquipo = findViewById(R.id.imageEquipo);
        nombreEquipo = findViewById(R.id.nombreEquipo);
        nombreReferenciaEquipo = findViewById(R.id.nombreReferenciaEquipo);
        desc_equipo = findViewById(R.id.desc_equipo);
        btnEliminarEquipo=findViewById(R.id.btnEliminar);

        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.caja_agregar_equipo_todos, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();
        btnAgregarEquipo = dialogView.findViewById(R.id.btnAgregar);

        btnAgregarEquipo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                View dialogView = getLayoutInflater().inflate(R.layout.caja_eliminar_equipo_todos, null);
                Button btnEliminar = dialogView.findViewById(R.id.btnEliminar);

                btnEliminar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
                alertDialog.show();
            }
        });

        btnCrearEquipo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(_16_ver_equipo_admin_todos.this, _19_crear_equipo_admin.class);
                startActivity(intent);
            }
        });

        cargarInfo();


    }

    public void actualizarLista()
    {
        editTextBuscaEquipo = findViewById(R.id.editTextBuscaEquipo);
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
        List<Equipo> filteredList = new ArrayList<>();

        if (text.isEmpty()) {
            filteredList.addAll(listaEquipo);
        } else {
            for (Equipo equipo : listaEquipo) {
                if (equipo.getNombre().toLowerCase().contains(text.toLowerCase())) {
                    filteredList.add(equipo);
                }
            }
        }

        adapter.updateList(filteredList);
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
        equipoApiService = retrofit.create(EquipoApiService.class);
        gimnasioItemApiService = retrofit.create(GimnasioItemApiService.class);
    }

    private void cargarInfo()
    {
        cargarPerfil(new InfoCallback() {
            @Override
            public void onCompletion() {
                cargarEquipos(new InfoCallback() {
                    @Override
                    public void onCompletion () {

                        cargarEquiposGym(new InfoCallback() {
                            @Override
                            public void onCompletion() {
                                mostrarEquipos();
                                actualizarLista();
                            }
                        });


                    } });
            }
        });


    }

    interface InfoCallback {
        void onCompletion();
    }

    private void cargarPerfil(_16_ver_equipo_admin_todos.InfoCallback callback)
    {
        Call<UsuarioAdministrador> call = usuarioAdministradorApiService.getUsuarioById(userId);
        call.enqueue(new Callback<UsuarioAdministrador>() {
            @Override
            public void onResponse(Call<UsuarioAdministrador> call, Response<UsuarioAdministrador> response) {
                if (response.isSuccessful()) {
                    usuarioAdministrador = response.body();
                    setTextAdminName.setText(usuarioAdministrador.getNombre());
                    callback.onCompletion();
                } else {
                }
            }

            @Override
            public void onFailure(Call<UsuarioAdministrador> call, Throwable t) {
                Toast.makeText(_16_ver_equipo_admin_todos.this, "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void cargarEquipos(_16_ver_equipo_admin_todos.InfoCallback callback)
    {
        Call <List<Equipo>> call = equipoApiService.getAll();
        call.enqueue(new Callback <List<Equipo>>() {
            @Override
            public void onResponse(Call <List<Equipo>> call, Response <List<Equipo>> response) {
                if (response.isSuccessful()) {
                    listaEquipo = response.body();
                    callback.onCompletion();
                } else {
                }
            }

            @Override
            public void onFailure(Call <List<Equipo>> call, Throwable t) {
                Toast.makeText(_16_ver_equipo_admin_todos.this, "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void cargarEquiposGym(_16_ver_equipo_admin_todos.InfoCallback callback)
    {
        Call <List<GimnasioItem>> call = gimnasioItemApiService.getGimnasioItemsByGimnasioid(usuarioAdministrador.getGimnasioId());
        call.enqueue(new Callback <List<GimnasioItem>>() {
            @Override
            public void onResponse(Call <List<GimnasioItem>> call, Response <List<GimnasioItem>> response) {
                if (response.isSuccessful()) {
                    listaEquipoGym = response.body();
                    callback.onCompletion();
                } else {
                }
            }

            @Override
            public void onFailure(Call <List<GimnasioItem>> call, Throwable t) {
                Toast.makeText(_16_ver_equipo_admin_todos.this, "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void mostrarEquipos()
    {
        for(Equipo equipo: listaEquipo)
        {
            if(equipo.getNombre().contains("BodyWeight"))
            {
                listaEquipo.remove(equipo);
            }
        }
        adapter = new CajaEquipoAdapter(listaEquipo,listaEquipoGym);
        RecyclerView recyclerView = findViewById(R.id.recyclerView_16);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
}
