package api.Admi;

import java.util.List;

import model.Admi.Equipo;
import model.Admi.GimnasioItem;
import model.Admi.Item;
import model.Admi.TipoEquipo;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface EquipoApiService {
    @GET("equipo/getTipoEquipoByItemId/{itemId}")
    Call<Integer> findTipoEquipoIdByItemId(@Path("itemId") Long itemId);

    @GET("equipo")
    Call <List<Equipo>> getAll();

    @GET("equipo/getEquipoByEjercicioId/{ejercicioId}")
    Call <List<String>> findEquipoByEjercicioId(@Path("ejercicioId") Long ejercicioId);

    @GET("equipo/{itemId}")
    Call <Equipo> getById(@Path("itemId") Long itemId);

    @GET("equipo/getMusculosByEquipoId/{idItem}")
    Call <List<String>> findMusculoByEquipoId(@Path("idItem") Long itemId);

    @GET("equipo/getTipoName/{idItem}")
    Call <TipoEquipo> findTipoNameByEquipoId(@Path("idItem") Long itemId);

}
