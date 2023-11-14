package api.Exercise;

import model.User.ProgresoxEjercicio;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface EjercicioProgresoxEjercicioApiService {

    @GET("ejercicio-progresoxejercicio/findByEjeIdLatest/{ejercicioId}/{usuarioId}")
    Call<ProgresoxEjercicio> getLatestProgresoxEjercicio(@Path("ejercicioId") int ejercicioId, @Path("usuarioId") int usuarioId);

}
