package api.Admi;

import java.util.List;

import model.Admi.UbicacionxItem;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UbicacionxItemApiService {

    @GET("ubicacionxitem/getByItemId/{itemid}/{gimnasioid}")
    Call<List<UbicacionxItem>> getUbicacionxItemsByItemId(@Path("itemid") int itemid, @Path("gimnasioid") int gimnasioid);

    @GET("ubicacionxitem/getByGimnasioId/{gimnasioid}")
    Call<List<UbicacionxItem>> getByGimnasioId(@Path("gimnasioid") int gimnasioid);

    @PUT("ubicacionxitem/update")
    Call<UbicacionxItem> updateUbicacionxItem(@Body UbicacionxItem ubicacionxItem);

    @POST("ubicacionxitem/add")
    Call<UbicacionxItem> addUbicacionxItem(@Body UbicacionxItem ubicacionxItem);
}
