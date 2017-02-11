package retrofit;

import pojo.Results;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by shriroop on 10-Feb-17.
 */

public interface ZapposService {
    @GET("Search?")
    Call<Results> loadResults(@Query("term") String term, @Query("key") String key);
}
