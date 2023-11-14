package com.example.smartcoach.ui;

import android.content.Context;
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
import java.util.Map;

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
                    holder.equipoImageView.setTag(adjustedPosition);
                }
            }
        }

        holder.equipoImageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    // Clonar la vista
                    ImageView clonedImageView = new ImageView(v.getContext());
                    clonedImageView.setImageDrawable(((ImageView) v).getDrawable());
                    // Convertir 36dp a píxeles
                    final float scale = v.getContext().getResources().getDisplayMetrics().density;
                    int sizeInPixels = (int) (36 * scale + 0.5f);

                    // Establecer el ancho y el alto de la vista clonada
                    clonedImageView.measure(View.MeasureSpec.makeMeasureSpec(sizeInPixels, View.MeasureSpec.EXACTLY),
                            View.MeasureSpec.makeMeasureSpec(sizeInPixels, View.MeasureSpec.EXACTLY));
                    clonedImageView.layout(0, 0, sizeInPixels, sizeInPixels);
                    clonedImageView.setTag(v.getTag());
                    View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(clonedImageView);
                    v.startDrag(null, shadowBuilder, clonedImageView, 0);
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
