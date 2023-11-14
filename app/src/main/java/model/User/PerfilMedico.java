package model.User;

import java.util.Date;

public class PerfilMedico {

    private int id;
    private Date fecha;
    private Integer usuarioClienteUsuarioid;

    public PerfilMedico(int id, Date fecha, Integer usuarioClienteUsuarioid) {
        this.id = id;
        this.fecha = fecha;
        this.usuarioClienteUsuarioid = usuarioClienteUsuarioid;
    }

    public PerfilMedico() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }


    public Integer getUsuarioClienteUsuarioid() {
        return usuarioClienteUsuarioid;
    }

    public void setUsuarioClienteUsuarioid(Integer usuarioClienteUsuarioid) {
        this.usuarioClienteUsuarioid = usuarioClienteUsuarioid;
    }
}

