package model.Admi;

public class Gimnasio {

    private Long id;
    private String nombre;
    private Integer pisos;
    private String direccion;
    private String barrio;
    private String imagenGimnasio;

    public Gimnasio(Long id, String nombre, Integer pisos, String direccion, String barrio, String imagenGimnasio) {
        this.id = id;
        this.nombre = nombre;
        this.pisos = pisos;
        this.direccion = direccion;
        this.barrio = barrio;
        this.imagenGimnasio = imagenGimnasio;
    }

    public Gimnasio()
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

    public Integer getPisos() {
        return pisos;
    }

    public void setPisos(Integer pisos) {
        this.pisos = pisos;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getBarrio() {
        return barrio;
    }

    public void setBarrio(String barrio) {
        this.barrio = barrio;
    }

    public String getImagenGimnasio() {
        return imagenGimnasio;
    }

    public void setImagenGimnasio(String imagenGimnasio) {
        this.imagenGimnasio = imagenGimnasio;
    }

    @Override
    public String toString() {
        return "Gimnasio{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", pisos=" + pisos +
                ", direccion='" + direccion + '\'' +
                ", barrio='" + barrio + '\'' +
                ", imagenGimnasio='" + imagenGimnasio + '\'' +
                '}';
    }
}
