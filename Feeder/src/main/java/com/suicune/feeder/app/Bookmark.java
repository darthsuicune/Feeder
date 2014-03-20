package com.suicune.feeder.app;

import android.content.ContentResolver;

/**
 * Created by lapuente on 20.03.14.
 */
public class Bookmark implements Entry{
    @Override
    public String getTitle() {
        return null;
    }

    @Override
    public String getUrl() {
        return null;
    }

    @Override
    public String getDate() {
        return null;
    }

    @Override
    public boolean isRead() {
        return false;
    }

    @Override
    public boolean isFav() {
        return false;
    }

    @Override
    public Source getSource() {
        return null;
    }

    @Override
    public void markAsRead(ContentResolver cr) {

    }

    @Override
    public void markAsFav(ContentResolver cr) {

    }
}
