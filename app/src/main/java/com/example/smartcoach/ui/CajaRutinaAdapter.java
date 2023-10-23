package com.example.smartcoach.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.smartcoach.R;

import java.util.List;

import model.Exercise.CajaRutina;

public class CajaRutinaAdapter extends RecyclerView.Adapter<CajaRutinaAdapter.CajaRutinaViewHolder> {

    private List<CajaRutina> cajaRutinas;

    public CajaRutinaAdapter(List<CajaRutina> cajaRutinas) {
        this.cajaRutinas = cajaRutinas;
    }

    @Override
    public CajaRutinaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.caja_ejercicio_rutina, parent, false);
        return new CajaRutinaViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CajaRutinaViewHolder holder, int position) {
        CajaRutina cajaRutina = cajaRutinas.get(position);
        if(cajaRutina!=null)
        {
            holder.nombreEjercicio.setText(cajaRutina.getEjercicio().getNombre());
            holder.valorSerie.setText(cajaRutina.getProgresoxEjercicio().getSerie().toString());
            holder.valorRepeticiones.setText(cajaRutina.getProgresoxEjercicio().getRepeticiones().toString());
        }

    }

    @Override
    public int getItemCount() {
        return cajaRutinas.size();
    }

    public class CajaRutinaViewHolder extends RecyclerView.ViewHolder {
        public TextView nombreEjercicio;
        public TextView valorSerie;
        public TextView valorRepeticiones;

        public CajaRutinaViewHolder(View view) {
            super(view);
            nombreEjercicio = view.findViewById(R.id.nombreEjercicio_98);
            valorSerie = view.findViewById(R.id.valorSerie);
            valorRepeticiones = view.findViewById(R.id.valorRepeticiones);
           // imagenEjercicio = view.findViewById(R.id.imagenEjercicio);
        }
    }
}
