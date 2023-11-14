package model.User;

public class NivelActividadFisica {
    private Integer id;
    private String titulo;

    public NivelActividadFisica(Integer id, String titulo) {
        this.id = id;
        this.titulo = titulo;
    }

    public NivelActividadFisica() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

}
