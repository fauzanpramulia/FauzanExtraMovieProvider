package com.fauzanpramulia.fauzanextramovies.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface FavoritDao{

    @Query("SELECT * FROM favorit")
    List<Favorit> getAllMovies();

    @Query("SELECT * FROM favorit WHERE id = :id")
    Favorit getById(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertFavorit(Favorit favorit);

    @Query("DELETE FROM favorit")
    void clear();

    @Delete
    void delete(Favorit favorit);
}