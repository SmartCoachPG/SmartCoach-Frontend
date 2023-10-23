package com.example.smartcoach.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartcoach.R;

import java.util.ArrayList;
import java.util.List;

import api.Admi.EquipoApiService;
import api.Admi.GimnasioApiService;
import api.Admi.GimnasioItemApiService;
import api.Admi.UsuarioAdministradorApiService;
import api.SharedPreferencesUtil;
import api.retro;
import model.Admi.Equipo;
import model.Admi.Gimnasio;
import model.Admi.GimnasioItem;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CajaEquipoAdapter extends RecyclerView.Adapter<CajaEquipoAdapter.CajaEquipoViewHolder> {

    private List<Equipo> equipos;
    private Context context;
    private List<GimnasioItem> gimnasioItems;
    private Gimnasio gimnasio;
    Dialog dialog;
    private int cantidadE=0;
    String token;
    Long userId;
    GimnasioItemApiService gimnasioItemApiService;

    public CajaEquipoAdapter(List<Equipo> equipos , List<GimnasioItem> gimnasioItems, Gimnasio gimnasio,Context context) {
        this.equipos = equipos;
        this.gimnasioItems = gimnasioItems;
        this.gimnasio = gimnasio;
        this.context = context;
        userId = SharedPreferencesUtil.getUserId(context);
        token = SharedPreferencesUtil.getToken(context);
        iniciarPeticiones();
    }

    private void iniciarPeticiones()
    {
        OkHttpClient okHttpClient = retro.getUnsafeOkHttpClientWithToken(token);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://10.0.2.2:8043/api/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        gimnasioItemApiService = retrofit.create(GimnasioItemApiService.class);
    }

    @Override
    public CajaEquipoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.caja_agregar_equipo_todos, parent, false);
        return new CajaEquipoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CajaEquipoViewHolder holder, int position) {
        Equipo equipo = equipos.get(position);
        holder.nombreEquipo.setText(equipo.getNombre());
        holder.referenciaE.setText(equipo.getReferencia());
        holder.descripcionE.setText(equipo.getDescripcion());
        int bandera = 0;

        for(GimnasioItem gimnasioItem : gimnasioItems)
        {
            if(equipo.getId()==gimnasioItem.getItemid())
            {
                bandera=1;
            }
        }

        if(bandera==0)
        {
            holder.btnAgregar.setBackgroundResource(R.drawable.rounded_orange_background);
            holder.btnAgregar.setText("Añadir");
            holder.btnAgregar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    añadirEquipo(gimnasio.getId().intValue(),equipo.getId().intValue(),equipo.getNombre());
                    notifyItemChanged(position);
                }
            });
        }
        else
        {
            holder.btnAgregar.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.rounded_grey_background));
            holder.btnAgregar.setText("Eliminar");
            holder.btnAgregar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    eliminarEquipo(gimnasio.getId().intValue(),equipo.getId().intValue());
                    notifyItemChanged(position);

                }
            });
        }

    }

    public void añadirEquipo(int idGimnasio,int idEquipo, String nombre)
    {
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout._16_anadir_equipo_cantidad);
        dialog.getWindow().setBackgroundDrawable(null);

        TextView nombreE = dialog.findViewById(R.id.nombreEquipo_16);
        nombreE.setText(nombre);

        Button botonConfirmar = dialog.findViewById(R.id.botonConfirmar_16);
        botonConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText cantidad = dialog.findViewById(R.id.cantidadEquipo_16);
                cantidadE = Integer.parseInt(cantidad.getText().toString());
                Log.d("CREAR EQUIPO", "idGimnasio: "+idGimnasio+" idEquipo:"+idEquipo+" cantidad:"+cantidadE);
                crearGimnasioItem(idGimnasio,idEquipo,cantidadE);
                dialog.dismiss();
            }
        });

        Button botonCancelar = dialog.findViewById(R.id.botonCancelar_16);
        botonCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Acciones a realizar cuando se presiona "Cancelar"
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private  void eliminarEquipo(int idGimnasio, int idItem)
    {
        for(GimnasioItem gi: gimnasioItems)
        {
            if(gi.getItemid()==idItem&&gi.getGimnasioid()==idGimnasio)
            {
                gimnasioItems.remove(gi);
            }
        }
        Call<Void> call = gimnasioItemApiService.deleteGimnasioItem(idGimnasio,idItem);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context, "Equipo Eliminado", Toast.LENGTH_SHORT).show();
                    notifyDataSetChanged();
                } else {
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(context, "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void crearGimnasioItem(int idGimnasio,int idItem , int cantidad)
    {
        GimnasioItem nuevo = new GimnasioItem();
        nuevo.setCantidad(cantidad);
        nuevo.setGimnasioid(idGimnasio);
        nuevo.setItemid(idItem);
        Call<GimnasioItem> call = gimnasioItemApiService.addGimnasioItem(nuevo);
        call.enqueue(new Callback<GimnasioItem>() {
            @Override
            public void onResponse(Call<GimnasioItem> call, Response<GimnasioItem> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context, "Equipo añadido", Toast.LENGTH_SHORT).show();
                    gimnasioItems.add(nuevo);
                    notifyDataSetChanged();
                } else {
                }
            }

            @Override
            public void onFailure(Call<GimnasioItem> call, Throwable t) {
                Toast.makeText(context, "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void updateList(List<Equipo> newList) {
        equipos = new ArrayList<>();
        equipos.addAll(newList);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return equipos.size();
    }

    public class CajaEquipoViewHolder extends RecyclerView.ViewHolder {
        public TextView nombreEquipo;
        public TextView referenciaE;
        public TextView descripcionE;
        public Button btnAgregar;

        public CajaEquipoViewHolder(View view) {
            super(view);
            nombreEquipo = view.findViewById(R.id.nombreEquipo);
            referenciaE = view.findViewById(R.id.nombreReferenciaEquipo);
            descripcionE = view.findViewById(R.id.desc_equipo);
            descripcionE.setMovementMethod(new ScrollingMovementMethod());
            btnAgregar = view.findViewById(R.id.btnAgregar);
        }
    }
}
