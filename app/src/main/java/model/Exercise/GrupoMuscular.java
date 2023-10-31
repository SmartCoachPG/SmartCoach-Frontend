package model.Exercise;

public class GrupoMuscular {

    private Long id;
    private String nombre;
    private Integer ubicacion;

    public GrupoMuscular(Long id, String nombre, Integer ubicacion) {
        this.id = id;
        this.nombre = nombre;
        this.ubicacion = ubicacion;
    }

    public GrupoMuscular(){}

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

    public Integer getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(Integer ubicacion) {
        this.ubicacion = ubicacion;
    }

    @Override
    public String toString() {
        return "GrupoMuscular{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", ubicacion=" + ubicacion +
                '}';
    }
}
