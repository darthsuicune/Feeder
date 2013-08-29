package com.suicune.feeder.database;

import android.provider.BaseColumns;

public final class FeedsContract {

    public static class Feed implements BaseColumns {
        public static final String TABLE_NAME = "feed";
        public static final String DEFAULT_ORDER = _ID + " DESC";

        public static final String TITLE = "title";
        public static final String CONTENT = "content";
        public static final String DATE = "date";
        public static final String READ = "read";
        public static final String SOURCE = "source";
    }

    public static class Sources implements BaseColumns {
        public static final String TABLE_NAME = "sources";
        public static final String DEFAULT_ORDER = _ID + " DESC";

        public static final String NAME = "name";
        public static final String URL = "url";
        public static final String GROUP = "group";
    }

    public static class Groups implements BaseColumns {
        public static final String TABLE_NAME = "groups";
        public static final String DEFAULT_ORDER = _ID + " DESC";

        public static final String NAME = "name";
    }

}
