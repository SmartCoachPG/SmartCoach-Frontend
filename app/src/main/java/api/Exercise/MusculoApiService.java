package api.Exercise;

import java.util.List;

import model.Exercise.Musculo;
import model.User.ObjetivoRutina;
import retrofit2.Call;
import retrofit2.http.GET;

public interface MusculoApiService {

    @GET("musculo/crear")
    Call<List<Musculo>> getAllCreate();

}
