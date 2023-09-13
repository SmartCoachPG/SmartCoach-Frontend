package model.User;


import com.google.gson.annotations.SerializedName;

public class Usuario {

    private Long id;
    private String nombre;
    private String email;
    private String contrasenna;
    private byte[] fotoPerfil;
    @SerializedName("admi")
    private Integer admi;
    private String token;


    public Usuario() {
    }

    public Usuario(String nombre, String email, String contrasenna, byte[] fotoPerfil,Integer admi) {
        this.nombre = nombre;
        this.email = email;
        this.contrasenna = contrasenna;
        this.fotoPerfil = fotoPerfil;
        this.admi = admi;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContrasenna() {
        return contrasenna;
    }

    public void setContrasenna(String contrasenna) {
        this.contrasenna = contrasenna;
    }

    public byte[] getFotoPerfil() {
        return fotoPerfil;
    }

    public void setFotoPerfil(byte[] fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }

    public Integer getAdmi() {
        return admi;
    }

    public void setAdmi(Integer admi) {
        this.admi = admi;
    }


}