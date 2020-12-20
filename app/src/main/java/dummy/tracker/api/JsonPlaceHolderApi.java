package dummy.tracker.api;

import java.util.List;

import dummy.tracker.objects.Infected;
import retrofit2.Call;
import retrofit2.http.GET;

public interface JsonPlaceHolderApi {

    @GET("users")
    Call<List<Infected>> getInfected();
}
