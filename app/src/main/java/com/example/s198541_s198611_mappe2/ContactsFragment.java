package com.example.s198541_s198611_mappe2;

import android.app.Fragment;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
// import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.app.LoaderManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
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

        // List<Person> persons = dbHandler.getAllPersons();

        /*String[] personNameArray = new String[persons.size()];

        int count = 0;
        for(Person pers : persons) {
            String log = "Id: " + pers.get_ID() + ", Name: " +
                    pers.getName() + ", PhoneNumber: " + pers.getPhoneNumber() +
                    ", Birthday: " + pers.getBirthday() + ", Message is null for now";

            personNameArray[count++] = pers.getName() + " " + pers.getBirthday();
            Log.d("Persons: ", log);
        }*/

        //String[] uiBindFrom = {ContactsContract.Contacts.DISPLAY_NAME};
        String[] uiBindFrom = { DBHandler.KEY_NAME, DBHandler.KEY_BIRTHDAY }; // TODO: ADD DBHandler.KEY_BIRTHDAY ?

        int[] uiBindTo = { android.R.id.text1 };

        mAdapter = new SimpleCursorAdapter(getActivity().getBaseContext(),
                android.R.layout.simple_list_item_1, null, uiBindFrom, uiBindTo, 0);
        ListView listView = (ListView) getActivity().findViewById(R.id.list_view);
        listView.setAdapter(mAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity().getBaseContext(), position + " klikket", Toast.LENGTH_SHORT)
                        .show();
            }
        });

        searchView = (SearchView) getActivity().findViewById(R.id.search_view);
        int idSearchViewInputText = searchView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        TextView searchViewText = (TextView) searchView.findViewById(idSearchViewInputText);
        searchViewText.setHintTextColor(Color.WHITE);
        searchViewText.setTextColor(Color.WHITE);

        searchView.setOnQueryTextListener(this);

        loaderManager.initLoader(0, null, this);    // 0 er id'en
    }

    public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
        /*String[] projection = {ContactsContract.Contacts._ID, ContactsContract.Contacts.DISPLAY_NAME};
        cursorLoader = new CursorLoader(getActivity().getBaseContext(), ContactsContract.Contacts.CONTENT_URI,
                projection, null, null, null);*/

        String[] projection = { DBHandler.KEY_ID, DBHandler.KEY_NAME } ;
        cursorLoader = new CursorLoader(getActivity().getBaseContext(), PersonCP.CONTENT_URI, projection, null, null, null);
        /*cursorLoader = new CursorLoader(getActivity().getBaseContext(), PersonCP.CONTENT_URI, projection, null, null,
                DBHandler.KEY_NAME);*/

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
        mAdapter.getFilter().filter(newText);
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

/*
    // Når trykker på Legg til-knappen
    public void leggTil(View view) {
        EditText navnefelt = (EditText) findViewById(R.id.navn);
        EditText telefonfelt = (EditText) findViewById(R.id.telefon);

        String navn = navnefelt.getText().toString();
        String telefon = telefonfelt.getText().toString();

        // Teste på om telefonnummeret finnes i kontaktene fra før?

        if(!navn.equals("") && !telefon.equals("")) {
            Kontakt kontakt = new Kontakt(navn, telefon);
            db.leggTilKontakt(kontakt);

            // Egentlig: dialogboks/pop-up med beskjed
//            Kontakt k = db.finnKontakt(kontakt.get_ID());
//            Log.d("LEGG TIL: ", "Kontakt lagt til! Navn: " + k.getNavn());

            List<Kontakt> kontakter = db.finnAlleKontakter();
            for(Kontakt kont : kontakter) {
                String log = "Id: " + kont.get_ID() + ", Navn: " +
                        kont.getNavn() + ", Telefon: " + kont.getTelefon();
                Log.d("Navn: ", log);
            }
        }
        else {
            // Egentlig: dialogboks/pop-up med beskjed
            Log.d("LEGG TIL: ", "Både navn og telefonnummer må fylles inn!");
        }
    }

    // Når trykker på Slett-knappen
    public void slett(View view) {
        String telefon = ((EditText) findViewById(R.id.telefon_slette)).getText().toString();

        Kontakt k = db.finnKontakt(telefon);

        if(k != null) {
            db.slettKontakt(k);

            Log.d("SLETTE: ", "Kontakt slettet!");

            List<Kontakt> kontakter = db.finnAlleKontakter();
            for(Kontakt kont : kontakter) {
                String log = "Id: " + kont.get_ID() + ", Navn: " +
                        kont.getNavn() + ", Telefon: " + kont.getTelefon();
                Log.d("Navn: ", log);
            }
        } else {
            Log.d("SLETTE: ", "Kontakt med telefonnr. ikke funnet!");
        }
    }

    // TODO: HAR IKKE TESTET DENNE!
    // Når trykker på Endre-knappen:
    public void endre(View view) {
        // ev.: EditText navnefeltFra = (EditText) findViewById(R.id.navn_endre_fra);

        EditText telefonfeltFra = (EditText) findViewById(R.id.telefon_endre_fra);
        String telefon = telefonfeltFra.getText().toString();

        Kontakt k = db.finnKontakt(telefon);

        if(k != null) {
            EditText navnefeltTil = (EditText) findViewById(R.id.navn_endre_til);
            EditText telefonfeltTil = (EditText) findViewById(R.id.telefon_endre_til);

            String nyttNavn = navnefeltTil.toString();
            String nyttTelefonnr = telefonfeltTil.toString();

            if(!nyttNavn.equals("")) {
                k.setNavn(navnefeltTil.toString());
            }

            // også teste om er bare tall (om Integer.parseInt(..) er mulig) (try catch)
            if(!nyttTelefonnr.equals("")) {
                k.setTelefon(telefonfeltTil.toString());
            }

            db.oppdaterKontakt(k);

            listAlle();
        } else {
            Log.d("ENDRE: ", "Fant ikke kontakt med dette telefonnummeret!");
        }
    }

    // TODO: HAR IKKE TESTET DENNE!
    // Når trykker på Finn-knappen:
    public void finn(View view) {
        EditText navnefelt = (EditText) findViewById(R.id.navn_finne);
        EditText telefonfelt = (EditText) findViewById(R.id.telefon_finne);

        String navn = navnefelt.getText().toString();
        String telefon = telefonfelt.getText().toString();

        Kontakt k;

        // Et alternativ (dekker ikke alle muligheter):
        if(!navn.equals("")) {
            k = db.finnKontakt(navn);
        } else if(!telefon.equals("")) {
            k = db.finnKontakt(telefon);
        } else {
            k = null;
        }

        if(k != null) {
            Log.d("FINNE: ", "Fant en kontakt!");

            TextView tekstomraade = (TextView) findViewById(R.id.list_alle_tekstomraade);
            tekstomraade.setText("");   // clear

            tekstomraade.setText("Navn: " + k.getNavn() + " Telefon: " + k.getTelefon());
        } else {
            Log.d("FINNE: ", "Fant ingen kontakter som matcher dette navnet (ev. tlfnr. hvis navn tomt)!");
        }
    }

    // Når trykker på List alle-knappen:
    public void listAlle() {
        TextView tekstomraade = (TextView) findViewById(R.id.list_alle_tekstomraade);
        tekstomraade.setText("");   // clear

        List<Kontakt> alleKontakter = db.finnAlleKontakter();

        for(Kontakt kont : alleKontakter) {
            tekstomraade.append("Navn: " + kont.getNavn() + " Telefon: " + kont.getTelefon() + "\n");
        }

        // også håndtere hvis er ingen kontakter
    }
*/
}