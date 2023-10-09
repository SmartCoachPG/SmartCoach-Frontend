package api.User;

import model.User.Valor;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ValorApiService {

    @POST("valor/crear")
    Call<Valor> createValor(@Body Valor valor);
}
