package com.example.s198541_s198611_mappe2;

import android.app.Fragment;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
// import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.app.LoaderManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FilterQueryProvider;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ContactsFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>,
        SearchView.OnQueryTextListener {

    LoaderManager loaderManager;
    CursorLoader cursorLoader;
    SimpleCursorAdapter mAdapter;
    DBHandler dbHandler;
    String TAG = "LOADER";
    SearchView searchView;
    ListView listView;

    String mCurFilter;

    public ContactsFragment() {

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

        String[] uiBindFrom = { DBHandler.KEY_NAME, DBHandler.KEY_BIRTHDAY };
        int[] uiBindTo = { android.R.id.text1, android.R.id.text2 };

        mAdapter = new SimpleCursorAdapter(getActivity().getBaseContext(),
                android.R.layout.simple_list_item_2, null, uiBindFrom, uiBindTo, 0);
        listView = (ListView) getActivity().findViewById(R.id.list_view);
        listView.setAdapter(mAdapter);

        // ADDING:
        listView.setTextFilterEnabled(true);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Go to editing (or deleting) this person

                int idPerson = (int) id;

                Intent i = new Intent(getActivity(), EditContactActivity.class);

                Person p = dbHandler.getPerson(idPerson);
                i.putExtra("ID", p.get_ID());
                i.putExtra("NAME", p.getName());
                i.putExtra("PHONE", p.getPhoneNumber());
                i.putExtra("BIRTHDAY", p.getBirthday());
                i.putExtra("MESSAGE", p.getMessage());

                startActivity(i);
            }
        });

        searchView = (SearchView) getActivity().findViewById(R.id.search_view);

        int idSearchViewInputText = searchView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        TextView searchViewText = (TextView) searchView.findViewById(idSearchViewInputText);
        searchViewText.setHintTextColor(Color.WHITE);
        searchViewText.setTextColor(Color.WHITE);

        searchView.setOnQueryTextListener(this);

        /*mAdapter.setFilterQueryProvider(new FilterQueryProvider() {
            @Override
            public Cursor runQuery(CharSequence constraint) {
                String partial = constraint.toString();

                String[] projection = {DBHandler.KEY_ID, DBHandler.KEY_NAME, DBHandler.KEY_BIRTHDAY};
                String[] selectionArgs = { String.valueOf(partial) };
                cursorLoader = new CursorLoader(getActivity().getBaseContext(), PersonCP.CONTENT_URI, projection,
                        DBHandler.KEY_NAME + " LIKE ?", selectionArgs, DBHandler.KEY_NAME);

                return cursorLoader;
            }
        });*/

        loaderManager.initLoader(0, null, this);
    }

    public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
        String[] projection = { DBHandler.KEY_ID, DBHandler.KEY_NAME, DBHandler.KEY_BIRTHDAY } ;
        cursorLoader = new CursorLoader(getActivity().getBaseContext(), PersonCP.CONTENT_URI, projection,
                null, null, DBHandler.KEY_NAME);
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

    @Override
    public boolean onQueryTextChange(String newText) {
        /*String[] projection = {DBHandler.KEY_ID, DBHandler.KEY_NAME, DBHandler.KEY_BIRTHDAY};
        String[] selectionArgs = { String.valueOf(newText) };
        cursorLoader = new CursorLoader(getActivity().getBaseContext(), PersonCP.CONTENT_URI, projection,
                DBHandler.KEY_NAME + " LIKE ?", selectionArgs, DBHandler.KEY_NAME);

        loaderManager.initLoader(0, null, this);*/

        /*mAdapter.setFilterQueryProvider(new FilterQueryProvider() {
            @Override
            public Cursor runQuery(CharSequence constraint) {
                dbHandler.getAllPersonsFromName(constraint.toString());
                Cursor cursor = new CursorLoader();
                return cursor;
            }
        });*/

        //mAdapter.getFilter().filter(newText);

        /*if(TextUtils.isEmpty(newText)) {
            listView.clearTextFilter();
        } else {
            listView.setFilterText(newText);
        }*/

        String newFilter = !TextUtils.isEmpty(newText) ? newText : null;
        // Don't do anything if the filter hasn't actually changed.
        // Prevents restarting the loader when restoring state.
        if (mCurFilter == null && newFilter == null) {
            return true;
        }

        if (mCurFilter != null && mCurFilter.equals(newFilter)) {
            return true;
        }

        mCurFilter = newFilter;

        

        getLoaderManager().restartLoader(0, null, this);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        //onQueryTextChange(query);
        return false;
    }
}