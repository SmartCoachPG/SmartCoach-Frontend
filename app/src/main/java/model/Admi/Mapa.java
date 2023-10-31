package model.Admi;

public class Mapa {

    private Long id;
    private Integer nivel;
    private Integer ancho;
    private Integer alto;
    private Integer version;
    private Integer gimnasioId;

    public Mapa(Long id, Integer nivel, Integer ancho, Integer alto, Integer version, Integer gimnasioId) {
        this.id = id;
        this.nivel = nivel;
        this.ancho = ancho;
        this.alto = alto;
        this.version = version;
        this.gimnasioId = gimnasioId;
    }

    public Mapa() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNivel() {
        return nivel;
    }

    public void setNivel(Integer nivel) {
        this.nivel = nivel;
    }

    public Integer getAncho() {
        return ancho;
    }

    public void setAncho(Integer ancho) {
        this.ancho = ancho;
    }

    public Integer getAlto() {
        return alto;
    }

    public void setAlto(Integer alto) {
        this.alto = alto;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Integer getGimnasioId() {
        return gimnasioId;
    }

    public void setGimnasioId(Integer gimnasioId) {
        this.gimnasioId = gimnasioId;
    }

    @Override
    public String toString() {
        return "Mapa{" +
                "id=" + id +
                ", nivel=" + nivel +
                ", ancho=" + ancho +
                ", alto=" + alto +
                ", version=" + version +
                ", gimnasioId=" + gimnasioId +
                '}';
    }
}