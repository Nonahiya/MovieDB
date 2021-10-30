package com.enricowakiman.moviedb.adapter;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.enricowakiman.moviedb.R;
import com.enricowakiman.moviedb.helper.Const;
import com.enricowakiman.moviedb.helper.DateFormatting;
import com.enricowakiman.moviedb.model.NowPlaying;

import java.util.List;

public class NowPlayingAdapter extends RecyclerView.Adapter<NowPlayingAdapter.CardViewViewHolder> {

    private Context context;
    private List<NowPlaying.Results> listNowPlaying;
    private List<NowPlaying.Results> getListNowPlaying(){return listNowPlaying;}
    public void setListNowPlaying(List<NowPlaying.Results> listNowPlaying){
        this.listNowPlaying = listNowPlaying;
    }
    public void addListNowPlaying(List<NowPlaying.Results> listNowPlaying){
        this.listNowPlaying.addAll(listNowPlaying);
    }
    public NowPlayingAdapter(Context context){
        this.context = context;
    }

    @NonNull
    @Override
    public CardViewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_now_playing, parent, false);
        return new NowPlayingAdapter.CardViewViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull CardViewViewHolder holder, int position) {
        final NowPlaying.Results results = getListNowPlaying().get(position);
        holder.lbl_title.setText(results.getTitle());
        holder.lbl_overview.setText(results.getOverview());
        holder.lbl_release_date.setText(DateFormatting.dateFormat(results.getRelease_date()));
        if (results.getPoster_path() != null && !results.getPoster_path().isEmpty()) {
            Glide.with(context).load(Const.IMG_URL + results.getPoster_path()).into(holder.img_poster);
        }
//        holder.cv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                Intent intent = new Intent(context, MovieDetailsActivity.class);
////                intent.putExtra("movie_id", ""+results.getId());
////                context.startActivity(intent);
////                Bundle bundle = new Bundle();
////                bundle.putString("movieId", ""+results.getId());
////                Navigation.findNavController(v).navigate((R.id.action_nowPlayingFragment_to_movieDetailsFragment), (bundle));
//            }
//        });
    }

    @Override
    public int getItemCount() {
        int itemCount;
        if (getListNowPlaying() != null && !getListNowPlaying().isEmpty()) {
            itemCount = getListNowPlaying().size();
        }
        else {
            itemCount = 0;
        }
        return itemCount;
    }

    public class CardViewViewHolder extends RecyclerView.ViewHolder {
        ImageView img_poster;
        TextView lbl_title, lbl_overview, lbl_release_date;
        CardView cv;
        public CardViewViewHolder(@NonNull View itemView) {
            super(itemView);
            img_poster = itemView.findViewById(R.id.img_poster_card_nowplaying);
            lbl_title = itemView.findViewById(R.id.lbl_title_card_nowplaying);
            lbl_overview = itemView.findViewById(R.id.lbl_overview_card_nowplaying);
            lbl_release_date = itemView.findViewById(R.id.lbl_releasedate_card_nowplaying);
            cv = itemView.findViewById(R.id.cv_card_nowplaying);
        }
    }


}
