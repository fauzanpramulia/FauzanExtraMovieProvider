package com.fauzanpramulia.fauzanextramovies;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;
import com.fauzanpramulia.fauzanextramovies.model.MovieItems;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {
    @BindView(R.id.detail_title)TextView detailTitle;
    @BindView(R.id.detail_overview)TextView detailDesk;
    @BindView(R.id.detail_rating)TextView detailRating;
    @BindView(R.id.detail_TanggalRilis)TextView detailTanggal;
    @BindView(R.id.detail_poster) ImageView imgView;
    @BindView(R.id.myToggleButton) ToggleButton toggleButton;
    public static String EXTRA_DETAIL_MOVIE = "extra_detail_movie";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

            MovieItems movie = getIntent().getParcelableExtra(EXTRA_DETAIL_MOVIE);

            detailTitle.setText(movie.getTitle());
            detailDesk.setText(movie.getOverview());
            detailRating.setText(String.valueOf(movie.getVote_average()));
            detailTanggal.setText(movie.getRelease_date());

            String url = "http://image.tmdb.org/t/p/w300" + movie.getPoster_path();
            Glide.with(this)
                    .load(url)
                    .into(imgView);


        toggleButton.setChecked(false);
        toggleButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.img_star_grey));
        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    toggleButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.img_star_yellow));
                else
                    toggleButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.img_star_grey));
            }
        });
    }
}
