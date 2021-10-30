package com.enricowakiman.moviedb.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.enricowakiman.moviedb.model.Credits;
import com.enricowakiman.moviedb.model.Movies;
import com.enricowakiman.moviedb.model.NowPlaying;
import com.enricowakiman.moviedb.model.Upcoming;
import com.enricowakiman.moviedb.repositories.MovieRepository;

public class MovieViewModel extends AndroidViewModel {
    private MovieRepository repository;

    public MovieViewModel(@NonNull Application application){
        super(application);
        repository = MovieRepository.getInstance();
    }

    // ViewModel getMovie by id
    private MutableLiveData<Movies> resultGetMovieById = new MutableLiveData<>();
    public void getMovieById(String movieId) {
        resultGetMovieById = repository.getMovieData(movieId);
    }
    public LiveData<Movies> getResultGetMovieById(){
        return resultGetMovieById;
    }
    // .

    // ViewModel getNowPlaying
    private MutableLiveData<NowPlaying> resultGetNowPlaying = new MutableLiveData<>();
    public void getNowPlaying(int page) { resultGetNowPlaying = repository.getNowPlayingData(page); }
    public LiveData<NowPlaying> getResultGetNowPlaying(){
        return resultGetNowPlaying;
    }
    // .

    // ViewModel getUpcoming
    private MutableLiveData<Upcoming> resultGetUpcoming = new MutableLiveData<>();
    public void getUpcoming(int page) {
        resultGetUpcoming = repository.getUpcomingData(page);
    }
    public LiveData<Upcoming> getResultGetUpcoming(){
        return resultGetUpcoming;
    }
    // .

    // ViewModel getCredits
    private MutableLiveData<Credits> resultGetCreditsById = new MutableLiveData<>();
    public void getCreditsById(String movieId) {
        resultGetCreditsById = repository.getCreditsData(movieId);
    }
    public LiveData<Credits> getResultGetCreditsById(){
        return resultGetCreditsById;
    }
    // .
}
