package model.User;

public class UnidadMetrica {

    private int id;
    private String metrica;

    public UnidadMetrica(int id, String metrica) {
        this.id = id;
        this.metrica = metrica;
    }

    public UnidadMetrica() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMetrica() {
        return metrica;
    }

    public void setMetrica(String metrica) {
        this.metrica = metrica;
    }
}
