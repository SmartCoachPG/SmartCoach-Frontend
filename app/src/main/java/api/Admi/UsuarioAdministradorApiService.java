package api.Admi;

import api.User.UsuarioApiService;
import model.Admi.UsuarioAdministrador;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;


public interface UsuarioAdministradorApiService {

    @GET("usuarioadministrador/{id}")
    Call<UsuarioAdministrador> getUsuarioById(@Path("id") Long id);

    @PUT("usuarioadministrador/{id}")
    Call<UsuarioAdministrador> updateUsuarioAdministrador(@Path("id") Long id, @Body UsuarioAdministrador usuarioAdministrador);


}

