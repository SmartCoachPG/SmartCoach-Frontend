package api.Exercise;

import java.util.List;
import model.Exercise.ImagenEjercicio;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ImagenEjercicioApiService {

    @GET("imagenejercicio/findByEjercicioid/{id}")
    Call<List<ImagenEjercicio>> findByEjercicioid(@Path("id") int id);


}
