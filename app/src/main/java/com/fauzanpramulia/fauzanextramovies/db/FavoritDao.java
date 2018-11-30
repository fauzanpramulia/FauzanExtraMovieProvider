package com.fauzanpramulia.fauzanextramovies.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.database.Cursor;

import java.util.List;

@Dao
public interface FavoritDao{

    @Query("SELECT * FROM favorit")
    List<Favorit> getAllMovies();

    @Query("SELECT * FROM favorit WHERE id = :id")
    Favorit getById(int id);

    @Query("SELECT COUNT(*) FROM favorit WHERE id = :id")
    int checkFavorite(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertFavorit(Favorit favorit);

    @Query("DELETE FROM favorit")
    void clear();

    @Delete
    void delete(Favorit favorit);

    @Query("SELECT * FROM " + Favorit.TABLE_NAME)
    Cursor selectAll();

    @Insert
    long insert(Favorit favorit);

    @Insert
    long[] insertAll(Favorit[] favorits);

    @Query("DELETE FROM " + Favorit.TABLE_NAME + " WHERE " + Favorit.ID + " = :id")
    int deleteById(long id);

    @Update
    int update(Favorit favorit);

    @Query("SELECT * FROM " + Favorit.TABLE_NAME + " WHERE " + Favorit.ID + " = :id")
    Cursor selectById(long id);

}