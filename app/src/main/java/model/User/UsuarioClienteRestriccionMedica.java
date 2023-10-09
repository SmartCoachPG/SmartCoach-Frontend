package model.User;

public class UsuarioClienteRestriccionMedica {

    private Integer usuarioClienteUsuarioid;
    private Integer restriccionMedicaid;

    public UsuarioClienteRestriccionMedica(Integer usuarioClienteUsuarioid, Integer restriccionMedicaid) {
        this.usuarioClienteUsuarioid = usuarioClienteUsuarioid;
        this.restriccionMedicaid = restriccionMedicaid;
    }

    public UsuarioClienteRestriccionMedica()
    {

    }

    public Integer getUsuarioClienteUsuarioid() {
        return usuarioClienteUsuarioid;
    }

    public void setUsuarioClienteUsuarioid(Integer usuarioClienteUsuarioid) {
        this.usuarioClienteUsuarioid = usuarioClienteUsuarioid;
    }

    public Integer getRestriccionMedicaid() {
        return restriccionMedicaid;
    }

    public void setRestriccionMedicaid(Integer restriccionMedicaid) {
        this.restriccionMedicaid = restriccionMedicaid;
    }

    @Override
    public String toString() {
        return "UsuarioCliente_RestriccionMedica{" +
                "usuarioClienteUsuarioid=" + usuarioClienteUsuarioid +
                ", restriccionMedicaid=" + restriccionMedicaid +
                '}';
    }
}
