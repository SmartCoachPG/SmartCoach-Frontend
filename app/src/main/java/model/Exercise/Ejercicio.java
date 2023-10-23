package model.Exercise;


import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Ejercicio implements Parcelable {

    private Long id;
    private String nombre;
    private String instruccionEjecucion;
    private String instruccionRespiracion;

    public Ejercicio(Long id, String nombre, String instruccionEjecucion, String instruccionRespiracion) {
        this.id = id;
        this.nombre = nombre;
        this.instruccionEjecucion = instruccionEjecucion;
        this.instruccionRespiracion = instruccionRespiracion;
    }

    public Ejercicio(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getInstruccionEjecucion() {
        return instruccionEjecucion;
    }

    public void setInstruccionEjecucion(String instruccionEjecucion) {
        this.instruccionEjecucion = instruccionEjecucion;
    }

    public String getInstruccionRespiracion() {
        return instruccionRespiracion;
    }

    public void setInstruccionRespiracion(String instruccionRespiracion) {
        this.instruccionRespiracion = instruccionRespiracion;
    }

    @Override
    public String toString() {
        return "Ejercicio{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", instruccionEjecucion='" + instruccionEjecucion + '\'' +
                ", instruccionRespiracion='" + instruccionRespiracion + '\'' +
                '}';
    }

    protected Ejercicio(Parcel in) {
        id = (Long) in.readValue(Long.class.getClassLoader());
        nombre = in.readString();
        instruccionEjecucion = in.readString();
        instruccionRespiracion = in.readString();
    }

    public static final Creator<Ejercicio> CREATOR = new Creator<Ejercicio>() {
        @Override
        public Ejercicio createFromParcel(Parcel in) {
            return new Ejercicio(in);
        }

        @Override
        public Ejercicio[] newArray(int size) {
            return new Ejercicio[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeString(nombre);
        dest.writeString(instruccionEjecucion);
        dest.writeString(instruccionRespiracion);
    }
}
