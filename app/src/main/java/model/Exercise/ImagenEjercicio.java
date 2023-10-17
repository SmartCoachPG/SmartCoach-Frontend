package model.Exercise;

public class ImagenEjercicio {

    private Long id;
    private String imagen;
    private Ejercicio ejercicio;

    public ImagenEjercicio(Long id, String imagen, Ejercicio ejercicio) {
        this.id = id;
        this.imagen = imagen;
        this.ejercicio = ejercicio;
    }

    public ImagenEjercicio()
    {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public Ejercicio getEjercicio() {
        return ejercicio;
    }

    public void setEjercicio(Ejercicio ejercicio) {
        this.ejercicio = ejercicio;
    }
}
