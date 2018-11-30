package com.fauzanpramulia.fauzanextramovies;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.fauzanpramulia.fauzanextramovies.adapter.MovieAdapter;
import com.fauzanpramulia.fauzanextramovies.db.AppDatabase;
import com.fauzanpramulia.fauzanextramovies.db.Favorit;
import com.fauzanpramulia.fauzanextramovies.model.MovieItems;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FavoritActivity extends AppCompatActivity {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.progress_bar)ProgressBar progressBar;
    MovieAdapter adapter;
    ArrayList<MovieItems> daftarFilm = new ArrayList<>();
    AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorit);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Fauzan Favorit");
        toolbar.inflateMenu(R.menu.main_menu);
        db = Room.databaseBuilder(this, AppDatabase.class, "tmdb.db")
                .allowMainThreadQueries()
                .build();

        adapter = new MovieAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        tampilFavorit();
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        tampilFavorit();
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
//        if (id==R.id.menu_refresh){
//            //getNowPlayingMoview();
//            //loadDummyData();
//        }
        if (id==R.id.menu_language){
            Intent intent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(intent);
        }
        if (id==R.id.favorit){
            Intent intent = new Intent(this, FavoritActivity.class);
            startActivity(intent);
        }
        if (id==R.id.home){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    public void tampilFavorit(){
        progressBar.setVisibility(View.VISIBLE);
        List<Favorit> favorits = db.favoritDao().getAllMovies();
        ArrayList<MovieItems> movies = new ArrayList<>();
        for (Favorit n : favorits){
            MovieItems m = new MovieItems(
                    n.id,
                    n.title,
                    n.overview,
                    n.vote_average,
                    n.release_date,
                    n.poster_path
            );
            movies.add(m);
        }
        adapter.setDataFilm(movies);
        progressBar.setVisibility(View.INVISIBLE);
    }
}
