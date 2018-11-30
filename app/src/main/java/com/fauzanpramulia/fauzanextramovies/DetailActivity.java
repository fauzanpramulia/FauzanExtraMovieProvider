package com.fauzanpramulia.fauzanextramovies;

import android.arch.persistence.room.Room;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;
import com.fauzanpramulia.fauzanextramovies.db.AppDatabase;
import com.fauzanpramulia.fauzanextramovies.db.Favorit;
import com.fauzanpramulia.fauzanextramovies.model.MovieItems;

import java.util.List;

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
    AppDatabase db;
    int favorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        db = Room.databaseBuilder(this, AppDatabase.class, "tmdb.db")
                .allowMainThreadQueries()
                .build();

//        Uri uri = getIntent().getData();

            final MovieItems movie = getIntent().getParcelableExtra(EXTRA_DETAIL_MOVIE);

            detailTitle.setText(movie.getTitle());
            detailDesk.setText(movie.getOverview());
            detailRating.setText(String.valueOf(movie.getVote_average()));
            detailTanggal.setText(movie.getRelease_date());

            String url = "http://image.tmdb.org/t/p/w300" + movie.getPoster_path();
            Glide.with(this)
                    .load(url)
                    .into(imgView);
            int ada = checkFavorite(movie);
            if (ada==1){
                toggleButton.setChecked(true);
                toggleButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.img_star_yellow));
            }else{
                toggleButton.setChecked(false);
                toggleButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.img_star_grey));
            }
        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){

                    saveMovieToDb(movie);
                    Toast.makeText(DetailActivity.this, "Film "+movie.getTitle()+"Menjadi Favorit", Toast.LENGTH_SHORT).show();
                    toggleButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.img_star_yellow));
                }
                else{
                    deleteFromDb(movie);
                    Toast.makeText(DetailActivity.this, "Dihapus dari favorit", Toast.LENGTH_SHORT).show();
                    toggleButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.img_star_grey));
                }

            }
        });
    }




    private void saveMovieToDb(final MovieItems movie) {
        new Thread(new Runnable() {
            @Override
            public void run() {

                    Favorit favorit = new Favorit();
                    favorit.id = movie.id;
                    favorit.title = movie.title;
                    favorit.overview = movie.overview;
                    favorit.vote_average = movie.vote_average;
                    favorit.release_date = movie.release_date;
                    favorit.poster_path = movie.poster_path;

                    db.favoritDao().insertFavorit(favorit);

            }
        }).start();

    }

    private void deleteFromDb(final MovieItems movie) {
        new Thread(new Runnable() {
            @Override
            public void run() {

                Favorit favorit = new Favorit();
                favorit.id = movie.id;
                favorit.title = movie.title;
                favorit.overview = movie.overview;
                favorit.vote_average = movie.vote_average;
                favorit.release_date = movie.release_date;
                favorit.poster_path = movie.poster_path;

                db.favoritDao().delete(favorit);
            }
        }).start();

    }

    private int checkFavorite(final MovieItems movie) {
        int favorite;

                favorite =  db.favoritDao().checkFavorite(movie.getId());

        return favorite;
    }
}
