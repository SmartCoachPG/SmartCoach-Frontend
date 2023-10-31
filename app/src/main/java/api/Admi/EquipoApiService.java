package api.Admi;

import java.util.List;

import model.Admi.Equipo;
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

    @GET("equipo/{id}")
    Call <Equipo> getById(@Path("id")long equipoId);

    @GET("equipo/getTipoName/{idItem}")
    Call <TipoEquipo> getTipoNameByEquipoId(@Path("idItem")long idItem);

    @GET("equipo/getMusculosByEquipoId/{idItem}")
    Call <List<String>> getMusculosByEquipoId(@Path("idItem")long idItem);
}
