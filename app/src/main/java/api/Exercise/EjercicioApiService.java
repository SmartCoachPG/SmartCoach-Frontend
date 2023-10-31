package api.Exercise;


import model.Exercise.Ejercicio;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface EjercicioApiService {

    @GET("ejercicio/{ejercicioId}")
    Call<Ejercicio> findById(@Path("ejercicioId") Long ejercicioId);


}
