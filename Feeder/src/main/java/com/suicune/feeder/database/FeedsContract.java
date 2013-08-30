package com.suicune.feeder.database;

import android.provider.BaseColumns;

public final class FeedsContract {

    public static class Items implements BaseColumns {
        public static final String TABLE_NAME = "items";
        public static final String DEFAULT_ORDER = _ID + " DESC";

        public static final String TITLE = "title";
        public static final String CONTENT = "content";
        public static final String DATE = "date";
        public static final String READ = "read";
        public static final String SOURCE = "source";
    }

    public static class Feeds implements BaseColumns {
        public static final String TABLE_NAME = "feeds";
        public static final String DEFAULT_ORDER = _ID + " DESC";

        public static final String NAME = "name";
        public static final String URL = "url";
        public static final String GROUP = "groupid";
    }

    public static class Groups implements BaseColumns {
        public static final String TABLE_NAME = "groups";
        public static final String DEFAULT_ORDER = _ID + " DESC";

        public static final String NAME = "name";
    }

}
