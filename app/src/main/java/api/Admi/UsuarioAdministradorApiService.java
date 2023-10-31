package api.Admi;

import model.Admi.UsuarioAdministrador;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;


public interface UsuarioAdministradorApiService {

    @GET("usuarioadministrador/{id}")
    Call<UsuarioAdministrador> getUsuarioById(@Path("id") Long id);

    @PUT("usuarioadministrador/{id}")
    Call<UsuarioAdministrador> updateUsuarioAdministrador(@Path("id") Long id, @Body UsuarioAdministrador usuarioAdministrador);

    @POST("usuarioadministrador/crear")
    Call<UsuarioAdministrador> createUsuarioAdministrador(@Body UsuarioAdministrador usuarioAdministrador);

    @DELETE("usuarioadministrador/{id}")
    Call<Void> deleteUsuarioAdministrador(@Path("id")Long id);

}

