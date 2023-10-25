package com.example.smartcoach.ui;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartcoach.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Admi.Equipo;
import model.Admi.UbicacionxItem;

public class EquipoAdapter extends RecyclerView.Adapter<EquipoAdapter.ViewHolder> {

    private final Context context;
    Map<Integer,String> iconosName = new HashMap<>();
    Map<Integer,String> iconos= new HashMap<>();

    Boolean tipo;

    public EquipoAdapter(Context context, Map<Integer,String> iconosName, Map<Integer,String> iconos,Boolean tipo) {
        this.context = context;
        this.iconosName=iconosName;
        this.iconos = iconos;
        this.tipo= tipo;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView equipoImageView;
        public TextView equipoTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            equipoImageView = itemView.findViewById(R.id.nombreEquipo_adapter_31);
            equipoTextView = itemView.findViewById(R.id.imagenEquipo_adapter_31);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.lista_elementos_adapter, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if (tipo && position < 5) {
            holder.equipoTextView.setText(iconosName.get(position));

            String imageName = iconos.get(position);
            if(imageName!=null) {
                int imageResId = context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());
                if (imageResId != 0) {
                    holder.equipoImageView.setImageResource(imageResId);
                    holder.equipoImageView.setTag(position);
                }
            }
        }
        else {
            int adjustedPosition = position + 5;
            holder.equipoTextView.setText(iconosName.get(adjustedPosition));
            String imageName = iconos.get(adjustedPosition);
            if (imageName != null) {
                int imageResId = context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());
                if (imageResId != 0) {
                    holder.equipoImageView.setImageResource(imageResId);
                }
            }
        }

        holder.equipoImageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {

                    View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
                    v.startDrag(null, shadowBuilder, v, 0);
                    return true;
                } else {
                    return false;
                }
            }
        });

    }


    @Override
    public int getItemCount() {
        if (tipo) {
            return Math.min(5, iconosName.size());
        }
        else {
            return Math.max(0, iconosName.size() - 5);
        }
    }
}
