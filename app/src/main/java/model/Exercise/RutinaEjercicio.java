package model.Exercise;

public class RutinaEjercicio {

    private int rutinaId;
    private int ejercicioId;

    public RutinaEjercicio(int rutinaId, int ejercicioId) {
        this.rutinaId = rutinaId;
        this.ejercicioId = ejercicioId;
    }

    public  RutinaEjercicio() {}

    public int getRutinaId() {
        return rutinaId;
    }

    public void setRutinaId(int rutinaId) {
        this.rutinaId = rutinaId;
    }

    public int getEjercicioId() {
        return ejercicioId;
    }

    public void setEjercicioId(int ejercicioId) {
        this.ejercicioId = ejercicioId;
    }
}
