package com.fauzanpramulia.fauzanextramovies.provider;

import android.content.ContentProvider;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.OperationApplicationException;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.fauzanpramulia.fauzanextramovies.db.AppDatabase;
import com.fauzanpramulia.fauzanextramovies.db.Favorit;
import com.fauzanpramulia.fauzanextramovies.db.FavoritDao;

import java.util.ArrayList;


public class MovieProvider extends ContentProvider {


    public static final String AUTHORITY = "com.fauzanpramulia.fauzanextramovies.provider";
    public static final Uri URI_MOVIE = Uri.parse("content://" + AUTHORITY + "/" + Favorit.TABLE_NAME);
    private static final int CODE_FAVORIT_DIR = 1;
    private static final int CODE_FAVORIT_ITEM = 2;
    private static final UriMatcher MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        MATCHER.addURI(AUTHORITY, Favorit.TABLE_NAME, CODE_FAVORIT_DIR);
        MATCHER.addURI(AUTHORITY, Favorit.TABLE_NAME + "/*", CODE_FAVORIT_ITEM);
    }

    @Override
    public boolean onCreate() {
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection,
                        @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        final int code = MATCHER.match(uri);
        if (code == CODE_FAVORIT_DIR || code == CODE_FAVORIT_ITEM) {
            final Context context = getContext();
            if (context == null) {
                return null;
            }
            FavoritDao cheese = AppDatabase.getInstance(context).favoritDao();
            final Cursor cursor;
            if (code == CODE_FAVORIT_DIR) {
                cursor = cheese.selectAll();
            } else {
                cursor = cheese.selectById(ContentUris.parseId(uri));
            }
            cursor.setNotificationUri(context.getContentResolver(), uri);
            return cursor;
        } else {
            throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (MATCHER.match(uri)) {
            case CODE_FAVORIT_DIR:
                return "vnd.android.cursor.dir/" + AUTHORITY + "." + Favorit.TABLE_NAME;
            case CODE_FAVORIT_ITEM:
                return "vnd.android.cursor.item/" + AUTHORITY + "." + Favorit.TABLE_NAME;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        switch (MATCHER.match(uri)) {
            case CODE_FAVORIT_DIR:
                final Context context = getContext();
                if (context == null) {
                    return null;
                }
                final long id = AppDatabase.getInstance(context).favoritDao()
                        .insert(Favorit.fromContentValues(values));
                context.getContentResolver().notifyChange(uri, null);
                return ContentUris.withAppendedId(uri, id);
            case CODE_FAVORIT_ITEM:
                throw new IllegalArgumentException("Invalid URI, cannot insert with ID: " + uri);
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection,
                      @Nullable String[] selectionArgs) {
        switch (MATCHER.match(uri)) {
            case CODE_FAVORIT_DIR:
                throw new IllegalArgumentException("Invalid URI, cannot update without ID" + uri);
            case CODE_FAVORIT_ITEM:
                final Context context = getContext();
                if (context == null) {
                    return 0;
                }
                final int count = AppDatabase.getInstance(context).favoritDao()
                        .deleteById(ContentUris.parseId(uri));
                context.getContentResolver().notifyChange(uri, null);
                return count;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection,
                      @Nullable String[] selectionArgs) {
        switch (MATCHER.match(uri)) {
            case CODE_FAVORIT_DIR:
                throw new IllegalArgumentException("Invalid URI, cannot update without ID" + uri);
            case CODE_FAVORIT_ITEM:
                final Context context = getContext();
                if (context == null) {
                    return 0;
                }
                final Favorit favorit = Favorit.fromContentValues(values);
                favorit.id = (int) ContentUris.parseId(uri);
                final int count = AppDatabase.getInstance(context).favoritDao()
                        .update(favorit);
                context.getContentResolver().notifyChange(uri, null);
                return count;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @NonNull
    @Override
    public ContentProviderResult[] applyBatch(
            @NonNull ArrayList<ContentProviderOperation> operations)
            throws OperationApplicationException {
        final Context context = getContext();
        if (context == null) {
            return new ContentProviderResult[0];
        }
        final AppDatabase database = AppDatabase.getInstance(context);
        database.beginTransaction();
        try {
            final ContentProviderResult[] result = super.applyBatch(operations);
            database.setTransactionSuccessful();
            return result;
        } finally {
            database.endTransaction();
        }
    }

    @Override
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] valuesArray) {
        switch (MATCHER.match(uri)) {
            case CODE_FAVORIT_DIR:
                final Context context = getContext();
                if (context == null) {
                    return 0;
                }
                final AppDatabase database = AppDatabase.getInstance(context);
                final Favorit[] cheeses = new Favorit[valuesArray.length];
                for (int i = 0; i < valuesArray.length; i++) {
                    cheeses[i] = Favorit.fromContentValues(valuesArray[i]);
                }
//                return database.favoritDao().insertAll(cheeses).length;
            case CODE_FAVORIT_ITEM:
                throw new IllegalArgumentException("Invalid URI, cannot insert with ID: " + uri);
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }
}
