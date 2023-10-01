package model.User;

public class RestriccionMedica {

    private int id;
    private String nombreLimitacion;

    public RestriccionMedica(int id, String nombreLimitacion) {
        this.id = id;
        this.nombreLimitacion = nombreLimitacion;
    }

    public RestriccionMedica() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreLimitacion() {
        return nombreLimitacion;
    }

    public void setNombreLimitacion(String nombreLimitacion) {
        this.nombreLimitacion = nombreLimitacion;
    }
}
