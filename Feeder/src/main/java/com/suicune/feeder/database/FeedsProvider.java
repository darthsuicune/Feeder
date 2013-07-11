package com.suicune.feeder.database;

import android.content.*;
import android.database.Cursor;
import android.net.Uri;

public class FeedsProvider extends ContentProvider {
    protected static final String CONTENT_NAME = "com.suicune.feeder.database.Provider";
    public static final Uri CONTENT_FEED = Uri.parse("content://" + CONTENT_NAME + "/feed");
    public static final Uri CONTENT_SOURCES = Uri.parse("content://" + CONTENT_NAME + "/sources");
    public static final Uri CONTENT_GROUPS = Uri.parse("content://" + CONTENT_NAME + "/groups");

    FeedsOpenHelper mDbHelper;

    private static final int FEED = 1;
    private static final int FEED_ID = 2;
    private static final int SOURCES = 3;
    private static final int SOURCES_ID = 4;
    private static final int GROUPS = 5;
    private static final int GROUPS_ID = 6;

    static UriMatcher sUriMatcher;
    static {
        sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        sUriMatcher.addURI(CONTENT_NAME, FeedsContract.Feed.TABLE_NAME, FEED);
        sUriMatcher.addURI(CONTENT_NAME, FeedsContract.Feed.TABLE_NAME + "/#", FEED_ID);
        sUriMatcher.addURI(CONTENT_NAME, FeedsContract.Sources.TABLE_NAME, SOURCES);
        sUriMatcher.addURI(CONTENT_NAME, FeedsContract.Sources.TABLE_NAME + "/#", SOURCES_ID);
        sUriMatcher.addURI(CONTENT_NAME, FeedsContract.Groups.TABLE_NAME, GROUPS);
        sUriMatcher.addURI(CONTENT_NAME, FeedsContract.Groups.TABLE_NAME + "/#", GROUPS_ID);
    }

    @Override
    public boolean onCreate() {
        mDbHelper = new FeedsOpenHelper(getContext());
        return mDbHelper != null;
    }

    @Override
    public String getType(Uri uri) {
        switch(sUriMatcher.match(uri)) {
            case FEED:
                return ContentResolver.CURSOR_DIR_BASE_TYPE + CONTENT_NAME + "." + FeedsContract.Feed.TABLE_NAME;
            case FEED_ID:
                return ContentResolver.CURSOR_ITEM_BASE_TYPE + CONTENT_NAME + "." + FeedsContract.Feed.TABLE_NAME;
            case SOURCES:
                return ContentResolver.CURSOR_DIR_BASE_TYPE + CONTENT_NAME + "." + FeedsContract.Sources.TABLE_NAME;
            case SOURCES_ID:
                return ContentResolver.CURSOR_ITEM_BASE_TYPE + CONTENT_NAME + "." + FeedsContract.Sources.TABLE_NAME;
            case GROUPS:
                return ContentResolver.CURSOR_DIR_BASE_TYPE + CONTENT_NAME + "." + FeedsContract.Groups.TABLE_NAME;
            case GROUPS_ID:
                return ContentResolver.CURSOR_ITEM_BASE_TYPE + CONTENT_NAME + "." + FeedsContract.Groups.TABLE_NAME;
            default:
                return null;
        }
    }

    private String getTable(Uri uri) {
        switch(sUriMatcher.match(uri)) {
            case FEED_ID:
            case FEED:
                return FeedsContract.Feed.TABLE_NAME;
            case SOURCES_ID:
            case SOURCES:
                return FeedsContract.Sources.TABLE_NAME;
            case GROUPS_ID:
            case GROUPS:
                return FeedsContract.Groups.TABLE_NAME;
            default:
                return null;
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        String table = getTable(uri);
        long id = mDbHelper.getWritableDatabase().insert(table, null, values);
        Uri result = null;
        if (id != -1) {
            result = ContentUris.withAppendedId(uri, id);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        getContext().getContentResolver().notifyChange(result, null);
        return result;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        String table = getTable(uri);
        boolean distinct = false;
        String groupBy = null;
        String having = null;
        String limit = null;
        Cursor cursor = mDbHelper.getReadableDatabase().query(distinct, table,
                projection, selection, selectionArgs, groupBy, having,
                sortOrder, limit);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        String table = getTable(uri);
        int count = mDbHelper.getWritableDatabase().delete(table, selection,
                selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        String table = getTable(uri);
        int count = mDbHelper.getWritableDatabase().update(table, values,
                selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

}

