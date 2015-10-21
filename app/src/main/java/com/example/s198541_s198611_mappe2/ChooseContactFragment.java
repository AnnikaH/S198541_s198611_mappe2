package com.example.s198541_s198611_mappe2;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class ChooseContactFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>,
        SearchView.OnQueryTextListener {

    LoaderManager loaderManager;
    CursorLoader cursorLoader;
    SimpleCursorAdapter mAdapter;
    DBHandler dbHandler;
    String TAG = "LOADER";
    SearchView searchView;

    public ChooseContactFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.list, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        loaderManager = getActivity().getLoaderManager();

        dbHandler = new DBHandler(getActivity().getBaseContext());

        String[] uiBindFrom = {DBHandler.KEY_NAME, DBHandler.KEY_BIRTHDAY};
        int[] uiBindTo = {android.R.id.text1, android.R.id.text2};

        mAdapter = new SimpleCursorAdapter(getActivity().getBaseContext(),
                android.R.layout.simple_list_item_multiple_choice, null, uiBindFrom, uiBindTo, 0);
        final ListView listView = (ListView) getActivity().findViewById(R.id.list_view);
        listView.setAdapter(mAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CheckedTextView item = (CheckedTextView) view;
                item.setChecked(true);
                Toast.makeText(getActivity().getBaseContext(), position + " klikket", Toast.LENGTH_SHORT)
                        .show();
            }
        });

        searchView = (SearchView) getActivity().findViewById(R.id.search_view);

        int idSearchViewInputText = searchView.getContext().getResources().getIdentifier("android:id/search_src_text",
                null, null);
        TextView searchViewText = (TextView) searchView.findViewById(idSearchViewInputText);
        searchViewText.setHintTextColor(Color.WHITE);
        searchViewText.setTextColor(Color.WHITE);

        searchView.setOnQueryTextListener(this);

        loaderManager.initLoader(0, null, this);
    }

    public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {

        String[] projection = {DBHandler.KEY_ID, DBHandler.KEY_NAME, DBHandler.KEY_BIRTHDAY};
        cursorLoader = new CursorLoader(getActivity().getBaseContext(), PersonCP.CONTENT_URI, projection,
                null, null, DBHandler.KEY_NAME);
        return cursorLoader;
    }

    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if (mAdapter != null && cursor != null)
            mAdapter.swapCursor(cursor);
            // swap the new cursor in
        else
            Log.v(TAG, "OnLoadFinished: mAdapter is null");
    }

    public void onLoaderReset(Loader<Cursor> arg0) {
        if (mAdapter != null)
            mAdapter.swapCursor(null);
        else
            Log.v(TAG, "OnLoadFinished: mAdapter is null");
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        mAdapter.getFilter().filter(newText);
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

}