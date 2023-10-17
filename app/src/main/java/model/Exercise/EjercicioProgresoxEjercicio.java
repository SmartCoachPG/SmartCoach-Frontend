package model.Exercise;

public class EjercicioProgresoxEjercicio {
    private Integer ejercicioId;
    private Integer progresoxEjercicioId;

    public EjercicioProgresoxEjercicio(Integer ejercicioId, Integer progresoxEjercicioId) {
        this.ejercicioId = ejercicioId;
        this.progresoxEjercicioId = progresoxEjercicioId;
    }

    public EjercicioProgresoxEjercicio(){

    }

    public Integer getEjercicioId() {
        return ejercicioId;
    }

    public void setEjercicioId(Integer ejercicioId) {
        this.ejercicioId = ejercicioId;
    }

    public Integer getProgresoxEjercicioId() {
        return progresoxEjercicioId;
    }

    public void setProgresoxEjercicioId(Integer progresoxEjercicioId) {
        this.progresoxEjercicioId = progresoxEjercicioId;
    }
}
