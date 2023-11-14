package com.example.smartcoach.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.smartcoach.R;
import java.util.List;

public class _100_adaptadorI_equipo extends RecyclerView.Adapter<_100_adaptadorI_equipo.InnerViewHolder> {

    private List<String> equipoEjercicio;

    public _100_adaptadorI_equipo(List<String> equipoEjercicio) {
        this.equipoEjercicio = equipoEjercicio;
    }

    @NonNull
    @Override
    public InnerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout._100_adaptador_equipo_item, parent, false);
        return new InnerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InnerViewHolder holder, int position) {
        holder.textView.setText(equipoEjercicio.get(position));
    }

    @Override
    public int getItemCount() {
        return equipoEjercicio.size();
    }

    public static class InnerViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public InnerViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.adaptadorI_equipoText);
        }
    }
}
