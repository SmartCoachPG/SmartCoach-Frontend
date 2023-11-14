package api.Admi;

import java.util.List;

import model.Admi.Mapa;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface MapaApiService {

    @GET("mapa/getMapaByGId/{gimnasioId}")
    Call <List<Mapa>> getMapasByGimnasioId(@Path("gimnasioId") Integer gimnasioId);

    @POST("mapa")
    Call <Mapa> save(@Body Mapa mapa);
}
