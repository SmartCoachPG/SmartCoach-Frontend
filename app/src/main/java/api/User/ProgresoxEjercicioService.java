package api.User;

import java.util.List;

import model.User.ProgresoxEjercicio;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ProgresoxEjercicioService {
    @GET("progresoxejercicio/getByUser/{id}")
    Call<List<ProgresoxEjercicio>> getByUsuarioClienteId(@Path("id") Integer id);
}
