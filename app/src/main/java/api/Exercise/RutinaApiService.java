package api.Exercise;

import java.util.List;
import model.Exercise.CajaRutina;
import model.Exercise.Rutina;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RutinaApiService {

    @POST("rutina/crear")
    Call<Rutina> create(@Body Rutina rutina);

    @GET("rutina/getByUser/{id}")
    Call<List<Rutina>> getByUsuarioClienteId(@Path("id") int id);

    @GET("rutina/getEjercicioRut/{idUsuario}/{idRut}")
    Call<List<CajaRutina>> getEjerciciosByRutina(@Path("idUsuario")int idUsuario,@Path("idRut")int idRut);

}
