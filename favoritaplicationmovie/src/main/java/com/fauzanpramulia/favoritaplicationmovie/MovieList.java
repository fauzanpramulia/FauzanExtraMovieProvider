package com.fauzanpramulia.favoritaplicationmovie;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieList {
    @SerializedName("results")
    public List<MovieItems> results;
}
