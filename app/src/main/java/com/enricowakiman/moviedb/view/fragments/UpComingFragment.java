package com.enricowakiman.moviedb.view.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.enricowakiman.moviedb.R;
import com.enricowakiman.moviedb.adapter.UpcomingAdapter;
import com.enricowakiman.moviedb.helper.ItemClickSupport;
import com.enricowakiman.moviedb.model.Upcoming;
import com.enricowakiman.moviedb.viewmodel.MovieViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UpComingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UpComingFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public UpComingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UpComingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UpComingFragment newInstance(String param1, String param2) {
        UpComingFragment fragment = new UpComingFragment();
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

    private RecyclerView rv_upcoming;
    private MovieViewModel viewModel;
    private ProgressBar loading, loading_scroller;
    private UpcomingAdapter adapter;
    private int page = 1;
    Boolean isLoading = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_up_coming, container, false);

        loading = view.findViewById(R.id.progressBar_upcoming_fragment);
        loading_scroller = view.findViewById(R.id.loadingBar_upcoming_fragment);
        loading_scroller.setVisibility(View.INVISIBLE);

        rv_upcoming = view.findViewById(R.id.rv_upcoming_fragment);
        rv_upcoming.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new UpcomingAdapter(getActivity());
        rv_upcoming.setAdapter(adapter);

        viewModel = new ViewModelProvider(getActivity()).get(MovieViewModel.class);
        viewModel.getUpcoming(page);
        viewModel.getResultGetUpcoming().observe(getActivity(), showUpcoming);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                loading.setVisibility(View.INVISIBLE);
            }
        }, 1000);

        rv_upcoming.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager llm = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (isLoading) {
                    if (llm.findLastVisibleItemPosition() >= (20*(page-1)+1)) {
                        isLoading = false;
                        loading(isLoading);
                    }
                }
                if (!isLoading && (llm.findFirstVisibleItemPosition() >= (20*page)-10 && llm.findFirstVisibleItemPosition() <= 20*page)) {
                    isLoading = true;
                    loading(isLoading);
                    getMoreData();
                }
            }
        });

        return view;
    }

    private void loading(Boolean isLoading) {
        if (isLoading) {
            loading_scroller.setVisibility(View.VISIBLE);
        } else {
            loading_scroller.setVisibility(View.INVISIBLE);
        }
    }

    private void getMoreData() {
        page += 1;
        viewModel.getUpcoming(page);
        viewModel.getResultGetUpcoming().observe(getActivity(), showUpcomingNextPage);
    }

    private Observer<Upcoming> showUpcoming = new Observer<Upcoming>() {
        @Override
        public void onChanged(Upcoming upComing) {
            adapter.setListUpcoming(upComing.getResults());
            rv_upcoming.setAdapter(adapter);

            ItemClickSupport.addTo(rv_upcoming).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                @Override
                public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString("movieId", ""+upComing.getResults().get(position).getId());
                    Navigation.findNavController(v).navigate((R.id.action_upComingFragment_to_movieDetailsFragment), (bundle));
                }
            });
        }
    };

    private Observer<Upcoming> showUpcomingNextPage = new Observer<Upcoming>() {
        @Override
        public void onChanged(Upcoming upComing) {
            adapter.addListUpcoming(upComing.getResults());
            adapter.notifyItemInserted((20*page));
            int currentSize = 20*page;
            int nextSize = currentSize+20;
            while (currentSize < nextSize) {
                currentSize++;
            }
            adapter.notifyDataSetChanged();
        }
    };
}