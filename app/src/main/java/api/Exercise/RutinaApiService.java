package api.Exercise;

import java.util.List;

import model.Exercise.Rutina;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RutinaApiService {

    @POST("rutina/crear")
    Call<Rutina> create(@Body Rutina rutina);

}
