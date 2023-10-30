package api.Admi;

import java.util.List;

import model.Admi.Gimnasio;
import model.Admi.GimnasioItem;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface GimnasioItemApiService {

    @GET("gimnasioItem/getByGimnasioId/{gimnasioid}")
    Call <List<GimnasioItem>> getGimnasioItemsByGimnasioid(@Path("gimnasioid") int gimnasioid);

    @POST("gimnasioItem/add")
    Call <GimnasioItem> addGimnasioItem(@Body GimnasioItem gimnasioItem);

    @DELETE("gimnasioItem/delete/{gimnasioid}/{itemid}")
    Call <Void> deleteGimnasioItem(@Path("gimnasioid") int gimnasioid,@Path("itemid") int itemid);

    @PUT("gimnasioItem/update")
    Call <GimnasioItem> updateGimnasioItem(@Body GimnasioItem gimnasioItem);

    @GET("gimnasioItem/{gimnasioid}/{itemid}")
    Call <GimnasioItem> getGimnasioItem(@Path("gimnasioid")int gimnasioid,@Path("itemid")int itemid);
}
