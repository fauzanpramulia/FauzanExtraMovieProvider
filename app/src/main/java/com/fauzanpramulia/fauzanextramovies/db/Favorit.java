package com.fauzanpramulia.fauzanextramovies.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.content.ContentValues;

@Entity(tableName = Favorit.TABLE_NAME)
public class Favorit {

    public static final String TABLE_NAME = "favorit";

    public static final String ID = "id";
    public static final String TITLE = "title";
    public static final String OVERVIEW = "overview";
    public static final String VOTE_AVERAGE= "vote_average";
    public static final String RELEASE_DATE = "release_date";
    public static final String POSTER_PATH = "poster_path";

    @PrimaryKey
    @ColumnInfo(name = ID)
    public int id;

    @ColumnInfo(name = TITLE)
    public String title;

    @ColumnInfo(name = OVERVIEW)
    public String overview;

    @ColumnInfo(name = VOTE_AVERAGE)
    public double vote_average;

    @ColumnInfo(name = RELEASE_DATE)
    public String release_date;

    @ColumnInfo(name = POSTER_PATH)
    public String poster_path;

    public static Favorit fromContentValues(ContentValues values) {
        final Favorit favorit = new Favorit();
        if (values.containsKey(ID)) {
            favorit.id = values.getAsInteger(ID);
        }
        if (values.containsKey(TITLE)) {
            favorit.title = values.getAsString(TITLE);
        }
        if (values.containsKey(OVERVIEW)) {
            favorit.overview = values.getAsString(OVERVIEW);
        }
        if (values.containsKey(VOTE_AVERAGE)) {
            favorit.vote_average = values.getAsDouble(VOTE_AVERAGE);
        }
        if (values.containsKey(RELEASE_DATE)) {
            favorit.release_date = values.getAsString(RELEASE_DATE);
        }
        if (values.containsKey(POSTER_PATH)) {
            favorit.poster_path = values.getAsString(POSTER_PATH);
        }



        return favorit;
    }

}
