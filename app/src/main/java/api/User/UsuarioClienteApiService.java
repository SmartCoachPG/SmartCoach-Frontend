package api.User;

import model.Admi.UsuarioAdministrador;
import model.User.Usuario;
import model.User.UsuarioCliente;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UsuarioClienteApiService {

    @GET("usuariocliente/{id}")
    Call<UsuarioCliente> getUsuarioById(@Path("id") Long id);

    @PUT("usuariocliente/{id}")
    Call<UsuarioCliente> updateUsuarioCliente(@Path("id") Long id, @Body UsuarioCliente usuarioCliente);

    @POST("usuariocliente/crear")
    Call<UsuarioCliente> createUsuarioCliente(@Body UsuarioCliente usuarioCliente);

    @DELETE("usuariocliente/{id}")
    Call<Void> deleteUsuarioCliente(@Path("id")Long id);

    @GET("usuariocliente/crearRutina/{id}")
    Call<Void> crearRutina(@Path("id") Long id);
}
