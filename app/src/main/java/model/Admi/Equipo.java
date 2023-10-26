package model.Admi;

public class Equipo extends Item {

    private String referencia;
    private Integer usuarioId;
    private Integer tipoEquipoId;

    private String descripcion;

    public Equipo(Long id, String imagen, String nombre, String referencia, Integer usuarioId, Integer tipoEquipoId, String descripcion) {
        super(id, imagen, nombre);
        this.referencia = referencia;
        this.usuarioId = usuarioId;
        this.tipoEquipoId = tipoEquipoId;
        this.descripcion = descripcion;
    }

    public Equipo(String referencia, Integer usuarioId, Integer tipoEquipoId, String descripcion) {
        this.referencia = referencia;
        this.usuarioId = usuarioId;
        this.tipoEquipoId = tipoEquipoId;
        this.descripcion = descripcion;
    }

    public Equipo(){}

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public Integer getUsuarioClienteId() {
        return usuarioId;
    }

    public void setUsuarioClienteId(Integer usuarioClienteId) {
        this.usuarioId = usuarioClienteId;
    }

    public Integer getTipoEquipoId() {
        return tipoEquipoId;
    }

    public void setTipoEquipoId(Integer tipoEquipoId) {
        this.tipoEquipoId = tipoEquipoId;
    }

    public Integer getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "Item{"+super.toString()+"}"+
                "Equipo{" +
                "referencia='" + referencia + '\'' +
                ", usuarioId=" + usuarioId +
                ", tipoEquipoId=" + tipoEquipoId +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}

