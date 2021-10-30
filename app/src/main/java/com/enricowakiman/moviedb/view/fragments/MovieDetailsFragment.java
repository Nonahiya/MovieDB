package com.enricowakiman.moviedb.view.fragments;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.enricowakiman.moviedb.R;
import com.enricowakiman.moviedb.adapter.CreditsAdapter;
import com.enricowakiman.moviedb.adapter.ProductionCompanyAdapter;
import com.enricowakiman.moviedb.helper.Const;
import com.enricowakiman.moviedb.helper.DateFormatting;
import com.enricowakiman.moviedb.helper.ItemClickSupport;
import com.enricowakiman.moviedb.model.Credits;
import com.enricowakiman.moviedb.model.Movies;
import com.enricowakiman.moviedb.viewmodel.MovieViewModel;

import java.util.ArrayList;

import jp.wasabeef.glide.transformations.BlurTransformation;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MovieDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MovieDetailsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MovieDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MovieDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MovieDetailsFragment newInstance(String param1, String param2) {
        MovieDetailsFragment fragment = new MovieDetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    private MovieViewModel viewModel;
    private TextView lbl_title, lbl_genre, lbl_desc, lbl_tagline, lbl_releasedate, lbl_rating, lbl_adult, lbl_popularity;
    private ImageView img_poster, img_banner;
    private RecyclerView rv, rv_cast;
    private CardView cv_loading, cv;
    private ProductionCompanyAdapter adapter;
    private CreditsAdapter adapter_cast;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movie_details, container, false);

        viewModel = new ViewModelProvider(MovieDetailsFragment.this).get(MovieViewModel.class);

        cv_loading = view.findViewById(R.id.cv_loading_moviedetails_fragment);
        cv = view.findViewById(R.id.cv_moviedetails_fragment);
        cv.setVisibility(View.INVISIBLE);

        img_poster = view.findViewById(R.id.img_poster_moviedetails_fragment);
        img_banner = view.findViewById(R.id.img_banner_moviedetails_fragment);
        lbl_title = view.findViewById(R.id.lbl_title_moviedetails_fragment);
        lbl_genre = view.findViewById(R.id.lbl_genre_moviedetails_fragment);
        lbl_desc = view.findViewById(R.id.lbl_desc_moviedetails_fragment);
        lbl_tagline = view.findViewById(R.id.lbl_tagline_moviedetails_fragment);
        lbl_releasedate = view.findViewById(R.id.lbl_releasedate_moviedetails_fragment);
        lbl_rating = view.findViewById(R.id.lbl_rating_moviedetails_fragment);
        lbl_adult = view.findViewById(R.id.lbl_adult_moviedetails_fragment);
        lbl_popularity = view.findViewById(R.id.lbl_popularity_moviedetails_fragment);
        rv = view.findViewById(R.id.rv_prodcomp_moviedetails_fragment);
        rv_cast = view.findViewById(R.id.rv_cast_moviedetails_fragment);


        rv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        adapter = new ProductionCompanyAdapter(getActivity());
        rv.setAdapter(adapter);

        rv_cast.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        adapter_cast = new CreditsAdapter(getActivity());
        rv_cast.setAdapter(adapter_cast);

        String movieId = getArguments().getString("movieId");

        viewModel.getMovieById(movieId);
        viewModel.getResultGetMovieById().observe(getActivity(), showResultMovie);

        viewModel.getCreditsById(movieId);
        viewModel.getResultGetCreditsById().observe(getActivity(), showResultCredits);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                cv_loading.setVisibility(View.INVISIBLE);
                cv.setVisibility(View.VISIBLE);
            }
        }, 1000);

        return view;
    }

    private Observer<Movies> showResultMovie = new Observer<Movies>() {
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void onChanged(Movies movies) {
            String genre = "Genre(s): ";
            for (int i = 0; i < movies.getGenres().size(); i++) {
                if (i == movies.getGenres().size()-1) {
                    genre += movies.getGenres().get(i).getName();
                }
                else {
                    genre += movies.getGenres().get(i).getName() + ", ";
                }
            }
            if (movies.getPoster_path() != null && !movies.getPoster_path().toString().isEmpty()) {
                Glide.with(MovieDetailsFragment.this).load(Const.IMG_URL + movies.getPoster_path().toString()).into(img_poster);
            }
            if (movies.getBackdrop_path() != null && !movies.getBackdrop_path().isEmpty()) {
                Glide.with(MovieDetailsFragment.this).load(Const.IMG_URL + movies.getBackdrop_path())
                        .apply(bitmapTransform(new BlurTransformation(5))).into(img_banner);;
            }
            lbl_title.setText(movies.getTitle());
            lbl_genre.setText(genre);
            lbl_desc.setText(movies.getOverview());
            if (movies.getTagline() == "") {
                lbl_tagline.setVisibility(View.GONE);
            }
            else {
                lbl_tagline.setText(movies.getTagline());
            }
            lbl_releasedate.setText(DateFormatting.dateFormat(movies.getRelease_date()));
            lbl_rating.setText("Rating: "+movies.getVote_average()+" ("+movies.getVote_count()+")");
            if (movies.isAdult() == true) {
                lbl_adult.setText("For Adults");
            }
            else {
                lbl_adult.setText("For All Ages");
            }
            lbl_popularity.setText("Popularity: "+movies.getPopularity());
            ArrayList<String> prodcomp_path = new ArrayList<>();
            ArrayList<String> prodcomp_name = new ArrayList<>();
            for (int i = 0; i < movies.getProduction_companies().size(); i++) {
                prodcomp_path.add(movies.getProduction_companies().get(i).getLogo_path());
                prodcomp_name.add(movies.getProduction_companies().get(i).getName());
            }
            adapter.setItems(prodcomp_path, prodcomp_name);
            adapter.notifyDataSetChanged();
            ItemClickSupport.addTo(rv).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                @Override
                public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                    Toast.makeText(getActivity(), prodcomp_name.get(position), Toast.LENGTH_SHORT).show();
                }
            });
        }
    };

    private Observer<Credits> showResultCredits = new Observer<Credits>() {
        @Override
        public void onChanged(Credits credits) {
            adapter_cast.setCredits(credits);
            adapter_cast.notifyDataSetChanged();
            ItemClickSupport.addTo(rv_cast).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                @Override
                public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                    Toast.makeText(getActivity(), credits.getCast().get(position).getName() + " (" + credits.getCast().get(position).getCharacter() + ")", Toast.LENGTH_SHORT).show();
                }
            });
        }
    };
}