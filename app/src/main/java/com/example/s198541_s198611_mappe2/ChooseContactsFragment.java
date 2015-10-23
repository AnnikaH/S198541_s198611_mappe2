package com.example.s198541_s198611_mappe2;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
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

import java.util.ArrayList;

public class ChooseContactsFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>,
        SearchView.OnQueryTextListener {

    private LoaderManager loaderManager;
    private CursorLoader cursorLoader;
    private SimpleCursorAdapter mAdapter;
    private DBHandler dbHandler;
    private String TAG = "LOADER";
    private SearchView searchView;
    private ArrayList<Long> ids;

    public ChooseContactsFragment() {

    }

    // Called from onItemClick:
    private void addId(long id) {
        ids.add(id);
    }

    // Called from onItemClick:
    private void removeId(long id) {
        ids.remove(id);
    }

    // Called from ChooseContactsActivity:
    public ArrayList<Long> getCheckedIds() {
        return ids;
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

        ids = new ArrayList<>();

        String[] uiBindFrom = {DBHandler.KEY_NAME, DBHandler.KEY_BIRTHDAY};
        int[] uiBindTo = {android.R.id.text1, android.R.id.text2};

        mAdapter = new SimpleCursorAdapter(getActivity().getBaseContext(),
                android.R.layout.simple_list_item_multiple_choice, null, uiBindFrom, uiBindTo, 0);
        ListView listView = (ListView) getActivity().findViewById(R.id.list_view);
        listView.setAdapter(mAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CheckedTextView item = (CheckedTextView) view;

                if(item.isChecked()) {
                    item.setChecked(false);
                    removeId(id);   /* removing this id from the ids ArrayList (this ArrayList is
                    eventually sent to ChooseContactsActivity and further on to ChangeMessageForContactsActivity) */
                }
                else {
                    item.setChecked(true);
                    addId(id);  /* adding this id to the ids ArrayList (this ArrayList is
                    eventually sent to ChooseContactsActivity and further on to ChangeMessageForContactsActivity) */
                }
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