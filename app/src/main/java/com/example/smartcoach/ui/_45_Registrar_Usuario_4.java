package com.example.smartcoach.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.smartcoach.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import api.DateSerializer;
import api.User.UnidadMetricaApiService;
import api.User.ValorEvaluacionFisicaApiService;
import api.retro;
import model.Exercise.Rutina;
import model.User.UnidadMetrica;
import model.User.UsuarioCliente;
import model.User.Valor;
import model.User.ValorEvaluacionFisica;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class _45_Registrar_Usuario_4 extends AppCompatActivity {

    UnidadMetricaApiService unidadMetricaApiService;
    ValorEvaluacionFisicaApiService valorEvaluacionFisicaApiService;
    List<UnidadMetrica> listaUnidadMetrica = new ArrayList<>();
    List<ValorEvaluacionFisica> listaValorEvaluacionF = new ArrayList<>();
    private List<Valor> listaValores = new ArrayList<>();
    private int apiCallsCompleted = 0;
    ImageButton botonSiguiente;
    LinearLayout caja;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout._45_registrar_usuario_4);
        iniciarPeticiones();
        llenarListaVEF();
        llenarListaUM();

        botonSiguiente = findViewById(R.id.boton_siguiente_45);
        botonSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarInfo();
            }
        });
}

    private void iniciarPeticiones()
    {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Date.class, new DateSerializer())
                .create();

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = retro.getUnsafeOkHttpClient().newBuilder()
                .addInterceptor(logging)  // Agrega el interceptor al cliente OkHttp
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://10.0.2.2:8043/api/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        unidadMetricaApiService = retrofit.create(UnidadMetricaApiService.class);
        valorEvaluacionFisicaApiService = retrofit.create(ValorEvaluacionFisicaApiService.class);

    }

    private void llenarListaVEF() {

        Call<List<ValorEvaluacionFisica>> call = valorEvaluacionFisicaApiService.getAll();
        call.enqueue(new Callback<List<ValorEvaluacionFisica>>() {
            @Override
            public void onResponse(Call<List<ValorEvaluacionFisica>> call, Response<List<ValorEvaluacionFisica>> response) {
                if (response.isSuccessful()) {
                    listaValorEvaluacionF = response.body();
                } else {
                    Log.e("Error", "Error en la respuesta: " + response.code());
                }checkApiCallsCompletion();
            }


            @Override
            public void onFailure(Call<List<ValorEvaluacionFisica>> call, Throwable t) {
                Log.e("Error", "Fallo en la petición: " + t.getMessage());
            }
        });

    }

    private void llenarListaUM()
    {
        Call<List<UnidadMetrica>> call = unidadMetricaApiService.getAll();
        call.enqueue(new Callback<List<UnidadMetrica>>() {
            @Override
            public void onResponse(Call<List<UnidadMetrica>> call, Response<List<UnidadMetrica>> response) {
                if (response.isSuccessful()) {
                    listaUnidadMetrica = response.body();
                } else {
                    Log.e("Error", "Error en la respuesta: " + response.code());
                }
                checkApiCallsCompletion();

            }

            @Override
            public void onFailure(Call<List<UnidadMetrica>> call, Throwable t) {
                Log.e("Error", "Fallo en la petición: " + t.getMessage());
            }
        });
    }

    private void cargarDatos()
    {
        Integer idU;
        Integer tama = listaValorEvaluacionF.size();
        if(tama!=null)
        {
            for (int i = 0; i < listaValorEvaluacionF.size(); i++) {
                final ValorEvaluacionFisica pasar = listaValorEvaluacionF.get(i);
                caja = findViewById(getResources().getIdentifier("caja_composicion_corporal_" + (i + 1) + "_45", "id", getPackageName()));
                TextView nombreValorTextView = caja.findViewById(R.id.nombreValor_45);
                TextView unidadTextView = caja.findViewById(R.id.unidad_45);
                ImageButton botonInformacion = caja.findViewById(R.id.boton_informacion_45);
                nombreValorTextView.setText(listaValorEvaluacionF.get(i).getNombre());
                idU = listaValorEvaluacionF.get(i).getUnidadMetricaId();
                if(idU==null)
                {
                    unidadTextView.setText("");
                }
                else
                {
                    unidadTextView.setText(listaUnidadMetrica.get(idU-1).getMetrica());
                }

                botonInformacion.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mostrarDescripcionDialog(pasar);
                    }
                });
            }
        }

    }

    private void checkApiCallsCompletion() {
        apiCallsCompleted++;
        if (apiCallsCompleted == 2) { // Si ambas llamadas han completado
            cargarDatos();
        }
    }

    private void mostrarDescripcionDialog(ValorEvaluacionFisica valor) {
        // Crear un AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // Inflar el layout para el dialog
        View view = getLayoutInflater().inflate(R.layout._46_descripcion_composicion, null);

        TextView nombreTextView = view.findViewById(R.id.tituloComposicionCorporal);
        TextView descripcionTextView = view.findViewById(R.id.descripciónComposicionCorporal);
        nombreTextView.setText(valor.getNombre());
        descripcionTextView.setText(valor.getDescripcion());
        ImageButton botonCerrar = view.findViewById(R.id.btnSalirComposicionCorporal);

        builder.setView(view);

        // Mostrar el dialog
        AlertDialog dialog = builder.create();
        dialog.show();

        botonCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    private void guardarInfo()
    {

        String valorString;
        for (int i = 0; i < listaValorEvaluacionF.size(); i++) {
            caja = findViewById(getResources().getIdentifier("caja_composicion_corporal_" + (i + 1) + "_45", "id", getPackageName()));
            EditText textoIngreso = caja.findViewById(R.id.valor_45);
            Valor nuevo = new Valor();
            nuevo.setValorEvaluacionFisicaid(i+1);
            valorString = textoIngreso.getText().toString().replace(',', '.');
            nuevo.setValor(Float.parseFloat(valorString));
            listaValores.add(nuevo);
        }

        UsuarioCliente usuarioCliente = (UsuarioCliente) getIntent().getSerializableExtra("usuarioCliente");
        int musculoObjetivo = getIntent().getIntExtra("musculoObjetivo", 0); // 0 es un valor predeterminado en caso de que no se encuentre el extra.
        ArrayList<Rutina> listaRutinas = (ArrayList<Rutina>) getIntent().getSerializableExtra("listaRutinas");

        Intent intent = new Intent(_45_Registrar_Usuario_4.this, _59_registrar_usuario_5.class);

        intent.putExtra("usuarioCliente",usuarioCliente);
        intent.putExtra("musculoObjetivo",musculoObjetivo);
        intent.putExtra("listaRutinas", new ArrayList<>(listaRutinas));
        intent.putExtra("listaValoresComposicionCorporal",new ArrayList<>(listaValores));
        startActivity(intent);
    }
}


