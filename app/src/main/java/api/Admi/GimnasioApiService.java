package api.Admi;

import java.util.List;

import model.Admi.Equipo;
import model.Admi.Gimnasio;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GimnasioApiService {

    @GET("gimnasio")
    Call<List<Gimnasio>> getAll();

    @GET("gimnasio/{id}")
    Call <Gimnasio> findById(@Path("id") Long id);
}
