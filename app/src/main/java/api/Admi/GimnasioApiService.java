package api.Admi;

import java.util.List;

import model.Admi.Gimnasio;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface GimnasioApiService {

    @GET("gimnasio")
    Call<List<Gimnasio>> getAll();

    @GET("gimnasio/{id}")
    Call <Gimnasio> findById(@Path("id") Long id);

    @POST("gimnasio")
    Call <Gimnasio> save(@Body Gimnasio gimnasio);

    @DELETE("gimnasio/{id}")
    Call <Void> deleteById(@Path("id") Long id);
}
