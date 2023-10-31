package com.example.smartcoach.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.smartcoach.R;

import java.util.ArrayList;
import java.util.List;

import model.Exercise.CajaRutina;


public class CajaRutinaAdapterM extends RecyclerView.Adapter<CajaRutinaAdapterM.CajaRutinaMViewHolder> {

    private List<CajaRutina> cajaRutinas;
    private List<CajaRutina> opciones;

    private List<Integer> anadidos = new ArrayList<>();
    private List<Integer> eliminados = new ArrayList<>();
    int contador=0;

    public CajaRutinaAdapterM(List<CajaRutina> cajaRutinas,List<CajaRutina> opciones) {
        this.cajaRutinas = cajaRutinas;
        this.opciones = opciones;
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
                    handleElimination(cajaRutina);
                    if(contador<opciones.size())
                    {
                        CajaRutina nuevaCajaRutina = opciones.get(contador);
                        cajaRutinas.set(position, nuevaCajaRutina);
                        configurarVista(holder, nuevaCajaRutina);
                        notifyItemChanged(position);
                        contador++;
                    }

                }

            });
        }

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