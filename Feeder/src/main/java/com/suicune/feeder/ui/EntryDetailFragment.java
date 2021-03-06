package com.suicune.feeder.ui;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.suicune.feeder.R;
import com.suicune.feeder.app.Entry;
import com.suicune.feeder.app.FeedEntry;
import com.suicune.feeder.app.Settings;
import com.suicune.feeder.database.FeedsContract;
import com.suicune.feeder.database.FeedsProvider;

/**
 * A fragment representing a single Entry detail screen.
 * This fragment is either contained in a {@link EntryListActivity}
 * in two-pane mode (on tablets) or a {@link EntryDetailActivity}
 * on handsets.
 */
public class EntryDetailFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    private static final int LOADER_ITEM = 1;

    private SharedPreferences prefs;

    /**
     * The dummy content this fragment is presenting.
     */
    private Entry mEntry;

    //TODO: Change for actual layout.
    private TextView textView;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public EntryDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            getLoaderManager().restartLoader(LOADER_ITEM, getArguments(), this);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_item_detail, container, false);
        textView = (TextView) rootView.findViewById(R.id.item_detail);

        showDetails();

        return rootView;
    }

    public void showDetails(){
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        Uri uri = FeedsProvider.CONTENT_ITEMS;
        String selection = FeedsContract.Items._ID + "=?";
        String[] selectionArgs = {
            Long.toString(bundle.getLong(ARG_ITEM_ID))
        };
        return new CursorLoader(getActivity(), uri, null, selection, selectionArgs, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        mEntry = new FeedEntry(cursor);
        showDetails();
        if(prefs != null && !mEntry.isRead()
                && prefs.getBoolean(Settings.MARK_AS_READ_WHEN_SHOWN, true)) {
            mEntry.markAsRead(getActivity().getContentResolver());
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {
    }
}
