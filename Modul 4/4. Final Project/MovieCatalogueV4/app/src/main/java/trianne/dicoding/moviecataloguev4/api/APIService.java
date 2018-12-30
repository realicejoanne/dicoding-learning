package trianne.dicoding.moviecataloguev4.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import trianne.dicoding.moviecataloguev4.entity.Results;

public interface APIService {
    @GET("3/discover/movie")
    Call<Results> getAllMovies(@Query("api_key") String api_key,
                               @Query("language") String language,
                               @Query("sort_by") String sort_by,
                               @Query("page") String page);
    @GET("/3/search/movie")
    Call<Results> getSearchMovie(@Query("api_key") String api_key,
                                        @Query("language") String language,
                                        @Query("query") String query,
                                        @Query("page") String page);

    @GET("/3/movie/now_playing")
    Call<Results> getNowPlayingMovie(@Query("api_key") String api_key,
                                            @Query("language") String language);

    @GET("/3/movie/upcoming")
    Call<Results> getUpcomingMovie(@Query("api_key") String api_key,
                                          @Query("language") String language);
}
