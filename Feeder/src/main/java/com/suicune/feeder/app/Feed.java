package com.suicune.feeder.app;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.text.TextUtils;
import com.suicune.feeder.database.FeedsContract;
import com.suicune.feeder.database.FeedsProvider;

import java.util.ArrayList;

/**
 * Created by lapuente on 11.07.13.
 */
public class Feed {

    public int mId;
    public String mName;
    public String mUrl;
    public String mGroup;
    public ArrayList<Item> mItemList;

    /**
     * Public constructor to create a Feed from the dialog.
     */
    public Feed(String name, String url) {
        mName = name;
        mUrl = url;
    }

    /**
     * Public constructor to create Feeds from the DB.
     * @param cursor
     */
    public Feed(Cursor cursor) {
        mItemList = new ArrayList<Item>();
        if(cursor.moveToFirst()){
            mId = cursor.getInt(cursor.getColumnIndex(FeedsContract.Feeds._ID));
            mName = cursor.getString(cursor.getColumnIndex(FeedsContract.Feeds.NAME));
            mUrl = cursor.getString(cursor.getColumnIndex(FeedsContract.Feeds.URL));
            mGroup = cursor.getString(cursor.getColumnIndex(FeedsContract.Feeds.GROUP));
        }
    }

    public boolean hasNewElements() {
        return !mItemList.get(0).mRead;
    }

    /**
     * Saves the feed on the database
     */
    public void save(ContentResolver cr) {
        //TODO: Save the new information to the db
        if(TextUtils.isEmpty(mName) || TextUtils.isEmpty(mUrl)){
            return;
        }
        ContentValues values = new ContentValues();
        values.put(FeedsContract.Feeds.NAME, mName);
        values.put(FeedsContract.Feeds.URL, mUrl);
        cr.insert(FeedsProvider.CONTENT_FEEDS, values);
    }

    /**
     * Retrieves the feed from the internet and sets its data in this object.
     */
    public void retrieve(ContentResolver cr) {
        //TODO: All
        save(cr);
    }
}
