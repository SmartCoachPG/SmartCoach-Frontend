package api.User;

import java.util.List;

import model.User.ObjetivoRutina;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ObjetivoRutinaApiService {

    @GET("objetivorutina/{id}")
    Call<ObjetivoRutina> getById(@Path("id") Long id);

    @GET("objetivorutina")
    Call<List<ObjetivoRutina>> getAll();

    @GET("objetivorutina/crear")
    Call<List<ObjetivoRutina>> getAllCreate();

}
