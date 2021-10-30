package com.enricowakiman.moviedb.repositories;

import androidx.lifecycle.MutableLiveData;

import com.enricowakiman.moviedb.helper.Const;
import com.enricowakiman.moviedb.model.Credits;
import com.enricowakiman.moviedb.model.Movies;
import com.enricowakiman.moviedb.model.NowPlaying;
import com.enricowakiman.moviedb.model.Upcoming;
import com.enricowakiman.moviedb.retrofit.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieRepository {
    private static MovieRepository repository;

    private MovieRepository(){}

    public static MovieRepository getInstance(){
        if(repository == null){
            repository = new MovieRepository();
        }
        return repository;
    }

    public MutableLiveData<Movies> getMovieData(String movieId){
        final MutableLiveData<Movies> result = new MutableLiveData<>();

        ApiService.endpoint().getMovieById(movieId, Const.API_KEY).enqueue(new Callback<Movies>() {
            @Override
            public void onResponse(Call<Movies> call, Response<Movies> response) {
                result.setValue(response.body());
            }

            @Override
            public void onFailure(Call<Movies> call, Throwable t) {

            }
        });

        return result;
    }

    public MutableLiveData<NowPlaying> getNowPlayingData(){
        final MutableLiveData<NowPlaying> result = new MutableLiveData<>();

        ApiService.endpoint().getNowPlaying(Const.API_KEY).enqueue(new Callback<NowPlaying>() {
            @Override
            public void onResponse(Call<NowPlaying> call, Response<NowPlaying> response) {
                result.setValue(response.body());
            }

            @Override
            public void onFailure(Call<NowPlaying> call, Throwable t) {

            }
        });

        return result;
    }

    public MutableLiveData<Upcoming> getUpcomingData(){
        final MutableLiveData<Upcoming> result = new MutableLiveData<>();

        ApiService.endpoint().getUpcoming(Const.API_KEY).enqueue(new Callback<Upcoming>() {
            @Override
            public void onResponse(Call<Upcoming> call, Response<Upcoming> response) {
                result.setValue(response.body());
            }

            @Override
            public void onFailure(Call<Upcoming> call, Throwable t) {

            }
        });

        return result;
    }

    public MutableLiveData<Credits> getCreditsData(String movieId){
        final MutableLiveData<Credits> result = new MutableLiveData<>();

        ApiService.endpoint().getCreditsById(movieId, Const.API_KEY).enqueue(new Callback<Credits>() {
            @Override
            public void onResponse(Call<Credits> call, Response<Credits> response) {
                result.setValue(response.body());
            }

            @Override
            public void onFailure(Call<Credits> call, Throwable t) {

            }
        });

        return result;
    }
}
