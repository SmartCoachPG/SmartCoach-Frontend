package com.example.smartcoach.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.smartcoach.R;

import java.util.List;

import model.Exercise.CajaRutina;


public class CajaRutinaAdapterM extends RecyclerView.Adapter<CajaRutinaAdapterM.CajaRutinaMViewHolder> {

    private List<CajaRutina> cajaRutinas;

    public CajaRutinaAdapterM(List<CajaRutina> cajaRutinas) {
        this.cajaRutinas = cajaRutinas;
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
        }
    }
}