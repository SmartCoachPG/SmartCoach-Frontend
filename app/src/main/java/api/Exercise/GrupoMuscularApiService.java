package api.Exercise;

import java.util.List;
import model.Exercise.GrupoMuscular;
import retrofit2.Call;
import retrofit2.http.GET;

public interface GrupoMuscularApiService {

    @GET("grupomuscular/crear")
    Call<List<GrupoMuscular>> getAll();

}
