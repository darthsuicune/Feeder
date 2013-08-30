package com.suicune.feeder.app;

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
            mId = cursor.getInt(cursor.getColumnIndex(FeedsContract.Items._ID));
            mTitle = cursor.getString(cursor.getColumnIndex(FeedsContract.Items.TITLE));
            mContent = cursor.getString(cursor.getColumnIndex(FeedsContract.Items.CONTENT));
            mDate = cursor.getString(cursor.getColumnIndex(FeedsContract.Items.DATE));
            mRead = (cursor.getInt(cursor.getColumnIndex(FeedsContract.Items.READ)) > 0);
            mSource = cursor.getInt(cursor.getColumnIndex(FeedsContract.Items.SOURCE));
        }
    }

    public boolean markAsRead(ContentResolver cr){
        this.mRead = true;

        ContentValues values = new ContentValues();
        values.put(FeedsContract.Items.READ, true);
        String where = FeedsContract.Items._ID + "=?";
        String[] selectionArgs = {
                Integer.toString(mId)
        };

        return (cr.update(FeedsProvider.CONTENT_ITEMS, values, where, selectionArgs) > 0);

    }
}
