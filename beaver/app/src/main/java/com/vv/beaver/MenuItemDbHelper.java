package com.vv.beaver;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.vv.beaver.Menu.MenuItem;

public class MenuItemDbHelper extends SQLiteOpenHelper {
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + MenuItemDbColumns.MenuItemDbEntry.TABLE_NAME                                      + " ("      +
                    MenuItemDbColumns.MenuItemDbEntry._ID                               + " INTEGER PRIMARY KEY"    + COMMA_SEP +
                    MenuItemDbColumns.MenuItemDbEntry.COLUMN_NAME_MENU_ITEM_ID          + TEXT_TYPE                 + COMMA_SEP +
                    MenuItemDbColumns.MenuItemDbEntry.COLUMN_NAME_MENU_ITEM_NAME        + TEXT_TYPE                 + COMMA_SEP +
                    MenuItemDbColumns.MenuItemDbEntry.COLUMN_NAME_MENU_ITEM_PRICE       + TEXT_TYPE                 + COMMA_SEP +
                    MenuItemDbColumns.MenuItemDbEntry.COLUMN_NAME_MENU_ITEM_OWNER_ID    + TEXT_TYPE                 + " )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + MenuItemDbColumns.MenuItemDbEntry.TABLE_NAME;
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "MenuItem.db";

    public MenuItemDbHelper(Context context) {
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
    public void addEntry(int id, String name, int price, int owner_id) {
        // Gets the data repository in write mode
        SQLiteDatabase db = getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(MenuItemDbColumns.MenuItemDbEntry.COLUMN_NAME_MENU_ITEM_ID       , id       );
        values.put(MenuItemDbColumns.MenuItemDbEntry.COLUMN_NAME_MENU_ITEM_NAME     , name     );
        values.put(MenuItemDbColumns.MenuItemDbEntry.COLUMN_NAME_MENU_ITEM_PRICE    , price     );
        values.put(MenuItemDbColumns.MenuItemDbEntry.COLUMN_NAME_MENU_ITEM_OWNER_ID , owner_id  );
        // Insert the new row, returning the primary key value of the new row
        long newRowId;
        newRowId = db.insert(
                MenuItemDbColumns.MenuItemDbEntry.TABLE_NAME, null,
                values);

        Log.d("MenuItemDbHelper", "addEntry: done");
    }

    /*private void getEntry(int num) {
        SQLiteDatabase db = getReadableDatabase();
        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                MenuItemDbColumns.MenuItemDbEntry.COLUMN_NAME_MENU_ITEM_ID,
                MenuItemDbColumns.MenuItemDbEntry.COLUMN_NAME_MENU_ITEM_NAME,
        };
        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                MenuItemDbColumns.MenuItemDbEntry.COLUMN_NAME_MENU_ITEM_ID + " DESC";
        String selection = MenuItemDbColumns.MenuItemDbEntry.COLUMN_NAME_MENU_ITEM_ID + "=" + String.valueOf(num);

        Cursor c = db.query(
                MenuItemDbColumns.MenuItemDbEntry.TABLE_NAME,  // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );
        c.moveToFirst();
        int itemId = c.getInt(c.getColumnIndexOrThrow(MenuItemDbColumns.MenuItemDbEntry.COLUMN_NAME_MENU_ITEM_ID));
        String title = c.getString(c.getColumnIndexOrThrow(MenuItemDbColumns.MenuItemDbEntry.COLUMN_NAME_MENU_ITEM_NAME));
        Log.d("MenuItemDbHelper", "getEntry: itemId="+itemId+"title="+title);
    }*/

    public void loadHolderFromDb() {
        SQLiteDatabase db = getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                MenuItemDbColumns.MenuItemDbEntry.COLUMN_NAME_MENU_ITEM_ID          ,
                MenuItemDbColumns.MenuItemDbEntry.COLUMN_NAME_MENU_ITEM_NAME        ,
                MenuItemDbColumns.MenuItemDbEntry.COLUMN_NAME_MENU_ITEM_PRICE       ,
                MenuItemDbColumns.MenuItemDbEntry.COLUMN_NAME_MENU_ITEM_OWNER_ID    ,
        };

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                MenuItemDbColumns.MenuItemDbEntry.COLUMN_NAME_MENU_ITEM_ID + " ASC";

        Cursor c = db.query(
                MenuItemDbColumns.MenuItemDbEntry.TABLE_NAME,   // The table to query
                projection,                                     // The columns to return
                null,                                           // The columns for the WHERE clause
                null,                                           // The values for the WHERE clause
                null,                                           // don't group the rows
                null,                                           // don't filter by row groups
                sortOrder                                       // The sort order
        );
        c.moveToFirst();
        int id = -1;
        while(!c.isAfterLast()) {
            id              = c.getInt      (c.getColumnIndexOrThrow(MenuItemDbColumns.MenuItemDbEntry.COLUMN_NAME_MENU_ITEM_ID         ));
            String title    = c.getString   (c.getColumnIndexOrThrow(MenuItemDbColumns.MenuItemDbEntry.COLUMN_NAME_MENU_ITEM_NAME       ));
            int price       = c.getInt      (c.getColumnIndexOrThrow(MenuItemDbColumns.MenuItemDbEntry.COLUMN_NAME_MENU_ITEM_PRICE      ));
            int owner_id    = c.getInt      (c.getColumnIndexOrThrow(MenuItemDbColumns.MenuItemDbEntry.COLUMN_NAME_MENU_ITEM_OWNER_ID   ));
            DataHolder.getInstance().getMenuItemsList().add(new MenuItem(id, title, price, owner_id));
            c.moveToNext();
        }
        DataHolder.getInstance().setNextMenuItemId(id + 1);


        //Log.d("MenuItemDbHelper", "getEntry: itemId="+itemId+"title="+title);
    }

    public void updateOwnerId(int id, int new_owner_id) {
        SQLiteDatabase db = getReadableDatabase();
        // New value for one column
        ContentValues values = new ContentValues();
        values.put(MenuItemDbColumns.MenuItemDbEntry.COLUMN_NAME_MENU_ITEM_OWNER_ID, new_owner_id);
        // Which row to update, based on the ID
        String selection = MenuItemDbColumns.MenuItemDbEntry.COLUMN_NAME_MENU_ITEM_ID + "=" + String.valueOf(id);
        int count = db.update(
                MenuItemDbColumns.MenuItemDbEntry.TABLE_NAME,
                values,
                selection,
                null);
    }

    public void deleteEntry(int num) {
//        // Define 'where' part of query.
        SQLiteDatabase db = getWritableDatabase();
        String selection = MenuItemDbColumns.MenuItemDbEntry.COLUMN_NAME_MENU_ITEM_ID + "=" + String.valueOf(num);
        db.delete(MenuItemDbColumns.MenuItemDbEntry.TABLE_NAME, selection, null);
    }
//    }
    public void removeSelectedOwnerFromAllProducts(int owner_id_to_remove) {
        SQLiteDatabase db = getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(MenuItemDbColumns.MenuItemDbEntry.COLUMN_NAME_MENU_ITEM_OWNER_ID, -1);
        String selection = MenuItemDbColumns.MenuItemDbEntry.COLUMN_NAME_MENU_ITEM_OWNER_ID + "=" + String.valueOf(owner_id_to_remove);
        int count = db.update(
                MenuItemDbColumns.MenuItemDbEntry.TABLE_NAME,
                values,
                selection,
                null);
    }
}