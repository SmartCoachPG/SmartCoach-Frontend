package api.Admi;

import java.util.List;

import model.Admi.UbicacionxItem;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface UbicacionxItemApiService {

    @GET("ubicacionxitem/getByItemId/{itemid}/{gimnasioid}")
    Call<List<UbicacionxItem>> getUbicacionxItemsByItemId(@Path("itemid") int itemid, @Path("gimnasioid") int gimnasioid);
}
