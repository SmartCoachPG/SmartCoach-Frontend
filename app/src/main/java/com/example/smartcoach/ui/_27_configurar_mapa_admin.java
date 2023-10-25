package com.example.smartcoach.ui;

import static android.view.View.GONE;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartcoach.R;

import api.Admi.GimnasioApiService;
import api.Admi.MapaApiService;
import api.Admi.UsuarioAdministradorApiService;
import api.SharedPreferencesUtil;
import api.retro;
import model.Admi.UsuarioAdministrador;
import model.User.UsuarioCliente;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class _27_configurar_mapa_admin extends BaseActivityAdmi {

    TextView descripcion_27;
    Button btnVer_27, btnCrearMapa_27, btnModificarInfoMapa_27, btnModificarEquiposElementos_27, btnEliminarMapa_27;

    Long userId;
    String token;

    UsuarioAdministradorApiService usuarioAdministradorApiService;
    GimnasioApiService gimnasioApiService;
    UsuarioAdministrador usuarioAdministrador = new UsuarioAdministrador();
    Dialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout._27_configurar_mapa_admin);
        getSupportActionBar().hide();

        userId = SharedPreferencesUtil.getUserId(_27_configurar_mapa_admin.this);
        token = SharedPreferencesUtil.getToken(_27_configurar_mapa_admin.this);
        iniciarPeticiones();
        descripcion_27 = findViewById(R.id.descripcion_27);
        btnVer_27 = findViewById(R.id.btnVer_27);
        btnCrearMapa_27 = findViewById(R.id.btnCrearMapa_27);
        btnModificarInfoMapa_27 = findViewById(R.id.btnModificarInfoMapa_27);
        btnModificarEquiposElementos_27 = findViewById(R.id.btnModificarEquiposElementos_27);
        btnEliminarMapa_27 = findViewById(R.id.btnEliminarMapa_27);

        cargarAdmi(new InfoCallback() {
            @Override
            public void onCompletion() {
                Log.d("27", "usuario gim?: "+usuarioAdministrador.getGimnasioId());
                if(usuarioAdministrador.getGimnasioId()==null||usuarioAdministrador.getGimnasioId()==0)
                {
                    Log.d("27", "usuario gim?: "+usuarioAdministrador.getGimnasioId());
                    btnCrearMapa_27.setVisibility(View.VISIBLE);
                    btnVer_27.setVisibility(View.GONE);
                    btnModificarInfoMapa_27.setVisibility(View.GONE);
                    btnModificarEquiposElementos_27.setVisibility(View.GONE);
                    btnEliminarMapa_27.setVisibility(View.GONE);
                }
                else
                {
                    btnCrearMapa_27.setVisibility(View.GONE);
                    btnVer_27.setVisibility(View.VISIBLE);
                    btnModificarInfoMapa_27.setVisibility(View.VISIBLE);
                    btnModificarEquiposElementos_27.setVisibility(View.VISIBLE);
                    btnEliminarMapa_27.setVisibility(View.VISIBLE);
                }


            }
        });

        btnVer_27.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(_27_configurar_mapa_admin.this, _35_ver_mapa_admin.class);
                startActivity(intent);
            }
        });

        btnCrearMapa_27.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(_27_configurar_mapa_admin.this, _37_crea_tu_gimnasio_admin.class);
                startActivity(intent);
            }
        });

        btnModificarInfoMapa_27.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(_27_configurar_mapa_admin.this, _36_modificar_informacion_gimnasio.class);
                startActivity(intent);
            }
        });

        btnModificarEquiposElementos_27.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(_27_configurar_mapa_admin.this, _31_armar_mapa_admin.class);
                startActivity(intent);
            }
        });

        btnEliminarMapa_27.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });


    }

    private void showDialog() {
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout._38_mensaje_confirmacion_eliminacion_gimnasio); // Usa el nombre de tu archivo XML
        dialog.getWindow().setBackgroundDrawable(null);

        Button botonConfirmar = dialog.findViewById(R.id.botonConfirmar_38);
        botonConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Acciones a realizar cuando se presiona "Confirmar"
                eliminarCuenta();
            }
        });

        Button botonCancelar = dialog.findViewById(R.id.botonCancelar_38);
        botonCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Acciones a realizar cuando se presiona "Cancelar"
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void eliminarCuenta()
    {
        Call<Void> call = gimnasioApiService.deleteById(usuarioAdministrador.getGimnasioId().longValue());

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(_27_configurar_mapa_admin.this, "Gimnasio eliminada", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    startActivity(new Intent(_27_configurar_mapa_admin.this, _27_configurar_mapa_admin.class));

                } else {
                    Toast.makeText(_27_configurar_mapa_admin.this, "Error al eliminar la cuenta", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(_27_configurar_mapa_admin.this, "Error de conexión", Toast.LENGTH_SHORT).show();
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

        usuarioAdministradorApiService = retrofit.create(UsuarioAdministradorApiService.class);
        gimnasioApiService = retrofit.create(GimnasioApiService.class);
    }

    interface InfoCallback {
        void onCompletion();
    }

    private void cargarAdmi(_27_configurar_mapa_admin.InfoCallback callback)
    {
        Call<UsuarioAdministrador> call = usuarioAdministradorApiService.getUsuarioById(userId);
        call.enqueue(new Callback<UsuarioAdministrador>() {
            @Override
            public void onResponse(Call <UsuarioAdministrador> call, Response<UsuarioAdministrador> response) {
                if (response.isSuccessful()) {
                    usuarioAdministrador = response.body();
                    callback.onCompletion();
                }
            }
            @Override
            public void onFailure(Call <UsuarioAdministrador> call, Throwable t) {
                Toast.makeText(_27_configurar_mapa_admin.this, "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
