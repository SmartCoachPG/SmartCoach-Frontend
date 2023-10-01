package model.User;

public class Valor {

    private int id;
    private float valor;
    private int perfilMedicoid;
    private int valorEvaluacionFisicaid;

    public Valor(int id, float valor, int perfilMedicoid, int valorEvaluacionFisicaid) {
        this.id = id;
        this.valor = valor;
        this.perfilMedicoid = perfilMedicoid;
        this.valorEvaluacionFisicaid = valorEvaluacionFisicaid;
    }

    public Valor() {}

    // ... getters y setters ...

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public int getPerfilMedicoid() {
        return perfilMedicoid;
    }

    public void setPerfilMedicoid(int perfilMedicoid) {
        this.perfilMedicoid = perfilMedicoid;
    }

    public int getValorEvaluacionFisicaid() {
        return valorEvaluacionFisicaid;
    }

    public void setValorEvaluacionFisicaid(int valorEvaluacionFisicaid) {
        this.valorEvaluacionFisicaid = valorEvaluacionFisicaid;
    }
}
