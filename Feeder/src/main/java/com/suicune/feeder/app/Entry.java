package com.suicune.feeder.app;

import android.content.ContentResolver;

/**
 * Created by lapuente on 11.07.13.
 */
public interface Entry {
    public String getTitle();
    public String getUrl();
    public String getDate();
    public boolean isRead();
    public boolean isFav();
    public Source getSource();

    public void markAsRead(ContentResolver cr);
    public void markAsFav(ContentResolver cr);
}