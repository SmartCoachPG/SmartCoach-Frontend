package model.Exercise;

import android.os.Parcel;
import android.os.Parcelable;

import model.User.ProgresoxEjercicio;

public class CajaRutina implements Parcelable {

    private Ejercicio ejercicio;
    private ProgresoxEjercicio progresoxEjercicio;
    private ImagenEjercicio imagenEjercicio;

    public CajaRutina(Ejercicio ejercicio, ProgresoxEjercicio progresoxEjercicio, ImagenEjercicio imagenEjercicio) {
        this.ejercicio = ejercicio;
        this.progresoxEjercicio = progresoxEjercicio;
        this.imagenEjercicio = imagenEjercicio;
    }

    public CajaRutina()
    {

    }

    public Ejercicio getEjercicio() {
        return ejercicio;
    }

    public void setEjercicio(Ejercicio ejercicio) {
        this.ejercicio = ejercicio;
    }

    public ProgresoxEjercicio getProgresoxEjercicio() {
        return progresoxEjercicio;
    }

    public void setProgresoxEjercicio(ProgresoxEjercicio progresoxEjercicio) {
        this.progresoxEjercicio = progresoxEjercicio;
    }

    public ImagenEjercicio getImagenEjercicio() {
        return imagenEjercicio;
    }

    public void setImagenEjercicio(ImagenEjercicio imagenEjercicio) {
        this.imagenEjercicio = imagenEjercicio;
    }

    @Override
    public String toString() {
        return "CajaRutina{" +
                "ejercicio=" + ejercicio +
                ", progresoxEjercicio=" + progresoxEjercicio +
                ", imagenEjercicio=" + imagenEjercicio +
                '}';
    }

    protected CajaRutina(Parcel in) {
        ejercicio = in.readParcelable(Ejercicio.class.getClassLoader());
        progresoxEjercicio = in.readParcelable(ProgresoxEjercicio.class.getClassLoader());
    }

    public static final Creator<CajaRutina> CREATOR = new Creator<CajaRutina>() {
        @Override
        public CajaRutina createFromParcel(Parcel in) {
            return new CajaRutina(in);
        }

        @Override
        public CajaRutina[] newArray(int size) {
            return new CajaRutina[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(ejercicio, flags);
        dest.writeParcelable(progresoxEjercicio, flags);
    }
}
