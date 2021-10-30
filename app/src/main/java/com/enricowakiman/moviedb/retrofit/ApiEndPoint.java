package com.enricowakiman.moviedb.retrofit;

import com.enricowakiman.moviedb.model.Credits;
import com.enricowakiman.moviedb.model.Movies;
import com.enricowakiman.moviedb.model.NowPlaying;
import com.enricowakiman.moviedb.model.Upcoming;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiEndPoint {
    @GET("movie/{movie_id}")
    Call<Movies> getMovieById(
        @Path("movie_id") String movieId,
        @Query("api_key") String apiKey
    );

    @GET("movie/now_playing")
    Call<NowPlaying> getNowPlaying(
            @Query("api_key") String apiKey,
            @Query("page") int pageIndex
    );

    @GET("movie/upcoming")
    Call<Upcoming> getUpcoming(
            @Query("api_key") String apiKey,
            @Query("page") int pageIndex
    );

    @GET("movie/{movie_id}/credits")
    Call<Credits> getCreditsById(
            @Path("movie_id") String movieId,
            @Query("api_key") String apiKey
    );
}
