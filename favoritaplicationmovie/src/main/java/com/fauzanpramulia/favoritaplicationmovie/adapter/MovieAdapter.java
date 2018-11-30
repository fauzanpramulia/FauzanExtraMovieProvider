package com.fauzanpramulia.favoritaplicationmovie.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fauzanpramulia.favoritaplicationmovie.DetailActivity;
import com.fauzanpramulia.favoritaplicationmovie.MovieItems;
import com.fauzanpramulia.favoritaplicationmovie.R;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieHolder>  {
    ArrayList<MovieItems> dataFilm;
    Context context;
    public void setDataFilm(ArrayList<MovieItems> films){
        this.dataFilm = films;
        notifyDataSetChanged();
    }
    public MovieAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public MovieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
        return new MovieHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieHolder holder, final int position) {
        MovieItems movie = dataFilm.get(position);
        holder.textTitle.setText(movie.getTitle());
        holder.textRating.setText(String.valueOf(movie.getVote_average()));
        holder.textTanggalRilis.setText(movie.getRelease_date());
        String url = "http://image.tmdb.org/t/p/w300" + movie.getPoster_path();
        Glide.with(holder.itemView)
                .load(url)
                .into(holder.imagePoster);
        holder.view_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // sending data process
                Intent i = new Intent(context, DetailActivity.class);
//                    Uri uri = Uri.parse(CONTENT_URI+"/"+ v.getId());
//                    i.setData(uri);
                i.putExtra(DetailActivity.EXTRA_DETAIL_MOVIE, dataFilm.get(position));
                context.startActivity(i);

            }
        });
    }

    @Override
    public int getItemCount() {
        if (dataFilm !=null){
            return dataFilm.size();
        }
        return 0;
    }

    public class MovieHolder extends RecyclerView.ViewHolder {
        ImageView imagePoster;
        TextView textTitle;
        TextView textRating;
        TextView textTanggalRilis;
        CardView view_container;
        public MovieHolder(View itemView) {
            super(itemView);
            imagePoster = itemView.findViewById(R.id.img_poster);
            textTitle = itemView.findViewById(R.id.textTitle);
            textRating = itemView.findViewById(R.id.textRating);
            textTanggalRilis = itemView.findViewById(R.id.textTanggalRilis);
            view_container = (CardView)itemView.findViewById(R.id.container);
        }
    }
}
