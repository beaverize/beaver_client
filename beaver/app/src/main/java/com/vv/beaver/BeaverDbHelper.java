package com.vv.beaver;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.vv.beaver.Beaver.BeaverItem;

public class BeaverDbHelper extends SQLiteOpenHelper {
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + BeaverDbColumns.BeaverDbEntry.TABLE_NAME                                + " ("      +
                    BeaverDbColumns.BeaverDbEntry._ID                     + " INTEGER PRIMARY KEY,"               +
                    BeaverDbColumns.BeaverDbEntry.COLUMN_NAME_BEAVER_ID   + TEXT_TYPE                 + COMMA_SEP +
                    BeaverDbColumns.BeaverDbEntry.COLUMN_NAME_BEAVER_NAME + TEXT_TYPE                             + " )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + BeaverDbColumns.BeaverDbEntry.TABLE_NAME;
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Beaver.db";

    public BeaverDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
    public void addEntry(int id, String name) {
        // Gets the data repository in write mode
        SQLiteDatabase db = getWritableDatabase();

// Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(BeaverDbColumns.BeaverDbEntry.COLUMN_NAME_BEAVER_ID    ,   id);
        values.put(BeaverDbColumns.BeaverDbEntry.COLUMN_NAME_BEAVER_NAME  , name);
// Insert the new row, returning the primary key value of the new row
        long newRowId;
        newRowId = db.insert(
                BeaverDbColumns.BeaverDbEntry.TABLE_NAME, null,
                values);

        Log.d("BeaverDbHelper", "addEntry: done");
    }

    private void getEntry(int num) {
        SQLiteDatabase db = getReadableDatabase();

// Define a projection that specifies which columns from the database
// you will actually use after this query.
        String[] projection = {
                BeaverDbColumns.BeaverDbEntry.COLUMN_NAME_BEAVER_ID,
                BeaverDbColumns.BeaverDbEntry.COLUMN_NAME_BEAVER_NAME,
        };

// How you want the results sorted in the resulting Cursor
        String sortOrder =
                BeaverDbColumns.BeaverDbEntry.COLUMN_NAME_BEAVER_ID + " DESC";
        String selection = BeaverDbColumns.BeaverDbEntry.COLUMN_NAME_BEAVER_ID + "=" + String.valueOf(num);

        Cursor c = db.query(
                BeaverDbColumns.BeaverDbEntry.TABLE_NAME,  // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );
        c.moveToFirst();
        int itemId = c.getInt(c.getColumnIndexOrThrow(BeaverDbColumns.BeaverDbEntry.COLUMN_NAME_BEAVER_ID));
        String title = c.getString(c.getColumnIndexOrThrow(BeaverDbColumns.BeaverDbEntry.COLUMN_NAME_BEAVER_NAME));
        Log.d("BeaverDbHelper", "getEntry: itemId="+itemId+"title="+title);
    }
    public void loadHolderFromDb() {
        SQLiteDatabase db = getReadableDatabase();

// Define a projection that specifies which columns from the database
// you will actually use after this query.
        String[] projection = {
                BeaverDbColumns.BeaverDbEntry.COLUMN_NAME_BEAVER_ID,
                BeaverDbColumns.BeaverDbEntry.COLUMN_NAME_BEAVER_NAME,
        };

// How you want the results sorted in the resulting Cursor
        String sortOrder =
                BeaverDbColumns.BeaverDbEntry.COLUMN_NAME_BEAVER_ID + " ASC";

        Cursor c = db.query(
                BeaverDbColumns.BeaverDbEntry.TABLE_NAME,  // The table to query
                projection,                               // The columns to return
                null,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );
        c.moveToFirst();
        int id = -1;
        while(!c.isAfterLast()) {
            id = c.getInt(c.getColumnIndexOrThrow(BeaverDbColumns.BeaverDbEntry.COLUMN_NAME_BEAVER_ID));
            String title = c.getString(c.getColumnIndexOrThrow(BeaverDbColumns.BeaverDbEntry.COLUMN_NAME_BEAVER_NAME));
            DataHolder.getInstance().getBeaverItemsList().add(new BeaverItem(title, id));
            c.moveToNext();
        }
        DataHolder.getInstance().setNextBeaverId(id + 1);


        //Log.d("BeaverDbHelper", "getEntry: itemId="+itemId+"title="+title);
    }

//    public void updateTitle(int num, String new_title) {
//        SQLiteDatabase db = getReadableDatabase();
//
//// New value for one column
//        ContentValues values = new ContentValues();
//        values.put(BeaverDbColumns.BeaverDbEntry.COLUMN_NAME_TITLE, new_title);
//
//// Which row to update, based on the ID
//        String selection = BeaverDbColumns.BeaverDbEntry.COLUMN_NAME_ENTRY_ID + "=" + String.valueOf(num);
//        int count = db.update(
//                BeaverDbColumns.BeaverDbEntry.TABLE_NAME,
//                values,
//                selection,
//                null);
//    }

    public void deleteEntry(int num) {   //UPDATE vovka&vitka 24/07/2016
        // Define 'where' part of query.
        SQLiteDatabase db = getWritableDatabase();
        String selection = BeaverDbColumns.BeaverDbEntry.COLUMN_NAME_BEAVER_ID + "=" + String.valueOf(num);
        db.delete(BeaverDbColumns.BeaverDbEntry.TABLE_NAME, selection, null);
    }
}