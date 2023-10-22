package com.example.smartcoach.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.smartcoach.R;

import java.util.ArrayList;
import java.util.List;

import api.SharedPreferencesUtil;
import model.Admi.Gimnasio;

public class CajaGimnasioAdapter extends RecyclerView.Adapter<CajaGimnasioAdapter.CajaGimnasioViewHolder> {

    private Context context;
    private List<Gimnasio> gimnasios;

    private  OnSuscribirClickListener listener;

    public CajaGimnasioAdapter(List<Gimnasio> gimnasios,Context context,OnSuscribirClickListener listener) {
        this.listener = listener;
        this.context = context;
        this.gimnasios = gimnasios;
    }

    @Override
    public CajaGimnasioViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.caja_gimnasios, parent, false);
        return new CajaGimnasioViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CajaGimnasioViewHolder holder, int position) {
        Gimnasio gimnasio = gimnasios.get(position);
        holder.nombreGym.setText(gimnasio.getNombre());
        holder.barrioGym.setText(gimnasio.getBarrio());
        holder.direccionGym.setText(gimnasio.getDireccion());

        String imageString = gimnasio.getImagenGimnasio(); // Asegúrate de que este método exista y retorne la cadena Base64 de la imagen
        if (imageString != null && !imageString.isEmpty()) {
            byte[] decodedString = Base64.decode(imageString, Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            holder.imagenGym.setImageBitmap(decodedByte);
        }

        holder.btnSuscribirse.setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SharedPreferencesUtil.saveGimnasio(context,gimnasio.getId().intValue());
                    if (listener != null) {
                        listener.onSuscribirClick(gimnasio.getId().intValue());
                    }
                }
            }
        );


    }

    public void updateList(List<Gimnasio> newList) {
        gimnasios = new ArrayList<>();
        gimnasios.addAll(newList);
        notifyDataSetChanged();
    }

    public interface OnSuscribirClickListener {
        void onSuscribirClick(int gimnasioId);
    }


    @Override
    public int getItemCount() {
        return gimnasios.size();
    }

    public class CajaGimnasioViewHolder extends RecyclerView.ViewHolder {
        public TextView nombreGym;
        public TextView barrioGym;
        public TextView direccionGym;

        public ImageView imagenGym;

        public Button btnSuscribirse;

        public CajaGimnasioViewHolder(View view) {
            super(view);
            nombreGym = view.findViewById(R.id.nombreGym_cajaGym);
            barrioGym = view.findViewById(R.id.barrio_cajaGym);
            direccionGym = view.findViewById(R.id.direccion_cajaGym);
            btnSuscribirse = view.findViewById(R.id.btnSuscribir);
            imagenGym = view.findViewById(R.id.imageGym_cajaGym);
        }
    }
}