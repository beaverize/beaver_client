package com.vv.beaver;

import android.provider.BaseColumns;

public final class MenuItemDbColumns {
    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public MenuItemDbColumns() {}

    /* Inner class that defines the table contents */
    public static abstract class MenuItemDbEntry implements BaseColumns {
        public static final String TABLE_NAME                       = "MenuItemEntry";
        public static final String COLUMN_NAME_MENU_ITEM_ID         = "entryid";
        public static final String COLUMN_NAME_MENU_ITEM_NAME       = "title";
        public static final String COLUMN_NAME_MENU_ITEM_PRICE      = "price";
        public static final String COLUMN_NAME_MENU_ITEM_OWNER_ID   = "owner_id";
    }
}