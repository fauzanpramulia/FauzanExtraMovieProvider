package com.fauzanpramulia.fauzanextramovies.fragment;

import android.database.Cursor;
import android.database.MatrixCursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.fauzanpramulia.fauzanextramovies.BuildConfig;
import com.fauzanpramulia.fauzanextramovies.MovieList;
import com.fauzanpramulia.fauzanextramovies.R;
import com.fauzanpramulia.fauzanextramovies.TmdbClient;
import com.fauzanpramulia.fauzanextramovies.adapter.MovieAdapter;
import com.fauzanpramulia.fauzanextramovies.model.MovieItems;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Tab2Upcoming extends Fragment {
    ProgressBar progressBar;
    RecyclerView recyclerView;
    MovieAdapter adapter;
    ArrayList<MovieItems> daftarFilm = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab2_upcoming, container,false);
        recyclerView = (RecyclerView)rootView.findViewById(R.id.recycler_view);
        progressBar = (ProgressBar) rootView.findViewById(R.id.progress_bar);
        getUpComing();
        adapter = new MovieAdapter(getActivity());
        //adapter.setHandler();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        return rootView;
    }
    private void getUpComing(){
        recyclerView.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        String API_BASE_URL = getResources().getString(R.string.url_base);
        String BAHASA_URL = getResources().getString(R.string.bahasa_film);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        TmdbClient client =  retrofit.create(TmdbClient.class);

        Call<MovieList> call = client.getUpcoming(BuildConfig.My_Db_api_key, BAHASA_URL);
        call.enqueue(new Callback<MovieList>() {
            @Override
            public void onResponse(Call<MovieList> call, Response<MovieList> response)   {
                MovieList movieList = response.body();
                List<MovieItems> listMovieItem = movieList.results;


                adapter.setDataFilm((ArrayList<MovieItems>) listMovieItem);
                recyclerView.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<MovieList> call, Throwable t) {
                progressBar.setVisibility(View.INVISIBLE);
            }
        });

    }
    public Cursor getCursorFromList (List<MovieItems> movies){
        MatrixCursor cursor = new MatrixCursor(
                new String[]{"title","overview","vote_average","release_date","poster_path"}
        );
        for (MovieItems movie : movies){
            cursor.newRow().add("_id", movie.getId())
                    .add("title",movie.getTitle())
                    .add("overview", movie.getOverview())
                    .add("vote_average", movie.getVote_average())
                    .add("release_date", movie.getRelease_date())
                    .add("poster_path", movie.getPoster_path());
        }


        return cursor;
    }
}
