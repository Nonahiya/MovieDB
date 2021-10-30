package com.enricowakiman.moviedb.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.enricowakiman.moviedb.R;
import com.enricowakiman.moviedb.helper.Const;
import com.enricowakiman.moviedb.model.Movies;
import com.enricowakiman.moviedb.viewmodel.MovieViewModel;

public class MovieDetailsActivity extends AppCompatActivity {

    private MovieViewModel viewModel;
    private TextView lbl_id, lbl_title, lbl_genre, lbl_desc, lbl_tagline, lbl_releasedate, lbl_rating, lbl_adult;
    private ImageView img_poster;
    private String movie_id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        viewModel = new ViewModelProvider(MovieDetailsActivity.this).get(MovieViewModel.class);

        Intent intent = getIntent();
        movie_id = intent.getStringExtra("movie_id");

        img_poster = findViewById(R.id.img_poster_moviedetails);
        lbl_id = findViewById(R.id.lbl_id_moviedetails);
        lbl_title = findViewById(R.id.lbl_title_moviedetails);
        lbl_genre = findViewById(R.id.lbl_genre_moviedetails);
        lbl_desc = findViewById(R.id.lbl_desc_moviedetails);
        lbl_tagline = findViewById(R.id.lbl_tagline_moviedetails);
        lbl_releasedate = findViewById(R.id.lbl_releasedate_moviedetails);
        lbl_rating = findViewById(R.id.lbl_rating_moviedetails);
        lbl_adult = findViewById(R.id.lbl_adult_moviedetails);

        viewModel.getMovieById(movie_id);
        viewModel.getResultGetMovieById().observe(MovieDetailsActivity.this, showResultMovie);

    }

    private Observer<Movies> showResultMovie = new Observer<Movies>() {
        @Override
        public void onChanged(Movies movies) {
            String genre = "";
            for (int i = 0; i < movies.getGenres().size(); i++) {
                if (i == movies.getGenres().size()-1) {
                    genre += movies.getGenres().get(i).getName();
                }
                else {
                    genre += movies.getGenres().get(i).getName() + ", ";
                }
            }
            String img_path = movies.getPoster_path().toString();
            Glide.with(MovieDetailsActivity.this).load(Const.IMG_URL + img_path).into(img_poster);
            lbl_id.setText(movie_id);
            lbl_title.setText(movies.getTitle());
            lbl_genre.setText(genre);
            lbl_desc.setText(movies.getOverview());
            if (movies.getTagline() == "") {
                lbl_tagline.setVisibility(View.GONE);
            }
            else {
                lbl_tagline.setText(movies.getTagline());
            }
            lbl_releasedate.setText(movies.getRelease_date());
            lbl_rating.setText("Rating: "+movies.getVote_average());
            if (movies.isAdult() == true) {
                lbl_adult.setText("For Adults");
            }
            else {
                lbl_adult.setText("For All Ages");
            }
        }
    };

    @Override
    public void onBackPressed() {
        finish();
    }
}