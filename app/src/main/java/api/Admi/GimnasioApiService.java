package api.Admi;

import java.util.List;

import model.Admi.Equipo;
import model.Admi.Gimnasio;
import retrofit2.Call;
import retrofit2.http.GET;

public interface GimnasioApiService {

    @GET("gimnasio")
    Call<List<Gimnasio>> getAll();
}
