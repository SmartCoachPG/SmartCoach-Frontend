package model.Exercise;

import android.os.Parcel;
import android.os.Parcelable;

public class ImagenEjercicio {

    private Long id;
    private String imagen;
    private int ejercicio;

    public ImagenEjercicio(Long id, String imagen, int ejercicio) {
        this.id = id;
        this.imagen = imagen;
        this.ejercicio = ejercicio;
    }

    public ImagenEjercicio()
    {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public int getEjercicio() {
        return ejercicio;
    }

    public void setEjercicio(int ejercicio) {
        this.ejercicio = ejercicio;
    }

    protected ImagenEjercicio(Parcel in) {
        id = (Long) in.readValue(Long.class.getClassLoader());
        imagen = in.readString();
        ejercicio = in.readInt();
    }

    @Override
    public String toString() {
        return "ImagenEjercicio{" +
                "id=" + id +
                "ejercicioId: "+ejercicio+
                '}';
    }



}
