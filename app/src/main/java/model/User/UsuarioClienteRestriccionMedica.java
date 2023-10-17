package model.User;

public class UsuarioClienteRestriccionMedica {

    private Integer usuarioClienteid;
    private Integer restriccionMedicaid;

    public UsuarioClienteRestriccionMedica(Integer usuarioClienteid, Integer restriccionMedicaid) {
        this.usuarioClienteid = usuarioClienteid;
        this.restriccionMedicaid = restriccionMedicaid;
    }

    public UsuarioClienteRestriccionMedica()
    {

    }

    public Integer getUsuarioClienteid() {
        return usuarioClienteid;
    }

    public void setUsuarioClienteid(Integer usuarioClienteid) {
        this.usuarioClienteid = usuarioClienteid;
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
                "usuarioClienteUsuarioid=" + usuarioClienteid +
                ", restriccionMedicaid=" + restriccionMedicaid +
                '}';
    }
}
