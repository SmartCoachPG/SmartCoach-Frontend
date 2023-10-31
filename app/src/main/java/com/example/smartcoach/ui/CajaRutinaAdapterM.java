package com.example.smartcoach.ui;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.smartcoach.R;

import java.util.ArrayList;
import java.util.List;

import model.Exercise.CajaRutina;


public class CajaRutinaAdapterM extends RecyclerView.Adapter<CajaRutinaAdapterM.CajaRutinaMViewHolder> {

    private List<CajaRutina> cajaRutinas;
    private List<CajaRutina> opciones;

    private Context context;
    private List<Integer> anadidos = new ArrayList<>();
    private List<Integer> eliminados = new ArrayList<>();
    int contador=0;

    public CajaRutinaAdapterM(List<CajaRutina> cajaRutinas,List<CajaRutina> opciones,Context context) {
        this.cajaRutinas = cajaRutinas;
        this.opciones = opciones;
        this.context = context;
    }

    @Override
    public CajaRutinaMViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.caja_ejercicios_modificar, parent, false);
        return new CajaRutinaMViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CajaRutinaMViewHolder holder, int position) {
        CajaRutina cajaRutina = cajaRutinas.get(position);
        if(cajaRutina!=null)
        {
            configurarVista(holder,cajaRutina);
            holder.btnEliminar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showDialogConfirmarE(cajaRutina,holder,position);
                }

            });
        }

    }

    private void showDialogConfirmarE(CajaRutina cajaRutina,CajaRutinaMViewHolder holder,int position) {
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout._116_mensaje_confirmacion_eliminacion_ejercicio_rutina);
        dialog.getWindow().setBackgroundDrawable(null);

        TextView nombreE = dialog.findViewById(R.id.textoNombreEjercicio);
        nombreE.setText(cajaRutina.getEjercicio().getNombre());
        Button botonConfirmar = dialog.findViewById(R.id.botonConfirmar);
        botonConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Acciones a realizar cuando se presiona "Confirmar"
                dialog.dismiss();
                if(contador+1<opciones.size())
                {
                    handleElimination(cajaRutina);
                    CajaRutina nuevaCajaRutina = opciones.get(contador);
                    cajaRutinas.set(position, nuevaCajaRutina);
                    configurarVista(holder, nuevaCajaRutina);
                    notifyItemChanged(position);
                    contador++;
                }
                else {
                    showDialogNoOpciones();
                }
            }

        });

        Button botonCancelar = dialog.findViewById(R.id.botonCancelar);
        botonCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void showDialogNoOpciones() {
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout._117_mensaje_error_eliminar_ejercicio);
        dialog.getWindow().setBackgroundDrawable(null);
        Button botonConfirmar = dialog.findViewById(R.id.btnSeguirCamposVacios);
        botonConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }

        });

        dialog.show();
    }

    private void configurarVista(CajaRutinaMViewHolder holder, CajaRutina cajaRutina){
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

    private void handleElimination(CajaRutina cajaRutina){
        Log.d("Eliminando", "quiere eliminar ejercicio: "+ cajaRutina.getEjercicio().getId());
        Log.d("Elimando", "ejercicio: "+cajaRutinas);
        Log.d("Elimando", "opciones: "+opciones);
        eliminados.add(cajaRutina.getEjercicio().getId().intValue());
        anadidos.add(opciones.get(contador).getEjercicio().getId().intValue());
    }

    @Override
    public int getItemCount() {
        return cajaRutinas.size();
    }

    public class CajaRutinaMViewHolder extends RecyclerView.ViewHolder {
        public TextView nombreEjercicio;
        public TextView valorSerie;
        public TextView valorRepeticiones;
        public ImageView imagenEjercicio;

        public ImageButton btnEliminar;

        public CajaRutinaMViewHolder(View view) {
            super(view);
            nombreEjercicio = view.findViewById(R.id.nombreEjercicio_98);
            valorSerie = view.findViewById(R.id.valorSerie);
            valorRepeticiones = view.findViewById(R.id.valorRepeticiones);
            imagenEjercicio = view.findViewById(R.id.imageView);
            btnEliminar = view.findViewById(R.id.btnEliminarEjercicioModificar);
        }
    }
}