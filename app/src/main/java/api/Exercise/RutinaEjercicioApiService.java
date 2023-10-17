package api.Exercise;

import java.util.List;

import model.Exercise.Ejercicio;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RutinaEjercicioApiService {

    @GET("rutinaejercicio/getByRutinaId/{rutinaId}")
    Call<List<Ejercicio>> getEjerciciosByRutinaId(@Path("rutinaId") int rutinaId);

}
