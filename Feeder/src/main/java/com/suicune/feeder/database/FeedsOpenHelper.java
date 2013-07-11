package com.suicune.feeder.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class FeedsOpenHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "Feeds";
    private static final int DB_VERSION = 1;

    private static final String CREATE = "CREATE TABLE ";
    private static final String KEY = " INTEGER PRIMARY KEY AUTOINCREMENT, ";

    public FeedsOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        if (db.isReadOnly()) {
            db = getWritableDatabase();
        }

        db.execSQL(CREATE + FeedsContract.Feed.TABLE_NAME + " ("
                + FeedsContract.Feed._ID + KEY
                + FeedsContract.Feed.NAME + "TEXT NOT NULL, "
                + FeedsContract.Feed.CONTENT + "BLOB NOT NULL, "
                + FeedsContract.Feed.DATE + "TEXT NOT NULL, "
                + FeedsContract.Feed.READ + "BOOLEAN)"
        );

        db.execSQL(CREATE + FeedsContract.Sources.TABLE_NAME + " ("
                + FeedsContract.Sources._ID + KEY
                + FeedsContract.Sources.NAME + "TEXT NOT NULL, "
                + FeedsContract.Sources.URL + "TEXT NOT NULL, "
                + FeedsContract.Sources.GROUP + "INTEGER)"
        );

        db.execSQL(CREATE + FeedsContract.Groups.TABLE_NAME + " ("
                + FeedsContract.Groups._ID + KEY
                + FeedsContract.Groups.NAME + "TEXT NOT NULL)"
        );

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
