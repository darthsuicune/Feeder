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

        db.execSQL(CREATE + FeedsContract.Items.TABLE_NAME + " ("
                + FeedsContract.Items._ID + KEY
                + FeedsContract.Items.TITLE + " TEXT NOT NULL, "
                + FeedsContract.Items.CONTENT + " BLOB NOT NULL, "
                + FeedsContract.Items.DATE + " TEXT NOT NULL, "
                + FeedsContract.Items.READ + " BOOLEAN, "
                + FeedsContract.Items.SOURCE + " INTEGER NOT NULL)"
        );

        db.execSQL(CREATE + FeedsContract.Feeds.TABLE_NAME + " ("
                + FeedsContract.Feeds._ID + KEY
                + FeedsContract.Feeds.NAME + " TEXT NOT NULL, "
                + FeedsContract.Feeds.URL + " TEXT NOT NULL, "
                + FeedsContract.Feeds.GROUP + " INTEGER)"
        );

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
