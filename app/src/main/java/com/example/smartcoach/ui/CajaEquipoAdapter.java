package com.example.smartcoach.ui;

import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartcoach.R;

import java.util.ArrayList;
import java.util.List;

import model.Admi.Equipo;
import model.Admi.GimnasioItem;

public class CajaEquipoAdapter extends RecyclerView.Adapter<CajaEquipoAdapter.CajaEquipoViewHolder> {

    private List<Equipo> equipos;
    private List<GimnasioItem> gimnasioItems;

    public CajaEquipoAdapter(List<Equipo> equipos , List<GimnasioItem> gimnasioItems) {
        this.equipos = equipos;
        this.gimnasioItems = gimnasioItems;
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
        }
        else
        {
            holder.btnAgregar.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.rounded_grey_background));
            holder.btnAgregar.setText("Eliminar");
        }


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
