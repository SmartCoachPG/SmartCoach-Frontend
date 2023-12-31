package model.User;

import android.os.Parcel;
import android.os.Parcelable;
import java.sql.Time;
import java.util.Date;


public class ProgresoxEjercicio implements Parcelable {

    private Long id;
    private Integer peso;
    private Date fecha;
    private Integer valoracion;
    private Integer serie;
    private Integer repeticiones;
    private String comentarios;
    private Time descansoEntreSeries;
    private Integer usuarioClienteId;

    public ProgresoxEjercicio(Long id, Integer peso, Date fecha, Integer valoracion, Integer serie, Integer repeticiones, String comentarios, Time descansoEntreSeries, Integer usuarioClienteId) {
        this.id = id;
        this.peso = peso;
        this.fecha = fecha;
        this.valoracion = valoracion;
        this.serie = serie;
        this.repeticiones = repeticiones;
        this.comentarios = comentarios;
        this.descansoEntreSeries = descansoEntreSeries;
        this.usuarioClienteId = usuarioClienteId;
    }

    public ProgresoxEjercicio() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPeso() {
        return peso;
    }

    public void setPeso(Integer peso) {
        this.peso = peso;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Integer getValoracion() {
        return valoracion;
    }

    public void setValoracion(Integer valoracion) {
        this.valoracion = valoracion;
    }

    public Integer getSerie() {
        return serie;
    }

    public void setSerie(Integer serie) {
        this.serie = serie;
    }

    public Integer getRepeticiones() {
        return repeticiones;
    }

    public void setRepeticiones(Integer repeticiones) {
        this.repeticiones = repeticiones;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public Time getDescansoEntreSeries() {
        return descansoEntreSeries;
    }

    public void setDescansoEntreSeries(Time descansoEntreSeries) {
        this.descansoEntreSeries = descansoEntreSeries;
    }

    public Integer getUsuarioClienteId() {
        return usuarioClienteId;
    }

    public void setUsuarioClienteId(Integer usuarioClienteId) {
        this.usuarioClienteId = usuarioClienteId;
    }

    @Override
    public String toString() {
        return "ProgresoxEjercicio{" +
                "id=" + id +
                ", peso=" + peso +
                ", fecha=" + fecha +
                ", valoracion=" + valoracion +
                ", serie=" + serie +
                ", repeticiones=" + repeticiones +
                ", comentarios='" + comentarios + '\'' +
                ", descansoEntreSeries=" + descansoEntreSeries +
                ", usuarioClienteId=" + usuarioClienteId +
                '}';
    }

    protected ProgresoxEjercicio(Parcel in) {
        id = (Long) in.readValue(Long.class.getClassLoader());
        peso = (Integer) in.readValue(Integer.class.getClassLoader());
        fecha = new Date(in.readLong());
        valoracion = (Integer) in.readValue(Integer.class.getClassLoader());
        serie = (Integer) in.readValue(Integer.class.getClassLoader());
        repeticiones = (Integer) in.readValue(Integer.class.getClassLoader());
        comentarios = in.readString();
        descansoEntreSeries = new Time(in.readLong());
        usuarioClienteId = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Creator<ProgresoxEjercicio> CREATOR = new Creator<ProgresoxEjercicio>() {
        @Override
        public ProgresoxEjercicio createFromParcel(Parcel in) {
            return new ProgresoxEjercicio(in);
        }

        @Override
        public ProgresoxEjercicio[] newArray(int size) {
            return new ProgresoxEjercicio[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(peso);
        dest.writeLong(fecha.getTime());
        dest.writeValue(valoracion);
        dest.writeValue(serie);
        dest.writeValue(repeticiones);
        dest.writeString(comentarios);
        dest.writeLong(descansoEntreSeries.getTime());  // Asumiendo que Time es java.sql.Time
        dest.writeValue(usuarioClienteId);
    }
}
