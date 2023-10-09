package api.User;

import model.User.PerfilMedico;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface PerfilMedicoApiService {

    @POST ("perfilmedico/crear")
    Call<PerfilMedico> createPerfilMedico(@Body PerfilMedico perfilMedico);

}
