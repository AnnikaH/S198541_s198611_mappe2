package com.example.s198541_s198611_mappe2;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class BirthdaysFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    LoaderManager loaderManager;
    CursorLoader cursorLoader;
    SimpleCursorAdapter mAdapter;
    DBHandler dbHandler;
    String TAG = "LOADER";
    ListView listView;

    public BirthdaysFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.list_birthdays, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        loaderManager = getActivity().getLoaderManager();
        dbHandler = new DBHandler(getActivity().getBaseContext());

        String[] uiBindFrom = { DBHandler.KEY_BIRTHDAY, DBHandler.KEY_NAME };
        int[] uiBindTo = { android.R.id.text1, android.R.id.text2 };

        mAdapter = new SimpleCursorAdapter(getActivity().getBaseContext(),
                android.R.layout.simple_list_item_2, null, uiBindFrom, uiBindTo, 0);

        listView = (ListView) getActivity().findViewById(R.id.list_view);
        listView.setAdapter(mAdapter);
        loaderManager.initLoader(0, null, this);
    }

    public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
        String[] projection = { DBHandler.KEY_ID, DBHandler.KEY_NAME, DBHandler.KEY_BIRTHDAY } ;
        cursorLoader = new CursorLoader(getActivity().getBaseContext(), PersonCP.CONTENT_URI, projection,
                null, null, DBHandler.KEY_BIRTHDAY);
        return cursorLoader;
    }

    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if(mAdapter != null && cursor != null)
            mAdapter.swapCursor(cursor);
            // swap the new cursor in
        else
            Log.v(TAG, "OnLoadFinished: mAdapter is null");
    }

    public void onLoaderReset(Loader<Cursor> arg0) {
        if(mAdapter != null)
            mAdapter.swapCursor(null);
        else
            Log.v(TAG, "OnLoadFinished: mAdapter is null");
    }
}