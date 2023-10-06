package api.User;

import java.util.List;

import model.User.ObjetivoRutina;
import model.User.RestriccionMedica;
import retrofit2.Call;
import retrofit2.http.GET;

public interface RestriccionMedicaApiService {

    @GET("restriccionmedica/libre")
    Call<List<RestriccionMedica>> getAll();
}
