package model.User;

import android.os.Parcel;

import java.io.Serializable;

public class RestriccionMedica implements Serializable {

    private int id;
    private String nombreLimitacion;

    public RestriccionMedica(int id, String nombreLimitacion) {
        this.id = id;
        this.nombreLimitacion = nombreLimitacion;
    }

    public RestriccionMedica() {}

    protected RestriccionMedica(Parcel in) {
        id = in.readInt();
        nombreLimitacion = in.readString();
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreLimitacion() {
        return nombreLimitacion;
    }

    public void setNombreLimitacion(String nombreLimitacion) {
        this.nombreLimitacion = nombreLimitacion;
    }

    @Override
    public String toString() {
        return "RestriccionMedica{" +
                "id=" + id +
                ", nombreLimitacion='" + nombreLimitacion + '\'' +
                '}';
    }
}
