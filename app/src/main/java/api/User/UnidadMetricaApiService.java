package api.User;

import java.util.List;

import model.User.UnidadMetrica;
import retrofit2.Call;
import retrofit2.http.GET;

public interface UnidadMetricaApiService {

    @GET("unidadmetrica/libre")
    Call<List<UnidadMetrica>> getAll();

}
