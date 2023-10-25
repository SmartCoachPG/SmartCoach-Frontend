package api.Exercise;

import java.util.List;

import model.Exercise.Musculo;
import model.User.ObjetivoRutina;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface MusculoApiService {

    @GET("musculo/crear")
    Call<List<Musculo>> getAllCreate();

    @GET("musculo/findByEjercicioId/{ejercicioId}")
    Call<List<String>> findMusculosByEjercicioId(@Path("ejercicioId") Long ejercicioId);


}
