package model.Exercise;


public class Ejercicio {

    private Long id;
    private String nombre;
    private String instruccionEjecucion;
    private String instruccionRespiracion;

    public Ejercicio(Long id, String nombre, String instruccionEjecucion, String instruccionRespiracion) {
        this.id = id;
        this.nombre = nombre;
        this.instruccionEjecucion = instruccionEjecucion;
        this.instruccionRespiracion = instruccionRespiracion;
    }

    public Ejercicio(){

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

    public String getInstruccionEjecucion() {
        return instruccionEjecucion;
    }

    public void setInstruccionEjecucion(String instruccionEjecucion) {
        this.instruccionEjecucion = instruccionEjecucion;
    }

    public String getInstruccionRespiracion() {
        return instruccionRespiracion;
    }

    public void setInstruccionRespiracion(String instruccionRespiracion) {
        this.instruccionRespiracion = instruccionRespiracion;
    }

    @Override
    public String toString() {
        return "Ejercicio{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", instruccionEjecucion='" + instruccionEjecucion + '\'' +
                ", instruccionRespiracion='" + instruccionRespiracion + '\'' +
                '}';
    }
}
