package com.suicune.feeder.app;

import android.app.IntentService;
import android.content.Intent;

/**
 * Created by lapuente on 30.08.13.
 */
public class FeedRetrieverService extends IntentService {
    private static final String FEED_RETRIEVER_SERVICE_NAME = "FeedRetriever";

    public FeedRetrieverService() {
        super(FEED_RETRIEVER_SERVICE_NAME);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Feed feed = new Feed("", ""); //TODO replace for actually getting the feed online
        if (feed.hasNewElements()) {
            feed.save(getContentResolver());
            showNotification(feed);
        }

    }

    private void showNotification(Feed feed){

    }
}
