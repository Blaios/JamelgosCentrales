package dummy.tracker.api;

import java.util.List;

import dummy.tracker.objects.Infected;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface JsonPlaceHolderApi {

    @GET("users")
    Call<List<Infected>> getInfected();

    @POST("users")
    Call<Infected> createInfected(@Body Infected infected);
}
