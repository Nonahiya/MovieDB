package com.enricowakiman.moviedb.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.enricowakiman.moviedb.R;
import com.enricowakiman.moviedb.helper.Const;
import com.enricowakiman.moviedb.model.Credits;

public class CreditsAdapter extends RecyclerView.Adapter<CreditsAdapter.CardViewViewHolder> {

    private Context context;
    private Credits credits;
    private Credits getCredits(){return credits;}
    public void setCredits(Credits credits){
        this.credits = credits;
    }
    public CreditsAdapter(Context context){
        this.context = context;
    }

    @NonNull
    @Override
    public CardViewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cast, parent, false);
        return new CreditsAdapter.CardViewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewViewHolder holder, int position) {
        final Credits.Cast casts = getCredits().getCast().get(position);
        holder.lbl_real.setText(casts.getName());
        holder.lbl_character.setText(casts.getCharacter());
        String path = casts.getProfile_path();
        if (path != null && !path.isEmpty()) {
            Glide.with(context).load(Const.IMG_URL + path).into(holder.img_cast);
        }
    }

    @Override
    public int getItemCount() {
        return getCredits() == null ? 0 : getCredits().getCast().size();
    }

    public class CardViewViewHolder extends RecyclerView.ViewHolder {
        ImageView img_cast;
        TextView lbl_real, lbl_character;
        CardView cv;
        public CardViewViewHolder(@NonNull View itemView) {
            super(itemView);
            img_cast = itemView.findViewById(R.id.img_cast);
            lbl_real = itemView.findViewById(R.id.lbl_realname_cast);
            lbl_character = itemView.findViewById(R.id.lbl_charactername_cast);
            cv = itemView.findViewById(R.id.cv_cast);
        }
    }
}
