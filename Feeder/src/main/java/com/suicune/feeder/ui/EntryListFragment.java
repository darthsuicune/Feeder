package com.suicune.feeder.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.suicune.feeder.R;
import com.suicune.feeder.app.Feed;
import com.suicune.feeder.database.FeedsContract;
import com.suicune.feeder.database.FeedsProvider;

/**
 * A list fragment representing a list of Items. This fragment
 * also supports tablet devices by allowing list items to be given an
 * 'activated' state upon selection. This helps indicate which item is
 * currently being viewed in a {@link EntryDetailFragment}.
 * <p/>
 * Activities containing this fragment MUST implement the {@link Callbacks}
 * interface.
 */
public class EntryListFragment extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor> {

    /**
     * The serialization (saved instance state) Bundle key representing the
     * activated item position. Only used on tablets.
     */
    private static final String STATE_ACTIVATED_POSITION = "activated_position";
    private static final String ARG_FEED_URL = "feed_url";
    private static final String DIALOG_ADD_FEED_TAG = "dialog add feed tag";
    private static final int LOADER_GET_FEED = 1;
    private static final int LOADER_FEEDS = 2;
    /**
     * A dummy implementation of the {@link Callbacks} interface that does
     * nothing. Used only when this fragment is not attached to an activity.
     */
    private static Callbacks sDummyCallbacks = new Callbacks() {
        @Override
        public void onItemSelected(long id) {
        }
    };
    SimpleCursorAdapter mAdapter;
    /**
     * The fragment's current callback object, which is notified of list item
     * clicks.
     */
    private Callbacks mCallbacks = sDummyCallbacks;
    /**
     * The current activated item position. Only used on tablets.
     */
    private int mActivatedPosition = ListView.INVALID_POSITION;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public EntryListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if ((getActivity() != null) && (getActivity().getIntent() != null)
                && (getActivity().getIntent().getAction() == Intent.ACTION_VIEW)) {
            createFeedFromIntent();
        }

        String[] from = { FeedsContract.Feeds.NAME };
        int[] to = { android.R.id.text1 };
        mAdapter = new FeedListAdapter(getActivity(), from, to);
        setListAdapter(mAdapter);
        getLoaderManager().restartLoader(LOADER_FEEDS, null, this);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // Activities containing this fragment must implement its callbacks.
        if (!(activity instanceof Callbacks)) {
            throw new IllegalStateException("Activity must implement fragment's callbacks.");
        }

        mCallbacks = (Callbacks) activity;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Restore the previously serialized activated item position.
        if (savedInstanceState != null
                && savedInstanceState.containsKey(STATE_ACTIVATED_POSITION)) {
            setActivatedPosition(savedInstanceState.getInt(STATE_ACTIVATED_POSITION));
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

        // Reset the active callbacks interface to the dummy implementation.
        mCallbacks = sDummyCallbacks;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_itemlist, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_add_source:
                addSource("", "");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onListItemClick(ListView listView, View view, int position, long id) {
        super.onListItemClick(listView, view, position, id);

        // Notify the active callbacks interface (the activity, if the
        // fragment is attached to one) that an item has been selected.
        mCallbacks.onItemSelected(id);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mActivatedPosition != ListView.INVALID_POSITION) {
            // Serialize and persist the activated item position.
            outState.putInt(STATE_ACTIVATED_POSITION, mActivatedPosition);
        }
    }

    /**
     * Turns on activate-on-click mode. When this mode is on, list items will be
     * given the 'activated' state when touched.
     */
    public void setActivateOnItemClick(boolean activateOnItemClick) {
        // When setting CHOICE_MODE_SINGLE, ListView will automatically
        // give items the 'activated' state when touched.
        getListView().setChoiceMode(activateOnItemClick
                ? ListView.CHOICE_MODE_SINGLE
                : ListView.CHOICE_MODE_NONE);
    }

    private void setActivatedPosition(int position) {
        if (position == ListView.INVALID_POSITION) {
            getListView().setItemChecked(mActivatedPosition, false);
        } else {
            getListView().setItemChecked(position, true);
        }

        mActivatedPosition = position;
    }

    /**
     * This method creates a feed when the application is called externally. It sets the dialog with
     * automatic sensible options
     */
    private void createFeedFromIntent() {
        Uri uri = getActivity().getIntent().getData();
        addSource(uri.getAuthority(), uri.toString());
    }

    /**
     * This method should display a Dialog that adds the possibility to add a feed from the URL
     */
    private void addSource(String title, String url) {
        new FeedAdderDialog(title, url).show(getFragmentManager(), DIALOG_ADD_FEED_TAG);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle bundle) {
        switch (id) {
            case LOADER_FEEDS:
                return new CursorLoader(getActivity(), FeedsProvider.CONTENT_ITEMS, null,
                        null, null, null);
            default:
                return null;
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        switch (cursorLoader.getId()) {
            case LOADER_FEEDS:
                mAdapter.swapCursor(cursor);
            default:
                return;
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }

    /**
     * A callback interface that all activities containing this fragment must
     * implement. This mechanism allows activities to be notified of item
     * selections.
     */
    public interface Callbacks {
        /**
         * Callback for when an item has been selected.
         */
        public void onItemSelected(long id);
    }

    public class FeedAdderDialog extends DialogFragment {
        private String mTitle;
        private String mUrl;

        private TextView mTitleView;
        private TextView mUrlView;

        public FeedAdderDialog(String title, String url){
            mTitle = title;
            mUrl = url;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            View v = getActivity().getLayoutInflater().
                    inflate(R.layout.dialog_add_feed, null);

            mTitleView = (TextView) v.findViewById(R.id.dialog_add_feed_name);
            mUrlView = (TextView) v.findViewById(R.id.dialog_add_feed_url);

            mTitleView.setText(mTitle);
            mUrlView.setText(mUrl);

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setView(v);
            builder.setPositiveButton(R.string.dialog_add_feed,
                    new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    if(!TextUtils.isEmpty(mTitle) && !TextUtils.isEmpty(mUrl)){
                        mTitle = mTitleView.getText().toString();
                        mUrl = mUrlView.getText().toString();
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                new Feed(mTitle, mUrl).retrieve(getActivity().getContentResolver());
                            }
                        }).start();
                    } else {
                        Toast.makeText(getActivity(), R.string.error_dialog_add_feed_no_data,
                                Toast.LENGTH_LONG).show();
                    }
                }
            });
            builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    FeedAdderDialog.this.getDialog().cancel();
                }
            });
            return builder.create();
        }
    }

    public class FeedListAdapter extends SimpleCursorAdapter {
        public FeedListAdapter(Context context, String[] from, int[] to){
            super(context, R.layout.activity_item_list, null, from, to,
                    CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        }

//        @Override
//        public View getView(int position, View convertView, ViewGroup parent) {
//            View row = convertView;
//            if (row == null) {
//                LayoutInflater inflater = (LayoutInflater) getActivity()
//                        .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
//                //TODO inflate layout and do stuff with it.
//            }
//            return super.getView(position, row, parent);
//        }
    }
}
