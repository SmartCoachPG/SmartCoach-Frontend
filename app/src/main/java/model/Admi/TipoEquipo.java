package model.Admi;

public class TipoEquipo {

    private Long id;
    private String nombre;

    public TipoEquipo(Long id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public TipoEquipo()
    {

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

    @Override
    public String toString() {
        return "TipoEquipo{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}

