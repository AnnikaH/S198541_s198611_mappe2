package com.example.s198541_s198611_mappe2;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class PersonCP extends ContentProvider {

    public static final String PROVIDER = "com.example.s198541_s198611_mappe2";
    public static final Uri CONTENT_URI = Uri.parse("content://" + PROVIDER + "/person"); // the URI to my data

    private static final int CONTACT = 1;
    private static final int MCONTACTS = 2;

    DBHandler dbHandler;
    SQLiteDatabase db;

    private static final UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER, "person", MCONTACTS);
        uriMatcher.addURI(PROVIDER, "person/#", CONTACT);
    }

    @Override
    public boolean onCreate() {
        dbHandler = new DBHandler(getContext());
        db = dbHandler.getWritableDatabase();
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor cur;

        if(uriMatcher.match(uri) == CONTACT) {
            cur = db.query(DBHandler.TABLE_PERSONS, projection, DBHandler.KEY_ID + " = " + uri.getPathSegments().get(1),
                    selectionArgs, null, null, sortOrder);
        }
        else {
            cur = db.query(DBHandler.TABLE_PERSONS, null, null, null, null, null, null);
            //cur = db.query(DBHandler.TABLE_PERSONS, new String[] { DBHandler.KEY_ID, DBHandler.KEY_NAME }, null, null, null, null, null);
        }

        return cur;
    }

    @Override
    public String getType(Uri uri) {
        switch(uriMatcher.match(uri)) {
            case MCONTACTS: return "vnd.android.cursor.dir/vnd.example.person";
            case CONTACT: return "vnd.android.cursor.item/vnd.example.person";

            //case MCONTACTS: return "vnd.android.cursor.dir/vnd.example.Person";
            //case CONTACT: return "vnd.android.cursor.item/vnd.example.Person";

            default: throw new IllegalArgumentException("Invalid URI " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        //SQLiteDatabase database = dbHandler.getWritableDatabase();
        db = dbHandler.getWritableDatabase();
        db.insert(DBHandler.TABLE_PERSONS, null, values);

        // get all the data:
        Cursor c = db.query(DBHandler.TABLE_PERSONS, null, null, null, null, null, null);

        // get the ID to the last row:
        c.moveToLast();

        long minId = c.getLong(0);
        // get the object that sent the URI:
        getContext().getContentResolver().notifyChange(uri, null);

        // Sending back the uri and the ID to the last post/row:
        // This is a confirmation that you now have added to post 4 for example:
        return ContentUris.withAppendedId(uri, minId);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        if(uriMatcher.match(uri) == CONTACT) {
            db.delete(DBHandler.TABLE_PERSONS, DBHandler.KEY_ID + " = " + uri.getPathSegments().get(1), selectionArgs);
            getContext().getContentResolver().notifyChange(uri, null);
            return 1;
        }

        if(uriMatcher.match(uri) == MCONTACTS) {
            db.delete(DBHandler.TABLE_PERSONS, null, null);
            getContext().getContentResolver().notifyChange(uri, null);
            return 2;
        }

        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        if(uriMatcher.match(uri) == CONTACT) {
            db.update(DBHandler.TABLE_PERSONS, values, DBHandler.KEY_ID + " = " + uri.getPathSegments().get(1), null);
            getContext().getContentResolver().notifyChange(uri, null);
            return 1;
        }

        if(uriMatcher.match(uri) == MCONTACTS) {
            db.update(DBHandler.TABLE_PERSONS, null, null, null);
            getContext().getContentResolver().notifyChange(uri, null);
            return 2;
        }

        return 0;
    }
}