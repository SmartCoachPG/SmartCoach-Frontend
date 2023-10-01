package model.Exercise;

public class Musculo {

    private Long id;
    private String nombreMusculo;

    public Musculo(Long id, String nombreMusculo) {
        this.id = id;
        this.nombreMusculo = nombreMusculo;
    }

    public Musculo() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreMusculo() {
        return nombreMusculo;
    }

    public void setNombreMusculo(String nombreMusculo) {
        this.nombreMusculo = nombreMusculo;
    }
}
