package api.User;

import java.util.Map;

import model.Admi.UsuarioAdministrador;
import model.User.Usuario;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;


public interface UsuarioApiService {

        @POST("usuarios/iniciarsesion")
        Call<Usuario> iniciarSesion(@Body Map<String, String> credenciales);

        @GET("usuarios/{id}")
        Call<Usuario> getUsuarioById(@Path("id") Long id);




}
