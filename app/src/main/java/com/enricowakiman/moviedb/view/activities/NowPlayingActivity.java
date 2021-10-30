//package com.enricowakiman.moviedb.view.activities;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.lifecycle.Observer;
//import androidx.lifecycle.ViewModelProvider;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import android.os.Bundle;
//import android.view.View;
//
//import com.enricowakiman.moviedb.R;
//import com.enricowakiman.moviedb.adapter.NowPlayingAdapter;
//import com.enricowakiman.moviedb.helper.ItemClickSupport;
//import com.enricowakiman.moviedb.model.NowPlaying;
//import com.enricowakiman.moviedb.viewmodel.MovieViewModel;
//
//public class NowPlayingActivity extends AppCompatActivity {
//
//    private RecyclerView rv_nowplaying;
//    private MovieViewModel viewModel;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_now_playing);
//
//        rv_nowplaying = findViewById(R.id.rv_nowplaying);
//        viewModel = new ViewModelProvider(NowPlayingActivity.this).get(MovieViewModel.class);
//        viewModel.getNowPlaying();
//        viewModel.getResultGetNowPlaying().observe(NowPlayingActivity.this, showNowPlaying);
//    }
//
//    private Observer<NowPlaying> showNowPlaying = new Observer<NowPlaying>() {
//        @Override
//        public void onChanged(NowPlaying nowPlaying) {
//            rv_nowplaying.setLayoutManager(new LinearLayoutManager(NowPlayingActivity.this));
//            NowPlayingAdapter adapter = new NowPlayingAdapter(NowPlayingActivity.this);
//            adapter.setListNowPlaying(nowPlaying.getResults());
//            rv_nowplaying.setAdapter(adapter);
//        }
//    };
//}