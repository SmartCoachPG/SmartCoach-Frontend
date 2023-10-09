package model.Exercise;

import java.io.Serializable;
import java.sql.Time;

public class Rutina implements Serializable {

    private int id;
    private Time horaI;
    private Time horaF;
    private String dia;
    private Time duracion;
    private int cantEjercicios;
    private int usuarioClienteId;

    private int grupoMuscularId;


    public Rutina(Time horaI, Time horaF, String dia, Time duracion, int cantEjercicios, int usuarioClienteId, int grupoMuscularId) {
        this.id = id;
        this.horaI = horaI;
        this.horaF = horaF;
        this.dia = dia;
        this.duracion = duracion;
        this.cantEjercicios = cantEjercicios;
        this.usuarioClienteId = usuarioClienteId;
        this.grupoMuscularId = grupoMuscularId;
    }

    public Rutina() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getGrupoMuscularId() {
        return grupoMuscularId;
    }

    public void setGrupoMuscularId(int grupoMuscularId) {
        this.grupoMuscularId = grupoMuscularId;
    }

    @Override
    public String toString() {
        return "Rutina{" +
                "id=" + id +
                ", horaI=" + horaI +
                ", horaF=" + horaF +
                ", dia='" + dia + '\'' +
                ", duracion=" + duracion +
                ", cantEjercicios=" + cantEjercicios +
                ", usuarioClienteId=" + usuarioClienteId +
                ", grupoMuscularId=" + grupoMuscularId +
                '}';
    }
}
