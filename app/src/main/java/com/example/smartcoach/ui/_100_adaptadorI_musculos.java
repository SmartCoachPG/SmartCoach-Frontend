package com.example.smartcoach.ui;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartcoach.R;

import java.util.List;

public class _100_adaptadorI_musculos extends RecyclerView.Adapter<_100_adaptadorI_musculos.InnerViewHolder> {

    private List<String> musculosEjercicio;

    public _100_adaptadorI_musculos(List<String> musculosEjercicio) {
        this.musculosEjercicio = musculosEjercicio;
    }

    @NonNull
    @Override
    public InnerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_restriccion_medica, parent, false);
        return new InnerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InnerViewHolder holder, int position) {
        Log.d("RUTINA", "en adaptador I: "+musculosEjercicio);
        holder.textView.setText(musculosEjercicio.get(position));
    }

    @Override
    public int getItemCount() {
        return musculosEjercicio.size();
    }

    public static class InnerViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public InnerViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textViewItem);
        }
    }
}