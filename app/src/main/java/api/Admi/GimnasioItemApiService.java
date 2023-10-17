package api.Admi;

import java.util.List;

import model.Admi.GimnasioItem;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GimnasioItemApiService {

    @GET("gimnasioItem/getByGimnasioId/{gimnasioid}")
    Call <List<GimnasioItem>> getGimnasioItemsByGimnasioid(@Path("gimnasioid") int gimnasioid);
}
