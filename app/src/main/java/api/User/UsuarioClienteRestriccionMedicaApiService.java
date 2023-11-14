package api.User;

import model.User.UsuarioClienteRestriccionMedica;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UsuarioClienteRestriccionMedicaApiService {

    @POST("usuarioclienterestriccionmedica/crear")
    Call<UsuarioClienteRestriccionMedica> createRestriccion(@Body UsuarioClienteRestriccionMedica usuarioCliente_restriccionMedica);


}

