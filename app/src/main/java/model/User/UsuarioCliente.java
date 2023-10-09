package model.User;

import java.util.Date;

public class UsuarioCliente extends Usuario{

    private String genero;

    private Date fechaDeNacimiento;

    private Integer gimnasioid;

    private Integer nivelActividadFisicaid;

    private Integer objetivoRutinaid;

    private Integer grupoMuscularid;

    public UsuarioCliente() {
    }

    public UsuarioCliente(String genero, Date fechaDeNacimiento, Integer gimnasioid, Integer nivelActividadFisicaid, Integer objetivoRutinaid, Integer grupoMuscularid) {
        this.genero = genero;
        this.fechaDeNacimiento = fechaDeNacimiento;
        this.gimnasioid = gimnasioid;
        this.nivelActividadFisicaid = nivelActividadFisicaid;
        this.objetivoRutinaid = objetivoRutinaid;
        this.grupoMuscularid = grupoMuscularid;
    }

    public UsuarioCliente(String nombre, String email, String contrasenna, String fotoPerfil, Integer admi, String genero, Date fechaDeNacimiento, Integer gimnasioid, Integer nivelActividadFisicaid, Integer objetivoRutinaid, Integer grupoMuscularid) {
        super(nombre, email, contrasenna, fotoPerfil, admi);
        this.genero = genero;
        this.fechaDeNacimiento = fechaDeNacimiento;
        this.gimnasioid = gimnasioid;
        this.nivelActividadFisicaid = nivelActividadFisicaid;
        this.objetivoRutinaid = objetivoRutinaid;
        this.grupoMuscularid = grupoMuscularid;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public Date getFechaDeNacimiento() {
        return fechaDeNacimiento;
    }

    public void setFechaDeNacimiento(Date fechaDeNacimiento) {
        this.fechaDeNacimiento = fechaDeNacimiento;
    }

    public Integer getGimnasioid() {
        return gimnasioid;
    }

    public void setGimnasioid(Integer gimnasioid) {
        this.gimnasioid = gimnasioid;
    }

    public Integer getNivelActividadFisicaid() {
        return nivelActividadFisicaid;
    }

    public void setNivelActividadFisicaid(Integer nivelActividadFisicaid) {
        this.nivelActividadFisicaid = nivelActividadFisicaid;
    }

    public Integer getObjetivoRutinaid() {
        return objetivoRutinaid;
    }

    public void setObjetivoRutinaid(Integer objetivoRutinaid) {
        this.objetivoRutinaid = objetivoRutinaid;
    }

    public Integer getGrupoMuscularid() {
        return grupoMuscularid;
    }

    public void setGrupoMuscularid(Integer grupoMuscularid) {
        this.grupoMuscularid = grupoMuscularid;
    }

    @Override
    public String toString() {
        return "Usuario{"+super.toString()+"}"+
                "UsuarioCliente{" +
                "genero='" + genero + '\'' +
                ", fechaDeNacimiento=" + fechaDeNacimiento +
                ", gimnasioid=" + gimnasioid +
                ", nivelActividadFisicaid=" + nivelActividadFisicaid +
                ", objetivoRutinaid=" + objetivoRutinaid +
                ", grupoMuscularid=" + grupoMuscularid +
                '}';
    }
}