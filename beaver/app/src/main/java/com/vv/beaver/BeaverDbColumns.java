package com.vv.beaver;

import android.provider.BaseColumns;

public final class BeaverDbColumns {
    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public BeaverDbColumns() {}

    /* Inner class that defines the table contents */
    public static abstract class BeaverDbEntry implements BaseColumns {
        public static final String TABLE_NAME               = "BeaverEntry";
        public static final String COLUMN_NAME_BEAVER_ID    = "entryid";
        public static final String COLUMN_NAME_BEAVER_NAME  = "title";
        //web update
        //web update4
    }
}
