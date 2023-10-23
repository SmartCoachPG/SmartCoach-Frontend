package com.example.smartcoach.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartcoach.R;

public class RutinaAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    // Definiendo tipos de vistas
    public static final int IMAGE_VIEW = 0;
    public static final int LAYOUT_ONE = 1;
    public static final int LAYOUT_TWO = 2;
    public static final int LAYOUT_THREE = 3;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == IMAGE_VIEW) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.imageview_layout, parent, false);
            return new ImageViewHolder(view);
        } else if (viewType == LAYOUT_ONE) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout._111_informacion_detallada_ejercicio_1, parent, false);
            return new LayoutOneViewHolder(view);
        } else if (viewType == LAYOUT_TWO) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout._112_113_informacion_detallada_ejercicio_2_3, parent, false);
            return new LayoutTwoViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout._112_113_informacion_detallada_ejercicio_2_3, parent, false);
            return new LayoutThreeViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        // Aquí configuras tus vistas según la posición y el tipo
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return IMAGE_VIEW;
        } else if (position == 1) {
            return LAYOUT_ONE;
        } else if (position == 2) {
            return LAYOUT_TWO;
        } else {
            return LAYOUT_THREE;
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            // Inicializar tus vistas aquí
        }
    }

    public class LayoutOneViewHolder extends RecyclerView.ViewHolder {
        public LayoutOneViewHolder(@NonNull View itemView) {
            super(itemView);
            // Inicializar tus vistas aquí
        }
    }

    public class LayoutTwoViewHolder extends RecyclerView.ViewHolder {
        public LayoutTwoViewHolder(@NonNull View itemView) {
            super(itemView);
            // Inicializar tus vistas aquí
        }
    }

    public class LayoutThreeViewHolder extends RecyclerView.ViewHolder {
        public LayoutThreeViewHolder(@NonNull View itemView) {
            super(itemView);
            // Inicializar tus vistas aquí
        }
    }


    // ... (Y así sucesivamente para los otros ViewHolder)
}
