package com.example.smartcoach.ui;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.smartcoach.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import api.DateSerializer;
import api.Exercise.EjercicioProgresoxEjercicioApiService;
import api.Exercise.ImagenEjercicioApiService;
import api.Exercise.RutinaApiService;
import api.Exercise.RutinaEjercicioApiService;
import api.SharedPreferencesUtil;
import api.TimeDeserializer;
import api.TimeSerializer;
import api.User.ProgresoxEjercicioService;
import api.User.UsuarioClienteApiService;
import api.retro;
import model.Exercise.CajaRutina;
import model.Exercise.EjercicioProgresoxEjercicio;
import model.Exercise.Rutina;
import model.Exercise.RutinaEjercicio;
import model.User.ProgresoxEjercicio;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class CajaRutinaAdapterM extends RecyclerView.Adapter<CajaRutinaAdapterM.CajaRutinaMViewHolder> {

    private List<CajaRutina> cajaRutinas;
    private List<CajaRutina> opciones;
    private Button btnGuardar;
    private Context context;

    int idRutina;
    private List<Integer> anadidos = new ArrayList<>();
    private List<Integer> eliminados = new ArrayList<>();
    int contador=0;

    Long userId;
    String token;

    RutinaEjercicioApiService rutinaEjercicioApiService;
    ProgresoxEjercicioService progresoxEjercicioService;
    EjercicioProgresoxEjercicioApiService ejercicioProgresoxEjercicioApiService;

    public CajaRutinaAdapterM(List<CajaRutina> cajaRutinas,List<CajaRutina> opciones,Context context,Button btnGuardar,int idRutina) {
        this.cajaRutinas = cajaRutinas;
        this.opciones = opciones;
        this.context = context;
        this.btnGuardar = btnGuardar;
        this.idRutina = idRutina;
    }

    @Override
    public CajaRutinaMViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.caja_ejercicios_modificar, parent, false);
        return new CajaRutinaMViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CajaRutinaMViewHolder holder, int position) {
        CajaRutina cajaRutina = cajaRutinas.get(position);
        if(cajaRutina!=null)
        {
            configurarVista(holder,cajaRutina);
            holder.btnEliminar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showDialogConfirmarE(cajaRutina,holder,position);
                }

            });
        }

    }

    private void showDialogConfirmarE(CajaRutina cajaRutina,CajaRutinaMViewHolder holder,int position) {
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout._116_mensaje_confirmacion_eliminacion_ejercicio_rutina);
        dialog.getWindow().setBackgroundDrawable(null);

        TextView nombreE = dialog.findViewById(R.id.textoNombreEjercicio);
        nombreE.setText(cajaRutina.getEjercicio().getNombre());
        Button botonConfirmar = dialog.findViewById(R.id.botonConfirmar);
        botonConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Acciones a realizar cuando se presiona "Confirmar"
                dialog.dismiss();
                if(contador+1<opciones.size())
                {
                    handleElimination(cajaRutina);
                    CajaRutina nuevaCajaRutina = opciones.get(contador);
                    cajaRutinas.set(position, nuevaCajaRutina);
                    configurarVista(holder, nuevaCajaRutina);
                    notifyItemChanged(position);
                    contador++;
                }
                else {
                    showDialogNoOpciones();
                }
            }

        });

        Button botonCancelar = dialog.findViewById(R.id.botonCancelar);
        botonCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void showDialogNoOpciones() {
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout._117_mensaje_error_eliminar_ejercicio);
        dialog.getWindow().setBackgroundDrawable(null);
        Button botonConfirmar = dialog.findViewById(R.id.btnSeguirCamposVacios);
        botonConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }

        });

        dialog.show();
    }

    private void configurarVista(CajaRutinaMViewHolder holder, CajaRutina cajaRutina){
        holder.nombreEjercicio.setText(cajaRutina.getEjercicio().getNombre());
        holder.valorSerie.setText(cajaRutina.getProgresoxEjercicio().getSerie().toString());
        holder.valorRepeticiones.setText(cajaRutina.getProgresoxEjercicio().getRepeticiones().toString());
        String imageString = cajaRutina.getImagenEjercicio().getImagen();
        if(imageString!=null)
        {
            byte[] imageBytes = Base64.decode(imageString, Base64.DEFAULT);
            Bitmap decodedBitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            holder.imagenEjercicio.setImageBitmap(decodedBitmap);
        }
    }

    private void handleElimination(CajaRutina cajaRutina){
        Log.d("Eliminando", "quiere eliminar ejercicio: "+ cajaRutina.getEjercicio().getId());
        Log.d("Elimando", "ejercicio: "+cajaRutinas);
        Log.d("Elimando", "opciones: "+opciones);
        eliminados.add(cajaRutina.getEjercicio().getId().intValue());
        anadidos.add(opciones.get(contador).getEjercicio().getId().intValue());
    }

    private void iniciarPeticiones() {

        userId = SharedPreferencesUtil.getUserId(context);
        token = SharedPreferencesUtil.getToken(context);
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Date.class, new DateSerializer())
                .registerTypeAdapter(Time.class, new TimeSerializer())
                .registerTypeAdapter(Time.class, new TimeDeserializer())
                .create();

        OkHttpClient okHttpClient = retro.getUnsafeOkHttpClientWithToken(token)
                .newBuilder()
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://10.0.2.2:8043/api/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        progresoxEjercicioService = retrofit.create(ProgresoxEjercicioService.class);
        rutinaEjercicioApiService = retrofit.create(RutinaEjercicioApiService.class);
        ejercicioProgresoxEjercicioApiService = retrofit.create(EjercicioProgresoxEjercicioApiService.class);
    }

    private void eliminarEjer(int idEjercicio, _115_modificar_rutina_ejercicios.LlenarRutinasCallback callback)
    {
        Call<Void> call = rutinaEjercicioApiService.deleteById(idRutina,idEjercicio);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Log.d("Eliminar","se borro el ejercicio "+idEjercicio);
                } else {
                    // Maneja errores del servidor, por ejemplo, un error 404 o 500.
                    Log.e("Error", "Error en la respuesta: " + response.code());
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

    private void anadirEjer(int idEjercicio, _115_modificar_rutina_ejercicios.LlenarRutinasCallback callback)
    {
        anadirRutinaEjercicio(idEjercicio,()->{
            ProgresoxEjercicio progresoxEjercicio = new ProgresoxEjercicio();
            for(CajaRutina op : opciones)
            {
                if(op.getEjercicio().getId()==idEjercicio)
                    progresoxEjercicio = op.getProgresoxEjercicio();
            }
            anadirProgresoxEjercicio(idEjercicio,progresoxEjercicio,()->
            {
                callback.onCompletion();
            });
        });
    }

    private void anadirRutinaEjercicio(int idEjercicio, _115_modificar_rutina_ejercicios.LlenarRutinasCallback callback)
    {
        RutinaEjercicio rutinaEjercicio = new RutinaEjercicio();
        rutinaEjercicio.setEjercicioId(idEjercicio);
        rutinaEjercicio.setRutinaId(idRutina);
        Call<RutinaEjercicio> call = rutinaEjercicioApiService.add(rutinaEjercicio);
        call.enqueue(new Callback<RutinaEjercicio>() {
            @Override
            public void onResponse(Call<RutinaEjercicio> call, Response<RutinaEjercicio> response) {
                if (response.isSuccessful()) {
                    Log.d("Eliminar","se añadio el ejercicio: "+idEjercicio);
                } else {
                    // Maneja errores del servidor, por ejemplo, un error 404 o 500.
                    Log.e("Error", "Error en la respuesta: " + response.code());
                }
                callback.onCompletion();
            }
            @Override
            public void onFailure(Call<RutinaEjercicio> call, Throwable t) {
                // Maneja errores de red o de conversión de datos
                Log.e("Error", "Fallo en la petición: " + t.getMessage());

            }
        });
    }

    private void anadirProgresoxEjercicio(int idEjercicio, ProgresoxEjercicio progresoxEjercicio, _115_modificar_rutina_ejercicios.LlenarRutinasCallback callback)
    {
        Call<ProgresoxEjercicio> call = progresoxEjercicioService.add(progresoxEjercicio);
        call.enqueue(new Callback<ProgresoxEjercicio>() {
            @Override
            public void onResponse(Call<ProgresoxEjercicio> call, Response<ProgresoxEjercicio> response) {
                if (response.isSuccessful()) {
                    Log.d("Eliminar","se añadio el progreso para el  "+idEjercicio);
                } else {
                    // Maneja errores del servidor, por ejemplo, un error 404 o 500.
                    Log.e("Error", "Error en la respuesta: " + response.code());
                }
                callback.onCompletion();
            }
            @Override
            public void onFailure(Call<ProgresoxEjercicio> call, Throwable t) {
                // Maneja errores de red o de conversión de datos
                Log.e("Error", "Fallo en la petición: " + t.getMessage());

            }
        });
    }

    private void filtrarListas()
    {
        Set<Integer> set1 = new HashSet<>(anadidos);
        Set<Integer> set2 = new HashSet<>(eliminados);
        Set<Integer> duplicates = new HashSet<>(set1);
        duplicates.retainAll(set2);
        set1.removeAll(duplicates);
        set2.removeAll(duplicates);
        anadidos = new ArrayList<>(set1);
        eliminados = new ArrayList<>(set2);
    }
    @Override
    public int getItemCount() {
        return cajaRutinas.size();
    }

    public class CajaRutinaMViewHolder extends RecyclerView.ViewHolder {
        public TextView nombreEjercicio;
        public TextView valorSerie;
        public TextView valorRepeticiones;
        public ImageView imagenEjercicio;
        public ImageButton btnEliminar;

        public CajaRutinaMViewHolder(View view) {
            super(view);
            nombreEjercicio = view.findViewById(R.id.nombreEjercicio_98);
            valorSerie = view.findViewById(R.id.valorSerie);
            valorRepeticiones = view.findViewById(R.id.valorRepeticiones);
            imagenEjercicio = view.findViewById(R.id.imageView);
            btnEliminar = view.findViewById(R.id.btnEliminarEjercicioModificar);

            btnGuardar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("Eliminar", "quiere guardar cambios");
                    Log.d("Eliminar", "eliminados "+eliminados);
                    Log.d("Eliminar", "añadidos"+ anadidos);
                    iniciarPeticiones();
                    // filtrar listas
                    filtrarListas();
                    // manejar eliminados
                    for(int eliminar: eliminados)
                    {
                        eliminarEjer(eliminar,()-> {});
                    }
                    // manejar añadidos
                    for(int nuevo: anadidos)
                    {
                        anadirEjer(nuevo,()->{});
                    }
                    Toast.makeText(context, "Cambios guardados", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}