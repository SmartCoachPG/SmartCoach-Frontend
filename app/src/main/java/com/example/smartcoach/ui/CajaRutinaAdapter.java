package com.example.smartcoach.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
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
            String imageString = cajaRutina.getImagenEjercicio().getImagen();
            if(imageString!=null)
            {
                byte[] imageBytes = Base64.decode(imageString, Base64.DEFAULT);
                Bitmap decodedBitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                holder.imagenEjercicio.setImageBitmap(decodedBitmap);
            }
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

        public ImageView imagenEjercicio;

        public CajaRutinaViewHolder(View view) {
            super(view);
            nombreEjercicio = view.findViewById(R.id.nombreEjercicio_98);
            valorSerie = view.findViewById(R.id.valorSerie);
            valorRepeticiones = view.findViewById(R.id.valorRepeticiones);
            imagenEjercicio = view.findViewById(R.id.imageView);
        }
    }
}
