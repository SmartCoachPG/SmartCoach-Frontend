package api.User;

import java.util.List;

import model.User.ObjetivoRutina;
import model.User.RestriccionMedica;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RestriccionMedicaApiService {

    @GET("restriccionmedica/libre")
    Call<List<RestriccionMedica>> findAll(@Query("query") String query);
}
