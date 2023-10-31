package api.User;

import java.util.List;
import model.User.ValorEvaluacionFisica;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ValorEvaluacionFisicaApiService {

    @GET("valorevaluacionfisica/libre")
    Call<List<ValorEvaluacionFisica>> getAll();

}
