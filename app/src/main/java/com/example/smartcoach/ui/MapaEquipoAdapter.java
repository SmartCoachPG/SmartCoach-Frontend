package com.example.smartcoach.ui;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartcoach.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import model.Admi.Gimnasio;
import model.Admi.GimnasioItem;
import model.Admi.UbicacionxItem;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MapaEquipoAdapter extends  RecyclerView.Adapter<MapaEquipoAdapter.MapaEquipoViewHolder>
{
        private List<Equipo> equipos = new ArrayList<>();
        private Map<Integer,String> tipoEquipo = new HashMap<>();
        private Dialog dialog;
        private Context context;
        private UbicacionxItem ubicacionxItem;
        private UbicacionxItemApiService ubicacionxItemApiService;
        private GimnasioItemApiService gimnasioItemApiService;
        private OnDefinirButtonClickListener listener;
        private ImageView cuadrado;

        private int gimnasioId;

        boolean tiene= false;

        GimnasioItem gimnasioItem = new GimnasioItem();
        public MapaEquipoAdapter(int gimnasioId,List<Equipo> equipos, Map<Integer,String> tipoEquipo, UbicacionxItem ubicacionxItem, Dialog dialog, Context context, OnDefinirButtonClickListener listener, ImageView cuadrado) {
            this.equipos = equipos;
            this.tipoEquipo = tipoEquipo;
            this.context = context;
            this.ubicacionxItem = ubicacionxItem;
            this.dialog = dialog;
            this.listener = listener;
            this.cuadrado = cuadrado;
            this.gimnasioId =gimnasioId;
        }


        @Override
        public MapaEquipoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.caja_equipo_mapa, parent, false);
            return new MapaEquipoViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(MapaEquipoViewHolder holder, int position) {
            Equipo item = equipos.get(position);

            holder.nombreE.setText(item.getNombre());
            holder.referenciaE.setText(item.getReferencia());
            holder.descripcionE.setText(item.getDescripcion());
            holder.tipoM.setText(tipoEquipo.get(item.getTipoEquipoId()));

            if(item.getImagen()!=null)
            {
                byte[] imageBytes = Base64.decode(item.getImagen(), Base64.DEFAULT);
                Bitmap decodedBitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                holder.imagen.setImageBitmap(decodedBitmap);
            }
            iniciarPeticiones();
            holder.definirB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(ubicacionxItem.getId()!=0)
                    {
                        definirEquipo(item.getId().intValue());
                    }
                    else {
                        crearyDefinirEquipo(item.getId().intValue(),()->
                        {
                            revisarGimnasioItem(()->{
                                if(tiene)
                                    actualizarGimnasioItem(()->{});
                                else
                                    crearGimnasioItem(()->{});
                            });
                        });
                    }
                    holder.definirB.setBackgroundResource(R.drawable.rounded_grey_background);
                    holder.definirB.setText("Definido");
                    if (listener != null) {
                        listener.onDefinirButtonClick(true,cuadrado,ubicacionxItem);
                    }
                }
            });


        }

        private void definirEquipo(int itemId)
        {
            this.ubicacionxItem.setItemid(itemId);
            Call<UbicacionxItem> call = ubicacionxItemApiService.updateUbicacionxItem(this.ubicacionxItem);
            call.enqueue(new Callback<UbicacionxItem>() {
                @Override
                public void onResponse(Call<UbicacionxItem> call, Response<UbicacionxItem> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(context, "Equipo asignado", Toast.LENGTH_SHORT).show();
                    } else {
                        // Maneja errores del servidor, por ejemplo, un error 404 o 500.
                        Log.e("Error", "Error en la respuesta: " + response.code());
                    }
                }

                @Override
                public void onFailure(Call<UbicacionxItem> call, Throwable t) {
                    // Maneja errores de red o de conversión de datos
                    Log.e("Error", "Fallo en la petición: " + t.getMessage());
                }
            });
        }

        private void crearyDefinirEquipo(int itemId,_31_armar_mapa_admin.InfoCallback callback)
        {
            this.ubicacionxItem.setItemid(itemId);
            Call<UbicacionxItem> call = ubicacionxItemApiService.addUbicacionxItem(this.ubicacionxItem);
            call.enqueue(new Callback<UbicacionxItem>() {
                @Override
                public void onResponse(Call<UbicacionxItem> call, Response<UbicacionxItem> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(context, "Item creado y equipo asignado", Toast.LENGTH_SHORT).show();
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

        private void revisarGimnasioItem(_31_armar_mapa_admin.InfoCallback callback)
        {
            Call<GimnasioItem> call = gimnasioItemApiService.getGimnasioItem(gimnasioId,ubicacionxItem.getItemid());
            call.enqueue(new Callback<GimnasioItem>() {
                @Override
                public void onResponse(Call<GimnasioItem> call, Response<GimnasioItem> response) {
                    if (response.isSuccessful()) {
                        if(response.body()!=null)
                        {
                            gimnasioItem = response.body();
                            tiene = true;
                        }
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

        private void crearGimnasioItem(_31_armar_mapa_admin.InfoCallback callback)
        {
        GimnasioItem nuevo = new GimnasioItem();
        nuevo.setCantidad(1);
        nuevo.setGimnasioid(gimnasioId);
        nuevo.setItemid(ubicacionxItem.getItemid());

        Call<GimnasioItem> call = gimnasioItemApiService.addGimnasioItem(nuevo);
        call.enqueue(new Callback<GimnasioItem>() {
            @Override
            public void onResponse(Call<GimnasioItem> call, Response<GimnasioItem> response) {
                if (response.isSuccessful()) {
                    if(response.body()!=null)
                    {
                        tiene = true;
                    }
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

        private void actualizarGimnasioItem(_31_armar_mapa_admin.InfoCallback callback)
        {
            int cantidadO = gimnasioItem.getCantidad()+1;
            gimnasioItem.setCantidad(cantidadO);

            Call<GimnasioItem> call = gimnasioItemApiService.addGimnasioItem(gimnasioItem);
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

        private void iniciarPeticiones()
        {
            Long userId = SharedPreferencesUtil.getUserId(context);
            String token = SharedPreferencesUtil.getToken(context);
            OkHttpClient okHttpClient = retro.getUnsafeOkHttpClientWithToken(token);
            Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://10.0.2.2:8043/api/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
             ubicacionxItemApiService = retrofit.create(UbicacionxItemApiService.class);
             gimnasioItemApiService = retrofit.create(GimnasioItemApiService.class);
      }

        @Override
        public int getItemCount() {
            return equipos.size();
        }

        public static class MapaEquipoViewHolder extends RecyclerView.ViewHolder {
            TextView nombreE, referenciaE,descripcionE,tipoM;
            Button definirB;
            ImageView imagen;

            public MapaEquipoViewHolder(@NonNull View itemView) {
                super(itemView);
                nombreE = itemView.findViewById(R.id.nombreEquipo_33);
                referenciaE = itemView.findViewById(R.id.nombreReferenciaEquipo_33);
                descripcionE = itemView.findViewById(R.id.desc_equipo_33);
                tipoM = itemView.findViewById(R.id.tipoEjericicio_33);
                imagen = itemView.findViewById(R.id.imageEquipo_33);
                definirB = itemView.findViewById(R.id.btnDefinir_33);
            }
        }
    }