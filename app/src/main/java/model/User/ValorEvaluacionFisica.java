package model.User;

public class ValorEvaluacionFisica {

    private int id;
    private String nombre;
    private String descripcion;
    private int unidadMetricaId;

    public ValorEvaluacionFisica(int id, String nombre, String descripcion, int unidadMetricaId) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.unidadMetricaId = unidadMetricaId;
    }

    public ValorEvaluacionFisica() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getUnidadMetricaId() {
        return unidadMetricaId;
    }

    public void setUnidadMetricaId(int unidadMetricaId) {
        this.unidadMetricaId = unidadMetricaId;
    }
}
