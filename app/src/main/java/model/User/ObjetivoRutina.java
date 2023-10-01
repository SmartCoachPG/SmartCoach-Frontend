package model.User;


public class ObjetivoRutina {

    private Long id;
    private String nombre;

    public ObjetivoRutina(Long id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public ObjetivoRutina() {
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

}

