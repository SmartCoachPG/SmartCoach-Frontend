package api.Admi;

import java.util.List;

import model.Admi.Equipo;
import model.Admi.GimnasioItem;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface EquipoApiService {
    @GET("equipo/getTipoEquipoByItemId/{itemId}")
    Call<Integer> findTipoEquipoIdByItemId(@Path("itemId") Long itemId);

    @GET("equipo")
    Call <List<Equipo>> getAll();

}
