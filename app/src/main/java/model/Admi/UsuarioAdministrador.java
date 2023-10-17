package model.Admi;


import java.util.Date;

import model.User.Usuario;

public class UsuarioAdministrador extends Usuario
{
    private Long cedula;
    private String puesto;
    private Integer verificado;
    private Date fechaDeRenovacion;

    private Integer gimnasioId;

    public UsuarioAdministrador() {
    }

    public UsuarioAdministrador(Long cedula, String puesto, Integer verificado, Date fechaDeRenovacion, Integer gimnasioId) {
        this.cedula = cedula;
        this.puesto = puesto;
        this.verificado = verificado;
        this.fechaDeRenovacion = fechaDeRenovacion;
        this.gimnasioId = gimnasioId;
    }

    public Long getCedula() {
        return cedula;
    }

    public void setCedula(Long cedula) {
        this.cedula = cedula;
    }

    public String getPuesto() {
        return puesto;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }

    public Integer getVerificado() {
        return verificado;
    }

    public void setVerificado(Integer verificado) {
        this.verificado = verificado;
    }

    public Date getFechaDeRenovacion() {
        return fechaDeRenovacion;
    }

    public void setFechaDeRenovacion(Date fechaDeRenovacion) {
        this.fechaDeRenovacion = fechaDeRenovacion;
    }

    public Integer getGimnasioId() {
        return gimnasioId;
    }

    public void setGimnasioId(Integer gimnasioId) {
        this.gimnasioId = gimnasioId;
    }

    @Override
    public String toString() {
        return "UsuarioAdministrador{" +
                "cedula=" + cedula +
                ", puesto='" + puesto + '\'' +
                ", verificado=" + verificado +
                ", fechaDeRenovacion=" + fechaDeRenovacion +
                ", gimnasioId=" + gimnasioId +
                '}';
    }
}
