package com.suicune.feeder.content;

import android.database.Cursor;
import com.suicune.feeder.database.FeedsContract;

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

    public Feed(Cursor cursor) {
        mItemList = new ArrayList<Item>();
        if(cursor.moveToFirst()){
            mId = cursor.getInt(cursor.getColumnIndex(FeedsContract.Sources._ID));
            mName = cursor.getString(cursor.getColumnIndex(FeedsContract.Sources.NAME));
            mUrl = cursor.getString(cursor.getColumnIndex(FeedsContract.Sources.URL));
            mGroup = cursor.getString(cursor.getColumnIndex(FeedsContract.Sources.GROUP));
        }
    }


}
