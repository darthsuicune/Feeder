package com.suicune.feeder.content;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import com.suicune.feeder.database.FeedsContract;
import com.suicune.feeder.database.FeedsProvider;

/**
 * Created by lapuente on 11.07.13.
 */
public class Item {

    public int mId;
    public String mTitle;
    public String mContent;
    public String mDate;
    public boolean mRead;
    public int mSource;

    public Item(Cursor cursor){
        if(cursor.moveToFirst()){
            mId = cursor.getInt(cursor.getColumnIndex(FeedsContract.Feed._ID));
            mTitle = cursor.getString(cursor.getColumnIndex(FeedsContract.Feed.TITLE));
            mContent = cursor.getString(cursor.getColumnIndex(FeedsContract.Feed.CONTENT));
            mDate = cursor.getString(cursor.getColumnIndex(FeedsContract.Feed.DATE));
            mRead = (cursor.getInt(cursor.getColumnIndex(FeedsContract.Feed.READ)) > 0);
            mSource = cursor.getInt(cursor.getColumnIndex(FeedsContract.Feed.SOURCE));
        }
    }

    public boolean markAsRead(ContentResolver cr){
        ContentValues values = new ContentValues();
        values.put(FeedsContract.Feed.READ, true);
        String where = FeedsContract.Feed._ID + "=?";
        String[] selectionArgs = {
                Integer.toString(mId)
        };
        return (cr.update(FeedsProvider.CONTENT_FEED, values, where, selectionArgs) > 0);

    }
}
