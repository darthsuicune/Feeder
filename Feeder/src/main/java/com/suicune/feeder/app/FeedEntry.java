package com.suicune.feeder.app;

import android.content.ContentResolver;
import android.database.Cursor;

import com.suicune.feeder.database.FeedsContract;

/**
 * Created by lapuente on 20.03.14.
 */
public class FeedEntry implements Entry {

    public int mId;
    public String mTitle;
    public String mUrl;
    public String mContent;
    public String mDate;
    public boolean mRead;
    public boolean mFav;
    public Source mSource;

    public FeedEntry(Cursor cursor){
        mId = cursor.getInt(cursor.getColumnIndex(FeedsContract.Items._ID));
        mTitle = cursor.getString(cursor.getColumnIndex(FeedsContract.Items.TITLE));
        mContent = cursor.getString(cursor.getColumnIndex(FeedsContract.Items.CONTENT));
        mDate = cursor.getString(cursor.getColumnIndex(FeedsContract.Items.DATE));
        mRead = (cursor.getInt(cursor.getColumnIndex(FeedsContract.Items.READ)) > 0);
        //mSource = cursor.getInt(cursor.getColumnIndex(FeedsContract.Items.SOURCE));
    }

    public FeedEntry(String title, String url, String content, String date, boolean read,
                     boolean fav, Source source){
        mTitle = title;
        mUrl = url;
        mContent = content;
        mDate = date;
        mRead = read;
        mFav = fav;
        mSource = source;
    }

    @Override
    public String getTitle() {
        return mTitle;
    }

    @Override
    public String getUrl() {
        return mUrl;
    }

    @Override
    public String getDate() {
        return mDate;
    }

    @Override
    public boolean isRead() {
        return mRead;
    }

    @Override
    public boolean isFav() {
        return mFav;
    }

    @Override
    public Source getSource() {
        return mSource;
    }

    public void markAsRead(ContentResolver cr){
        this.mRead = true;
    }

    public void markAsFav(ContentResolver cr){
        this.mFav = true;
    }

}
