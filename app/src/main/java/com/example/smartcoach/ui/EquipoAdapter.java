package com.example.smartcoach.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.smartcoach.R;

import java.util.List;

public class EquipoAdapter extends RecyclerView.Adapter<EquipoAdapter.ViewHolder> {

    private List<String> listaDeEquipos;
    private Context context;
    private boolean isMusculo;

    // Constructor para RecyclerView con diseño item_equipo_usado.xml
    public EquipoAdapter(Context context, List<String> listaDeEquipos) {
        this.context = context;
        this.listaDeEquipos = listaDeEquipos;
        this.isMusculo = false;
    }

    // Constructor para RecyclerView con diseño item_musculo_involucrado.xml
    public EquipoAdapter(Context context, List<String> listaDeEquipos, boolean isMusculo) {
        this.context = context;
        this.listaDeEquipos = listaDeEquipos;
        this.isMusculo = isMusculo;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layoutResId;
        if (isMusculo) {
            layoutResId = R.layout.item_musculo_involucrado;
        } else {
            layoutResId = R.layout.item_equipo_usado;
        }

        View view = LayoutInflater.from(parent.getContext()).inflate(layoutResId, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String item = listaDeEquipos.get(position);

        if (isMusculo) {
            holder.textViewItemMusculoInvolucrado.setText(item);
        } else {
            holder.textViewItemEquipoUsado.setText(item);
        }
    }

    @Override
    public int getItemCount() {
        return listaDeEquipos.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewItemEquipoUsado; // Para item_equipo_usado.xml
        public TextView textViewItemMusculoInvolucrado; // Para item_musculo_involucrado.xml

        public ViewHolder(View view) {
            super(view);
            textViewItemEquipoUsado = view.findViewById(R.id.textViewItemEquipoUsado);
            textViewItemMusculoInvolucrado = view.findViewById(R.id.textViewItemMusculoInvolucrado);
        }
    }
}
