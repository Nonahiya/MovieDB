package com.enricowakiman.moviedb.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.enricowakiman.moviedb.R;
import com.enricowakiman.moviedb.helper.Const;

import java.util.List;

public class ProductionCompanyAdapter extends RecyclerView.Adapter<ProductionCompanyAdapter.CardViewViewHolder> {

    private Context context;
    private List<String> prodcomp_path, prodcomp_name;

    public ProductionCompanyAdapter(Context context){
        this.context = context;
    }

    public void setItems(List<String> prodcomp_path, List<String> prodcomp_name) {
        this.prodcomp_path = prodcomp_path;
        this.prodcomp_name = prodcomp_name;
    }


    @NonNull
    @Override
    public ProductionCompanyAdapter.CardViewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_prodcomp, parent, false);
        return new ProductionCompanyAdapter.CardViewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductionCompanyAdapter.CardViewViewHolder holder, int position) {
        String path = prodcomp_path.get(position);
        if (path != null && !path.isEmpty()) {
            Glide.with(context).load(Const.IMG_URL + path).into(holder.img);
        }
    }

    @Override
    public int getItemCount() {
        int itemCount;
        if (prodcomp_path != null && !prodcomp_path.isEmpty()) {
            itemCount = prodcomp_path.size();
        }
        else {
            itemCount = 0;
        }
        return itemCount;
    }

    public class CardViewViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        CardView cv;
        public CardViewViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img_prodcomp);
            cv = itemView.findViewById(R.id.cv_prodcomp);
        }
    }
}
