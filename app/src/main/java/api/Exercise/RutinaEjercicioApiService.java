package api.Exercise;

import java.util.List;
import model.Exercise.Ejercicio;
import model.Exercise.RutinaEjercicio;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RutinaEjercicioApiService {

    @GET("rutinaejercicio/getByRutinaId/{rutinaId}")
    Call<List<Ejercicio>> getEjerciciosByRutinaId(@Path("rutinaId") int rutinaId);

    @DELETE("rutinaejercicio/{rutinaId}/{ejercicioId}")
    Call<Void> deleteById(@Path("rutinaId")int rutinaId,@Path("ejercicioId") int ejercicioId);

    @POST("rutinaejercicio")
    Call<RutinaEjercicio> add(@Body RutinaEjercicio rutinaEjercicio);
}
