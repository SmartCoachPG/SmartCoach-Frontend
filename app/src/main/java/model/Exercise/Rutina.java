package model.Exercise;

import java.io.Serializable;
import java.sql.Time;

public class Rutina implements Serializable {

    private int id;
    private String nombre;
    private Time horaI;
    private Time horaF;
    private String dia;
    private Time duracion;
    private int cantEjercicios;
    private int usuarioClienteId;

    public Rutina(int id, String nombre, Time horaI, Time horaF, String dia, Time duracion, int cantEjercicios, int usuarioClienteId) {
        this.id = id;
        this.nombre = nombre;
        this.horaI = horaI;
        this.horaF = horaF;
        this.dia = dia;
        this.duracion = duracion;
        this.cantEjercicios = cantEjercicios;
        this.usuarioClienteId = usuarioClienteId;
    }

    public Rutina() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Time getHoraI() {
        return horaI;
    }

    public void setHoraI(Time horaI) {
        this.horaI = horaI;
    }

    public Time getHoraF() {
        return horaF;
    }

    public void setHoraF(Time horaF) {
        this.horaF = horaF;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public Time getDuracion() {
        return duracion;
    }

    public void setDuracion(Time duracion) {
        this.duracion = duracion;
    }

    public int getCantEjercicios() {
        return cantEjercicios;
    }

    public void setCantEjercicios(int cantEjercicios) {
        this.cantEjercicios = cantEjercicios;
    }

    public int getUsuarioClienteId() {
        return usuarioClienteId;
    }

    public void setUsuarioClienteId(int usuarioClienteId) {
        this.usuarioClienteId = usuarioClienteId;
    }

    @Override
    public String toString() {
        return "Rutina{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", horaI=" + horaI +
                ", horaF=" + horaF +
                ", dia='" + dia + '\'' +
                ", duracion=" + duracion +
                ", cantEjercicios=" + cantEjercicios +
                ", usuarioClienteId=" + usuarioClienteId +
                '}';
    }
}
