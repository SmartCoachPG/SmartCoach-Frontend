package com.example.smartcoach.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.smartcoach.R;

import java.util.List;

import model.Exercise.CajaRutina;

public class MyAdapter2 extends RecyclerView.Adapter<MyAdapter2.MyViewHolder> {

    private List<CajaRutina> dataList;

    public MyAdapter2(List<CajaRutina> dataList) {
        this.dataList = dataList;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // Tus vistas aquí, por ejemplo:
        public TextView textView;

        public MyViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.caja_ejercicio_rutina, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        CajaRutina data = dataList.get(position);
        holder.textView.setText(data.getEjercicio().getNombre());
        // Configura otras vistas aquí
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }


}
