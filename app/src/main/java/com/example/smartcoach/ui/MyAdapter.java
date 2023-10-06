package com.example.smartcoach.ui;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartcoach.R;

import java.util.ArrayList;
import java.util.List;

import model.User.RestriccionMedica;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {


    private List<RestriccionMedica> mData;
    private List<RestriccionMedica> seleccionados = new ArrayList<>();
    private List<RestriccionMedica> seleccionTemporal = new ArrayList<>();



    public MyAdapter(List<RestriccionMedica> data) {
        mData = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_restriccion_medica, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RestriccionMedica item = mData.get(position);
        holder.textViewItem.setText(item.getNombreLimitacion());

        if (seleccionTemporal.contains(item)) {
            holder.itemView.setBackgroundResource(R.drawable.rounded_grey_background);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int pos = holder.getAdapterPosition();
                RestriccionMedica itemSeleccionado = mData.get(pos);
                if (seleccionTemporal.contains(itemSeleccionado)) {
                    seleccionTemporal.remove(itemSeleccionado);
                } else {
                    seleccionTemporal.add(itemSeleccionado);
                }
                notifyDataSetChanged();
            }
        });
    }


    public List<RestriccionMedica> getItemsSeleccionados() {
        return seleccionTemporal;
    }


    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewItem = itemView.findViewById(R.id.textViewItem);
        }
    }
}
